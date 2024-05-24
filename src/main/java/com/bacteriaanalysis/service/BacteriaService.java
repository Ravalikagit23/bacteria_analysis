package com.bacteriaanalysis.service;

//import com.bacteriaanalysis.dao.BacteriaDao;
//import com.bacteriaanalysis.dao.BacteriaDao;
import com.bacteriaanalysis.dao.BacteriaDimSampleDao;
import com.bacteriaanalysis.entity.DimSample;
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
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class BacteriaService {
    @Autowired
    BacteriaDimSampleDao bacteriaDimSampleDao;

    public ResponseEntity<List<DimSample> >addBacteria(BacteriaInput input) {
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
                    reader.readLine();//skipping the first line
                    // Read each line of the CSV file-
                    while ((line = reader.readLine()) != null) {
                        BacteriaSample bacteriaSample = this.getBacteriaSample(line);
                        bacteriaSampleList.add(bacteriaSample);
                        // Do something with each line of the CSV file
                    }

                } else if(zipEntry.getName().endsWith("value.csv")) {
                    // Read the content of the CSV file
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
                    String line;
                    reader.readLine();
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
List<DimSample> dimSample=new ArrayList<>();
        for(BacteriaValue bacteriaValue :bacteriaValueList){
            String bacteriaName = bacteriaValue.getBacteriaName();
            DimSample dimsample1=new DimSample();
          dimsample1.setBacteriaString(bacteriaName);
          dimsample1.setCreatedBy("Ravali");
          dimsample1.setCreateDate(new Date());
          dimSample.add(dimsample1);
        }
        bacteriaDimSampleDao.saveAll(dimSample);

        return new ResponseEntity<>(dimSample, HttpStatus.ACCEPTED);
    }

    public BacteriaSample getBacteriaSample(String line) {
        String[] tokens = line.split(",");
        BacteriaSample bacteriaSample=new BacteriaSample();
        bacteriaSample.setBacteriaName(tokens[0]);
        Map<String, Integer> propertiesMap=new HashMap<>();
        propertiesMap.put("4AHXT4", Integer.parseInt(tokens[1]));
        propertiesMap.put("VQBLOB", Integer.parseInt(tokens[2]));
        propertiesMap.put("QWE1YH", Integer.parseInt(tokens[3]));
        propertiesMap.put("M32852", Integer.parseInt(tokens[4]));
        propertiesMap.put("N1GPT5", Integer.parseInt(tokens[5]));
        propertiesMap.put("9GW59W", Integer.parseInt(tokens[6]));
        propertiesMap.put("81S8L1", Integer.parseInt(tokens[7]));
        propertiesMap.put("DURD3C", Integer.parseInt(tokens[8]));
        propertiesMap.put("XG8W8D", Integer.parseInt(tokens[9]));
        propertiesMap.put("HD882N", Integer.parseInt(tokens[10]));
        bacteriaSample.setPropertiesMap(propertiesMap);
        return bacteriaSample;
    }
    public BacteriaValue getBacteriaValue(String line){
         String[] tokens = line.split(",");
         BacteriaValue bacteriaValue=new BacteriaValue();
         bacteriaValue.setBacteriaName(tokens[0]);
        Map<String, Double> propertiesMap=new HashMap<>();
        propertiesMap.put("4AHXT4", Double.parseDouble(tokens[1]));
        propertiesMap.put("VQBLOB",Double.parseDouble(tokens[2]));
        propertiesMap.put("QWE1YH",Double.parseDouble(tokens[3]));
        propertiesMap.put("M32852", Double.parseDouble(tokens[4]));
        propertiesMap.put("N1GPT5", Double.parseDouble(tokens[5]));
        propertiesMap.put("9GW59W", Double.parseDouble(tokens[6]));
        propertiesMap.put("81S8L1", Double.parseDouble(tokens[7]));
        propertiesMap.put("DURD3C", Double.parseDouble(tokens[8]));
        propertiesMap.put("XG8W8D", Double.parseDouble(tokens[9]));
        propertiesMap.put("HD882N", Double.parseDouble(tokens[10]));
        bacteriaValue.setPropertiesMap(propertiesMap);
         return bacteriaValue;
     }
}