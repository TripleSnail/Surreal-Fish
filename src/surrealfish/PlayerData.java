/*
 * Copyright (c) 2009-2011 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package surrealfish;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic class to store data about players (Human and AI), could be replaced by
 * a database or similar, static access with synchronization. Access is assured
 * to be sequential during the game, so in theory syncing is not needed. Used on
 * server and on client.
 *
 * @author normenhansen
 *
 * TODO: Many methods use LinkedList instead of ArrayList. Consider converting
 * to ArrayList.
 */
@Serializable
public final class PlayerData {

    public static final String ENTITY_ID = "entity-id";
    public static final String HERO = "hero";
    public static final String NAME = "name";
    private static Map<Integer, PlayerData> players = new HashMap<>();
    private int id;
    private final Map<String, Float> floatData = new HashMap<>();
    private final Map<String, Integer> intData = new HashMap<>();
    private final Map<String, Long> longData = new HashMap<>();
    private final Map<String, Boolean> booleanData = new HashMap<>();
    private final Map<String, String> stringData = new HashMap<>();

    public static synchronized PlayerData getPlayerId(int id) {
        return players.get(id);
    }

    public static synchronized List<PlayerData> getPlayers() {
        List<PlayerData> list = new ArrayList<>(players.values());
        return list;
    }

    public static synchronized void setPlayers(
            List<PlayerData> playerDataList) {
        for (PlayerData playerData : playerDataList) {
            players.put(playerData.getId(), playerData);
        }
    }

    public static synchronized int getNew(String name) {
        int id = 0;
        while (players.containsKey(id)) {
            id++;
        }
        players.put(id, new PlayerData(id, name));
        return id;
    }

    public static synchronized void add(int id, PlayerData player) {
        players.put(id, player);
    }

    public static synchronized void remove(int id) {
        players.remove(id);
    }

    public static synchronized float getFloatData(int id, String key) {
        if (!players.containsKey(id)) {
            return -1;
        }
        return players.get(id).getFloatData(key);
    }

    public static synchronized void setData(int id, String key, float data) {
        if (!players.containsKey(id)) {
            return;
        }
        players.get(id).setData(key, data);
    }

    public static synchronized int getIntData(int id, String key) {
        if (!players.containsKey(id)) {
            return -1;
        }
        Integer data = players.get(id).getIntData(key);
        return data != null ? data.intValue() : -1;
    }

    public static synchronized void setData(int id, String key, int data) {
        if (!players.containsKey(id)) {
            return;
        }
        players.get(id).setData(key, data);
    }

    public static synchronized Long getLongData(int id, String key) {
        if (!players.containsKey(id)) {
            return new Long(-1);
        }
        return players.get(id).getLongData(key);
    }

    public static synchronized void setData(int id, String key, long data) {
        if (!players.containsKey(id)) {
            return;
        }
        players.get(id).setData(key, data);
    }

    public static synchronized Boolean getBooleanData(int id, String key) {
        if (!players.containsKey(id)) {
            return false;
        }
        return players.get(id).getBooleanData(key);
    }

    public static synchronized void setData(int id, String key, boolean data) {
        if (!players.containsKey(id)) {
            return;
        }
        players.get(id).setData(key, data);
    }

    public static synchronized String getStringData(int id, String key) {
        if (!players.containsKey(id)) {
            return "unknown";
        }
        return players.get(id).getStringData(key);
    }

    public static synchronized void setData(int id, String key, String data) {
        if (!players.containsKey(id)) {
            return;
        }
        players.get(id).setData(key, data);
    }

    public static synchronized void setDataForAll(String key, boolean data) {
        for (PlayerData player : PlayerData.getPlayers()) {
            player.setData(key, data);
        }
    }

    public static synchronized void destroyAllData() {
        players.clear();
    }

    public PlayerData() {
    }

    public PlayerData(int id) {
        this.id = id;
    }

    public PlayerData(int id, String name) {
        this.id = id;
        setData("name", name);
        setData("entity-id", -1);
    }

    public int getId() {
        return id;
    }

    public Float getFloatData(String key) {
        return floatData.get(key);
    }

    public void setData(String key, float data) {
        floatData.put(key, data);
    }

    public Integer getIntData(String key) {
        return intData.get(key);
    }

    public void setData(String key, int data) {
        intData.put(key, data);
    }

    public Long getLongData(String key) {
        return longData.get(key);
    }

    public void setData(String key, long data) {
        longData.put(key, data);
    }

    public Boolean getBooleanData(String key) {
        return booleanData.get(key);
    }

    public void setData(String key, boolean data) {
        booleanData.put(key, data);
    }

    public String getStringData(String key) {
        return stringData.get(key);
    }

    public void setData(String key, String data) {
        stringData.put(key, data);
    }
}