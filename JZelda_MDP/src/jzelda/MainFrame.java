package jzelda;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
	
	private JTextArea textArea; //JTextField per unica linea di testo
	private JButton titleButton;
	
	public MainFrame(){
		super("JZelda");
		
		
		setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		titleButton = new JButton("JZelda");
		
		setSize(1280,1200);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		add(textArea, BorderLayout.CENTER);
		add(titleButton, BorderLayout.PAGE_START);
	}
}
