package com.bacteriaanalysis.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
@Data
public class FactSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int bacteriaFkId;
    private String sampleId;
    private int bacteriaCount;
    private Double bacteriaValue;
    private Date createDate;
    private String createdBy;
    public FactSample(int id, int bacteriaFkId, String sampleId, int bacteriaCount, Double bacteriaValue, Date createDate, String createdBy) {
        this.id = id;
        this.bacteriaFkId = bacteriaFkId;
        this.sampleId = sampleId;
        this.bacteriaCount = bacteriaCount;
        this.bacteriaValue = bacteriaValue;
        this.createDate = createDate;
        this.createdBy = createdBy;

    }
    public FactSample(){

    }
}
