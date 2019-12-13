package com.company.sales.creditshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.company.sales.creditshelf.model.AcmeCorporationModel;

@Repository
public interface AcmeCorporationRepository extends JpaRepository<AcmeCorporationModel, Integer>{
	
	@Query(value = "select * from acme_corporation where product_id = ?1",nativeQuery = true)
	AcmeCorporationModel findByProductId(int productId);
}
