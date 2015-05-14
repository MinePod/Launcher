package fr.minepod.launcher.updater.actions.performers;

import java.util.Map;

import fr.minepod.launcher.updater.actions.ActionClass;
import fr.minepod.launcher.updater.actions.PerformerException;
import fr.minepod.utils.UtilsArchive;

public class UnZipPerformer extends AbstractPerformer {
  public UnZipPerformer(ActionClass action) {
    super(action);
  }


  public UnZipPerformer(String action, Map<Object, Object> args) {
    super(action, args);
  }

  @Override
  public void run() throws PerformerException {
    try {
      new UtilsArchive().unZip((String) this.getArgs().get("input"),
          (String) this.getArgs().get("output"));
    } catch (Exception e) {
      throw new PerformerException(e);
    }
  }
}
