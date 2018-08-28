package org.my.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.my.config.AppUtil;
import org.my.dto.TrieNode;
import org.my.service.IAutoSuggestService;
import org.my.service.IMasterDataService;
import org.my.service.ITrieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class AutoSuggestServiceImpl implementation of {@link IAutoSuggestService}.
 */
@Service
public class AutoSuggestServiceImpl implements IAutoSuggestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoSuggestServiceImpl.class);
	
	/** The master data service. */
	@Autowired
	private IMasterDataService masterDataService;
	
	/** The trie service. */
	@Autowired
	private ITrieService trieService;
	
	/** The cities root node. which is used in suggestCities API*/
	private TrieNode citiesRootNode;
	
	/**
	 * Populates the Trie structure while loading of the application context. It initialize the Trie data structure for all the cities
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostConstruct
	public void init() throws IOException {
		citiesRootNode = trieService.populateTrie(masterDataService.getCities());
	}
	
	public String suggestCities(String prefix, Integer limit) {
		LOGGER.info("Start of auto suggest strings population");
		List<String> result = trieService.getPrefixedStrings(citiesRootNode, prefix, limit);
		StringBuilder resultString = new StringBuilder();
		result.forEach(city-> resultString.append(AppUtil.toCamelCase(city)).append(System.getProperty("line.separator")));
		LOGGER.info("End of auto suggest strings population");
		return resultString.toString();
	}

}
