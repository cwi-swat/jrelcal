/*
 * Created on Jan 28, 2005
 */
package jrelcal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author bruntink
 */
public class RSFReader extends BufferedReader {
	private File RSFFile;

	/**
	 * @param file
	 */
	public RSFReader(Reader reader) {
		super(reader);
	}
	
	public HashMap<String, Relation<String,String>> getNamedRelations() throws IOException, RSFFormatException {
		HashMap<String, Relation<String,String>> map = new HashMap<String, Relation<String,String>>();
		
		String[] strings = parseLine();

		while(strings != null) {
			String relationName = strings[0];
			Pair<String,String> pair = new Pair<String, String>(strings[1], strings[2]);
				
			if(map.containsKey(relationName)) {
				map.get(relationName).add(pair);
			}
			else {
				Relation<String,String> newRelation = new Relation<String,String>();
				newRelation.add(pair);
				map.put(relationName, newRelation);
			}
			strings = parseLine();
		}
		
		return map;
	}
	
	public HashMap<String, OrderedSet<Pair<String,String>>> getNamedSets() throws IOException, RSFFormatException {
		HashMap<String, OrderedSet<Pair<String,String>>> map = new HashMap<String, OrderedSet<Pair<String,String>>>();

		String[] strings = parseLine();
		while(strings != null) {
			String setName = strings[0];
			Pair<String,String> pair = new Pair<String, String>(strings[1], strings[2]);
			
			if(map.containsKey(setName)) {
				map.get(setName).add(pair);
			}
			else {
				OrderedSet<Pair<String,String>> newSet = new OrderedSet<Pair<String,String>>();
				newSet.add(pair);
				map.put(setName, newSet);
			}
			strings = parseLine();
		}
		
		return map;
	}
	
	
	public Collection<Relation<String,String>> getRelations() throws IOException, RSFFormatException {
		HashMap<String,Relation<String,String>> map = getNamedRelations();
		return map.values();
	}
	
	private String[] parseLine() throws IOException, RSFFormatException {
		String line = readLine();
		String[] strings = null;
		
		if (line != null) {
			StringTokenizer tokens = new StringTokenizer(line);
			strings = new String[3];
			int numberOfTokens = tokens.countTokens();
			if(numberOfTokens < 3) {	
				throw new RSFFormatException("Too few tokens on line.");
			}
			if(numberOfTokens > 3) {
				throw new RSFFormatException("Too many tokens on line.");
			}
			strings[0] = tokens.nextToken();
			strings[1] = tokens.nextToken();
			strings[2] = tokens.nextToken();
			}
		return strings;
	}
	

}
