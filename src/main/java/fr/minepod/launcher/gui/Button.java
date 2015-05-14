package fr.minepod.launcher.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;

import org.json.simple.parser.ParseException;

import de.schlichtherle.truezip.file.TVFS;
import fr.minepod.launcher.LauncherConfig;
import fr.minepod.launcher.CrashReport;
import fr.minepod.launcher.LaunchJar;
import fr.minepod.launcher.Launcher;
import fr.minepod.launcher.updater.versions.VersionClass;

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

    if (Launcher.versionsUpdater != null) {
      final VersionClass version = Launcher.gui.getSelectedVersion();
      if (version.isSkip()) {
        CrashReport.show("Cette version est désactivée.", false);

        setEnabled(true);
      } else {
        LauncherConfig.logger.info("Launching version installing...");
        Launcher.gui.update(0);

        new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              Launcher.versionsUpdater.installVersion(Launcher.gui, version);
              TVFS.umount();

              Launcher.gui.setLoading(true);

              LauncherConfig.logger.info("Launching game soon...");
              new LaunchJar(LauncherConfig.launcherMinecraftJar);
            } catch (IOException | ParseException | InterruptedException | NoSuchAlgorithmException e) {
              CrashReport.show(e);
            }
          }
        }).start();
      }
    } else {
      CrashReport.show("Erreur de lancement, relancez le launcher.");
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
