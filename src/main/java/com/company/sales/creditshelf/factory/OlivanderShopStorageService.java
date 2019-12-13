package com.company.sales.creditshelf.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.model.OlivandersShopModel;
import com.company.sales.creditshelf.repository.OlivandersShopRepository;

@Component("olivanderShopStorageService")
public class OlivanderShopStorageService implements StorageService{
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesStorageService.class);

	@Autowired
	private OlivandersShopRepository olivandersShopRepository;

	@Override
	public void saveData(final InputStream inputStream) throws Exception {
		final long start = System.currentTimeMillis();
		parseCSVFile(inputStream);
		LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));

	}
	@Override
	public void parseCSVFile(final InputStream inputStream) throws Exception {
		final List<OlivandersShopModel> olivandersShopModels=new ArrayList<>();
		try {
			try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String line = br.readLine();
				while ((line=br.readLine()) != null) {
					final String[] data=line.split(",");
					final OlivandersShopModel olivandersShopModel=new OlivandersShopModel();
					olivandersShopModel.setName(data[1]);
					olivandersShopModel.setBuildCost(data[2]);
					olivandersShopModel.setCurrency(data[3]);

					olivandersShopModels.add(olivandersShopModel);
				}
				LOGGER.info("Saving a list of olivander of size {} records", olivandersShopModels.size());
				save(olivandersShopModels);
			}
		} catch(IOException e) {
			LOGGER.error("Failed to parse CSV file {}", e);
			throw new Exception("Failed to parse CSV file {}", e);
		}
	}

	public void save(List<OlivandersShopModel> olivandersShopModels) {
		for(int i = 0;i<olivandersShopModels.size();i++)
		olivandersShopRepository.saveAndFlush(olivandersShopModels.get(i));
	}
}
