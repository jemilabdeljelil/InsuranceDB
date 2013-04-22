package org.insurancedb.view.tui;

import org.insurancedb.model.*;
import org.insurancedb.controller.*;
import java.util.*;
import java.io.*;

public class InsuranceCompanyTextView {

	private InsuranceCompanyModelInterface model;

	private Map<Integer, InsuranceCompany> ic;
	private Set<Integer> icKeys;

	private Scanner s;
	private int input = -1;
	private String[] data;

	public InsuranceCompanyTextView(InsuranceCompanyModelInterface model) {

		this.model = model;
		ic = model.getInsuranceCompanies();
		icKeys = ic.keySet();

		System.out.println(	"\n---------------------------------------------------------------------\n" +
							"InsuranceDB - Insurance Company Management // (c)2013 Michael Birsak" +
							"\n---------------------------------------------------------------------");

		printAllInsuranceCompanies();
		mainLoop();


	}

	private void mainLoop(){
		s = new Scanner(System.in);
		while(input != 0){
			printMainOptions();
			try {
				System.out.print(">");
				input = s.nextInt();
				actionPerformed(input);
			} catch(InputMismatchException e){
				System.out.println("Error: Please choose a valid option!");
			} finally {
				s = new Scanner(System.in);
			}
		}
		s.close();
	}

	private void actionPerformed(int i){

		switch(i) {
			case 0:
				break;
			case 1:
				printAllInsuranceCompanies();
				break;
			case 2:
				chooseSelectInsuranceCompany();
				break;
			case 3:
				chooseAddInsuranceCompany();
				break;
			case 4:
				chooseDeleteInsuranceCompany();
				break;
			default:
				printAllInsuranceCompanies();
				break;
		}

	};

	private void chooseAddInsuranceCompany(){
		s = new Scanner(System.in);
		data = new String[6];
		try {
			System.out.print("Copmany name: ");
			data[0] = s.nextLine();
			System.out.print("Telephone: ");
			data[1] = s.nextLine();
			System.out.print("Website: ");
			data[2] = s.nextLine();
			System.out.print("Insurance cover: ");
			data[3] = s.nextLine();
			System.out.print("Broker percentage: ");
			float bp = s.nextFloat();
			data[4] = Float.toString(bp);
			s.nextLine();
			System.out.print("Genederal description: ");
			data[5] = s.nextLine();
			addInsuranceCompany(data);
		} catch(Exception e){
			System.out.println("Error: Please enter a valid field value!");
		} finally {
			input = -1;
			s = new Scanner(System.in);
		}
	}

	private void chooseSelectInsuranceCompany(){
		System.out.print("Select a company id: ");
		s = new Scanner(System.in);
		try {
			input = s.nextInt();
			printInsuranceCompany(input);		// Read from text file
			//selectInsuranceCompany(input);	// Read from map
		} catch(InputMismatchException e){
			System.out.println("Error: Please choose a valid id!");
		} finally {
			input = -1;
			s = new Scanner(System.in);
		}
	}

	private void chooseDeleteInsuranceCompany(){
		System.out.print("Select a company id to delete: ");
		s = new Scanner(System.in);
		try {
			input = s.nextInt();
			deleteInsuranceCompany(input);
		} catch(InputMismatchException e){
			System.out.println("Error: Please choose a valid id!");
		} finally {
			input = -1;
			s = new Scanner(System.in);
		}
	}

	private void printMainOptions() {
		System.out.println(	"\n------- Main Menu -------\n" + 
							"[1] List all companies  | \n" +
							"[2] Select a company    | \n" +
							"[3] Add a company       | \n" +
							"[4] Delete a company    | \n" +
							"[0] Close application   |");
	}

	private void printAllInsuranceCompanies() {
		System.out.println("\n---------------------------------------------------------------------");
		for(Integer i : icKeys){
			InsuranceCompany company = ic.get(i);
			System.out.println(	"["+ i + "]\t" + company.getCompanyName() + " | " + company.getTelephone() + " | " + company.getUrl());
		}
		System.out.println("---------------------------------------------------------------------");
	}

	private void printInsuranceCompany(int i) {
		String icString[] = model.readInsuranceCompany(i);
		if(icString != null){
			System.out.println("\n--- ID [" + i + "] ---------------------------------------------------------");
			System.out.println(	"Company: \t\t" + icString[0] + "\n" +
								"Telephone: \t\t" + icString[1] + "\n" +
								"Web Adress: \t\t" + icString[2] + "\n" +
								"Types of insurance: \t" + icString[3] + "\n" +
								"Broker percentage: \t" + icString[4] + "\n" +
								"General Description: \t" + icString[5]);
			System.out.println("---------------------------------------------------------------------");
		}	
	}


	private void selectInsuranceCompany(int i) {
		System.out.println(ic.get(i).toString());
	}

	private void addInsuranceCompany(String[] data){
		model.addInsuranceCompany(data);
	}

	private void deleteInsuranceCompany(int i) {
		model.deleteInsuranceCompany(i);
	}

}