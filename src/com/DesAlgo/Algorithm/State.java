package com.DesAlgo.Algorithm;

import java.util.*;
import java.io.*;

public class State{

	public static HashMap<String, State> leftState = new HashMap<>();
	public static int minLength;
	public static int numState;
	
	public int stateID;
	public char letter;
	public int depth;
	
	public State parent;
	
	public List<String> output = new ArrayList<>();
	public List<State> children = new ArrayList<>();
	public Set<Character> childrenCharacters = new HashSet<>();
	public HashMap<Character, Integer> jump = new HashMap<>();
	
	public static State createStartingState() {
		State startingState = new State();
		startingState.stateID = 0;
		startingState.letter = '$';
		startingState.depth = 0;
		startingState.parent = startingState;
		numState = 1;
		return startingState;
	}
	
	public void copyState(State source) {
		this.letter = source.letter;
		this.depth = source.depth;
		this.parent = source.parent;
		this.output = source.output;
		this.children = source.children;
		this.childrenCharacters = source.childrenCharacters;
		this.jump = source.jump;
	}
	
	public State addChild(char letter) {
		if (!this.childrenCharacters.contains(letter)) {
			State child = new State();
			child.stateID = State.numState;
			child.letter = letter;
			child.depth = this.depth + 1;
			child.parent = this;
			
			this.children.add(child);
			this.childrenCharacters.add(letter);
			State.numState++;
			return child;
		}
		return null;
	}
	
	public State addChild(char letter, String word) {
		if (!this.childrenCharacters.contains(letter)) {
			State child = new State();
			child.stateID = State.numState;
			child.letter = letter;
			child.depth = this.depth + 1;
			child.parent = this;
			child.output.add(word);
			
			this.children.add(child);
			this.childrenCharacters.add(letter);
			State.numState++;
			return child;
		}
		return null;
	}
	
	public State getChild(char letter) {
		for (int i = 0; i < this.children.size(); i++) {
			if (this.children.get(i).letter == letter)
				return this.children.get(i);
		}
		return null;
	}
	
}
