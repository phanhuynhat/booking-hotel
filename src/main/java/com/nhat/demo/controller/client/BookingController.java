package com.nhat.demo.controller.client;

import com.nhat.demo.entity.CreditCard;
import com.nhat.demo.service.CreditCardServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookingController {
    @Autowired
    private CreditCardServiceIF creditCartService;

    // xac nhan thong tin adults, children khi dat mot phong
    @GetMapping("/room-booking")
    public String toRoomBookingPages(Model model) {
        return "room-booking";
    }


    @ResponseBody
    @PostMapping("/check-credit-cart")
    public String checkCreditCart(@RequestBody CreditCard creditCard) {
        return creditCartService.ValidateCart(creditCard);
    }

}

