package org.insurancedb.view.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Custom JLabel class
 * 
 * @see JLabel
 */
class InsuranceCompanyLabel extends JLabel {
	
	/**
	 * The constructor sets the labels title
	 * and adds a border. The border is used as padding/margin
	 * in the view.
	 *
	 * @param title the labels title
	 */
	InsuranceCompanyLabel(String title){
		super(title);
		this.setBorder(new EmptyBorder(10,5,0,0));
	}

}