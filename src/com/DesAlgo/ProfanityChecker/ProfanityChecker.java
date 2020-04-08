package com.DesAlgo.ProfanityChecker;

import com.DesAlgo.Algorithm.*;
import java.util.*;
import java.io.*;

public class ProfanityChecker {

	public static void main(String[] args) throws Exception{
		PMM.buildPMM();
		String text = "Fuck you you black cock piece of shit God damn you are ugly xxx";
		HashMap<String, List<Integer>> profanityFound = PMM.search(text);
		printAllOccurences(text,profanityFound);
		System.out.println(censor(text,profanityFound));
	} 
	
	public static void printAllOccurences(String text, HashMap<String, List<Integer>> profanityFound) {
		
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
