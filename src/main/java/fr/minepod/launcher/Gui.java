package fr.minepod.launcher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class Gui {
  private JComboBox<String> versions = new JComboBox<>();
  private JProgressBar progress = new JProgressBar(0, 100);
  private Button button = new Button("Jouer");
  private int totalBytesRead = 0;
  private int totalLength = 0;

  private Map<String, VersionClass> versionsList;

  public Gui(Map<String, VersionClass> versionsList) throws IOException {
    this.versionsList = versionsList;

    JEditorPane page = new JEditorPane();
    page.setContentType("text/html");
    page.setEditable(false);
    page.setPage(Config.launcherChangelogPage);

    for (String key : versionsList.keySet()) {
      versions.addItem(key);
    }

    progress.setValue(0);
    progress.setStringPainted(true);

    JFrame j =
        new JFrame("MinePod Launcher - Salsepareille " + Config.launcherVersion + " "
            + Config.launcherBuildTime);

    JPanel top = new JPanel();
    top.setLayout(new BorderLayout());
    top.add(page);
    top.add(new JScrollPane(page));

    JPanel middle = new JPanel();
    // TODO: Avoid this
    middle.setMaximumSize(new Dimension(800, 20));
    middle.setLayout(new BorderLayout());
    middle.add(progress, BorderLayout.CENTER);

    JPanel bottom = new JPanel();
    // TODO: Avoid this
    bottom.setMaximumSize(new Dimension(800, 20));
    bottom.setLayout(new BorderLayout());
    bottom.add(new JLabel("Version: "), BorderLayout.LINE_START);
    bottom.add(versions, BorderLayout.CENTER);
    bottom.add(button, BorderLayout.LINE_END);

    JPanel whole = new JPanel();
    whole.setLayout(new BoxLayout(whole, BoxLayout.PAGE_AXIS));
    whole.add(top);
    whole.add(middle);
    whole.add(bottom);

    j.setContentPane(whole);
    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    j.setSize(new Dimension(800, 600));
    j.setLocationRelativeTo(null);
    j.setVisible(true);
  }

  public VersionClass getSelectedVersion() {
    return versionsList.get(versions.getSelectedItem());
  }

  public void setButtonState(boolean state) {
    button.setEnabled(state);
  }

  public void update(int percent) {
    System.out.println(percent);
    progress.setValue(percent);
    progress.update(progress.getGraphics());
  }

  public void addMax(int fileLength) {
    totalLength += fileLength;
  }

  public void add(int bytesRead) {
    totalBytesRead += bytesRead;
    update((int) ((double) totalBytesRead / (double) totalLength * 100D));
  }

  public void setLoading(boolean loading) {
    progress.setIndeterminate(loading);
    progress.update(progress.getGraphics());
  }

  public void finish() {
    update(100);
  }
}
