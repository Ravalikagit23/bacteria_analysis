package com.bacteriaanalysis.service;

//import com.bacteriaanalysis.dao.BacteriaDao;
//import com.bacteriaanalysis.dao.BacteriaDao;
import com.bacteriaanalysis.model.BacteriaInput;
import com.bacteriaanalysis.model.BacteriaSample;
import com.bacteriaanalysis.model.BacteriaValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class BacteriaService {
//    @Autowired
//    BacteriaDao bacteriaDao;

    public ResponseEntity<List<BacteriaSample> >addBacteria(BacteriaInput input) {
        String accessKey = input.getAccessKey();
        byte[] zipData = input.getZipFolder();
        List<BacteriaSample> bacteriaSampleList= new ArrayList<>();
        List<BacteriaValue> bacteriaValueList= new ArrayList<>();


        try (ByteArrayInputStream bais = new ByteArrayInputStream(zipData);
             ZipInputStream zis = new ZipInputStream(bais)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (zipEntry.getName().endsWith("cnt.csv")) {
                    // Read the content of the CSV file
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
                    String line;
                    // Read each line of the CSV file
                    while ((line = reader.readLine()) != null) {
                        BacteriaSample bacteriaSample = this.getBacteriaSample(line);
                        bacteriaSampleList.add(bacteriaSample);
                        // Do something with each line of the CSV file
                    }

                } else if(zipEntry.getName().endsWith("value.csv")) {
                    // Read the content of the CSV file
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
                    String line;
                    // Read each line of the CSV file
                    while ((line = reader.readLine()) != null) {
                        // Do something with each line of the CSV file
//
                        BacteriaValue bacteriaSample2 = this.getBacteriaValue(line);
                        bacteriaValueList.add(bacteriaSample2);

                    }

                }

            }
        } catch (IOException e) {
            System.out.println("Error occured while");
        }



        return new ResponseEntity<>(bacteriaSampleList, HttpStatus.ACCEPTED);
    }

    public BacteriaSample getBacteriaSample(String line) {
        return null;

    }
     public BacteriaValue getBacteriaValue(String line){
        return null;
     }
}
