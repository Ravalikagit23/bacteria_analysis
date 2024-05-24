package com.bacteriaanalysis.dao;

import com.bacteriaanalysis.entity.FactSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface BacteriaFactSampleDao extends JpaRepository<FactSample,Integer> {

}
