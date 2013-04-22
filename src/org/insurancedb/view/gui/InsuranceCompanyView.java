package org.insurancedb.view.gui;

import java.util.*;
import java.io.File;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import org.insurancedb.controller.*;
import org.insurancedb.model.*;

/**
 * InsuranceCompanyView is the main application view. It displays all data.
 * It implements all observers and registers with the model.
 * 
 * @see InsuranceCompanyModel
 * @see InsuranceCompanyController
 */
public class InsuranceCompanyView extends JFrame implements InsuranceCompanyEditPanelObserver, InsuranceCompanyTableObserver, InsuranceCompanyErrorObserver {

	private InsuranceCompanyModelInterface model;
	private ControllerInterface controller;
	private int recordEdited = -1; // Store the row if a record was edited

	private JPanel mainPanel;
	private JPanel editPanel;
	private JPanel tablePanel;
	private JPanel editPanelLeft;
	private JPanel editPanelCenter;
	private JPanel editPanelRight;

	private JTextField companyIdTextField;
	private JTextField companyNameTextField;
	private JTextField urlTextField;
	private JTextField insuranceTypesTextField;
	private JTextField telephoneTextField;
	private JTextField percentageTextField;
	private JTextField searchTextField;
	private JTextField generalDescriptionTextField;

	private JLabel companyIdLabel;
	private JLabel companyNameLabel;
	private JLabel urlLabel;
	private JLabel insuranceTypesLabel;
	private JLabel telephoneLabel;
	private JLabel percentageLabel;
	private JLabel searchLabel;
	private JLabel generalDescriptionLabel;
	private JLabel sortLabel;

	private JButton addInsuranceCompanyButton;
	private JButton saveInsuranceCompanyButton;
	private JButton deleteInsuranceCompanyButton;
	private JButton clearSearchButton;

	private JComboBox sortComboBox;

	private JScrollPane insuranceCompanyScrollPane;
	private JTable insuranceCompaniesTable;
	private InsuranceCompanyTableModel tableData;

	Image img = new ImageIcon(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "icon_128.png").getImage();

	/**
	 * The constructor sets up the view's controller and model interface.
	 * It registers as observer to the model and does furher initialization.
	 *
	 * @param controller ControllerInterface
	 * @param model InsuranceCompanyModelInterface
	 */
	public InsuranceCompanyView(ControllerInterface controller, InsuranceCompanyModelInterface model){

		super("InsuranceDB");

		this.controller = controller;
		this.model = model;

		model.registerObserver((InsuranceCompanyEditPanelObserver)this);
		model.registerObserver((InsuranceCompanyTableObserver)this);
		model.registerObserver((InsuranceCompanyErrorObserver)this);

		initComponents();
		createListeners();

		setLocationRelativeTo(null);
		setIconImage(img);
		setVisible(true);

	}

	/**
	 * Initializes all GUI components
	 */
	private void initComponents(){

		mainPanel = new JPanel();
		editPanel = new JPanel();
		editPanelLeft = new JPanel();
		editPanelCenter = new JPanel();
		editPanelRight = new JPanel();
		tablePanel = new JPanel();

		companyIdTextField = new JTextField();
		companyIdTextField.setEditable(false);
		companyNameTextField = new JTextField();
		urlTextField = new JTextField();
		insuranceTypesTextField = new JTextField();
		telephoneTextField = new JTextField();
		percentageTextField = new JTextField();
		percentageTextField.setToolTipText("<html>Only <b>numerical</b> values allowed</html>");
		searchTextField = new JTextField();
		searchTextField.setToolTipText("<html>Use either <b>AND</b> or <b>OR</b> for combined search queries<br>You can also use <b>spaces</b> instead of <b>OR</b></html>");
		generalDescriptionTextField = new JTextField();

		companyIdLabel = new InsuranceCompanyLabel("ID");
		companyNameLabel = new InsuranceCompanyLabel("Company");
		urlLabel = new InsuranceCompanyLabel("Website");
		insuranceTypesLabel = new InsuranceCompanyLabel("Insurance Types");
		telephoneLabel = new InsuranceCompanyLabel("Telephone");
		percentageLabel = new InsuranceCompanyLabel("Broker Percentage");
		searchLabel = new InsuranceCompanyLabel("Search Insurance Types");
		generalDescriptionLabel = new InsuranceCompanyLabel("General Description");
		sortLabel = new InsuranceCompanyLabel("Sorting");

		addInsuranceCompanyButton = new InsuranceCompanyButton("New Insurance Company", 190, 45);
		addInsuranceCompanyButton.setToolTipText("<html>Create a new <b>empty</b> Insurance Company</html>");
		saveInsuranceCompanyButton = new InsuranceCompanyButton("Save Insurance Company", 190, 45);
		saveInsuranceCompanyButton.setToolTipText("<html>Save the <b>selected</b> Insurance Company<br>Hint: Press <b>Enter</b> to quickly save an entry</html>");
		saveInsuranceCompanyButton.setDefaultCapable(true);
		getRootPane().setDefaultButton(saveInsuranceCompanyButton); // Set the save button as default button, so quicker editing by pressing Enter is possible
		deleteInsuranceCompanyButton = new InsuranceCompanyButton("Delete Insurance Company", 190, 45);
		deleteInsuranceCompanyButton.setToolTipText("<html>Delete the <b>selected</b> Insurance Company</html>");
		clearSearchButton = new InsuranceCompanyButton("x", 40, 35);
		clearSearchButton.setToolTipText("<html>Click to <b>clear</b> the search field</html>");

		String[] sortStrategies = {"ID","Company Name","Percentage"};
		sortComboBox = new JComboBox(sortStrategies);

		tableData = new InsuranceCompanyTableModel(model.getInsuranceCompanies());
		insuranceCompaniesTable = new JTable();

		insuranceCompanyScrollPane = new JScrollPane(insuranceCompaniesTable);

		// Set layout and add components to panels
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(editPanel, BorderLayout.NORTH);
				editPanel.setBorder(new EmptyBorder(10,10,10,10));
				editPanel.setLayout(new BorderLayout());
				editPanel.add(editPanelLeft, BorderLayout.CENTER);
					editPanelLeft.setBorder(new EmptyBorder(0,0,5,15));
					editPanelLeft.setLayout(new GridLayout(4,4,5,0));
					editPanelLeft.add(companyIdLabel);
					editPanelLeft.add(companyNameLabel);
					editPanelLeft.add(urlLabel);
					editPanelLeft.add(telephoneLabel);
					editPanelLeft.add(companyIdTextField);
					editPanelLeft.add(companyNameTextField); 
					editPanelLeft.add(urlTextField);
					editPanelLeft.add(telephoneTextField);
					editPanelLeft.add(percentageLabel);
					editPanelLeft.add(searchLabel);
					editPanelLeft.add(new JLabel());
					editPanelLeft.add(sortLabel);
					editPanelLeft.add(percentageTextField);
					editPanelLeft.add(searchTextField);
					Container searchBox = Box.createVerticalBox();
					searchBox.add(clearSearchButton);
					editPanelLeft.add(searchBox);
					editPanelLeft.add(sortComboBox);
				editPanel.add(editPanelRight, BorderLayout.EAST);
					Container box = Box.createVerticalBox();
					box.add(saveInsuranceCompanyButton);
					box.add(Box.createVerticalStrut(5));
					box.add(addInsuranceCompanyButton);
					box.add(Box.createVerticalStrut(5));
					box.add(deleteInsuranceCompanyButton);
					editPanelRight.add(box);
				editPanel.add(editPanelCenter, BorderLayout.SOUTH);
					editPanelCenter.setLayout(new GridLayout(4,1));
					editPanelCenter.add(insuranceTypesLabel);
					editPanelCenter.add(insuranceTypesTextField);
					editPanelCenter.add(generalDescriptionLabel);
					editPanelCenter.add(generalDescriptionTextField);
			mainPanel.add(tablePanel, BorderLayout.CENTER);
				tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10,10,10,10),"Insurance Companies Overview"));
				tablePanel.setLayout(new BorderLayout());
				tablePanel.add(insuranceCompanyScrollPane, BorderLayout.CENTER);

		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(900,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}	

	/**
	 * Creates the view's listeners
	 */
	public void createListeners(){

		// If the table selection changes, set the currentInsuranceCompany accordingly (Listens to keyboard and mouse)
		insuranceCompaniesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e){
				if((e.getValueIsAdjusting() == false) && (insuranceCompaniesTable.getSelectedRow() != -1)){
					int selectedCompanyId = Integer.valueOf((String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0));
					controller.selectInsuranceCompany(selectedCompanyId);
				}
			}
		});

		// Add a new InsuranceCompany
		addInsuranceCompanyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.addInsuranceCompany();
			}
		});

		// Save an InsuranceCompany
		saveInsuranceCompanyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				// Check if a row is selected
				if(insuranceCompaniesTable.getSelectedRow() != -1){
					int selectedCompanyId = Integer.parseInt((String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0));
					String[] newData = {
						companyNameTextField.getText(),
						telephoneTextField.getText(),
						urlTextField.getText(),
						insuranceTypesTextField.getText(),
						percentageTextField.getText(),
						generalDescriptionTextField.getText()
					};

					// Input validation
					boolean checkInput = true;
					for(String input : newData){
						if((input.equals("")) || (input.equals(":"))){
							checkInput = false;
						}
					}
					// Use regex pattern to check double values
					if(!percentageTextField.getText().matches("(-|\\+)?[0-9]+(\\.[0-9]+)?")){
						checkInput = false;
					}

					if(checkInput){
						recordEdited = insuranceCompaniesTable.getSelectedRow();
						controller.updateInsuranceCompany(selectedCompanyId, newData);
					} else {
						showErrorMsg("Error Saving Insurance Company", "Please enter proper values:\n(Fields cannot be blank or contain \":\")");
					}

				} else {
					showErrorMsg("Error Saving Insurance Company", "Please select a valid table row.");
				}
			}
		});

		// Delete an InsuranceCompany
		deleteInsuranceCompanyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				// Check if a row is selected
				if(insuranceCompaniesTable.getSelectedRow() != -1){
					int i = JOptionPane.showConfirmDialog(null,"<html>Are you sure you want to delete following Insurance Company:<br><br><b>ID: " +
						(String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0) + " - " +
						(String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 1) + "</b></html>", "Delete Insurance Company", JOptionPane.YES_NO_OPTION);

					if(i == JOptionPane.YES_OPTION){
						int selectedCompanyId = Integer.parseInt((String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0));
						controller.deleteInsuranceCompany(selectedCompanyId);
						searchTextField.setText("");
					}
				} else {
					showErrorMsg("Error Deleting Insurance Company", "Please select a valid table row.");
				}
			}
		});

		// Enable dynamic searching, by just typing into the searchTextField
		searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void removeUpdate(DocumentEvent e) {
				searchInsuranceCompany();
			}
			public void insertUpdate(DocumentEvent e) {
				searchInsuranceCompany();
			}
		});

		// Clear the searchTextField
		clearSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				searchTextField.setText("");
			}
		});

		// Enable sorting
		sortComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String sortStrategy = (String)sortComboBox.getSelectedItem();

				if(sortStrategy.equals("ID")){
					model.setSortingStrategy(null);
				} else if(sortStrategy.equals("Company Name")){
					model.setSortingStrategy(new InsuranceCompanyNameComparator());
				} else {
					model.setSortingStrategy(new InsuranceCompanyPercentageComparator());
				}

				updateTable();

			}
		});

	}

	/**
	 * Searches for InsuranceCompanies. Calls the controller and updates
	 * the tableData to the searchMap.
	 */
	private void searchInsuranceCompany(){
		String query = searchTextField.getText();
		controller.searchInsuranceCompanies(query);
		tableData.update(model.getSearchMap(), model.getSortingStrategy()); // Updates tableData and sortingStrategy
		// Enable automatic selection while searching
		if(tableData.getRowCount() > 0){
			selectRow();
			int selectedCompanyId = Integer.valueOf((String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0));
			controller.selectInsuranceCompany(selectedCompanyId);
		}
	}

	/**
	 * Makes sure that the right row is selected on adding a new record,
	 * or on editing a record.
	 */
	private void selectRow(){

		int tableRows = tableData.getRowCount();
		int tabelCols = tableData.getColumnCount();
		int rowToSelect = 0;

		if(recordEdited != -1){
			rowToSelect = recordEdited; // Select the edited record
		} else {
			for(int i = 0 ; i < tableRows ; i++){
				String companyName = (String)tableData.getValueAt(i,1);
				if(companyName.equals("-")){
					rowToSelect = i; // Select the new record
				}
			}
		}

		insuranceCompaniesTable.setRowSelectionInterval(rowToSelect, rowToSelect);

	}

	/**
	 * Initial method to populate the insuranceCompaniesTable
	 */
	public void loadTable(){

		Map<Integer, InsuranceCompany> data = model.getInsuranceCompanies();
		tableData = new InsuranceCompanyTableModel(data);
		insuranceCompaniesTable.setModel(tableData);
		insuranceCompaniesTable.getColumnModel().getColumn(0).setMaxWidth(50);
		insuranceCompaniesTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Check if db is empty and proceed with selecting only if the table is populated
		if(insuranceCompaniesTable.getRowCount() > 0){
			// Set selected company according to selected row on load
			selectRow();
			int selectedCompanyId = Integer.valueOf((String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0));
		}

	}

	/**
	 * Observer method to update the editPanel and it's contents
	 */
	public void updateEditPanel(){

		InsuranceCompany insuranceCompany = model.getCurrentInsuranceCompany();
		
		if(insuranceCompany != null){ // If no currentInsuranceCompany is set (if the db is empty)
			// Update all text fields
			String id = (String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0);
			companyIdTextField.setText(id);
			companyNameTextField.setText(insuranceCompany.getCompanyName());
			urlTextField.setText(insuranceCompany.getUrl());
			urlTextField.setCaretPosition(0);
			generalDescriptionTextField.setText(insuranceCompany.getGeneralDescription());
			generalDescriptionTextField.setCaretPosition(0);
			insuranceTypesTextField.setText(insuranceCompany.getInsuranceTypes());
			insuranceTypesTextField.setCaretPosition(0);
			telephoneTextField.setText(insuranceCompany.getTelephone());
			percentageTextField.setText(Float.toString(insuranceCompany.getPercentage()));

		}

	}

	/**
	 * Observer method to update the insuranceCompanyTable
	 */
	public void updateTable(){

		tableData.update(model.getInsuranceCompanies(), model.getSortingStrategy()); // Populate the table

		// Check if InsuranceCompany map is empty
		if(model.getInsuranceCompanies().size() > 0){
			selectRow();
			int selectedCompanyId = Integer.valueOf((String)insuranceCompaniesTable.getValueAt(insuranceCompaniesTable.getSelectedRow(), 0));
			controller.selectInsuranceCompany(selectedCompanyId);
			recordEdited = -1; // Reset the recordEdited field
		} else {
			// If all records are deleted, clear the edit panel
			companyIdTextField.setText("");
			companyNameTextField.setText("");
			urlTextField.setText("");
			generalDescriptionTextField.setText("");
			insuranceTypesTextField.setText("");
			telephoneTextField.setText("");
			percentageTextField.setText("");
		}

	}

	/**
	 * Observer method to show an error message
	 */
	public void showErrorMsg(Throwable t){

		JOptionPane.showMessageDialog(this, t, "Error", JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * Custom error message
	 */
	public void showErrorMsg(String title, String msg){

		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);

	}
	
}