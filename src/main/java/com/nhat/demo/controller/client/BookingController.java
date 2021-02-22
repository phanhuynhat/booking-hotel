package com.nhat.demo.controller.client;


import com.nhat.demo.entity.Booking;
import com.nhat.demo.entity.BookingDetail;
import com.nhat.demo.entity.BookingPerson;
import com.nhat.demo.entity.CreditCard;
import com.nhat.demo.model.BookingCart;
import com.nhat.demo.model.BookingDTO;
import com.nhat.demo.model.BookingItem;
import com.nhat.demo.service.BookingServiceIF;
import com.nhat.demo.service.CreditCardServiceIF;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.Map;

@Controller
public class BookingController {
    @Autowired
    private CreditCardServiceIF creditCartService;
    @Autowired
    private BookingServiceIF bookingService;
    @Autowired
    private BookingCart bookingCart;

    // xac nhan thong tin adults, children khi dat mot phong
    @GetMapping("/room-booking")
    public String toRoomBookingPages(Model model) {
        return "client/room-booking";
    }


    @ResponseBody
    @PostMapping("/check-credit-cart")
    @Transactional
    public String checkCreditCart(BookingDTO bookingDTO) {
        //tao credit cart tu DTO
        CreditCard creditCard = new CreditCard();
        creditCard.setOwnerName(bookingDTO.getOwnerName());
        creditCard.setCardNumber(bookingDTO.getCardNumber());
        creditCard.setExpiryMonth(bookingDTO.getExpiryMonth());
        creditCard.setExpiryYear(bookingDTO.getExpiryMonth());

        String result = creditCartService.ValidateCart(creditCard);
        if (result.equals("not found") || result.equals("not enough")) {
            return result;
        }

        // khi the hop le thi tien hanh
        // tru tien
        //ta mot booking rooom
        //tao n boookingtail
        //tao 1 perrson

        // tao mot random text

        String substr1 = RandomStringUtils.randomAlphanumeric(4);
        String substr2 = RandomStringUtils.randomAlphanumeric(4);
        String substr3 = RandomStringUtils.randomAlphanumeric(4);
        String bookingCode = substr1 + "-" + substr2 + "-" + substr3;

        //tao mot booking object
        Booking booking = new Booking();
        booking.setBookingCode(bookingCode);
        booking.setBookingPrice(bookingCart.calculateTotal());
        booking.setCheckInDate(booking.getCheckInDate());
        booking.setCheckOutDate(bookingCart.getCheckOutDate());
        // set mot promotion o bang promotion
        booking.setPromotion(bookingCart.getPromotion());


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



        Map<Integer, BookingItem> bookingItems = bookingCart.getBookingItems();
        for (BookingItem item : bookingItems.values()) {
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setAdults(item.getAdults());
            bookingDetail.setChidren(item.getChildren());
            // set booking cho cac bookingDetail
            bookingDetail.setBooking(booking);
            bookingDetail.setRoom(item.getRoom());

            //set bookingdetail cho cac booking
            booking.getBookingDetails().add(bookingDetail);

            //save booking thi se tu dong luu bookingperson va bookingDetail

            bookingService.saveBooking(booking);


        }


        return "ok";
    }

}

