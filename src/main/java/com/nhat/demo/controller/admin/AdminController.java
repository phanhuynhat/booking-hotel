package com.nhat.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(method = RequestMethod.GET)
    public String showAdminPage() {
        return "/manage/adminPage";
    }

    @GetMapping("addNewRoom")
    public String addNewRoom() {
        return "/manage/addNewRoom";
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
