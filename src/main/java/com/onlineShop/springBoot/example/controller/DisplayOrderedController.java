package com.onlineShop.springBoot.example.controller;

import com.onlineShop.springBoot.example.entity.DisplayOrdered;
import com.onlineShop.springBoot.example.service.Service;
import com.onlineShop.springBoot.example.model.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/display")
@Slf4j
public class DisplayOrderedController {
    @Autowired
    private Converter converter;

    private Service service;
    @GetMapping
    public ResponseEntity<List<DisplayOrdered>> getDisplayOrdered(){
        log.info("getDisplayOrdered()");
        return ResponseEntity.ok(service.displayOrderedList());

    }

}
