package fr.minepod.launcher.updater.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.minepod.launcher.CrashReport;
import fr.minepod.launcher.json.CustomJSONObject;

public class ActionPerformer {
  public ActionPerformer(Logger logger, CustomJSONObject json) throws InterruptedException {
    List<ActionPerformerThread> threads = new ArrayList<>();

    JSONArray files = json.getArray("actions");

    Iterator<?> iterator = files.iterator();
    while (iterator.hasNext()) {
      ActionClass action = new ActionClass(new CustomJSONObject((JSONObject) iterator.next()));

      logger.info("Action: " + action.getAction());
      logger.info("Args number: " + action.getArgs().size());

      ActionPerformerThread thread = new ActionPerformerThread(logger, action);
      thread.start();

      threads.add(thread);
    }

    for (ActionPerformerThread thread : threads) {
      thread.join();
      
      if(thread.getException() != null) {
        CrashReport.show(thread.getException());
      }
    }
  }
}
