package com.company.sales.creditshelf.factory;

import java.io.InputStream;

public interface StorageService {
	 void saveData(final InputStream inputStream) throws Exception;
	 void parseCSVFile(final InputStream inputStream) throws Exception;
}
