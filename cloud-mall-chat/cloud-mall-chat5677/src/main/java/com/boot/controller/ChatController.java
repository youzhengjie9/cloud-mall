package com.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/chat")
public class ChatController {

    @GetMapping(path = "/toChat")
    public String toChat()
    {

        return "chat";
    }


}
