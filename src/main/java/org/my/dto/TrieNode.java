package org.my.dto;

import java.util.Arrays;

import org.my.config.AppConstants;
import org.my.service.impl.TrieServiceImpl;

/**
 * The Class TrieNode.
 */
public class TrieNode {

	/** The children. 
	 *  It stores the children which represents each character from A-Z and one special characters.
	 * */
	private TrieNode[] children;
    
    /** The is word. */
    boolean isWord;

    public TrieNode(int characterCount) {
    	children = new TrieNode[characterCount];
    }

    /**
     * Checks if the provided character exists in the children of the current node.
     *
     * @param ch the input character
     * @return true, if character exists otherwise false
     */
    public boolean containsKey(char ch) {
    	return getChild(ch) != null ? true : false;
        
    }

    /**
     * Gets the child.
     *
     * @param ch the ch
     * @return the child
     */
    public TrieNode getChild(char ch) {
    	// First check if the input is special character. It is because special chacter should be placed at a fixed index.
    	Integer index = TrieServiceImpl.getIndexForSpecialCharacter(ch);
    	if(index == null) {
    		index = ch - AppConstants.START_OF_INDEX;
    		if(index >=0 && index<children.length) {
    			return children[index];    			
    		} else {
    			return null;
    		}
    	} else {
    		return children[index];
    	}
        
    }

    /**
     * Put.
     *
     * @param ch the ch
     * @param node the node
     */
    public void put(char ch, TrieNode node) {
    	// Putting special character at a fixed index of the children array.
    	Integer index = TrieServiceImpl.getIndexForSpecialCharacter(ch);
    	if(index == null) {
    		children[ch - AppConstants.START_OF_INDEX] = node;	
    	} else {
    		children[index] = node;
    	}
        
    }

    /**
     * Sets the word. It Marks that particular node presents a complete word.
     */
    public void setWord() {
        isWord = true;
    }

    /**
     * Checks if is current node represents a complete word.
     *
     * @return true, if is word
     */
    public boolean isWord() {
        return isWord;
    }

    /**
     * Checks for children. Iterate over the children array to check if any children is present or not.
     *
     * @param node the node
     * @return true, if successful
     */
    public boolean hasChildren(TrieNode node) {
        for (int i = 0; i < children.length; i++) {
            if (node.containsKey((char) (i + AppConstants.START_OF_INDEX))) {
                return true;
            }
        }
        return false;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(children);
		result = prime * result + (isWord ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrieNode other = (TrieNode) obj;
		if (!Arrays.equals(children, other.children))
			return false;
		if (isWord != other.isWord)
			return false;
		return true;
	}

}