package org.insurancedb.view.gui;

import java.awt.*;
import javax.swing.*;

/**
 * Custom JButton class
 * 
 * @see JButton
 */
class InsuranceCompanyButton extends JButton {

	private int width;
	private int height;

	/**
	 * The constructor sets the buttons title,
	 * width and height.
	 *
	 * @param label the buttons label
	 * @param w the buttons width
	 * @param h the buttons height
	 */
	InsuranceCompanyButton(String label, int w, int h){
		super(label);
		this.width = w;
		this.height = h;
	}

	/**
	 * Gets the minimum size
	 *
	 * @return Dimension returns minimum size Dimension
	 */
	public Dimension getMinimumSize() {
		return new Dimension(width,height);
	}

	/**
	 * Gets the preferred size
	 *
	 * @return Dimension returns preferred size Dimension
	 */
	public Dimension getPreferredSize() {
		return new Dimension(width,height);
	}

	/**
	 * Gets the maximum size
	 *
	 * @return Dimension returns maximum size Dimension
	 */
	public Dimension getMaximumSize() {
		return new Dimension(width,height);
	}
	
}