package com.orgtest.controller;

import com.orgtest.entities.FileModel;
import com.orgtest.entities.SimpleUser;
import com.orgtest.repositories.FileModelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/public")
public class FrontController {

    @Autowired
    private FileModelRepository fileModelRepository;
    private List<String> fileNames = new ArrayList<>();

    @GetMapping("/media")
    public String index(Model model) {
        model.addAttribute("fileNames", fileNames);
        return "pages/uploadmedia";
    }

    @PostMapping("/upload")
    public String uploadModelsFiles(@RequestParam("files") MultipartFile[] files, Model model) throws IOException {
        List<FileModel> fileModels = new ArrayList<>();
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            boolean image = contentType.startsWith("image");
            if(image) {
                String fileName = file.getOriginalFilename();
                byte[] sourceFileContent = file.getBytes(); // Get byte array content directly

                // Convert byte array content to Base64 string
                String base64Content = Base64.getEncoder().encodeToString(sourceFileContent);
                FileModel fileModel = new FileModel();
                fileModel.setFileName(fileName);
                fileModel.setFileType(contentType);
                fileModel.setContent(base64Content);
                fileModels.add(fileModel);
            }else {
                System.out.println("This is media content " + contentType+" not allowed");
            }
        }
        List<FileModel> fileModels1 = fileModelRepository.saveAll(fileModels);
        model.addAttribute("filecontent", fileModels1);
        return "pages/viewimage";
    }

    @GetMapping("/getImagesfile")
    public String getImages(Model model) {
        List<FileModel> all = fileModelRepository.findAll();
        model.addAttribute("filecontent", all);
        return "pages/viewimage";
    }

    @GetMapping("/public-page1")
    public String publicPage1() {
        return "public-page1";
    }

    @GetMapping("/registerp2")
    public String registerPage2(Model model) {
        SimpleUser user=new SimpleUser();
        model.addAttribute("simpleUser",user);
        return "register-page2";
    }


    @PostMapping("/submitForm")
    @ResponseBody
    public Map<String,String> submitForm(@RequestParam("username") String username, @RequestParam("email") String email) {
        // Process the form data here (e.g., save to database, perform business logic, etc.)
        System.out.println("Received form submission - Username: " + username + ", Email: " + email);

        // Prepare response data
        Map<String, String> response = new HashMap<>();
        response.put("message", "Form submitted successfully!");

        return response;
    }


}
