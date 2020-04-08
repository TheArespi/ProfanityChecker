package com.DesAlgo.Algorithm;

import java.util.*;
import java.io.*;

public class PMM {
	
	public static void printPMM(State startingState) {
		for (int i = 0; i < startingState.depth; i++)
			System.out.print("-----");
		System.out.print(startingState.letter);
		System.out.print(startingState.output);
		System.out.println(startingState.jump);
		for (int i = 0; i < startingState.children.size(); i++)
			printPMM(startingState.children.get(i));
	}
	
	public static void outputPMM(State startingState) throws Exception{
		File outputFile = new File("output.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		
		outputPMMStates(startingState,bw);
		
		bw.close();
	}
	
	private static void outputPMMStates(State currentState, BufferedWriter bw) throws Exception{
		for (int i = 0; i < currentState.depth; i++)
			bw.write("-----");
		bw.write(currentState.letter);
		bw.write("[");
		for (String word : currentState.output)
			bw.write("(" + word + ")");
		bw.write("] : {");
		for (Map.Entry<Character, Integer> entry : currentState.jump.entrySet())
			bw.write(entry.getKey() + "=" + entry.getValue() + ";");
		bw.write("}\n");
		for (int i = 0; i < currentState.children.size(); i++)
			outputPMMStates(currentState.children.get(i),bw);
	}
	
	private static List<String> getPatterns() throws Exception{
		File patternFile = new File("pattern.txt");
		BufferedReader br = new BufferedReader(new FileReader(patternFile));
		
		List<String> patterns = new ArrayList<>();
		String pattern = br.readLine();
		
		while(pattern != null) {
			patterns.add(pattern);
			pattern = br.readLine();
		}
		br.close();
		return patterns;
	}
	
	public static void buildPMM() throws Exception{
		
		List<String> pattern = getPatterns();
			
		State.minLength = pattern.get(0).length();
		for (int i = 1; i < pattern.size(); i++)
			State.minLength = Math.min(State.minLength, pattern.get(i).length());
			
		State startingState = State.createStartingState();
		
		HashMap<State, State> fail = new HashMap<>();
		
		buildTrie(startingState,pattern);
		fail = buildAux(startingState);
		buildJump(startingState,pattern,fail);
		
		File pmm = new File("pmm.file");
		if (pmm.exists())
			pmm.delete();
		else
			pmm.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(pmm));
		
		bw.write(State.leftState.size() + "\n");
		for (Map.Entry<String, State> entry : State.leftState.entrySet()) {
			bw.write(entry.getKey() + "\n");
			bw.write(entry.getValue().stateID + "\n");
		}
		bw.write(State.minLength + "\n");
		bw.write(State.numState + "\n");
		
		outputState(startingState,bw);
		
		bw.close();
	}
	
	private static void outputState(State startingState, BufferedWriter bw) throws Exception{
		 bw.write(startingState.stateID + "\n");
		 bw.write(startingState.letter + "\n");
		 bw.write(startingState.depth + "\n");
		 bw.write(startingState.parent.stateID + "\n");
		 bw.write(startingState.output.size() + "\n");
		 for (String output : startingState.output)
			 bw.write(output + "\n");
		 bw.write(startingState.children.size() + "\n");
		 for (State child : startingState.children)
			 bw.write(child.stateID + "\n");
		 bw.write(startingState.childrenCharacters.size() + "\n");
		 for (char childCharacter : startingState.childrenCharacters)
			 bw.write(childCharacter + "\n");
		 bw.write(startingState.jump.size() + "\n");
		 for (Map.Entry<Character, Integer> entry : startingState.jump.entrySet()){
			 bw.write(entry.getKey() + "\n");
			 bw.write(entry.getValue() + "\n");
		 }
		 
		 for (State child : startingState.children)
			 outputState(child,bw);
	}
	
	public static State loadPMM() throws Exception{
		State startingState = State.createStartingState();
		
		File pmm = new File("pmm.file");
		BufferedReader br = new BufferedReader(new FileReader(pmm));
		
		HashMap<Integer, String> notFoundLeftStates = new HashMap<>();
		int leftStateSize = Integer.parseInt(br.readLine());
		for (int i = 0 ; i < leftStateSize; i++) {
			String word = br.readLine();
			int leftStateID = Integer.parseInt(br.readLine());
			notFoundLeftStates.put(leftStateID,word);
		}
		
		State.minLength = Integer.parseInt(br.readLine());
		State.numState = Integer.parseInt(br.readLine());
			
		HashMap<Integer, State> lostChild = new HashMap<>(); 
		HashMap<Integer, State> stateIDs = new HashMap<>();
		
		inputState(startingState,br,lostChild, stateIDs, notFoundLeftStates);
		
		br.close();
		
		return startingState;
	}
	
	private static void inputState(State startingState, BufferedReader br, HashMap<Integer, State> lostChild, HashMap<Integer, State> stateIDs, HashMap<Integer, String> notFoundLeftStates) throws Exception {
		String id = br.readLine();
		if (id == null)
			return;
		startingState.stateID = Integer.parseInt(id);
		stateIDs.put(startingState.stateID, startingState);
		startingState.letter = br.readLine().charAt(0);
		startingState.depth  = Integer.parseInt(br.readLine());
		startingState.parent = stateIDs.get(Integer.parseInt(br.readLine()));
		int outputSize = Integer.parseInt(br.readLine());
		for (int i = 0; i < outputSize; i++)
			startingState.output.add(br.readLine());
		int childrenSize =  Integer.parseInt(br.readLine());
		for (int i = 0; i < childrenSize; i++)
			lostChild.put(Integer.parseInt(br.readLine()),startingState);
		int childrenCharSize = Integer.parseInt(br.readLine());
		for (int i = 0; i < childrenCharSize; i++)
			startingState.childrenCharacters.add(br.readLine().charAt(0));
		int jumpSize = Integer.parseInt(br.readLine());
		for (int i = 0; i < jumpSize; i++) {
			startingState.jump.put(br.readLine().charAt(0), Integer.parseInt(br.readLine()));
		}
		if (lostChild.containsKey(startingState.stateID)) {
			lostChild.get(startingState.stateID).children.add(startingState);
			lostChild.remove(startingState.stateID);
		}
		
		if (notFoundLeftStates.containsKey(startingState.stateID)) {
			State.leftState.put(notFoundLeftStates.get(startingState.stateID), startingState);
		}
		
		State newState = new State();
		inputState(newState,br,lostChild,stateIDs,notFoundLeftStates);
	}
	
	private static void buildTrie(State startingState, List<String> pattern) {
		State currentState;
		int j;
		
		for (int i = 0; i < pattern.size(); i++) {
			currentState = startingState;
			j = pattern.get(i).length() - 1;
			while(j >= 0 && currentState.childrenCharacters.contains(Character.toUpperCase(pattern.get(i).charAt(j)))) {
				currentState = currentState.getChild(Character.toUpperCase(pattern.get(i).charAt(j)));
				j--;
			}
			while(j > 0) {
				currentState = currentState.addChild(Character.toUpperCase(pattern.get(i).charAt(j)));
				j--;
			}
			if (j == -1)
				currentState.output.add(pattern.get(i));
			else
				currentState = currentState.addChild(Character.toUpperCase(pattern.get(i).charAt(j)),pattern.get(i));
			State.leftState.put(pattern.get(i), currentState);
		}
	}
	
	private static HashMap<State, State> buildAux(State startingState) {
		int totalStates, done;
		State currentState;
		
		HashMap<State, State> fail = new HashMap<>();
		Queue<State> states = new LinkedList<>();
		totalStates = State.numState;
		done = 1;
		
		fail.put(startingState, startingState);
		
		for (int i = 0; i < startingState.children.size(); i++) {
			fail.put(startingState.children.get(i), startingState);
			states.add(startingState.children.get(i));
		}
		
		while(done < totalStates) {
			currentState = states.remove();
			
			if (currentState.parent.depth != 0) {
				fail.put(currentState, fail.get(currentState.parent));
				do {
					if (fail.get(currentState).childrenCharacters.contains(currentState.letter))
						fail.put(currentState,fail.get(currentState).getChild(currentState.letter));
					else
						fail.put(currentState,fail.get(fail.get(currentState)));
				} while(!fail.get(currentState).childrenCharacters.contains(currentState.letter) && fail.get(currentState).depth != 0 && fail.get(currentState).letter != currentState.letter);
				
				for (int i = 0; i < fail.get(currentState).output.size(); i++)
					currentState.output.add(fail.get(currentState).output.get(i));
			}
			
			for (int i = 0; i < currentState.children.size(); i++)
				states.add(currentState.children.get(i));
			
			done++;	
		}
		
		return fail;
	}
	
	private static void buildJump(State startingState, List<String> pattern, HashMap<State, State> fail) {
		int totalStates, done, jump, minLen;
		State currentState;
		
		Queue<State> states = new LinkedList<>();
		totalStates = State.numState;
		done = 0;
		states.add(startingState);
		
		while(done < totalStates) {
			currentState = states.remove();
			
			for (int i = 0; i < currentState.children.size(); i++)
				states.add(currentState.children.get(i));
			
			for (char i = 'A'; i < 'Z'; i++) {
				jump = Math.min(jump1(currentState,pattern,i),jump2(currentState,pattern,fail));
				if (jump < State.minLength)
					currentState.jump.put(i, jump);
			}
			
			done++;
		}
	}
	
	private static int jump1(State currentState, List<String> pattern, char letter) {
		int jump = State.minLength;
		
		String word = buildWord(currentState,Character.toString(letter));
		word = word.toUpperCase();
		
		for (int i = 0; i < pattern.size(); i++) {
			for (int j = 1; j <= pattern.get(i).length() - word.length(); j++) {
				if (pattern.get(i).substring(pattern.get(i).length() - word.length() - j,pattern.get(i).length()  - j).toUpperCase().contentEquals(word)) {
					jump = Math.min(jump,j);
					break;
				}
			}
		}
		
		return jump;
	}
	
	private static int jump2(State currentState, List<String> pattern, HashMap<State, State> fail) {
		int jump = State.minLength;
		Set<State> prefix = new HashSet<>();
		State wordState = currentState;
		while(wordState.depth > 0) {
			prefix.add(wordState);
			wordState = wordState.parent;
		}
		for (int i = 0 ; i < pattern.size(); i++) {
			if (prefix.contains(fail.get(State.leftState.get(pattern.get(i)))))
				jump = Math.min(jump, pattern.get(i).length() - fail.get(State.leftState.get(pattern.get(i))).depth);
		}
		return jump;
	}
	
	private static String buildWord(State currentState, String word) {
		if (currentState.depth == 0)
			return word;
		else
			return buildWord(currentState.parent,word + currentState.letter);
	}
}
