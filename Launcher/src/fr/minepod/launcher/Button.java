package fr.minepod.launcher;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
 
public class Button extends JButton implements MouseListener{
	private String name;
	private String LauncherMinecraftJar;
	private String LauncherLocation;
	
	public Button(String str){
		super(str);
		this.name = str;
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
		new Downloader().LaunchGame(LauncherMinecraftJar, LauncherLocation);
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