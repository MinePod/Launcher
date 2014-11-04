package fr.minepod.launcher.updater.actions.performers;

import java.io.IOException;
import java.util.Map;

import fr.minepod.launcher.updater.actions.ActionClass;
import fr.minepod.launcher.updater.actions.PerformerException;
import fr.minepod.utils.UtilsFiles;

public class InjectPerformer extends AbstractPerformer {
  public InjectPerformer(ActionClass action) {
    super(action);
  }

  public InjectPerformer(String action, Map<Object, Object> args) {
    super(action, args);
  }

  @Override
  public void run() throws PerformerException {
    try {
      new UtilsFiles().mergeZip((String) this.getArgs().get("input"),
          (String) this.getArgs().get("merge"), (String) this.getArgs().get("output"));
    } catch (IOException e) {
      throw new PerformerException(e);
    }
  }
}
