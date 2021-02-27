package com.nhat.demo.controller.admin;

import com.nhat.demo.entity.Room;
import com.nhat.demo.entity.RoomType;
import com.nhat.demo.entity.RoomTypeImage;
import com.nhat.demo.repository.RoomRepository;
import com.nhat.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        public String saveRoom(@ModelAttribute Room room){

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
    public String saveRoomType(@ModelAttribute RoomType roomType, @RequestParam MultipartFile[] files){

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


//            ImageEntity imageEntity = new ImageEntity();
//            imageEntity.setName(file.getOriginalFilename());
//            imageRepository.save(imageEntity);
        }

        return "/manage/addNewRoomType";
    }

    @GetMapping("viewAllRoom")
    public String viewAllRoom(Model model) {

        return "/manage/viewAllRoom";
    }
}
