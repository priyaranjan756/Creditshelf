package com.company.sales.creditshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.sales.creditshelf.model.Sale;


public interface SaleRepository extends JpaRepository<Sale, Integer> {
	
	@Query(value = "select * from sale where company_name = ?1",nativeQuery = true)
	List<Sale> findByCompanyName(String companyName);
	
	@Query(value = "select * from sale where company_name = ?1 and order_date between ?2 and ?3",nativeQuery = true)
	List<Sale> findByCompanyNameAndOrderDate(String companyName,String startDate, String endDate);
}
