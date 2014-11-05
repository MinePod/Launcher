package fr.minepod.launcher.updater.actions.performers;

import java.io.File;
import java.util.Map;

import fr.minepod.launcher.updater.actions.ActionClass;
import fr.minepod.launcher.updater.actions.PerformerException;

public class MkdirPerformer extends AbstractPerformer {
  public MkdirPerformer(ActionClass action) {
    super(action);
  }

  public MkdirPerformer(String action, Map<Object, Object> args) {
    super(action, args);
  }

  @Override
  public void run() throws PerformerException {
    try {
      new File((String) this.getArgs().get("input")).mkdirs();
    } catch (Exception e) {
      throw new PerformerException(e);
    }
  }
}
