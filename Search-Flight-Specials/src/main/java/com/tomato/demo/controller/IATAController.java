package com.tomato.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tomato.demo.service.CSVtoIATAService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/iata")
public class IATAController {

    @Autowired
    private CSVtoIATAService csvtoIATAService;

    @GetMapping("/search")
    public List<Map<String, String>> searchIATAByKeyword(@RequestParam String keyword) {
        return csvtoIATAService.searchIATAByKeyword(keyword);
    }
}