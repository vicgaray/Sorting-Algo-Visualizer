package com.garay.sort;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class GamePanel extends JPanel {
	
	int sleep = 100;
	
	JButton startBtn;
	JButton resetBtn;
	JSlider sleepSlider;
	JComboBox selectionBtn;
	Timer timer;
	SortHandler handler;
	
	ArrayList<SortRectangle> rectangles;
	
	String selection;
	String[] OPTIONS = {"selection", "bubble", "quick", "merge"};
	
	Boolean searching = false;
	
	public GamePanel() {
		
		handler = new SortHandler(this);
		
		// Initialize widgets
		startBtn = new JButton("start");
		resetBtn = new JButton("reset");
		
		sleepSlider = new JSlider(0, 100);
		sleepSlider.setMajorTickSpacing(10);
		sleepSlider.setMinorTickSpacing(5);
		sleepSlider.setPaintTicks(true);
		sleepSlider.setPaintLabels(true);
		
		selectionBtn = new JComboBox(OPTIONS);
		selectionBtn.setSelectedIndex(0);
		selection = OPTIONS[0];
		
		startBtn.addActionListener(new StartAction());
		resetBtn.addActionListener(new ResetAction());
		selectionBtn.addActionListener(new ComboBoxAction());
		
		rectangles = new ArrayList<SortRectangle>();
		
		add(sleepSlider);
		add(selectionBtn);
		add(startBtn);
		add(resetBtn);
		
		setUp();
		
		// Initializes timer thread to be used for the game loop
		timer = new Timer();
		timer.schedule(new GameTimerTask(), 0, 17);
	}// end constructor
	
	class GameTimerTask extends TimerTask {
		
		/* Main game loop */
		public void run() {
			
			if (searching) {
				if (selection == "selection") {
					handler.selectionSort();
				} else if (selection == "bubble") {
					handler.bubbleSort();
				} else if (selection == "quick") {
					handler.beginQS();
				} else if (selection == "merge") {
					handler.beginMS(new SortRectangle[rectangles.size()], 0, rectangles.size()-1);
				}
			}// end if
			
			repaint();
		}// end run()
	}// end GameTimerTask inner class
	
	class ComboBoxAction implements ActionListener {
		
		/* Updates selection */
		public void actionPerformed(ActionEvent ev) {
			
			JComboBox cb = (JComboBox) ev.getSource();
			selection = (String) cb.getSelectedItem();
		}// end actionPerformed()
	}// end ComboBoxAction inner class
	
	class StartAction implements ActionListener {
		
		/* Calls searchInProgress() */
		public void actionPerformed(ActionEvent ev) {
			
			searchInProgress();
		}// end actionPerformed()
	}// end StartAction inner class
	
	class ResetAction implements ActionListener {
		
		/* Clears array and calls setUp()  */
		public void actionPerformed(ActionEvent ev) {
			
			rectangles.clear();
			setUp();
		}// end actionPerformed()
	}// end ResetAction inner class
	
	/* Calls createBars to make an array of rectangles */
	public void setUp() {
		
		createBars();
	}// end setUp()
	
	/* Makes an array of SortRectangles with various heights */
	public void createBars() {
		
		Random rand = new Random();
		for (int i=50; i<=650; i+=10) {
			
			int value = rand.nextInt(170 - 10) + 10;
			SortRectangle rect = new SortRectangle(i, (465-value), value);
			rectangles.add(rect);
		}// end for
	}// end createBars()
	
	/* Pauses actions, sets the sleep value for sleep the thread and repaint it, and begin search */
	public void searchInProgress() {
		
		startBtn.setEnabled(false);
		resetBtn.setEnabled(false);
		
		int sleepValue = sleepSlider.getValue();
		handler.sleep = 150 - sleepValue;
		
		searching = true;
	}// end searchInProcess()
	
	/* Enables buttons and stops search */
	public void searchOver() {
		
		startBtn.setEnabled(true);
		resetBtn.setEnabled(true);
		searching = false;
	}// end searchOver()
	
	/* Updates the GUI */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D gtd = (Graphics2D) g;
		
		for (SortRectangle rect: rectangles) {
			rect.draw(gtd);
		}// end for
	}// end paintComponent()
}// end GamePanel class
