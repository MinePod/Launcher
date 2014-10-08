package fr.minepod.launcher;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class Gui {
  private JProgressBar current = new JProgressBar(0, 100);
  private Button play = new Button(Langage.LAUNCHBUTTON.toString());
  private double totalBytesRead = 0.0D;
  private double totalLength = 0.0D;

  public Gui(String changelogPage, String LauncherVersion, String LauncherCompileTime)
      throws IOException {

    JEditorPane page = new JEditorPane();
    page.setContentType("text/html");
    page.setEditable(false);
    page.setPage(Config.launcherChangelogPage);

    current.setValue(0);
    current.setStringPainted(true);

    JFrame j =
        new JFrame("MinePod Launcher - Salsepareille " + LauncherVersion + " "
            + LauncherCompileTime);

    JPanel b1 = new JPanel();
    b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
    b1.add(page);

    JPanel b2 = new JPanel();
    b2.setLayout(new BoxLayout(b2, BoxLayout.LINE_AXIS));
    b2.add(current);
    b2.add(play);

    JPanel b3 = new JPanel();
    b3.setLayout(new BoxLayout(b3, BoxLayout.PAGE_AXIS));
    b3.add(new JScrollPane(page));
    b3.add(b1);
    b3.add(b2);

    j.setContentPane(b3);

    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    j.setSize(new Dimension(800, 600));
    j.setLocationRelativeTo(null);
    j.setVisible(true);
  }

  public void enableButton() {
    play.enableButton();
  }

  public void update(int UpdateNumber) {
    current.setValue(UpdateNumber);
  }

  public void setMax(Double fileLength) {
    totalLength += fileLength;
  }

  public void add(int bytesRead) {
    totalBytesRead += bytesRead;
    update(((int) Math.round(totalBytesRead / totalLength * 100.0D)));
  }

  public void setLoading(boolean loading) {
    current.setIndeterminate(loading);
  }

  public void finish() {
    update(100);
  }
}
