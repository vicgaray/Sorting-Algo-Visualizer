package com.garay.sort;

import java.awt.Color;
import java.awt.Graphics2D;

public class SortRectangle {
	
	int h;
	int w = 10;
	
	int x;
	int y;
	
	Color c = Color.PINK;
	
	public SortRectangle(int x, int y, int h) {
		
		this.x = x;
		this.y = y;
		
		this.h = h;
		
	}// end constructor
	
	public void draw(Graphics2D gtd) {
		gtd.setColor(c);
		gtd.fillRect(x, y, w, h);
		gtd.setColor(Color.BLACK);
		gtd.drawRect(x, y, w, h);
	}// end draw()
}
