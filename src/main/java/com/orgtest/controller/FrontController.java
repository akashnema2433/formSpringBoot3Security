package com.orgtest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orgtest.entities.*;
import com.orgtest.model.UserDTO;
import com.orgtest.repositories.FileModelRepository;

import com.orgtest.repositories.MultiFilesUserRepo;
import com.orgtest.repositories.PostsRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FrontController {

    @Autowired
    private FileModelRepository fileModelRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private MultiFilesUserRepo multiFilesUserRepo;


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
            if (image) {
                String fileName = file.getOriginalFilename();
                byte[] sourceFileContent = file.getBytes(); // Get byte array content directly

                // Convert byte array content to Base64 string
                String base64Content = Base64.getEncoder().encodeToString(sourceFileContent);
                FileModel fileModel = new FileModel();
                fileModel.setFileName(fileName);
                fileModel.setFileType(contentType);
                fileModel.setContent(base64Content);
                fileModels.add(fileModel);
            } else {
                System.out.println("This is media content " + contentType + " not allowed");
            }
        }
        List<FileModel> fileModels1 = fileModelRepository.saveAll(fileModels);
        model.addAttribute("filecontent", fileModels1);
        return "pages/viewimage";
    }

    @GetMapping("/multipleFile")
    public String multipleFileUploadPage(Model model) {
        model.addAttribute("user",new MultiFileWithUser());
        return "pages/multiplefileupload";
    }


    @PostMapping("/dataprocess")
    @ResponseBody
    public String uploadModelsFiles1(@ModelAttribute("user") MultiFileWithUser user,@RequestParam(value = "files",required = false) MultipartFile[] files) throws IOException {
        System.out.println(user.getFullName());
        user.setFullName(user.getFullName());
        user.setDescription(user.getDescription());
        MultiFileWithUser result = multiFilesUserRepo.save(user);
        if(files!=null&&files.length>0 && !Arrays.stream(files).toList().isEmpty()) {
            for (MultipartFile file : files) {
                System.out.println(file.getOriginalFilename());
                String originalFilename = file.getOriginalFilename();
                Posts posts=new Posts();
                posts.setPostURL(originalFilename);
                posts.setMultiFileWithUser(result);
                postsRepository.save(posts);
            }
            if(result!=null){
                System.out.println("save successfully");
            }
        }else{
            return "Please select files!!";
        }
        return "Successfully uploaded!! file : "+files.length;
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
        SimpleUser user = new SimpleUser();
        model.addAttribute("simpleUser", user);
        return "register-page2";
    }


    //    @PostMapping("/submitForm")
//    @ResponseBody
//    public Map<String,String> submitForm(@RequestParam("username") String username, @RequestParam("email") String email) {
//        // Process the form data here (e.g., save to database, perform business logic, etc.)
//        System.out.println("Received form submission - Username: " + username + ", Email: " + email);
//
//        // Prepare response data
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Form submitted successfully!");
//
//        return response;
//    }
    @PostMapping("/submitForm")
    @ResponseBody
    public Map<String, String> submitForm(@RequestBody SimpleUser user) {
        // Process the form data here (e.g., save to database, perform business logic, etc.)
        System.out.println("Received form submission - Username: " + user.getUsername() + ", Email: " + user.getPassword());

        // Prepare response data
        Map<String, String> response = new HashMap<>();
        response.put("message", "Form submitted successfully!");

        return response;
    }

    @GetMapping("/table")
    public String showTable() {
        return "showtable";
    }

    @GetMapping("/update-form")
    public String updatePage(Model model) {

        List<UserDTO> data = new ArrayList<>();
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(1);
        userDTO1.setName("Test");
        userDTO1.setEmail("test@gmail.com");
        data.add(userDTO1);

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2);
        userDTO2.setName("Test");
        userDTO2.setEmail("test@gmail.com");
        data.add(userDTO2);

        model.addAttribute("users", data);

        return "update-form";
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO.getId() + " : " + userDTO.getName());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully update data!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/gettest")
    @ResponseBody
    public JsonNode getTest() throws JsonProcessingException {
        String url="https://tshc.gov.in/Hcdbs/caseDetails.jsp?casedet=WP/5207/2024";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println("call....");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(forEntity.getBody());
        return root;
    }

}
