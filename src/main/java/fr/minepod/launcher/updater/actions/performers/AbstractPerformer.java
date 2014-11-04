package fr.minepod.launcher.updater.actions.performers;

import java.util.Map;

import fr.minepod.launcher.updater.actions.ActionClass;
import fr.minepod.launcher.updater.actions.PerformerException;

public abstract class AbstractPerformer {
  private String action;
  private Map<Object, Object> args;

  public AbstractPerformer(ActionClass action) {
    this.action = action.getAction();
    this.args = action.getArgs();
  }

  public AbstractPerformer(String action, Map<Object, Object> args) {
    this.action = action;
    this.args = args;
  }

  public String getAction() {
    return action;
  }

  public Map<Object, Object> getArgs() {
    return args;
  }

  public abstract void run() throws PerformerException;
}
