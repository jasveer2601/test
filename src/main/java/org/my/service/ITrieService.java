package org.my.service;

import java.util.List;

import org.my.dto.TrieNode;

/**
 * The Interface ITrieService. This interface provides implementation of Trie
 * data structure. A Trie is a special data structure used to store strings that
 * can be visualized like a graph. Implementation of this interface is provided
 * by {@link TrieServiceImpl} class.
 */
public interface ITrieService {

	/**
	 * Populate trie. This populates the Trie structure of the input strings and
	 * return the root of the tree. In this API, strings are stored in lower
	 * case to support searching case insensitive.
	 *
	 * @param data
	 *            the list of string
	 * @return the trie node, the root of the Trie.
	 */
	TrieNode populateTrie(List<String> data);

	/**
	 * Gets the prefixed strings. This will return all words presented in the
	 * Trie structure whose value is prefixed by the input prefix value. Also
	 * the result is restricted by the provided limit. Input value is first
	 * converted into lower-case as the Trie is populated in lower case for
	 * better performance.
	 *
	 * @param root
	 *            the root node of Trie
	 * @param prefix
	 *            value to be searched.
	 * @param limit
	 *            number of results restricted by this variable.
	 * @return the list of string prefixed by the input 'prefix'.
	 */
	List<String> getPrefixedStrings(TrieNode root, String prefix, int limit);
}
