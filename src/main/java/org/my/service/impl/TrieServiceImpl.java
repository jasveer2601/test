package org.my.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;

import org.my.config.AppConstants;
import org.my.dto.TrieNode;
import org.my.service.ITrieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class TrieServiceImpl. Implementation of {@link ITrieService} interface
 */
@Service
public class TrieServiceImpl implements ITrieService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrieServiceImpl.class);

	/** The special character map. */
	private static Map<Character, Integer> specialCharacterMap = new HashMap<>();

	/** The valid characters count. */
	private int validCharactersCount;

	@Value("${app.config.regex.input}")
	private String regex;

	@Value("${app.config.allowed.specialcharacters}")
	private String specialChacters;

	@PostConstruct
	public void init() {
		validCharactersCount = 26 + specialChacters.length();
		int index = 26;
		for (Character ch : specialChacters.toCharArray()) {
			specialCharacterMap.put(ch, index++);
		}
	}

	@Override
	public TrieNode populateTrie(final List<String> data) {
		LOGGER.info("Start of loading trie nodes");
		TrieNode root = new TrieNode(validCharactersCount);
		final Set<String> ignoredWords = new HashSet<>();
		// Converting each string in lowercase and if the string pass the Regex
		// it will be inserted into Trie.
		data.stream().forEach(word -> insert(root, word.toLowerCase(), ignoredWords));
		LOGGER.info("End of loading trie nodes");
		return root;
	}

	/**
	 * If the string pass the RegEx it will be stored into Trie.
	 *
	 * @param root
	 *            the root node of the Trie.
	 * @param word
	 *            the word to be inserted into Trie.
	 * @param ignoredWords
	 *            the set of words which are failed by RegEx.
	 */
	private void insert(final TrieNode root, final String word, final Set<String> ignoredWords) {

		if (!ignoredWords.contains(word) && word.matches(regex)) {
			TrieNode node = root;
			for (int i = 0; i < word.length(); i++) {
				char currWord = word.charAt(i);
				if (!node.containsKey(currWord)) {
					node.put(currWord, new TrieNode(validCharactersCount));
				}
				node = node.getChild(currWord);
			}
			// Mark that the current node represents a word.
			node.setWord();
		} else if (!ignoredWords.contains(word)) {
			ignoredWords.add(word);
			LOGGER.warn("Ignoring word : {}", word);
		}
	}

	@Override
	public List<String> getPrefixedStrings(final TrieNode root, final String input, final int limit) {
		LOGGER.info("Start of getting pre fixed strings");
		if(!input.matches(regex)) {
			throw new IllegalArgumentException("Invalid input");
		}
		// Converting into lower case Trie contains in lower case only
		String prefix = input.toLowerCase();
		List<String> result = new ArrayList<>();
		// Search if the prefix exists in the Trie or not
		TrieNode node = searchPrefix(root, prefix);

		// This list maintains all the characters of the output word which
		// starts with the prefix and also used for finding all branches of the
		// prefix string.
		List<Character> prefixStringCollection = new ArrayList<>();
		for (int i = 0; i < prefix.length(); i++) {
			prefixStringCollection.add(prefix.charAt(i));
		}
		if (node != null) {
			// Populates all branches of the current node
			autoTextComplete(node, prefixStringCollection, result, limit);
		} else {
			LOGGER.info("No Match found for the input prefix : ", prefix);
		}
		LOGGER.info("End of getting pre fixed strings");
		return result;
	}

	/**
	 * Search prefix.
	 *
	 * @param root
	 *            the root
	 * @param word
	 *            the word
	 * @return the trie node
	 */
	private TrieNode searchPrefix(final TrieNode root, final String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			char currWord = word.charAt(i);
			if (node.containsKey(currWord)) {
				node = node.getChild(currWord);
			} else {
				return null;
			}
		}
		return node;
	}

	/**
	 * Auto text complete.
	 *
	 * @param node
	 *            the node
	 * @param prefixStringCollection
	 *            the prefix string collection
	 * @param result
	 *            the result
	 * @param limit
	 *            the limit
	 */
	private void autoTextComplete(final TrieNode node, final List<Character> prefixStringCollection,
			final List<String> result, final int limit) {
		if (node == null || result.size() == limit) {
			return;
		}
		if (node.isWord()) {
			StringBuilder res = new StringBuilder();
			for (int i = 0; i < prefixStringCollection.size(); i++) {
				res.append(prefixStringCollection.get(i));
			}
			result.add(res.toString());
		}
		// Termination condition when node End is true and no more branching is
		// required
		if ((node.isWord() && !node.hasChildren(node)) || result.size() == limit) {
			return;
		}

		// Iterating over all characters which are present in the node to find
		// out all the branches
		for (int i = 0; i < validCharactersCount; i++) {
			char ch = (char) (i + AppConstants.START_OF_INDEX);
			if (node.containsKey(ch)) {
				// This check is required as we are using fixed index for
				// special chars and which need to be mapped to the actual char
				// value
				if (ch == AppConstants.CHAR_TO_REPLACE) {
					prefixStringCollection.add(' ');
				} else {
					prefixStringCollection.add(ch);
				}
				// calling recursively for each prefix plus available char value.
				// For example if the prefix is 'ab' and b node has available
				// chars e,f,g then call this method for 'abe', 'abf', 'abg'
				autoTextComplete(node.getChild(ch), prefixStringCollection, result, limit);
				prefixStringCollection.remove(prefixStringCollection.size() - 1);
			}
		}

	}

	/**
	 * Gets the index for special character.
	 *
	 * @param ch the ch
	 * @return the index for special character
	 */
	public static Integer getIndexForSpecialCharacter(final char ch) {
		return specialCharacterMap.get(ch);
	}

	/**
	 * Sets the special chacters. For Unit Testing.
	 *
	 * @param specialChacters the new special chacters
	 */
	public void setSpecialChacters(String specialChacters) {
		this.specialChacters = specialChacters;
	}

	/**
	 * Sets the regex. For unit Testing.
	 *
	 * @param regex the new regex
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}
}
