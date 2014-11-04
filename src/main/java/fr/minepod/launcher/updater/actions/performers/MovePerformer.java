package fr.minepod.launcher.updater.actions.performers;

import java.util.Map;

import fr.minepod.launcher.updater.actions.ActionClass;
import fr.minepod.launcher.updater.actions.PerformerException;
import fr.minepod.utils.UtilsFiles;

public class MovePerformer extends AbstractPerformer {
  public MovePerformer(ActionClass action) {
    super(action);
  }


  public MovePerformer(String action, Map<Object, Object> args) {
    super(action, args);
  }

  @Override
  public void run() throws PerformerException {
    new UtilsFiles().moveFile((String) this.getArgs().get("input"),
        (String) this.getArgs().get("output"));
  }
}
