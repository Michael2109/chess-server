package com.app.chessserver.controller;

import com.app.chessserver.model.Greeting;
import com.app.chessserver.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    @CrossOrigin(origins = "http://localhost:3000")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("Returning greeting");

        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fireGreeting();
        }).start();

        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    public void fireGreeting() {
        System.out.println("Fire");
        this.template.convertAndSend("/topic/greetings", new Greeting("Fire"));
    }
}
