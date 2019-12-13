package com.company.sales.creditshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.sales.creditshelf.model.OlivandersShopModel;

public interface OlivandersShopRepository extends JpaRepository<OlivandersShopModel, Integer>{

	@Query(value = "select * from olivanders_wand_shop where id = ?1",nativeQuery = true)
	OlivandersShopModel findByProductId(int productId);
}
