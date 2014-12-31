/*
 * Copyright 2014 eSailors IT Solutions GmbH
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.data;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

public class DataProvider {

    private static void storeDateByName(String name, String room, Activity activity) {

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(name, room);
        editor.apply();
    }

    private static void storeDateByRoom(String name, String room, Activity activity) {

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("ROOM_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(room, name);
        editor.apply();
    }

    private static String readNameByRoom(String room, Activity activity) {

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
        String name = settings.getString(room, "");
        return name;
    }

    private static String readRoomByName(String name, Activity activity) {

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("ROOM_NAME", 0);
        String room = settings.getString(name, "");
        return room;
    }

    public void addNewEntry(String line, Activity activity) {

        if (line.contains(":")) {

            String[] parts = line.split(":");
            String name = parts[0];
            String room = parts[1];

            name = name.replace(name.substring(name.length() - 1), "");
            room = room.replace(room.substring(room.length() - 2), "");
            room = room.substring(1);

            storeDateByName(name, room, activity);
            storeDateByRoom(room, name, activity);

		}
	}
	
	public String getRoomByName(String name, Activity activity) {
        return readRoomByName(name, activity);
    }

    public boolean isValueName(String value) {

        if (value.matches(".*\\d+.*")) {
            return false;
        } else {
            return true;
        }

	}

    public String[] getSearchList(Activity activity) {
        ArrayList<String> newList = new ArrayList<String>();
        newList = apendNames(newList, activity);
        newList = apendRooms(newList, activity);
        return newList.toArray(new String[newList.size()]);
    }

    private ArrayList<String> apendNames(ArrayList<String> currentList, Activity activity) {
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
        Map<String, String> map = new HashMap<String, String>();
        map = (Map<String, String>) settings.getAll();

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String currentName = it.next();
            currentList.add(currentName);
        }
        return currentList;
    }

    private ArrayList<String> apendRooms(ArrayList<String> currentList, Activity activity) {
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
        Map<String, String> map = new HashMap<String, String>();
        map = (Map<String, String>) settings.getAll();

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String currentRoom = map.get(it.next());
            currentList.add(currentRoom);
        }

        return currentList;
    }

    public boolean isDataStored(Activity activity) {
		
		SharedPreferences spNameRoom = activity.getApplicationContext().getSharedPreferences("NAME_ROOM", 0);
		SharedPreferences spRoomName = activity.getApplicationContext().getSharedPreferences("ROOM_NAME", 0);
		
		Map<String, String> nameRoomMap = new HashMap<String, String>();
		Map<String, String> roomNameMap = new HashMap<String, String>();
		nameRoomMap = (Map<String, String>) spNameRoom.getAll();
		roomNameMap = (Map<String, String>) spRoomName.getAll();
		
		
		if (nameRoomMap.isEmpty() || roomNameMap.isEmpty()) {
            Timber.d("No room map data is stored.");
			return false;
		}
        Timber.d("room map is stored.");
		return true;

	}

}
