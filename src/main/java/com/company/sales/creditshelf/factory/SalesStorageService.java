package com.company.sales.creditshelf.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.factory.StorageService;
import com.company.sales.creditshelf.model.Sale;
import com.company.sales.creditshelf.repository.SaleRepository;

@Component("salesStorageService")
public class SalesStorageService implements StorageService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesStorageService.class);
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Override
	public void saveData(final InputStream inputStream) throws Exception {
		final long start = System.currentTimeMillis();
	    parseCSVFile(inputStream);
	    LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));

	}
	@Override
    public void parseCSVFile(final InputStream inputStream) throws Exception {
        final List<Sale> sales=new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line = br.readLine();
                while ((line=br.readLine()) != null) {
                    final String[] data=line.split(",");
                    final Sale sale=new Sale();
                    sale.setCompanyName(data[0]);
                    sale.setOrderDate(data[1]);
                    if(StringUtils.isNotEmpty(data[2]))
                    sale.setOrderNumber(Integer.parseInt(data[2]));
                    if(StringUtils.isNotEmpty(data[3]))
                    sale.setProductId(Integer.parseInt(data[3]));
                    if(StringUtils.isNotEmpty(data[4]))
                    sale.setQuantity(Integer.parseInt(data[4]));
                    sale.setSalesPrice(data[5]);
                    sale.setCurrency(data[6]);
                    sales.add(sale);
                }
                LOGGER.info("Saving a list of sales of size {} records", sales.size());
                save(sales);
            }
        } catch(IOException e) {
            LOGGER.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
	
	public void save(List<Sale> sales) {
		for(int i = 0;i<sales.size();i++)
		saleRepository.saveAndFlush(sales.get(i));
	}
}
