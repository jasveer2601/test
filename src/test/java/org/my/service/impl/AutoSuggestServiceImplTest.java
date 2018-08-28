package org.my.service.impl;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.my.dto.TrieNode;
import org.my.service.ITrieService;

/**
 * The Class AutoSuggestServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class AutoSuggestServiceImplTest {

	/** The target. */
	@InjectMocks
	private AutoSuggestServiceImpl target;
	
	/** The trie service. */
	@Mock
	private ITrieService trieService;
	
	
	
	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		ArgumentCaptor<TrieNode> captor = ArgumentCaptor.forClass(TrieNode.class);
		ArgumentCaptor<String> prefix = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Integer> limit = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(trieService.getPrefixedStrings(captor.capture(), prefix.capture(), limit.capture())).thenReturn(Arrays.asList("ABC", "ABD"));
	}
	
	/**
	 * Test suggest cities.
	 */
	@Test
	public void testSuggestCities() {
		String result =this.target.suggestCities("AB", 2);
		assertEquals("ABC\r\nABD\r\n", result);
	}
}
