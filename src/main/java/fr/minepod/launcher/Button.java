package fr.minepod.launcher;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class Button extends JButton implements MouseListener {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public Button(String str) {
    super(str);
    this.addMouseListener(this);
    this.setEnabled(false);
  }

  public void enableButton() {
    this.setEnabled(true);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Config.logger.info("Lauching game soon...");
    this.setEnabled(false);
    Launcher.launchGame();
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }
}
