package org.my.service.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.my.dto.TrieNode;

/**
 * The Class TrieServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class TrieServiceImplTest {

	/** The target. */
	@InjectMocks
	private TrieServiceImpl target;
	
	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		target.setRegex("^[A-Za-z ]{0,100}$");
		target.setSpecialChacters(" ");
		target.init();
	}
	
	/**
	 * Test populate trie.
	 */
	@Test
	public void testPopulateTrie() {
		TrieNode trieNode = target.populateTrie(Arrays.asList("ABC", "ABD", "AB1"));
		assertNotNull(trieNode);
		assertEquals(true, trieNode.containsKey('a'));
		assertEquals(true, trieNode.getChild('a').containsKey('b'));
		assertEquals(true, trieNode.getChild('a').getChild('b').containsKey('c'));
		assertEquals(true, trieNode.getChild('a').getChild('b').containsKey('d'));
		assertEquals(false, trieNode.getChild('a').getChild('b').containsKey('1'));
	}
	
	/**
	 * Test get prefixed strings.
	 */
	@Test
	public void testGetPrefixedStrings() {
		TrieNode trieNode = target.populateTrie(Arrays.asList("ABC", "ABD", "AB1"));
		List<String> result = target.getPrefixedStrings(trieNode, "AB", 4);
		assertArrayEquals(new String[] {"abc", "abd"}, result.toArray(new String[result.size()]));
		
		result = target.getPrefixedStrings(trieNode, "AB", 1);
		assertArrayEquals(new String[] {"abc"}, result.toArray(new String[result.size()]));
		
		result = target.getPrefixedStrings(trieNode, "ABC", 1);
		assertArrayEquals(new String[] {"abc"}, result.toArray(new String[result.size()]));
		
		result = target.getPrefixedStrings(trieNode, "EF", 1);
		assertArrayEquals(new String[] {}, result.toArray(new String[result.size()]));
	}
	
	/**
	 * Test get prefixed strings with invalid value.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetPrefixedStringsWithInvalidValue() {
		TrieNode trieNode = target.populateTrie(Arrays.asList("ABC", "ABD", "AB1"));
		target.getPrefixedStrings(trieNode, "AB1", 4);
	}
	
}
