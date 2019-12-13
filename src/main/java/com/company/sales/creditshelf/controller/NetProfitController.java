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

import com.company.sales.creditshelf.exception.HostConnectException;
import com.company.sales.creditshelf.service.NetProfitService;

@RestController
@RequestMapping(value = "/profit")
public class NetProfitController {

	@Autowired
	private NetProfitService netProfitService;

	@RequestMapping(value = "/fetch", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> fetchNetProfit(@RequestParam("companyName") String companyName, 
			@RequestParam("year") int year){
		JSONObject result = new JSONObject();
		try {
			result = netProfitService.fetchNetProfit(companyName, year);
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.OK);
		}catch (HostConnectException e) {
			result.put("response", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.GATEWAY_TIMEOUT);
		}
		catch (Exception  e) {
			result.put("response", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(result.toMap(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
