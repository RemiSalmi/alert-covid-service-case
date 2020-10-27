package com.polytech.alertcovidservicecase.controllers;

import com.polytech.alertcovidservicecase.models.Positive;
import com.polytech.alertcovidservicecase.models.PositiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/positive")
public class PositiveController {

    @Autowired
    private PositiveRepository positiveRepository;

    @GetMapping
    public List<Positive> list() { return positiveRepository.findAll(); }
}
