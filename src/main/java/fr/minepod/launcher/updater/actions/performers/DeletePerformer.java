package fr.minepod.launcher.updater.actions.performers;

import java.util.Map;

import fr.minepod.launcher.updater.actions.ActionClass;
import fr.minepod.launcher.updater.actions.PerformerException;
import fr.minepod.utils.UtilsFiles;

public class DeletePerformer extends AbstractPerformer {
  public DeletePerformer(ActionClass action) {
    super(action);
  }

  public DeletePerformer(String action, Map<Object, Object> args) {
    super(action, args);
  }

  @Override
  public void run() throws PerformerException {
    new UtilsFiles().delete((String) this.getArgs().get("input"));
  }
}
