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

import com.company.sales.creditshelf.model.CapsuleCorporationModel;
import com.company.sales.creditshelf.repository.CapsuleCorporationRepository;

@Component(value = "capsuleCorporationStorageService")
public class CapsuleCorporationStorageService implements StorageService{
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesStorageService.class);

	@Autowired
	private CapsuleCorporationRepository capsuleCorporationRepository;

	@Override
	public void saveData(InputStream inputStream) throws Exception {
		final long start = System.currentTimeMillis();
		parseCSVFile(inputStream);
		LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));
	}

	@Override
	public void parseCSVFile(InputStream inputStream) throws Exception {
		final List<CapsuleCorporationModel> capsuleCorporationModels=new ArrayList<>();
		try {
			try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String line = br.readLine();
				while ((line=br.readLine()) != null) {
					final String[] data=line.split(",");
					final CapsuleCorporationModel capsuleCorporationModel=new CapsuleCorporationModel();
					capsuleCorporationModel.setProduct(data[1]);
					capsuleCorporationModel.setAssemblyCost(data[2]);
					capsuleCorporationModel.setCurrency(data[3]);

					capsuleCorporationModels.add(capsuleCorporationModel);
				}

			}
			LOGGER.info("Saving a list of capsule corporation of size {} records", capsuleCorporationModels.size());
			save(capsuleCorporationModels);
		} catch(final IOException e) {
			LOGGER.error("Failed to parse CSV file {}", e);
			throw new Exception("Failed to parse CSV file {}", e);
		}
	}

	public void save(List<CapsuleCorporationModel> capsuleCorporationModels) {
		for(int i = 0;i<capsuleCorporationModels.size();i++)
			capsuleCorporationRepository.saveAndFlush(capsuleCorporationModels.get(i));
	}
}
