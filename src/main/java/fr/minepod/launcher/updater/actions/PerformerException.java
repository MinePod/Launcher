package fr.minepod.launcher.updater.actions;

import fr.minepod.launcher.Utils;

public class PerformerException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 459134494486874817L;

  public PerformerException(Exception e) {
    super(Utils.getStackTrace(e));
  }
}
