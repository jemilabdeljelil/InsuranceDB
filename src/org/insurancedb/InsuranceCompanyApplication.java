package org.insurancedb;

import java.io.File;
import javax.swing.*;
import java.awt.*;
import org.insurancedb.view.gui.*;
import org.insurancedb.view.tui.*;
import org.insurancedb.model.*;
import org.insurancedb.controller.*;
import org.insurancedb.data.*;

/**
 * InsuranceCompanyApplication contains the applications
 * main method, which sets up the MVC framework as well as
 * the data access object.
 *
 * @author Michael Birsak
 * @version 1.0
 */
public class InsuranceCompanyApplication {

	public static void main(String[] args) {

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
			catch (Exception e) {
			// handle exception
		}
		
		// Create new data access object
		InsuranceCompanyDAO dao = new InsuranceCompanyDAO();

		// Try to initialize and load contents from db
		try {
			dao.initialize();
		} catch (DatabaseNotFoundException e) {
			JOptionPane.showMessageDialog(null, e, "Error: Database Not Found", JOptionPane.ERROR_MESSAGE);
		}

		// Set up model (pass dao) and controller (pass model)
		InsuranceCompanyModelInterface model = new InsuranceCompanyModel(dao);
		ControllerInterface controller = new InsuranceCompanyController(model);

		// In order to run the InsuranceCompanyTextView, comment the controller out and uncomment the next line;
		// InsuranceCompanyTextView view = new InsuranceCompanyTextView(model);

	}
	
}