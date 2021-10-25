package com.garay.sort;

import java.awt.Color;
import java.util.Collections;

public class SortHandler {
	
	GamePanel panel;
	int sleep = 100;
	
	public SortHandler(GamePanel panel) {
		
		this.panel = panel;
	} // end constructor
	
	/* Swaps the elements in the array and their x values on the GUI */
	public void swapRects(int currentValue, int targetValue) {
		
		int storedX = panel.rectangles.get(currentValue).x;
		panel.rectangles.get(currentValue).x = panel.rectangles.get(targetValue).x;
		panel.rectangles.get(targetValue).x = storedX;
		Collections.swap(panel.rectangles, currentValue, targetValue);
	} // end swapRects()
	
	/* Pause the thread to repaint the updated rectangles */
	public void paintRects() {
		
		try {
			panel.repaint();
			Thread.sleep(sleep);
		} catch (Exception e) {
			e.printStackTrace();
		} // end try-catch
	} // end paintRects()
	
	// Pauses the thread to repaint the updated rectangles or just updates the color of the rectangles
	public void paintRects(int rect, boolean pause, Color color) {
		
		try {
			if (pause) {
				panel.rectangles.get(rect).c = color;
				panel.repaint();
				Thread.sleep(sleep);
			} else {
				panel.rectangles.get(rect).c = color;
			} // end if-else
		} catch (Exception e) {
			e.printStackTrace();
		} // end try-catch
	} // end paintRects()
	
	public void paintRects(SortRectangle rect, boolean pause, Color color) {
		
		try {
			if (pause) {
				rect.c = color;
				panel.repaint();
				Thread.sleep(sleep);
			} else {
				rect.c = color;
			} // end if-else
		} catch (Exception e) {
			e.printStackTrace();
		} // end try-catch
	} // end paintRects()
	
	/* Runs a selection sort: searches the smallest value in every iteration. */
	public void selectionSort() {
		
		int min;
		for (int i=0; i<panel.rectangles.size()-1; i++) {
			// Set first value as minimum
			min = i; 
			paintRects(min, true, Color.RED);
			
			// Checks every value
			for (int j=i+1; j<panel.rectangles.size(); j++) {
				paintRects(j, true, Color.GREEN);
				
				// New minimum value found, repaints the operation
				if (panel.rectangles.get(j).h < panel.rectangles.get(min).h) {
					paintRects(min, false, Color.PINK);
					min = j;
					paintRects(min, true, Color.RED);
					continue;
				}// end if
				paintRects(j, true, Color.PINK);
			}// end inner for
				
			// Puts minimum to the front
			swapRects(min, i);
			paintRects(i, true, Color.CYAN);
		}// end for
		
		// Paints the last element
		paintRects(panel.rectangles.size()-1, true, Color.CYAN);
		
		panel.searchOver();
	}// end selectionSort()
	
	/* Runs the bubble sort: through each iteration switches two adjacent values based on which 
	 * one of them is smaller
	 */
	public void bubbleSort() {
		
		for (int i=0; i<panel.rectangles.size()-1; i++) {
			Boolean swapped = false;
			
			// Iterates all the values and its adjacent neighbor
			for (int j=0; j<panel.rectangles.size()-i-1; j++) {
				paintRects(j, false, Color.RED);
				paintRects(j+1, true, Color.GREEN);
				
				// Swap the current value with the next value if the current value is larger and repaints update
				if (panel.rectangles.get(j).h > panel.rectangles.get(j+1).h) {
					swapRects(j, j+1);
					swapped = true;
					paintRects();
				}// end if
				
				// Paints the rectangles back to the default color
				paintRects(j, false, Color.PINK);
				paintRects(j+1, true, Color.PINK);
			}// end inner for
			
			// Breaks early if no swaps were made, signaling array is sorted
			if (swapped == false) {
				break;
			}// end if
		}// end for
		
		// Pains all rectangles blue once completely sorted
		for (int i=0; i<panel.rectangles.size(); i++) {
			paintRects(i, true, Color.CYAN);
		}
		panel.searchOver();
	}// end bubbleSort()
	
	/* Runs the quick sort: picks a partitioner, and positions everything smaller than the partitioner's
	 * value to the left and everything larger to the right
	 */
	public void beginQS() {
		
		quickSort(0, panel.rectangles.size()-1);
		panel.searchOver();
	} // end beginQS()
	
	/* Recursive call to branch the array */
	public void quickSort(int start, int end) {
		
		if (start < end) {
			int pi = partition(start, end);
			paintRects(pi, true, Color.CYAN);
			
			// Left of the pivot
			quickSort(start, pi-1);
			// Right of the pivot
			quickSort(pi+1, end);
		} else {
			// Paints the last branch in the recursive call
			if (end >= 0 && end < panel.rectangles.size()) 
				paintRects(end, true, Color.CYAN);
			else if (start >= 0 && end < panel.rectangles.size()) 
				paintRects(start, true, Color.CYAN);
		} // end if-else
	}// end quickSort()
	
	/* Picks the last value in the set to be the partitioner 
	 * Organizes everything based on the partitioner
	 * returns the pivot location
	 */
	public int partition(int start, int end) {
		
		// Pivot value
		int pivot = panel.rectangles.get(end).h;
		int i = start - 1;
		paintRects(end, true, Color.RED);
		
		// Iterates through entire subset
		for (int j=start; j<=end-1; j++) {
			paintRects(j, true, Color.GREEN);
			
			// Sorts all values smaller than the partitioner to the left
			// All values bigger than the partitioner stay to the right
			// And paints them based on their group
			if (panel.rectangles.get(j).h < pivot) {
				i++;
				
				// Swap
				swapRects(j, i);
				paintRects(i, true, Color.WHITE);
			} else {
				paintRects(j, true, Color.MAGENTA);
			} // end if-else
		} // end for
		
		// Puts the partitioner in its proper location
		swapRects(end, i+1);
		paintRects();
		
		// Paints the subset back to its default color
		for (int j=start; j<=end; j++) {
			paintRects(j, false, Color.PINK);
		}// end for
		
		return i+1;
	}// end partition()
	
	/* Runs a merge sort: uses a temporary array to organize the elements and updates the main
	 * array based on the temporary array
	 */
	public void beginMS(SortRectangle[] temp, int start, int end) {
		
		mergeSort(temp, start, end);
		
		// Paints them cyan once completely sorted
		for (int i=0; i<panel.rectangles.size(); i++) {
			paintRects(i, true, Color.CYAN);
		}
		panel.searchOver();
	} // end beginMS()
	
	/* Recursive call to branch of the sections of the giant array */
	public void mergeSort(SortRectangle[] temp, int start, int end) {
		
		if (start < end) {
			int mid = (start+end)/2;
			
			mergeSort(temp, start, mid);
			mergeSort(temp, mid+1, end);
			
			// Colors the subset section that's about to be rebuilt
			for (int i=start; i<end+1; i++) {
				paintRects(i, false, Color.GREEN);
			}// end for
			
			mergeHalves(temp, start, end);
		} // end if
	} // end mergeSort()
	
	/* Moves the chosen element into the temporary array and updates the front end of the operation */
	public void moveRectToTemp(SortRectangle[] temp, SortRectangle rect, int i) {
		
		paintRects(rect, true, Color.RED); 
		
		// Store rectangle into temp and updates the GUI
		temp[i] = rect;
		rect.y = 290-rect.h;
		rect.x = 50+10*i;
		paintRects(rect, true, Color.MAGENTA);
	} // end moveRectToTemp()
	
	/* Rebuilds the branched section with its elements sorted */
	public void mergeHalves(SortRectangle[] temp, int start, int end) {
		
		int leftHalf = (start+end)/2;
		int rightHalf = leftHalf+1;
		int size = end - start + 1;
		
		int l = start;
		int r = rightHalf;
		int i = start;
		
		// Will add values in order to temporary array until one half is done incrementing through elements
		SortRectangle rect;
		while (l<=leftHalf && r<=end) {
			if (panel.rectangles.get(l).h <= panel.rectangles.get(r).h) {
				rect = panel.rectangles.get(l);
				moveRectToTemp(temp, rect, i);
				
				l++;
			} else {
				rect = panel.rectangles.get(r);
				moveRectToTemp(temp, rect, i);
				
				r++;
			} // end if-else
			i++;
		} // end while
		
		// Will finish with the remainder of the left half
		while (l<=leftHalf) {
			rect = panel.rectangles.get(l);
			moveRectToTemp(temp, rect, i);
			
			l++;
			i++;
		} // end while
		
		// Will finish with the remainder of the right half
		while (r<=end) {
			rect = panel.rectangles.get(r);
			moveRectToTemp(temp, rect, i);
			
			r++;
			i++;
		} // end while
		
		// Updates the main array 
		// You must update the entire array before painting bc
		// there might be duplicate elements pointing to the same object in the main array
		for (int j=0, k=start; j<size; j++, k++) {
			panel.rectangles.set(k, temp[k]);
		} // end for
		
		// Updates the front end
		for (int j=0, k=start; j<size; j++, k++) {
			rect = panel.rectangles.get(k);
			rect.y = 465-rect.h;
			paintRects(rect, true, Color.PINK);
		} // end for
	} // end mergeHalves()
} // end SortHandler class
