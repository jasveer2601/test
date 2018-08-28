package org.my.config;

/**
 * The Class AppUtil.
 */
public class AppUtil {
	
	private AppUtil() {
		
	}

	/**
	 * To camel case. Gives the camel case of the input string.
	 *
	 * @param init the init
	 * @return the string
	 */
	public static String toCamelCase(final String init) {
	    if (init==null)
	        return null;

	    final StringBuilder ret = new StringBuilder(init.length());

	    for (final String word : init.split(" ")) {
	        if (!word.isEmpty()) {
	            ret.append(Character.toUpperCase(word.charAt(0)));
	            ret.append(word.substring(1));
	        }
	        if (ret.length()!=init.length())
	            ret.append(" ");
	    }

	    return ret.toString();
	}
}
