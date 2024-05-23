package com.bacteriaanalysis.model;

import lombok.Data;

import java.util.Map;
@Data
public class BacteriaValue {
   private String bacteriaName;
    Map<String, Double> propertiesMap;

}
