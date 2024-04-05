package com.spring.pos2.controller;

import com.spring.pos2.dto.CategoryReq;
import com.spring.pos2.kafkaProducer.MessageProducer;
import com.spring.pos2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {
    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public String sendMessage(@RequestParam("message") String message){
        messageProducer.sendMessage("test", message);
        return "Message sent: " +message;
    }

    @GetMapping("/get")
    public List<CategoryReq> findAll(){
        return categoryService.findAll();
    }

}
