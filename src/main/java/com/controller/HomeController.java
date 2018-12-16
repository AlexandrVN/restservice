package com.controller;

import com.entity.Message;
import com.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping(value = {"/", "/index"})
    public String index(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "index";
    }

/*    @GetMapping(value = {"/", "index"})
    public ModelAndView test(HttpServletResponse response){
            //для вывода статистической страницы необходимо указывать полный путь: "/webapp/WEB-INF/eco-app/index.mustache"
            return new ModelAndView("index");
    }*/

    @GetMapping
    public String main(Map<String,Object> model){
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String add(
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model){

        Message message = new Message(text, tag);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);

        return "main";
    }



}
