package com.bacteriaanalysis.controller;

import com.bacteriaanalysis.entity.DimSample;
import com.bacteriaanalysis.entity.FactSample;
import com.bacteriaanalysis.model.BacteriaInput;
import com.bacteriaanalysis.model.BacteriaSample;
import com.bacteriaanalysis.service.BacteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("BacteriaDetails")
public class BacteriaController {
    @Autowired
    BacteriaService bacteriaService;

    @PostMapping
    public ResponseEntity<List<FactSample>> addBacteria(@RequestParam MultipartFile zipFolder, @RequestParam String accessKey ) throws IOException {
        return bacteriaService.addBacteria(zipFolder.getBytes(),accessKey);
}
}
