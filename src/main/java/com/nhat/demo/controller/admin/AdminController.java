package com.nhat.demo.controller.admin;

import com.nhat.demo.entity.Room;
import com.nhat.demo.entity.RoomType;
import com.nhat.demo.entity.RoomTypeImage;
import com.nhat.demo.repository.RoomRepository;
import com.nhat.demo.repository.RoomTypeRepository;
import com.nhat.demo.repository.RoomtypeImageRepository;
import com.nhat.demo.service.RoomServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/room";
    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomtypeImageRepository roomtypeImageRepository;

    @Autowired
    RoomServiceIF roomServiceIF;

    @RequestMapping(method = RequestMethod.GET)
    public String showAdminPage() {
        return "/manage/adminPage";
    }

    @GetMapping("addNewRoom")
    public String addNewRoom(Model model) {
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        Room room = new Room();
        model.addAttribute("room",room);
        model.addAttribute("roomTypeList",roomTypeList);
        return "/manage/addNewRoom";
    }

    @PostMapping("saveRoom")
        public String saveRoom(@Valid @ModelAttribute Room room,BindingResult bindingResult, Model model){
        Room newRoom = roomRepository.findByRoomNumber(room.getRoomNumber());
        if(newRoom != null){
            model.addAttribute("errorMessage","Phòng đã tồn tại!!!");
            List<RoomType> roomTypeList = roomTypeRepository.findAll();
            model.addAttribute("room",room);
            model.addAttribute("roomTypeList",roomTypeList);
            return "/manage/addNewRoom";
        }
        roomRepository.save(room);
        return "/manage/addNewRoom";
        }

    @GetMapping("addNewRoomType")
    public String addNewRoomType(Model model) {
        RoomType roomType = new RoomType();
        model.addAttribute("roomType",roomType);
        return "/manage/addNewRoomType";
    }

    @PostMapping("saveNewRoomType")
    public String saveRoomType(@ModelAttribute RoomType roomType, @RequestParam MultipartFile[] files, Model model){
           RoomType newRoomType = roomTypeRepository.findByTypeName(roomType.getTypeName());
           if(newRoomType != null){
               model.addAttribute("errorMessage","Tên loại phòng đã tồn tại!!!");
               model.addAttribute("roomType",roomType);
               return "/manage/addNewRoomType";
           }
        roomTypeRepository.save(roomType);
        StringBuffer fileNames = new StringBuffer();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            try {
                Files.write(fileNameAndPath,file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            RoomTypeImage roomTypeImage = new RoomTypeImage();
            roomTypeImage.setRoomType(roomType);
            roomTypeImage.setPath("/images/room/" + file.getOriginalFilename());
            roomtypeImageRepository.save(roomTypeImage);
        }

        return "/manage/addNewRoomType";
    }

    @GetMapping("/viewAllRoom")
    public String viewAllRoom(Model model){

            return listByPage(model, 1);

    }

    @GetMapping("viewAllRoom/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage ){
        Page<Room> rooms = roomServiceIF.getAllRoom(currentPage);
        int totalPages = rooms.getTotalPages();
        long totalItems = rooms.getTotalElements();
        model.addAttribute("rooms",rooms);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("currentPage",currentPage);

        return "/manage/viewAllRoom";
    }


//    @GetMapping("viewAllRoom")
//    public String viewAllRoom(Model model, HttpServletRequest request, RedirectAttributes redirect) {
////        List<Room> rooms = roomRepository.findAll();
////        model.addAttribute("rooms",rooms);
////        return "/manage/viewAllRoom";
//        request.getSession().setAttribute("roomList", null);
//
//        if(model.asMap().get("success") != null)
//            redirect.addFlashAttribute("success",model.asMap().get("success").toString());
//        return "redirect:/admin/viewAllRoom/page/1";
//    }

//    @GetMapping("viewAllRoom/page/{pageNumber}")
//    public String viewALlRoomPage(HttpServletRequest request,
//                                   @PathVariable int pageNumber, Model model) {
//        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("roomList");
//        int pagesize = 7;
//        List<Room> list = roomRepository.findAll();
//        if (pages == null) {
//            pages = new PagedListHolder<>(list);
//            pages.setPageSize(pagesize);
//        } else {
//            final int goToPage = pageNumber - 1;
//            if (goToPage <= pages.getPageCount() && goToPage >= 0) {
//                pages.setPage(goToPage);
//            }
//        }
//        request.getSession().setAttribute("rooms", pages);
//        int current = pages.getPage() + 1;
//        int begin = Math.max(1, current - list.size());
//        int end = Math.min(begin + 5, pages.getPageCount());
//        int totalPageCount = pages.getPageCount();
//        String baseUrl = "/viewAllRoom/page/";
//
//        model.addAttribute("beginIndex", begin);
//        model.addAttribute("endIndex", end);
//        model.addAttribute("currentIndex", current);
//        model.addAttribute("totalPageCount", totalPageCount);
//        model.addAttribute("baseUrl", baseUrl);
//        model.addAttribute("rooms", pages);
//
//        return "/manage/viewAllRoom";
//    }
}
