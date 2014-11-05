package fr.minepod.launcher.updater.actions;

import java.util.logging.Logger;

import fr.minepod.launcher.updater.actions.performers.CopyPerformer;
import fr.minepod.launcher.updater.actions.performers.DeletePerformer;
import fr.minepod.launcher.updater.actions.performers.InjectPerformer;
import fr.minepod.launcher.updater.actions.performers.MovePerformer;
import fr.minepod.launcher.updater.actions.performers.UnZipPerformer;

public class ActionPerformerThread extends Thread {
  Logger logger;
  ActionClass action;

  Exception exception;

  public ActionPerformerThread(Logger logger, ActionClass action) {
    this.logger = logger;
    this.action = action;
  }

  public void run() {
    try {
      if (action.getAction().equalsIgnoreCase("copy")) {
        new CopyPerformer(action).run();
      } else if (action.getAction().equalsIgnoreCase("delete")) {
        new DeletePerformer(action).run();
      } else if (action.getAction().equalsIgnoreCase("inject")) {
        new InjectPerformer(action).run();
      } else if (action.getAction().equalsIgnoreCase("move")) {
        new MovePerformer(action).run();
      } else if (action.getAction().equalsIgnoreCase("unzip")) {
        new UnZipPerformer(action).run();
      }
    } catch (PerformerException e) {
      exception = e;
    }
  }

  public Exception getException() {
    return exception;
  }
}
