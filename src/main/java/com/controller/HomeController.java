package com.controller;

import com.entity.Message;
import com.entity.User;
import com.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(value = {"/", "/index"})
    public String index(Map<String, Object> model) {
        return "index";
    }


/*    @GetMapping(value = {"/", "/index"})
    public String index(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "index";
    }*/

/*    @GetMapping(value = {"/", "index"})
    public ModelAndView test(HttpServletResponse response){
            //для вывода статистической страницы необходимо указывать полный путь: "/webapp/WEB-INF/eco-app/index.ftl"
            return new ModelAndView("index");
    }*/

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<Message> messages = messageRepository.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Message message = new Message(text, tag, user);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);

        return "main";
    }

}
