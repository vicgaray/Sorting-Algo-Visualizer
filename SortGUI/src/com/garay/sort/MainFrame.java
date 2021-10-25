package com.garay.sort;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	public MainFrame() {
		
		GamePanel panel = new GamePanel();
		panel.setLocation(0, 0);
		panel.setBackground(Color.DARK_GRAY);
		
		this.add(panel);
		
		panel.setVisible(true);
	}// end constructor
}// end MainFrame class
