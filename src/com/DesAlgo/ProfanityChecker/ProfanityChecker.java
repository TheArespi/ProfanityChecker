package com.DesAlgo.ProfanityChecker;

import com.DesAlgo.Algorithm.*;
import java.util.*;
import java.io.*;

public class ProfanityChecker {

	public static void main(String[] args) throws Exception{
		PMM.buildPMM();
		String text = "Fuck you you black cock piece of shit God damn you are ugly xxx";
		HashMap<String, List<Integer>> profanityFound = search(text);
		outputAllOccurences(text,profanityFound);
		System.out.println(censor(text,profanityFound));
	} 
	
	public static HashMap<String, List<Integer>> search(String text) throws Exception{
		State startingState, currentState;
		int i, j;
		
		startingState = PMM.loadPMM();
		
		HashMap<String, List<Integer>> found = new HashMap<>();
		currentState = startingState;
		
		i = State.minLength - 1;
		
		while(i < text.length()) {
			j = i;
			while(j != -1 && currentState.childrenCharacters.contains(Character.toUpperCase(text.charAt(j)))) {
				currentState = currentState.getChild(Character.toUpperCase(text.charAt(j)));
				for (int wordNum = 0; wordNum < currentState.output.size(); wordNum++) {
					if (!found.containsKey(currentState.output.get(wordNum)))
						found.put(currentState.output.get(wordNum), new ArrayList<>());
					if (!found.get(currentState.output.get(wordNum)).contains(j))
						found.get(currentState.output.get(wordNum)).add(j);
				}
				j--;
			}
			if (j < 0)
				j = 0;
			if (currentState.jump.containsKey(Character.toUpperCase(text.charAt(j))))
				i += currentState.jump.get(Character.toUpperCase(text.charAt(j)));
			else if (currentState.children.size() == 0 && currentState.jump.size() > 0) {
				for (Map.Entry<Character, Integer> entry : currentState.jump.entrySet()){
					i += currentState.jump.get(entry.getKey());
					break;
				}
			} else
				i += State.minLength;
			currentState = startingState;
		}
		
		return found;
	}
	
	public static void outputAllOccurences(String text, HashMap<String, List<Integer>> profanityFound) {
		
		for (Map.Entry<String, List<Integer>> entry : profanityFound.entrySet()) {
			System.out.println("\"" + entry.getKey() + "\" is found at: ");
			for (Integer index : entry.getValue()) {
				System.out.println("\t[" + index + "] \"" + text.substring(Math.max(0, index - 10),Math.min(text.length(), index + entry.getKey().length() + 10)) + "\"");		
			}
		}
	}
	
	public static String censor(String text, HashMap<String, List<Integer>> profanityFound) {
		
		StringBuilder censoredText = new StringBuilder(text);
		
		for(Map.Entry<String, List<Integer>> entry : profanityFound.entrySet()) {
			for (Integer index : entry.getValue()) {
				for (int i = 0; i < entry.getKey().length(); i++) 
					censoredText.setCharAt(index + i, '*');
			}
		}
		
		return censoredText.toString();
	}
}
