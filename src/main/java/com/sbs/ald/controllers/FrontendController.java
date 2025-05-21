package com.sbs.ald.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class FrontendController {

    @RequestMapping(value = {
        "/", 
        "/{path:^(?!api|static|ws|h2|swagger-ui).*$}", 
        "/{path:^(?!api|static|ws|h2|swagger-ui).*$}/**"
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}



