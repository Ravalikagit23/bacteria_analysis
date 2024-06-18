package com.bacteriaanalysis.service;

//import com.bacteriaanalysis.dao.BacteriaDao;
//import com.bacteriaanalysis.dao.BacteriaDao;

import com.bacteriaanalysis.dao.BacteriaDimSampleDao;
import com.bacteriaanalysis.dao.BacteriaFactSampleDao;
import com.bacteriaanalysis.entity.DimSample;
import com.bacteriaanalysis.entity.FactSample;
import com.bacteriaanalysis.exception.InvalidBacteriaException;
import com.bacteriaanalysis.model.BacteriaInput;
import com.bacteriaanalysis.model.BacteriaSample;
import com.bacteriaanalysis.model.BacteriaValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class BacteriaService {
    @Autowired
    BacteriaDimSampleDao bacteriaDimSampleDao;
    @Autowired
    BacteriaFactSampleDao bacteriaFactSampleDao;

    public ResponseEntity<List<FactSample>> addBacteria(byte[] zipFolder,String accessKey) {
        String access = accessKey;
        byte[] zipFile = zipFolder;
        Map<String, BacteriaSample> bacteriaSampleMap = new HashMap<>();
        Map<String, BacteriaValue> bacteriaValueMap = new HashMap<>();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(bais)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (zipEntry.getName().endsWith("cnt.csv")) {
                    // Read the content of the CSV file
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));

                    String tableHeader = reader.readLine();//skipping the first row
// Read each row of the CSV file-
                    System.out.println(tableHeader);
                    String row;
                    while ((row = reader.readLine())!= null) {
                        BacteriaSample bacteriaSample = this.getBacteriaSample(tableHeader,row);
                        bacteriaSampleMap.put(bacteriaSample.getBacteriaName(), bacteriaSample);
                        // Do something with each row of the CSV file
                    }
                } else if (zipEntry.getName().endsWith("value.csv")) {
                    // Read the content of the CSV file
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
                    String row1;
                    String tableHeader = reader.readLine();
                    // Read each row1 of the CSV file
                    while ((row1 = reader.readLine()) != null) {
                        // Do something with each row1 of the CSV file
                        BacteriaValue bacteriaSample2 = this.getBacteriaValue(tableHeader, row1);
                        bacteriaValueMap.put(bacteriaSample2.getBacteriaName(), bacteriaSample2);
                    }
                }
                else{
                    throw new InvalidBacteriaException("File was not found");
                }


            }

        } catch (Exception e) {
            System.out.println("Error occured while");
            e.printStackTrace();
        }


        if(bacteriaSampleMap.size()!=bacteriaValueMap.size()){
            throw new InvalidBacteriaException("bacteria string size mismatch");
        }
        List<DimSample> dimSampleList = new ArrayList<>();
        for (String bacteriaName : bacteriaValueMap.keySet()) {
            DimSample dimsample1 = new DimSample();
            dimsample1.setBacteriaString(bacteriaName);
            dimsample1.setCreatedBy("Ravali");
            dimsample1.setCreateDate(new Date());
            dimSampleList.add(dimsample1);
        }
        dimSampleList = bacteriaDimSampleDao.saveAll(dimSampleList);

        List<FactSample> factSampleList = saveFactSampleList(bacteriaSampleMap, bacteriaValueMap, dimSampleList);
        return new ResponseEntity<>(factSampleList, HttpStatus.ACCEPTED);
    }
    private List<FactSample> saveFactSampleList(Map<String, BacteriaSample> bacteriaSampleMap,
                                                Map<String, BacteriaValue> bacteriaValueMap, List<DimSample> dimSampleList) {
        List<FactSample> factSampleList = new ArrayList<>();
        for (DimSample newDimSampleObject : dimSampleList) { // For each bacteara-10 bactearia
            String bacteriaName = newDimSampleObject.getBacteriaString();
            if(!bacteriaSampleMap.containsKey(bacteriaName)|| !bacteriaValueMap.containsKey(bacteriaName)){
                throw new InvalidBacteriaException("Bacteria String mismatch") ;
            }

            BacteriaSample bacteriaSample = bacteriaSampleMap.get(bacteriaName);
            //bacteariaCounts
            Map<String, Integer> bacteriaSampleCounts = bacteriaSample.getPropertiesMap();//10- sample counts
            BacteriaValue bacteriaValue = bacteriaValueMap.get(bacteriaName);
            //bacteariaValues
            Map<String, Double> bacteriaSampleValues = bacteriaValue.getPropertiesMap();
            //10-//10- sample values<Sample Id, value>
            for (String sampleId : bacteriaSampleCounts.keySet()) {
                FactSample factSample1 = new FactSample();
                factSample1.setBacteriaFkId(newDimSampleObject.getBacteriaId());//1
                factSample1.setBacteriaCount(bacteriaSampleCounts.get(sampleId));
                factSample1.setBacteriaValue(bacteriaSampleValues.get(sampleId));
                factSample1.setSampleId(sampleId);
                factSample1.setCreateDate(newDimSampleObject.getCreateDate());
                factSample1.setCreatedBy(newDimSampleObject.getCreatedBy());
                factSampleList.add(factSample1);
            }
        }
        bacteriaFactSampleDao.saveAll(factSampleList);
        return factSampleList;
    }

    public BacteriaSample getBacteriaSample(String tableHeader,String dataRow) {
        String[] dataTokens = dataRow.split(",");
        String [] headerTokens=tableHeader.split(",");
        BacteriaSample bacteriaSample = new BacteriaSample();
        bacteriaSample.setBacteriaName(dataTokens[0]);
        Map<String, Integer> propertiesMap = new HashMap<>();
        if(dataTokens.length!=headerTokens.length){
            throw new InvalidBacteriaException("sample count mismatch");
        }
        int n=dataTokens.length;
        for (int i = 1; i < Math.min(headerTokens.length, dataTokens.length); i++) {
                // Attempt to parse the string into an integer
                int number = Integer.parseInt(dataTokens[i]);
                // Put the parsed number into the properties map with the corresponding header token
                propertiesMap.put(headerTokens[i], number);
        }
        bacteriaSample.setPropertiesMap(propertiesMap);
        return bacteriaSample;
    }
    public BacteriaValue getBacteriaValue(String tableHeader,String line) {
        String[] tokens = line.split(",");
        String [] headerToken=tableHeader.split(",");
        BacteriaValue bacteriaValue = new BacteriaValue();
        bacteriaValue.setBacteriaName(tokens[0]);
        Map<String, Double> propertiesMap = new HashMap<>();
        if(tokens.length!=headerToken.length){
            throw new InvalidBacteriaException("sample count mismatch");
        }
        int n=tokens.length;
        for(int i=1;i<n;i++){
            propertiesMap.put(headerToken[i], Double.parseDouble(tokens[i]));
        }
        bacteriaValue.setPropertiesMap(propertiesMap);
        return bacteriaValue;
    }
}