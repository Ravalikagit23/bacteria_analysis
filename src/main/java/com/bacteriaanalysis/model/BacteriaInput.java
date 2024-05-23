package com.bacteriaanalysis.model;

import lombok.Data;

@Data
public class BacteriaInput {
    private byte[] zipFolder;
    private String accessKey;
}
