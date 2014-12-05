package com.dataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class DataProvider {
	
	String TAG = DataProvider.class.getName().toString();
	//private Map<String, String> map = new HashMap<String, String>();
	
//	DataProvider() {
//		this.map = new HashMap<String, String>();
//	}
	
//	private Map<String, String> getData() {
//		
//		
//		map.put("Peter Papnase", "AF001");
//		map.put("Peter Ludwig", "AF002");
//		map.put("Paul Löwe", "AF003");
//		map.put("Martin Papnase", "BF001");
//		map.put("Michael Meyer", "BF001");
//		map.put("Klaus Kaufmann", "GF001");
//		map.put("Marita Meyer", "HF001");
//		
//		return map;
//	}
	
	public void addNewEntry(String line, Activity activity) {
		if (line.contains(":")) {
			
			String[] parts = line.split(":");
			String name = parts[0];
			String room = parts[1];
			
			name = name.replace(name.substring(name.length()-1), "");
			room = room.replace(room.substring(room.length()-2), "");
			room = room.substring(1);
			
			//Log.i(TAG, "SplashScreenActivity - addNewEntry for name: " + name);
			//Log.i(TAG, "SplashScreenActivity - addNewEntry for room: " + room);	
			
			//map.put(name, room);
			storeDateByName (name, room, activity);
			storeDateByRoom (room, name, activity);
			
		}
		
		//Log.i(TAG, "SplashScreenActivity - content of current map:" + map.toString());
	}
	
	
	
	public String getRoomByName(String name, Activity activity) {
			
		String room = readRoomByName(name, activity);	
		//room = map.get(name);
		return room;
	}
	
	
	public boolean isValueName (String value) {
		
		if (value.matches(".*\\d+.*")) {
			return false;
		} else {
			return true;
		}

	}
	
	public String[] getSearchList(Activity activity) {
		
		Log.i(TAG, "SplashScreenActivity - getSearchList reached");
		
		ArrayList<String> newList = new ArrayList<String>();
		newList = apendNames(newList, activity);
		newList = apendRooms(newList, activity);
		
		return newList.toArray(new String[newList.size()]);		
	}
	
	
	private ArrayList<String> apendNames(ArrayList<String> currentList, Activity activity) {
		Log.i(TAG, "SplashScreenActivity - apendNames reached");
		
		SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
		Map<String, String> map = new HashMap<String, String>();
		map = (Map<String, String>) settings.getAll();
				
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			String currentName = it.next();
			//Log.i(TAG, "SplashScreenActivity - apendNames: " + currentName);
			currentList.add(currentName);
		}
		return currentList;
	}
	
	private ArrayList<String> apendRooms(ArrayList<String> currentList, Activity activity) {
		Log.i(TAG, "SplashScreenActivity - apendRooms reached");
		
		SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
		Map<String, String> map = new HashMap<String, String>();
		map = (Map<String, String>) settings.getAll();

		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			String currentRoom = map.get(it.next());
			//Log.i(TAG, "SplashScreenActivity - apendRooms: " + currentRoom);
			currentList.add(currentRoom);
		}
		
		return currentList;
	}
	
	private static void storeDateByName (String name, String room, Activity activity) {
		
		SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);				
		SharedPreferences.Editor editor = settings.edit();		
		editor.putString(name, room);
		editor.apply(); 
	}
	
	private static void storeDateByRoom (String name, String room, Activity activity) {
		
		SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("ROOM_NAME", 0);				
		SharedPreferences.Editor editor = settings.edit();		
		editor.putString(room, name);
		editor.apply(); 
	}
	
	private static String readNameByRoom (String room, Activity activity) {
				
		SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
		String name = settings.getString(room, "");
		return name;
	}

	private static String readRoomByName (String name, Activity activity) {
	
		SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("ROOM_NAME", 0);		
		String room = settings.getString(name, ""); 
		return room;
	}
	
	public boolean isDataStored(Activity activity) {
		
		SharedPreferences spNameRoom = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
		SharedPreferences spRoomName = activity.getApplicationContext().getSharedPreferences("ROOM_NAME", 0);
		
		Map<String, String> nameRoomMap = new HashMap<String, String>();
		Map<String, String> roomNameMap = new HashMap<String, String>();
		nameRoomMap = (Map<String, String>) spNameRoom.getAll();
		roomNameMap = (Map<String, String>) spRoomName.getAll();
		
		
		if (nameRoomMap.isEmpty() || roomNameMap.isEmpty()) {
			Log.i(TAG, "SplashScreenActivity - No Data stored!!! ");
			return false;
		}
		
		Log.i(TAG, "SplashScreenActivity - Data already stored!!! ");
		
		return true;
		
		
		
	}
	
 

}
