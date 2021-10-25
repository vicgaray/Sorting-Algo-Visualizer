package com.garay.sort;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Platform {

	public static void main(String[] args) {
		
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(700, 500);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()/2 - frame.getSize().getWidth()/2),
				(int)(screenSize.getHeight()/2 - frame.getSize().getHeight()/2));
		
		frame.setResizable(false);
		frame.setTitle("Sorting");
		frame.setVisible(true);
	}// end main()

}// end main class
