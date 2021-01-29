package com.nhat.demo.controller.client;

import com.nhat.demo.service.RoomServiceIF;
import com.nhat.demo.service.RoomTypeServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class HomeController {
    @Autowired
    private RoomTypeServiceIF roomTypeService;
    @Autowired
    private RoomServiceIF roomService;

    @GetMapping("/")
    public String toIndexpage(Model model) {
        model.addAttribute("roomTypes", roomTypeService.getAllRoomType());
        return "index";
    }


    @GetMapping("/search-available-room")
    public String toOutRoomPage(Model model,
                                @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate checkInDate,
                                @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate checkOutDate,
                                @RequestParam int alduts,
                                @RequestParam int children) {

        model.addAttribute("availableRooms", roomService.getAvailableRoom(checkInDate, checkOutDate, alduts, children));

        return "available-room";

    } //da in clude


    @GetMapping("/room-detail")
    public String toRoomDetailPage(Model model,
                                 @RequestParam int roomId) {

        return "room-detail";
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
