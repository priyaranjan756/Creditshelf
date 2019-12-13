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

import com.company.sales.creditshelf.model.AcmeCorporationModel;
import com.company.sales.creditshelf.repository.AcmeCorporationRepository;

@Component("acmeCorporationStorageService")
public class AcmeCorporationStorageService  implements StorageService{

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesStorageService.class);

	@Autowired
	private AcmeCorporationRepository acmeCorporationRepository;

	@Override
	public void saveData(InputStream inputStream) throws Exception {
		final long start = System.currentTimeMillis();
		parseCSVFile(inputStream);
		LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));
	}

	@Override
	public void parseCSVFile(InputStream inputStream) throws Exception {
		final List<AcmeCorporationModel> acmeCorporationModels=new ArrayList<>();
		try {
			try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String line = br.readLine();
				while ((line=br.readLine()) != null) {
					final String[] data=line.split(",");
					final AcmeCorporationModel acmeCorporationModel=new AcmeCorporationModel();
					acmeCorporationModel.setDescription((data[0]));
					acmeCorporationModel.setPurchasePrice(data[1]);
					acmeCorporationModel.setCurrency(data[2]);

					acmeCorporationModels.add(acmeCorporationModel);
				}

			}
			LOGGER.info("Saving a list of acme corporation of size {} records", acmeCorporationModels.size());
			save(acmeCorporationModels);
		} catch(final IOException e) {
			LOGGER.error("Failed to parse CSV file {}", e);
			throw new Exception("Failed to parse CSV file {}", e);
		}
	}

	public void save(List<AcmeCorporationModel> acmeCorporationModels) {
		for(int i = 0;i<acmeCorporationModels.size();i++)
			acmeCorporationRepository.saveAndFlush(acmeCorporationModels.get(i));
	}

}
