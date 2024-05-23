package com.bacteriaanalysis.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class BacteriaSample {
    private String bacteriaName;
    Map<String, Integer>propertiesMap;

}
