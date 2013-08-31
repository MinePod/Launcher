package fr.minepod.launcher;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
 
public class Button extends JButton implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String LauncherMinecraftJar;
	private String LauncherLocation;
	
	public Button(String str){
		super(str);
		this.addMouseListener(this);
		this.setEnabled(false);
	}
	
	public void EnableButton(String LauncherMinecraftJar, String LauncherLocation) {
		this.setEnabled(true);
		this.LauncherMinecraftJar = LauncherMinecraftJar;
		this.LauncherLocation = LauncherLocation;
	}
  
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Lauching game soon...");
		this.setEnabled(false);
		Start.LaunchGame(LauncherMinecraftJar, LauncherLocation);
	}
  
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
 

}