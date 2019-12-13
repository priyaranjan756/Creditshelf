package com.company.sales.creditshelf.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.sales.creditshelf.exception.DataNotFoundException;
import com.company.sales.creditshelf.exception.HostConnectException;
import com.company.sales.creditshelf.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SalesController {

	@Autowired
	private SaleService saleService;

	@RequestMapping(value = "fetch", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> fetchSalesOfCompany(@RequestParam("companyName") String companyName){
		JSONObject result  = new JSONObject();
		try {
			result =  saleService.fetchSalesOfCompany(companyName);
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.OK);
		}
		catch (HostConnectException  e) {
			result.put("response", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.GATEWAY_TIMEOUT);
		}
		catch (DataNotFoundException  e) {
			result.put("response", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.NOT_FOUND);
		}
		catch (Exception  e) {
			result.put("response", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
