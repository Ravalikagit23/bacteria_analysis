package com.bacteriaanalysis.dao;

import com.bacteriaanalysis.entity.DimSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface BacteriaDimSampleDao extends JpaRepository<DimSample,Integer>{

}
