package com.onlineShop.springBoot.example.controller;

import com.onlineShop.springBoot.example.entity.DisplayOrdered;
import com.onlineShop.springBoot.example.service.Service;
import com.onlineShop.springBoot.example.model.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/display")
@Slf4j
public class DisplayOrderedController {
    @Autowired
    private Service service;

    // display order
    @GetMapping
    public ResponseEntity<List<DisplayOrdered>> getDisplayOrdered() {
        log.info("getDisplayOrdered()");
        return ResponseEntity.ok(service.displayOrderedList());

    }

    // display order by name and lastname
    @GetMapping(value = "/searchByPerson")
    public ResponseEntity<List<DisplayOrdered>> getDisplayOrderedByPerson(@RequestParam String name, @RequestParam String lastName) {

        List<DisplayOrdered> displayOrderedList = service.searchOrderedByPerson(name, lastName);

        if (displayOrderedList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
        return ResponseEntity.ok(displayOrderedList);
    }

}
