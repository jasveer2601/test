package org.my.api;

import org.my.service.IAutoSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class AutoSuggestController.
 */
@RestController
public class AutoSuggestController {

	/** The auto suggest service. */
	@Autowired
	private IAutoSuggestService autoSuggestService;
	
	/**
	 * Suggest cities.
	 *
	 * @param prefix the prefix
	 * @param count the count
	 * @return the string
	 */
	@RequestMapping(path="/suggest_cities",method = RequestMethod.GET, produces="text/plain")
	public String suggestCities(@RequestParam(name="start") String prefix, @RequestParam(name="atmost") Integer count) {
		return autoSuggestService.suggestCities(prefix, count);
	}
	
}
