package fr.minepod.launcher.json;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CustomJSONObject {
  JSONObject json;

  public CustomJSONObject(Object json) {
    this((JSONObject) json);
  }

  public CustomJSONObject(JSONObject json) {
    this.json = json;
  }

  public JSONObject getJSON() {
    return json;
  }

  public CustomJSONObject getCustomJSONObject(Object key) {
    return new CustomJSONObject(json.get(key));
  }

  public String getString(Object key) {
    return (String) json.get(key);
  }

  public int getInt(Object key) {
    return (int) json.get(key);
  }

  public JSONArray getArray(Object key) {
    return (JSONArray) json.get(key);
  }

  public Map<?, ?> getMap() {
    return json;
  }
}
