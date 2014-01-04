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
	
	public void EnableButton() {
		this.play.EnableButton();
	}

	public Gui(String changelogPage, String LauncherVersion, String LauncherCompileTime) {

		JEditorPane page = new JEditorPane();
		page.setContentType("text/html");
		page.setEditable(false);
		try {
			page.setPage(Config.LauncherChangelogPage);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		this.current.setValue(0);
		this.current.setStringPainted(true);

		JFrame j = new JFrame("MinePod Launcher - Salsepareille " + LauncherVersion + " " + LauncherCompileTime);

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

	public void Update(int UpdateNumber) {
		this.current.setIndeterminate(false);
		this.current.setValue(UpdateNumber);
	}
	
	public void SetMax(Double fileLength) {
		this.totalLength += fileLength;
	}
	
	public void Add(int bytesRead) {
		this.totalBytesRead += bytesRead;
		Update(((int) Math.round(totalBytesRead / totalLength * 100.0D)));
	}
	
	public void SetLoading() {
		this.current.setIndeterminate(true);
	}
	
	public void Finish() {
		Update(100);
	}

}