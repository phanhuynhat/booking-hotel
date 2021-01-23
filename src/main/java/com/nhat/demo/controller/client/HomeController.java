package com.nhat.demo.controller.client;

import com.nhat.demo.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private ServiceImpl service;

    @GetMapping("/")
    public String toIndexpage(Model model) {
        model.addAttribute("roomTypes", service.getAllRoomType());
        return "index";
    }


    @GetMapping("/search-available-room")
    public String toOutRoomPage(Model model) {

        return "available-room";

    } //da in clude


    @GetMapping("/about-us")
    public String toAboutUsPage(Model model) {
        return "about-us";
    }  //da include

    @GetMapping("/gallery")
    public String toGalleryPage(Model model) {
        return "gallery";
    }  //da include

    @GetMapping("/contact-us")
    public String toContactUsPage(Model model) {
        return "contact-us";
    }
    @GetMapping("/personal-info")
    public String toPersonalInfoPage(Model model) {
        return "personal-information";
    }

    @GetMapping("/booking-info")
    public String toBookingInfoPage(Model model) {
        return "booking-information";
    }

    @GetMapping("/payment-info")
    public String toPaymentInfoPage(Model model) {
        return "payment-information";
    }


    @GetMapping("/booking-done")
    public String toBookingDonePage(Model model) {
        return "booking-done";
    }

    @GetMapping("/room-booking")
    public String toRoomBookingPages(Model model) {
        return "room-booking";
    }

    @GetMapping("/news")
    public String toNewsPage(Model model) {
        return "news";
    }  // da  include

    @GetMapping("/staff")
    public String toStaffPage(Model model) {
        return "staff";
    }  //da inculude


    @GetMapping("/404")
    public String toPageNotFound(Model model) {
        return "404";
    } //da in clude


}
