package com.bacteriaanalysis.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
@Entity
public class DimSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bacteriaId;
    private String bacteriaString;
    private Date createDate;
    private String createdBy;
    public DimSample(int bacteriaId, String bacteriaString, Date createDate, String createdBy) {
        this.bacteriaId = bacteriaId;
        this.bacteriaString = bacteriaString;
        this.createDate = createDate;
        this.createdBy = createdBy;

    }
    public DimSample(){

    }

}
