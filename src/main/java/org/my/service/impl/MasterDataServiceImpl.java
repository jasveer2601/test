package org.my.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.my.service.IMasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

/**
 * The Class MasterDataServiceImpl implementation of {@link IMasterDataService}.
 */
@Service
public class MasterDataServiceImpl implements IMasterDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataServiceImpl.class);
	
	/** The Constant SEPARATOR. */
	private static final String SEPARATOR = ",";

	@Value("${app.config.csv.file.path}")
	private String csvFilePath;

	@Value("${app.config.readFromFile}")
	private boolean readFromFile;
	
	@Value("${app.config.csv.file.url}")
	private String csvFileUrl;
	
	@Value("${app.config.csv.file.cityIndex}")
	private int cityIndex;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<String> getCities() throws IOException {
		LOGGER.info("Start of fetching all cities");
		final List<String> cities = new ArrayList<>();
		if (readFromFile) {
			LOGGER.info("Reading from file {}", csvFilePath);
			loadFromFileSystem(cities);
		} else {
			LOGGER.info("Reading from url {}", csvFileUrl);
			loadFromExternalSystem(cities);
		}
		LOGGER.info("End of fetching all cities");
		return cities;
	}

	private void loadFromExternalSystem(final List<String> cities) {
		ResponseExtractor<Void> responseExtractor = response -> {
			new BufferedReader(new InputStreamReader(response.getBody(), "utf-8")).lines().forEach(line->addCity(cities, line));
			return null;
		};
		this.restTemplate.execute(this.csvFileUrl, HttpMethod.GET, null, responseExtractor);
	}

	private void loadFromFileSystem(final List<String> cities) throws IOException {
		File file = new File(csvFilePath);
		if(!file.exists()) {
			file = new File(this.getClass().getClassLoader().getResource(csvFilePath).getPath());
		}
		try(BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"))) {
			bufferedReader.lines().forEach(line->addCity(cities, line));	
		}
	}
	
	private void addCity(final List<String> cities, String line) {
		cities.add(line.split(SEPARATOR)[cityIndex].trim());
	}
}
