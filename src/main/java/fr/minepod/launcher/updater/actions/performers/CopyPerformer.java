package fr.minepod.launcher.updater.actions.performers;

import java.io.IOException;
import java.util.Map;

import fr.minepod.launcher.updater.actions.ActionClass;
import fr.minepod.launcher.updater.actions.PerformerException;
import fr.minepod.utils.UtilsFiles;

public class CopyPerformer extends AbstractPerformer {
  public CopyPerformer(ActionClass action) {
    super(action);
  }

  public CopyPerformer(String action, Map<Object, Object> args) {
    super(action, args);
  }

  @Override
  public void run() throws PerformerException {
    try {
      new UtilsFiles().copyFile((String) this.getArgs().get("input"),
          (String) this.getArgs().get("output"));
    } catch (IOException e) {
      throw new PerformerException(e);
    }
  }
}
