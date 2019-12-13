//package com.company.sales.creditshelf.dao;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class CompanyFactory {
//
//	private static Map<String, CompanyDao> map = new HashMap<>();
//
//	static {
//		map.put("Acme corporation", new AcmeCorporationDao());
//		map.put("Ollivanders Wand Shop", new OlivandersShopDao());
//		map.put("Capsule Corporation", new CapsuleCorporationDao());
//	}
//
//	public CompanyDao getCompanyDaoObject(String companyName) {
//		return map.get(companyName);
//	}
//}
