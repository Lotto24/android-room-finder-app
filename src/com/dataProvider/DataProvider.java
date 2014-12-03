package com.dataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.util.Log;

public class DataProvider {
	
	String TAG = DataProvider.class.getName().toString();
	
	private Map<String, String> getData() {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("Peter Papnase", "AF001");
		map.put("Peter Ludwig", "AF002");
		map.put("Paul Löwe", "AF003");
		map.put("Martin Papnase", "BF001");
		map.put("Michael Meyer", "BF001");
		map.put("Klaus Kaufmann", "GF001");
		map.put("Marita Meyer", "HF001");
		
		return map;
	}
	

	
	public String getRoomByName(String name) {
		String room = null;	
		Map<String, String> map = getData();
		room = map.get(name);
		return room;
	}
	
	
	public boolean isValueName (String value) {
		
		if (value.matches(".*\\d+.*")) {
			return false;
		} else {
			return true;
		}

	}
	
	public String[] getSearchList() {
		ArrayList<String> newList = new ArrayList<String>();
		newList = apendNames(newList);
		newList = apendRooms(newList);
		
		return newList.toArray(new String[newList.size()]);		
	}
	
	
	private ArrayList<String> apendNames(ArrayList<String> currentList) {
		
		Map<String, String> map = getData();
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			String currentName = it.next();
			Log.i(TAG, "DataProvider - apendNames: " + currentName);
			currentList.add(currentName);
		}
		return currentList;
	}
	
	private ArrayList<String> apendRooms(ArrayList<String> currentList) {
		
		Map<String, String> map = getData();
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			String currentRoom = map.get(it.next());
			Log.i(TAG, "DataProvider - apendRooms: " + currentRoom);
			currentList.add(currentRoom);
		}
		
		return currentList;
	}

}
