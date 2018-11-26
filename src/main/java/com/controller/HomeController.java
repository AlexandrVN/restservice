package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HomeController {

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


}
