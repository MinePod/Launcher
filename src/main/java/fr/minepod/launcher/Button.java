package fr.minepod.launcher;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;

import org.json.simple.parser.ParseException;

public class Button extends JButton implements MouseListener {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public Button(String str) {
    super(str);
    addMouseListener(this);
    setEnabled(false);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    setEnabled(false);

    if (Launcher.versionsManager != null) {
      Config.logger.info("Launching version installing...");
      Launcher.gui.update(0);
      Launcher.gui.setLoading(true);

      try {
        Launcher.versionsManager.installVersion(Launcher.gui.getSelectedVersion());
        Config.logger.info("Launching game soon...");
        Launcher.launchGame();
      } catch (IOException | ParseException | InterruptedException | NoSuchAlgorithmException error) {
        CrashReport.show(error.toString());
      }
    } else {
      CrashReport.show("Null VersionsManager");
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}
}
