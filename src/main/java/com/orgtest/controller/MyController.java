package com.orgtest.controller;

import com.orgtest.entities.User;
import com.orgtest.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Controller
public class MyController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "pages/login";
    }


    @GetMapping("/register-page")
    public String registerPage(Model model) {
        User user=new User();
        model.addAttribute("userdata",user);
        return "pages/register";
    }

    @PostMapping("/register-process")
    public String registerPage(@ModelAttribute User user, @RequestParam(name = "file") MultipartFile file,HttpSession session) throws IOException {
        System.out.println(user.getUserFirstName());
        String currDateTime = (LocalDateTime.now() + "").replace(":", "-");
        if (!file.isEmpty()) {
            String profileName = currDateTime + "" + file.getOriginalFilename();
            File filePath = new ClassPathResource("/static/media").getFile();
            Path path = Paths.get(filePath.getAbsolutePath() + File.separator + profileName);
            System.out.println("Path : " + path.toString());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        user.setProfile(file.getOriginalFilename());
        User result = userService.register(user);
        if(result!=null){
            session.setAttribute("message","Successfully registered!!...Please login now");
            return "redirect:/login";
        }
        return "redirect:/register-page";
    }

}
