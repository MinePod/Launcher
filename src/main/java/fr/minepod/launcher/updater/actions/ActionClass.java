package fr.minepod.launcher.updater.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.minepod.launcher.Utils;
import fr.minepod.launcher.json.CustomJSONObject;

public class ActionClass {
  String action;
  Map<Object, Object> args;

  public ActionClass(CustomJSONObject json) {
    this(json.getString("action"), json.getCustomJSONObject("args").getMap());
  }

  public ActionClass(String action, Map<?, ?> args) {
    this.action = action;

    Map<Object, Object> filled = new HashMap<>();
    for (Entry<?, ?> entry : args.entrySet()) {
      Object key = entry.getKey();
      Object value = entry.getValue();

      if (value instanceof String) {
        value = (Object) Utils.fillLocations((String) value, true);
      }

      filled.put(key, value);
    }

    this.args = filled;
  }

  public String getAction() {
    return action;
  }

  public Map<Object, Object> getArgs() {
    return args;
  }
}
