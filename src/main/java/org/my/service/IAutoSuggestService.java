package org.my.service;

/**
 * The Interface IAutoSuggestService.
 */
public interface IAutoSuggestService {

	/**
	 * Suggest cities for the input prefix and the resulted lists limited by limit.
	 *
	 * @param prefix the prefix
	 * @param limit the limit
	 * @return the string
	 */
	String suggestCities(String prefix, Integer limit);
	
}
