package com.orgtest.controller;

import com.orgtest.entities.User;
import com.orgtest.service.UserService;
//import javax.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
        model.addAttribute("user",user);
        return "pages/register";
    }

    @PostMapping("/register-process")
    public String registerPage(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(name = "file") MultipartFile file, HttpSession session) throws IOException {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream().map(msg->msg.getDefaultMessage()).forEach(System.out::println);
            System.out.println();
            return "pages/register";
        }
        System.out.println(user.getUserFirstName());
        String currDateTime = (LocalDateTime.now() + "").replace(":", "-");
        if (!file.isEmpty()) {
            String profileName = currDateTime + "" + file.getOriginalFilename();
            File filePath = new ClassPathResource("/static/media").getFile();
            Path path = Paths.get(filePath.getAbsolutePath() + File.separator + profileName);
            System.out.println("Path : " + path.toString());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            user.setProfile(profileName);
        }
        User result = userService.register(user);
        if(result!=null){
            session.setAttribute("message","Successfully registered!!...Please login now");
            return "redirect:/login";
        }
        return "redirect:/register-page";
    }

    @GetMapping("/dashboard-page")
    public String dashboardPage(Principal principal,Model model){
        String email = principal.getName();
        User user = userService.getByEmail(email);
        List<User> users = userService.getAll();
        model.addAttribute("usersList",users);
        model.addAttribute("user",user);
        return "pages/dashboard";
    }

    @PostMapping("/update-process")
    public String updateUserProcess(@RequestParam(name = "userId")Long userId,@RequestParam(name = "firstName",required = false)String firstName, @RequestParam(name = "lastName",required = false)String lastName,@RequestParam(name = "password",required = false)String password,HttpSession session){
        User user=new User();
        user.setId(userId);
        user.setUserFirstName(firstName);
        user.setUserLastName(lastName);
        if(user.getPassword()!=null&&user.getPassword().isBlank()!=true) {
            user.setPassword(password);
        }
        userService.update(user);
        session.setAttribute("message","Profile Successfully has been Updated!!");
        return "redirect:/dashboard-page";
    }

    @GetMapping("/private-page")
    public String adminPage(){
        return "pages/private";
    }
    @GetMapping("/normal-page")
    public String normalPage(){
        return "pages/normal";
    }

    @GetMapping("/access-denied-page")
    public String accessDeniedPage(){
        return "pages/accessdenied";
    }

    @PostMapping("/delete-process")
    public String deleteProcess(@RequestParam(name = "userId")Long userId){
        userService.deleteById(userId);
        return "redirect:/register-page";
    }


}
