package fr.minepod.launcher;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class Console extends OutputStream {
	private JTextArea textArea;
	
	public Console(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int a) throws IOException {
        textArea.append(String.valueOf((char) a));
        textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
