package com.company.sales.creditshelf.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.sales.creditshelf.factory.StorageService;

@RestController
@RequestMapping("/api/upload")
public class UploadFileController {

	@Autowired
	@Qualifier(value = "salesStorageService")
	private StorageService saleService;

	@Autowired
	@Qualifier(value = "acmeCorporationStorageService")
	private StorageService acmeService;


	@Autowired
	@Qualifier(value = "olivanderShopStorageService")
	private StorageService olivanderService;

	@Autowired
	@Qualifier(value = "capsuleCorporationStorageService")
	private StorageService capsulCorporationService;


	@RequestMapping(value = "/files",method = RequestMethod.POST ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public  ResponseEntity<Map<String,Object>> uploadMultipleFile(@RequestParam(value = "files") MultipartFile[] files) {
		JSONObject result = new JSONObject();
		try {
			for(final MultipartFile file: files) {
				StorageService storageService = getStorageService(file.getOriginalFilename());
				storageService.saveData(file.getInputStream());
			}
			result.put("response", "File uploaded Successfully");
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}	

	public StorageService getStorageService(String fileName) {
		if(fileName.equals("Sales.csv"))
			return saleService;
		else if(fileName.equals("Capsule Corporation.csv"))
			return capsulCorporationService;
		else if(fileName.equals("Acme corporation.csv"))
			return acmeService;
		else if(fileName.equals("Ollivanders Wand Shop.csv"))
			return olivanderService;

		return saleService;
	}
}
