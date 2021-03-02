package com.nhat.demo.controller.client;


import com.nhat.demo.entity.*;
import com.nhat.demo.model.BookingCart;
import com.nhat.demo.model.BookingDTO;
import com.nhat.demo.model.BookingItem;
import com.nhat.demo.repository.BookingDetailRepository;
import com.nhat.demo.service.BookingServiceIF;
import com.nhat.demo.service.CreditCardServiceIF;
import com.nhat.demo.service.RoomServiceIF;
import com.nhat.demo.service.serviceIml.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
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
    private RoomServiceIF roomService;


    @Autowired
    private BookingDetailRepository bookingDetailRepository;


    // xac nhan lai thong tin adults, children khi dat mot phong
    @GetMapping("/room-booking")
    public String toRoomBookingPages(@RequestParam int roomId, Model model) {
        int childrenCapacity = roomService.getRoomById(roomId).getRoomType().getChildrenCapacity();
        int adultCapacity = roomService.getRoomById(roomId).getRoomType().getAdultCapacity();
        model.addAttribute("childrenCapacity", childrenCapacity);
        model.addAttribute("adultCapacity", adultCapacity);

        return "client/room-booking";
    }


    @GetMapping("/booking-done/{bookingCode}")
    public String toBookingDonePage(@PathVariable String bookingCode, Model model) {
        Booking booking = bookingService.getBookingByBookingCode(bookingCode);
        model.addAttribute("booking", booking);

        //phan nay dung de tao mot bien lam can cu de cho phep huy phong hay khong
        LocalDate now = LocalDate.now();
        LocalDate checkInDate = booking.getCheckInDate();
        model.addAttribute("isBeforeCheckInDate", now.isBefore(checkInDate));
        return "client/booking-done";
    }


    @GetMapping("/booking-info")
    public String toBokingInfo(@RequestParam String bookingCode, Model model) {
        model.addAttribute("viewInfo", Boolean.TRUE);
        return "forward:/booking-done/" + bookingCode;
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/cancel-booking")
    public String toCancelBooking(@RequestParam String bookingCode) {

        // hoan lai 80% cho khach hang
        Booking booking = bookingService.getBookingByBookingCode(bookingCode);
        String cardNumber = booking.getBookingPerson().getCardNumber();
        double amount = booking.getTotal() * 0.8;

        creditCardService.tranferMoney(CreditCardServiceIF.HOTELCARD, cardNumber, amount);
        //xoa bo booking
        bookingService.removeBookingByPromotionCode(bookingCode);


        return "client/cancel-booking";
    }


    // tim kiem bookingCode duoi database
    @PostMapping("/lookup")
    @ResponseBody
    public String toLookupPage(@RequestParam String bookingCode, Model Model) {
        Booking booking = bookingService.getBookingByBookingCode(bookingCode);
        if (booking == null) {
            return "not found";
        }
        return "found";
    }


    // khi ấn thanh toán
    @PostMapping("/booking-process")
    @Transactional(rollbackOn = Exception.class)
    @ResponseBody
    public Object bookingProcess(BookingDTO bookingDTO) throws MessagingException {

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
        bookingPerson.setCardNumber(bookingDTO.getCardNumber());

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

        // xoa tat ca moi thu trong gio hang
        bookingCart.setPromotion(new Promotion());
        bookingCart.setCheckOutDate(null);
        bookingCart.setCheckOutDate(null);
        bookingCart.setBookingItems(new HashMap<>());


        return bookingCode;

    }

}

