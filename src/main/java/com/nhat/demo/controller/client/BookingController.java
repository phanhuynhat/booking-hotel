package com.nhat.demo.controller.client;


import com.nhat.demo.entity.Booking;
import com.nhat.demo.entity.BookingDetail;
import com.nhat.demo.entity.BookingPerson;
import com.nhat.demo.entity.CreditCard;
import com.nhat.demo.model.BookingCart;
import com.nhat.demo.model.BookingDTO;
import com.nhat.demo.model.BookingItem;
import com.nhat.demo.repository.BookingDetailRepository;
import com.nhat.demo.service.BookingServiceIF;
import com.nhat.demo.service.CreditCardServiceIF;
import com.nhat.demo.service.serviceIml.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Map;

@Controller
public class BookingController {
    @Autowired
    private CreditCardServiceIF creditCardService;
    @Autowired
    private BookingServiceIF bookingService;
    @Autowired
    private BookingCart bookingCart;

    @Autowired
    private EmailService emailService;


    @Autowired
    private BookingDetailRepository bookingDetailRepository;




    // xac nhan thong tin adults, children khi dat mot phong
    @GetMapping("/room-booking")
    public String toRoomBookingPages(Model model) {
        return "client/room-booking";
    }


    @GetMapping("/booking-done/{bookingCode}")
    public String toBookingDonePage(@PathVariable String bookingCode, Model model) {
        model.addAttribute("booking", bookingService.getBookingByBookingCode(bookingCode));


        return "client/booking-done";

    }


    // khi ấn thanh toán
    @PostMapping("/booking-process")
    @Transactional(rollbackOn = Exception.class)
    @ResponseBody
    public Object checkCreditCart(BookingDTO bookingDTO) throws MessagingException {
        //tao CreditCard tu BookingDTO
        CreditCard creditCard = new CreditCard();
        creditCard.setOwnerName(bookingDTO.getOwnerName());
        creditCard.setCardNumber(bookingDTO.getCardNumber());
        creditCard.setExpiryMonth(bookingDTO.getExpiryMonth());
        creditCard.setExpiryYear(bookingDTO.getExpiryYear());

        // kiem tra tinh trang cua CreditCart
        String result = creditCardService.ValidateCart(creditCard);
        if (result.equals("not match") || result.equals("not enough")) {
            return result;
        }

        // khi the hop le thi tien hanh
        // tru tien khach hang ==> tang tien cua chu khach san (chuyen khoan)

        //ta mot booking rooom
        //tao n boookingdetail
        //tao 1 person

        // chuyen khoan
        creditCardService.tranferMoney(creditCard.getCardNumber(), CreditCardServiceIF.HOTELCARD, bookingCart.calculateTotal());


        // tao mot random String
        String bookingCode = bookingService.createBookingCode();

        //tao mot booking object
        Booking booking = new Booking();
        booking.setBookingCode(bookingCode);
        // KHÔNG LƯU TRỮ BOOKING PRICE
        booking.setCheckInDate(bookingCart.getCheckInDate());
        booking.setCheckOutDate(bookingCart.getCheckOutDate());
        // set mot promotion o bang promotion nếu có promotion
        if (bookingCart.getPromotion().getPromotionId() != 0) {
            booking.setPromotion(bookingCart.getPromotion());
        }


        // tao mot bookingperson object
        BookingPerson bookingPerson = new BookingPerson();
        bookingPerson.setFirstName(bookingDTO.getFirstName());
        bookingPerson.setLastName(bookingDTO.getLastName());
        bookingPerson.setGender(bookingDTO.getGender());
        bookingPerson.setEmail(bookingDTO.getEmail());
        bookingPerson.setPhone(bookingDTO.getPhone());
        bookingPerson.setIndentifyNo(bookingDTO.getIndentifyNo());

        //setbookingperson cho booking
        booking.setBookingPerson(bookingPerson);
        //set booking cho bookingperson
        bookingPerson.setBooking(booking);


        //convert BookingItem sang bookingDetail save vao database
        Map<Integer, BookingItem> bookingItems = bookingCart.getBookingItems();
        for (BookingItem item : bookingItems.values()) {
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setAdults(item.getAdults());
            bookingDetail.setChidren(item.getChildren());
            // set booking cho cac bookingDetail
            bookingDetail.setBooking(booking);
            //set room cho cac bookDetail
            bookingDetail.setRoom(item.getRoom());
            //set bookingdetail cho cac booking
            booking.getBookingDetails().add(bookingDetail);
        }

        //save booking thi se tu dong luu bookingperson va bookingDetail
        //vi da dung cascade = all
        bookingService.saveBooking(booking);


        // gửi email

        emailService.sendMail(bookingPerson, bookingCode);


        return bookingCode;

    }

}

