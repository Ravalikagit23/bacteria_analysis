package com.bacteriaanalysis.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class BacteriaSample {
    private String bacteriaName;
    Map<String, Integer>propertiesMap;

    public String getBacteriaName() {
        return bacteriaName;
    }

    public void setBacteriaName(String bacteriaName) {
        this.bacteriaName = bacteriaName;
    }

    public Map<String, Integer> getPropertiesMap() {
        return propertiesMap;
    }

    public void setPropertiesMap(Map<String, Integer> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }
}
