package com.nhat.demo.controller.client;

import com.nhat.demo.entity.Room;
import com.nhat.demo.model.BookingCart;
import com.nhat.demo.service.RoomServiceIF;
import com.nhat.demo.service.RoomTypeServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private BookingCart bookingCart;
    @Autowired
    private RoomTypeServiceIF roomTypeService;
    @Autowired
    private RoomServiceIF roomService;

    @GetMapping(value = "/")
    public String toIndexpage(Model model, HttpSession session) {
        model.addAttribute("roomTypes", roomTypeService.getAllRoomType());
        System.out.println(bookingCart);
        return "client/index";

    }

    @PostMapping("/search-available-room")
    public String toOutRoomPage(Model model,
                                @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate checkInDate,
                                @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate checkOutDate,
                                @RequestParam int adults,
                                @RequestParam int children) {


        bookingCart.setCheckInDate(checkInDate);
        bookingCart.setCheckOutDate(checkOutDate);

        List<Room> availableRoom = roomService.getAvailableRoom(checkInDate, checkOutDate, adults, children);

        //can loai bo nhung room da co trong cart.
        if (!bookingCart.getBookingItems().isEmpty()) {
            List<Room> filtedARoomList = availableRoom.stream()
                    .filter(room -> !bookingCart.getBookingItems().containsKey(room.getRoomId()))
                    .collect(Collectors.toList());
            model.addAttribute("availableRooms", filtedARoomList);

        } else {
            model.addAttribute("availableRooms", availableRoom);
        }

        return "client/available-room";

    } //da in clude


    @GetMapping("/room-detail")
    public String toRoomDetailPage(Model model,
                                   @RequestParam int roomId) {
        model.addAttribute("room", roomService.getRoomById(roomId));
        return "client/room-detail";
    }

    @GetMapping("/about-us")
    public String toAboutUsPage(Model model) {
        return "client/about-us";
    }  //da include

    @GetMapping("/gallery")
    public String toGalleryPage(Model model) {
        return "client/gallery";
    }  //da include

    @GetMapping("/contact-us")
    public String toContactUsPage(Model model) {
        return "client/contact-us";
    }

    @GetMapping("/personal-info")
    public String toPersonalInfoPage(Model model) {
        return "client/personal-information";
    }



    @GetMapping("/payment-info")
    public String toPaymentInfoPage(Model model) {
        return "client/payment-information";
    }




    @GetMapping("/news")
    public String toNewsPage(Model model) {
        return "client/news";
    }  // da  include

    @GetMapping("/staff")
    public String toStaffPage(Model model) {
        return "client/staff";
    }  //da inculude


    @GetMapping("/404")
    public String toPageNotFound(Model model) {
        return "client/404";
    } //da in clude


}
