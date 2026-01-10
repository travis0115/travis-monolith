package com.travis.monolith.module.demo.internal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author travis
 */
@Controller
public class IndexController {
    @RequestMapping( "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
