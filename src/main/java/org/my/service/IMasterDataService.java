package org.my.service;

import java.io.IOException;
import java.util.List;

/**
 * The Interface IMasterDataService. This interface provide the data from
 * external system that is why it is called MasterDataService. Currently this
 * have API only for fetching all the Cities.
 */
public interface IMasterDataService {

	/**
	 * Gets the cities. Used for fetching all the cities from the data provided by the external system.
	 *
	 * @return the cities
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	List<String> getCities() throws IOException;

}
