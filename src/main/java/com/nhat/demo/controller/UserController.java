package com.nhat.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String toIndexpage(Model model) {
        return "index";
    }

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
    } //da include

    @GetMapping("/personal-info")
    public String toPersonalInfoPage(Model model) {
        return "personal-information";
    }  //da include fragment

    @GetMapping("/booking-info")
    public String toBookingInfoPage(Model model) {
        return "booking-information";
    }  //da in include

    @GetMapping("/payment-info")
    public String toPaymentInfoPage(Model model) {
        return "payment-information";
    }  //da include


    @GetMapping("/booking-done")
    public String toBookingDonePage(Model model) {
        return "booking-done";
    }  //da include

    @GetMapping("/room-booking")
    public String toRoomBookingPages(Model model) {
        return "room-booking";
    }  // da in clude

    @GetMapping("/news")
    public String toNewsPage(Model model) {
        return "news";
    }  // da  include

    @GetMapping("/staff")
    public String toStaffPage(Model model) {
        return "staff";
    }  //da inculude

    @GetMapping("/our-room")
    public String toOutRoomPage(Model model) {
        return "our-room";
    } //da in clude

    @GetMapping("/404")
    public String toPageNotFound(Model model) {
        return "404";
    } //da in clude


}
