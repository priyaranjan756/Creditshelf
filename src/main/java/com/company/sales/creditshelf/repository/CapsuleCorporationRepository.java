package com.company.sales.creditshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.sales.creditshelf.model.CapsuleCorporationModel;

public interface CapsuleCorporationRepository extends JpaRepository<CapsuleCorporationModel, Integer> {
	@Query(value = "select * from capsule_corporation where id = ?1",nativeQuery = true)
	CapsuleCorporationModel findByProductId(int productId);
}
