package com.bacteriaanalysis.controller;

import com.bacteriaanalysis.model.BacteriaInput;
import com.bacteriaanalysis.model.BacteriaSample;
import com.bacteriaanalysis.service.BacteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("BacteriaDetails")
public class BacteriaController {
    @Autowired
    BacteriaService bacteriaService;
    @PostMapping
    public ResponseEntity<List<BacteriaSample>> addBacteria(@RequestBody BacteriaInput input) {
        return bacteriaService.addBacteria(input);
    }
}
