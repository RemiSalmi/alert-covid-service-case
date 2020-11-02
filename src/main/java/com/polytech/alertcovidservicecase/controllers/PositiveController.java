package com.polytech.alertcovidservicecase.controllers;

import com.polytech.alertcovidservicecase.models.Positive;
import com.polytech.alertcovidservicecase.repositories.PositiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/positive")
public class PositiveController {

    @Autowired
    private PositiveRepository positiveRepository;

    @GetMapping
    public List<Positive> list() { return positiveRepository.findAll(); }

    @GetMapping @RequestMapping("{id_user}")
    public List<Positive> get(@PathVariable Long id_user) {
        return positiveRepository.findAllByIdUser(id_user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Positive create(@RequestBody final Positive positive) {
        return positiveRepository.saveAndFlush(positive);
    }

    @DeleteMapping
    public void delete(@RequestBody final Positive positive) {
        positiveRepository.delete(positive);
    }
}
