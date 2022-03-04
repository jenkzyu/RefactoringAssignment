
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.text.MaskFormatter;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Menu extends JFrame {

	private static ArrayList<Customer> customerList = new ArrayList<Customer>();
	private int position = 0;
	private String password;
	private Customer customer = null;
	private CustomerAccount acc = new CustomerAccount();
	JFrame f, f1;
	JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
	JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
	JLabel customerIDLabel, passwordLabel;
	JTextField customerIDTextField, passwordTextField;
	Container content;
	Customer e;

	JPanel panel2;
	JButton add;
	String PPS, firstName, surname, DOB, CustomerID;
	static String pps = "";
	static String surName = "";
	static String firstname = "";
	static String dob = "";
	static String passWord = "";
	static String customerID = "";

	public static void main(String[] args) {
		Menu driver = new Menu();

		readFromFile();

		driver.menuStart();
	}

	private static void readFromFile() {
		ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();

		// Read from file
		try {
			Scanner file = new Scanner(new File("C:/refactoring/customerInfo.txt"));
			while (file.hasNextLine()) {
				String data = file.nextLine();
				if (data.contains("PPS number")) {
					String[] split = data.split("= ");
					pps = split[1];
					// System.out.println(pps);
				} else if (data.contains("Surname")) {
					String[] split1 = data.split("= ");
					surName = split1[1];
					// System.out.println(surName);
				} else if (data.contains("First Name")) {
					String[] split2 = data.split("= ");
					firstname = split2[1];
					// System.out.println(firstname);
				} else if (data.contains("Date of Birth")) {
					String[] split3 = data.split("= ");
					dob = split3[1];
					// System.out.println(dob);
				} else if (data.contains("Customer ID")) {
					String[] split4 = data.split("= ");
					customerID = split4[1];
					// System.out.println(customerID);
				} else if (data.contains("Password")) {
					String[] split5 = data.split("= ");
					passWord = split5[1];
					// System.out.println(passWord);
				}

				if (!pps.isEmpty() && !surName.isEmpty() && !firstname.isEmpty() && !dob.isEmpty()
						&& !customerID.isEmpty() && !passWord.isEmpty()) {
					customerList.add(new Customer(pps, surName, firstname, dob, customerID, passWord, accounts));

					pps = "";
					surName = "";
					firstname = "";
					dob = "";
					customerID = "";
					passWord = "";

				} else {
					// do nothing
				}

			}
			file.close();
		} catch (FileNotFoundException fileNotFound) {
			fileNotFound.printStackTrace();
		}

	}

	public void menuStart() {
		/*
		 * The menuStart method asks the user if they are a new customer, an existing
		 * customer or an admin. It will then start the create customer process if they
		 * are a new customer, or will ask them to log in if they are an existing
		 * customer or admin.
		 */
		f = frame("User Type");

		JPanel userTypePanel = new JPanel();
		final ButtonGroup userType = new ButtonGroup();
		JRadioButton radioButton;
		userTypePanel.add(radioButton = new JRadioButton("Existing Customer"));
		radioButton.setActionCommand("Customer");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("Administrator"));
		radioButton.setActionCommand("Administrator");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("New Customer"));
		radioButton.setActionCommand("New Customer");
		userType.add(radioButton);

		JPanel continuePanel = new JPanel();
		JButton continueButton = new JButton("Continue");
		continuePanel.add(continueButton);

		Container content = f.getContentPane();
		content.setLayout(new GridLayout(2, 1));
		content.add(userTypePanel);
		content.add(continuePanel);

		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String user = userType.getSelection().getActionCommand();
				// if user selects NEW
				// CUSTOMER--------------------------------------------------------------------------------------
				if (user.equals("New Customer")) {

					createCustomer();
				}

				// ADMIN----------------------------------------------------------------------------------------------
				if (user.equals("Administrator"))

				{
					adminUser();
				}

				// if user selects CUSTOMER
				// ----------------------------------------------------------------------------------------
				if (user.equals("Customer")) {
					customerLogin();
				}

			}

		});

		f.setVisible(true);

	}

	public void admin() {
		dispose();
		f = frame("Administrator Menu");
		f.setVisible(true);

		JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteCustomer = new JButton("Delete Customer");
		deleteCustomer.setPreferredSize(new Dimension(250, 20));
		deleteCustomerPanel.add(deleteCustomer);

		JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteAccount = new JButton("Delete Account");
		deleteAccount.setPreferredSize(new Dimension(250, 20));
		deleteAccountPanel.add(deleteAccount);

		JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bankChargesButton = new JButton("Apply Bank Charges");
		bankChargesButton.setPreferredSize(new Dimension(250, 20));
		bankChargesPanel.add(bankChargesButton);

		JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton interestButton = new JButton("Apply Interest");
		interestPanel.add(interestButton);
		interestButton.setPreferredSize(new Dimension(250, 20));

		JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton editCustomerButton = new JButton("Edit existing Customer");
		editCustomerPanel.add(editCustomerButton);
		editCustomerButton.setPreferredSize(new Dimension(250, 20));

		JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton navigateButton = new JButton("Navigate Customer Collection");
		navigatePanel.add(navigateButton);
		navigateButton.setPreferredSize(new Dimension(250, 20));

		JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton summaryButton = new JButton("Display Summary Of All Accounts");
		summaryPanel.add(summaryButton);
		summaryButton.setPreferredSize(new Dimension(250, 20));

		JPanel overdraftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton overdraftButton = new JButton("Update Overdraft of a Customer");
		overdraftPanel.add(overdraftButton);
		overdraftButton.setPreferredSize(new Dimension(250, 20));

		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton accountButton = new JButton("Add an Account to a Customer");
		accountPanel.add(accountButton);
		accountButton.setPreferredSize(new Dimension(250, 20));

		JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton returnButton = new JButton("Exit Admin Menu");
		returnPanel.add(returnButton);

		JLabel label1 = new JLabel("Please select an option");

		content = f.getContentPane();
		content.setLayout(new GridLayout(11, 1));
		content.add(label1);
		content.add(accountPanel);
		content.add(bankChargesPanel);
		content.add(overdraftPanel);
		content.add(interestPanel);
		content.add(editCustomerPanel);
		content.add(navigatePanel);
		content.add(summaryPanel);
		content.add(deleteCustomerPanel);
		content.add(deleteAccountPanel);
		content.add(returnPanel);

		bankChargesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Apply Charges to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							f.dispose();
							f =frame("Administrator Menu");

							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {

								box.addItem(customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();
							boxPanel.add(box);

							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Charge");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must add acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								admin();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										acc = customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";

										if (acc instanceof CustomerDepositAccount) {

											JOptionPane.showMessageDialog(f,
													"25" + euro + " deposit account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											acc.setBalance(acc.getBalance() - 25);
											JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										if (acc instanceof CustomerCurrentAccount) {

											JOptionPane.showMessageDialog(f,
													"15" + euro + " current account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											acc.setBalance(acc.getBalance() - 25);
											JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										f.dispose();
										admin();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										menuStart();
									}
								});

							}
						}
					}
				}

			}
		});

		// overdraft button
		overdraftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Apply Overdraft to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							f.dispose();
							f = frame("Administrator Menu");
							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {
								if (customer.getAccounts().get(i) instanceof CustomerCurrentAccount) {
									box.addItem(customer.getAccounts().get(i).getNumber());
								}
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();
							boxPanel.add(box);

							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Overdraft");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must add acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								admin();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										acc = customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										// String euro = "\u20ac";
										// add overdraft input
										double overdraft = 0;
										boolean validInput = true;

										if (acc instanceof CustomerCurrentAccount) {
											while (validInput) {
												String inputOverdraft = JOptionPane.showInputDialog(null,
														"Please input overdraft.");
												// Making sure that user only inputs and takes number. Regex
												// [StackOverflow] any
												// number + an optional decimal number
												// but can still take any number
												if (!inputOverdraft.matches("[0-9]*\\.?[0-9]*$"))
												{
													JOptionPane.showMessageDialog(null, "Please input overdraft",
															"Error", JOptionPane.OK_OPTION);
												} else {
													validInput = false;
													overdraft = Double.parseDouble(inputOverdraft);
													((CustomerCurrentAccount) acc).setOverdraft(overdraft);
													JOptionPane.showMessageDialog(f,
															"New overdraft = "
																	+ ((CustomerCurrentAccount) acc).getOverdraft(),
															"Success!", JOptionPane.INFORMATION_MESSAGE);
												}
											}

										}

										f.dispose();
										admin();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										menuStart();
									}
								});

							}
						}
					}
				}

			}
		});

		interestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Apply Interest to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							f.dispose();
							f=frame("Administrator Menu");
							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {

								box.addItem(customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();

							JLabel label = new JLabel("Select an account to apply interest to:");
							boxPanel.add(label);
							boxPanel.add(box);
							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Interest");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must add acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								admin();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										acc = customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";
										double interest = 0;
										boolean loop = true;

										while (loop) {
											//the isNumeric method tests to see if the string entered is numeric.
											String interestString = JOptionPane.showInputDialog(f,
													"Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");
											
											if (isNumeric(interestString)) {

												interest = Double.parseDouble(interestString);
												loop = false;

												acc.setBalance(
														acc.getBalance() + (acc.getBalance() * (interest / 100)));

												JOptionPane.showMessageDialog(f,
														interest + "% interest applied. \n new balance = "
																+ acc.getBalance() + euro,
														"Success!", JOptionPane.INFORMATION_MESSAGE);
											}

											else {
												JOptionPane.showMessageDialog(f, "You must enter a numerical value!",
														"Oops!", JOptionPane.INFORMATION_MESSAGE);
											}

										}

										f.dispose();
										admin();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										menuStart();
									}
								});

							}
						}
					}
				}

			}
		});

		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {

					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f, "Enter Customer ID:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							loop = false;
						}

					}

					f.dispose();
					f = frame("Administrator Menu");

					firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
					surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					customerIDTextField = new JTextField(20);
					passwordTextField = new JTextField(20);

					JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

					JPanel cancelPanel = new JPanel();

					textPanel.add(firstNameLabel);
					textPanel.add(firstNameTextField);
					textPanel.add(surnameLabel);
					textPanel.add(surnameTextField);
					textPanel.add(pPPSLabel);
					textPanel.add(pPSTextField);
					textPanel.add(dOBLabel);
					textPanel.add(dOBTextField);
					textPanel.add(customerIDLabel);
					textPanel.add(customerIDTextField);
					textPanel.add(passwordLabel);
					textPanel.add(passwordTextField);

					firstNameTextField.setText(customer.getFirstName());
					surnameTextField.setText(customer.getSurname());
					pPSTextField.setText(customer.getPPS());
					dOBTextField.setText(customer.getDOB());
					customerIDTextField.setText(customer.getCustomerID());
					passwordTextField.setText(customer.getPassword());

					// JLabel label1 = new JLabel("Edit customer details below. The save");

					JButton saveButton = new JButton("Save");
					JButton cancelButton = new JButton("Exit");

					cancelPanel.add(cancelButton, BorderLayout.SOUTH);
					cancelPanel.add(saveButton, BorderLayout.SOUTH);
					// content.removeAll();
					Container content = f.getContentPane();
					content.setLayout(new GridLayout(2, 1));
					content.add(textPanel, BorderLayout.NORTH);
					content.add(cancelPanel, BorderLayout.SOUTH);

					f.setContentPane(content);
					f.setSize(340, 350);
					f.setLocation(200, 200);
					f.setVisible(true);
					f.setResizable(false);

					saveButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							customer.setFirstName(firstNameTextField.getText());
							customer.setSurname(surnameTextField.getText());
							customer.setPPS(pPSTextField.getText());
							customer.setDOB(dOBTextField.getText());
							customer.setCustomerID(customerIDTextField.getText());
							customer.setPassword(passwordTextField.getText());

							JOptionPane.showMessageDialog(null, "Changes Saved.");
						}
					});

					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();

							admin();
						}
					});
				}
			}
		});

		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				f = frame("Summary of Transactions");

				f.setVisible(true);

				JLabel label1 = new JLabel("Summary of all transactions: ");

				JPanel returnPanel = new JPanel();
				JButton returnButton = new JButton("Return");
				returnPanel.add(returnButton);

				JPanel textPanel = new JPanel();

				textPanel.setLayout(new BorderLayout());
				JTextArea textArea = new JTextArea(40, 20);
				textArea.setEditable(false);
				textPanel.add(label1, BorderLayout.NORTH);
				textPanel.add(textArea, BorderLayout.CENTER);
				textPanel.add(returnButton, BorderLayout.SOUTH);

				JScrollPane scrollPane = new JScrollPane(textArea);
				textPanel.add(scrollPane);

				for (int a = 0; a < customerList.size(); a++)// For each customer, for each account, it displays each
																// transaction.
				{
					for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {
						acc = customerList.get(a).getAccounts().get(b);
						for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

							textArea.append(acc.getTransactionList().get(c).toString());
							// Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use
							// this to keep a running total but I couldnt get it working.

						}
					}
				}

				textPanel.add(textArea);
				content.removeAll();

				Container content = f.getContentPane();
				content.setLayout(new GridLayout(1, 1));
				// content.add(label1);
				content.add(textPanel);
				// content.add(returnPanel);

				returnButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						f.dispose();
						admin();
					}
				});
			}
		});

		navigateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					admin();
				} else {

					JButton first, previous, next, last, cancel, listAll, findAccNum, findBySurname;
					JPanel gridPanel, buttonPanel, cancelPanel;

					Container content = getContentPane();

					content.setLayout(new BorderLayout());

					buttonPanel = new JPanel();
					gridPanel = new JPanel(new GridLayout(8, 2));
					cancelPanel = new JPanel();

					firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
					surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					customerIDTextField = new JTextField(20);
					passwordTextField = new JTextField(20);

					first = new JButton("First");
					previous = new JButton("Previous");
					next = new JButton("Next");
					last = new JButton("Last");
					cancel = new JButton("Cancel");
					listAll = new JButton("List all Customer");
					findAccNum = new JButton("Find By Account Number");
					findBySurname = new JButton("Find By Surname");

					firstNameTextField.setText(customerList.get(0).getFirstName());
					surnameTextField.setText(customerList.get(0).getSurname());
					pPSTextField.setText(customerList.get(0).getPPS());
					dOBTextField.setText(customerList.get(0).getDOB());
					customerIDTextField.setText(customerList.get(0).getCustomerID());
					passwordTextField.setText(customerList.get(0).getPassword());

					firstNameTextField.setEditable(false);
					surnameTextField.setEditable(false);
					pPSTextField.setEditable(false);
					dOBTextField.setEditable(false);
					customerIDTextField.setEditable(false);
					passwordTextField.setEditable(false);

					gridPanel.add(firstNameLabel);
					gridPanel.add(firstNameTextField);
					gridPanel.add(surnameLabel);
					gridPanel.add(surnameTextField);
					gridPanel.add(pPPSLabel);
					gridPanel.add(pPSTextField);
					gridPanel.add(dOBLabel);
					gridPanel.add(dOBTextField);
					gridPanel.add(customerIDLabel);
					gridPanel.add(customerIDTextField);
					gridPanel.add(passwordLabel);
					gridPanel.add(passwordTextField);

					buttonPanel.add(first);
					buttonPanel.add(previous);
					buttonPanel.add(next);
					buttonPanel.add(last);
					buttonPanel.add(listAll);
					buttonPanel.add(findAccNum);
					buttonPanel.add(findBySurname);
					cancelPanel.add(cancel);

					content.add(gridPanel, BorderLayout.NORTH);
					content.add(buttonPanel, BorderLayout.CENTER);
					content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);

					first.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							position = 0;
							customersInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField,
									customerIDTextField, passwordTextField, position);
						}
					});

					previous.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position < 1) {
								// don't do anything
							} else {
								position = position - 1;
								customersInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField,
										customerIDTextField, passwordTextField, position);

							}
						}

					});

					next.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position == customerList.size() - 1) {
								// don't do anything
							} else {
								position = position + 1;

								customersInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField,
										customerIDTextField, passwordTextField, position);
							}

						}
					});

					last.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							position = customerList.size() - 1;

							customersInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField,
									customerIDTextField, passwordTextField, position);
						}
					});

					findAccNum.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							String input = JOptionPane.showInputDialog(f, "Enter Account Number");
							for (Customer ca : customerList) {
								ArrayList<CustomerAccount> ac = ca.getAccounts();
								for (int i = 0; i < ac.size(); i++) {
									if (ac.get(i).getNumber().equalsIgnoreCase(input)) {
										position = ac.size();
										customersInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField,
												customerIDTextField, passwordTextField, position);
									}
								}
							}
						}
					});

					findBySurname.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							String input = JOptionPane.showInputDialog(f, "Enter Surname");
							for (int i = 0; i < customerList.size(); i++) {
								if (customerList.get(i).getSurname().endsWith(input)) {
									position = i;
									customersInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField,
											customerIDTextField, passwordTextField, position);
								}
							}
						}
					});

					listAll.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							// f.dispose();
							f = frame("List of all Customers");
							f.setVisible(true);

							String[] column = { "PPS", "Surname", "Firstname", "DOB", "Customer ID" };

							ArrayList<Object[]> allCustomer = new ArrayList<Object[]>();
							for (int i = 0; i < customerList.size(); i++) {
								allCustomer.add(new Object[] { customerList.get(i).getPPS(),
										customerList.get(i).getSurname(), customerList.get(i).getFirstName(),
										customerList.get(i).getDOB(), customerList.get(i).getCustomerID(), });

							}
							JTable jt = new JTable();
							jt.setModel(new DefaultTableModel(allCustomer.toArray(new Object[][] {}), column));
							jt.setBounds(30, 40, 200, 300);
							JScrollPane sp = new JScrollPane(jt);
							f.add(sp);

						}
					});

					cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							dispose();
							admin();
						}
					});
					setContentPane(content);
					setSize(400, 350);
					setVisible(true);
				}
			}
		});

		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();
				} else {
					boolean loop = true;

					boolean found = false;

					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Add an Account to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							loop = false;
							// a combo box in an dialog box that asks the admin what type of account they
							// wish to create (deposit/current)
							String[] choices = { "Current Account", "Deposit Account" };
							String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
									"Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

							if (account.equals("Current Account")) {
								// create current account
								boolean valid = true;
								double balance = 0;
								// add overdraft input
								double overdraft = 0;
								boolean validInput = true;
								while (validInput) {
									String inputOverdraft = JOptionPane.showInputDialog(null,
											"Please input overdraft.");
									// Making sure that user only inputs and takes number. Regex [StackOverflow] any
									// number + an optional decimal number
									// but can still take any number
									if (!inputOverdraft.matches("[0-9]*\\.?[0-9]*$"))
									{
										JOptionPane.showMessageDialog(null, "Please input overdraft", "Error",
												JOptionPane.OK_OPTION);
									} else {
										validInput = false;
										overdraft = Double.parseDouble(inputOverdraft);
									}
								}

								String number = String.valueOf("C" + (customerList.indexOf(customer) + 1) * 10
										+ (customer.getAccounts().size() + 1));// this simple algorithm generates the
																				// account number
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
								int randomPIN = (int) (Math.random() * 9000) + 1000;
								String pin = String.valueOf(randomPIN);

								ATMCard atm = new ATMCard(randomPIN, valid);

								CustomerCurrentAccount current = new CustomerCurrentAccount(atm, overdraft, number,
										balance, transactionList);

								customer.getAccounts().add(current);
								// show overdraft when admin adds an overdraft to the current account + balance.
								JOptionPane.showMessageDialog(f,
										"Account number = " + number + "\n PIN = " + pin + "\n Balance = " + balance
												+ "\n Overdraft = " + overdraft,
										"Account created.", JOptionPane.INFORMATION_MESSAGE);

								f.dispose();
								admin();
							}

							if (account.equals("Deposit Account")) {
								// create deposit account

								double balance = 0, interest = 0;
								String number = String.valueOf("D" + (customerList.indexOf(customer) + 1) * 10
										+ (customer.getAccounts().size() + 1));// this simple algorithm generates the
																				// account number
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

								CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance,
										transactionList);

								customer.getAccounts().add(deposit);
								JOptionPane.showMessageDialog(f, "Account number = " + number, "Account created.",
										JOptionPane.INFORMATION_MESSAGE);

								f.dispose();
								admin();
							}

						}
					}
				}
			}
		});

		deleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					dispose();
					admin();
				} else {
					{
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Delete:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							if (customer.getAccounts().size() > 0) {
								JOptionPane.showMessageDialog(f,
										"This customer has accounts. \n You must delete a customer's accounts before deleting a customer ",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								customerList.remove(customer);
								JOptionPane.showMessageDialog(f, "Customer Deleted ", "Success.",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					}
				}
			}
		});

		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				{
					Object customerID = JOptionPane.showInputDialog(f,
							"Customer ID of Customer from which you wish to delete an account");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
							loop = false;
						}
					}

					if (found == false) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							f.dispose();
							loop = false;

							admin();
						}
					} else {
						// Here I would make the user select a an account to delete from a combo box. If
						// the account had a balance of 0 then it would be deleted. (I do not have time
						// to do this)
					}
				}
			}

		});
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				menuStart();
			}
		});
	}

	public void customer(Customer e1) {
		f = frame("Customer Menu");
		e = e1;
		f.setVisible(true);

		if (e.getAccounts().size() == 0) {
			JOptionPane.showMessageDialog(f,
					"This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
			menuStart();
		} else {
			JPanel buttonPanel = new JPanel();
			JPanel boxPanel = new JPanel();
			JPanel labelPanel = new JPanel();

			JLabel label = new JLabel("Select Account:");
			labelPanel.add(label);

			JButton returnButton = new JButton("Return");
			buttonPanel.add(returnButton);
			JButton continueButton = new JButton("Continue");
			buttonPanel.add(continueButton);

			JComboBox<String> box = new JComboBox<String>();
			for (int i = 0; i < e.getAccounts().size(); i++) {
				box.addItem(e.getAccounts().get(i).getNumber());
			}

			for (int i = 0; i < e.getAccounts().size(); i++) {
				if (e.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
					acc = e.getAccounts().get(i);
				}
			}

			boxPanel.add(box);
			content = f.getContentPane();
			content.setLayout(new GridLayout(3, 1));
			content.add(labelPanel);
			content.add(boxPanel);
			content.add(buttonPanel);

			returnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					f.dispose();
					menuStart();
				}
			});

			continueButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					f.dispose();

					f = frame("Customer Menu");

					f.setVisible(true);

					JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton statementButton = new JButton("Display Bank Statement");
					statementButton.setPreferredSize(new Dimension(250, 20));

					statementPanel.add(statementButton);

					JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton lodgementButton = new JButton("Lodge money into account");
					lodgementPanel.add(lodgementButton);
					lodgementButton.setPreferredSize(new Dimension(250, 20));

					JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton withdrawButton = new JButton("Withdraw money from account");
					withdrawalPanel.add(withdrawButton);
					withdrawButton.setPreferredSize(new Dimension(250, 20));

					JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
					JButton returnButton = new JButton("Exit Customer Menu");
					returnPanel.add(returnButton);

					JLabel label1 = new JLabel("Please select an option");

					content = f.getContentPane();
					content.setLayout(new GridLayout(5, 1));
					content.add(label1);
					content.add(statementPanel);
					content.add(lodgementPanel);
					content.add(withdrawalPanel);
					content.add(returnPanel);

					statementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							f = frame("Customer Menu");
							
							f.setVisible(true);

							JLabel label1 = new JLabel("Summary of account transactions: ");

							JPanel returnPanel = new JPanel();
							JButton returnButton = new JButton("Return");
							returnPanel.add(returnButton);

							JPanel textPanel = new JPanel();

							textPanel.setLayout(new BorderLayout());
							JTextArea textArea = new JTextArea(40, 20);
							textArea.setEditable(false);
							textPanel.add(label1, BorderLayout.NORTH);
							textPanel.add(textArea, BorderLayout.CENTER);
							textPanel.add(returnButton, BorderLayout.SOUTH);

							JScrollPane scrollPane = new JScrollPane(textArea);
							textPanel.add(scrollPane);

							for (int i = 0; i < acc.getTransactionList().size(); i++) {
								textArea.append(acc.getTransactionList().get(i).toString());

							}

							textPanel.add(textArea);
							content.removeAll();

							Container content = f.getContentPane();
							content.setLayout(new GridLayout(1, 1));
							// content.add(label1);
							content.add(textPanel);
							// content.add(returnPanel);

							returnButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									f.dispose();
									customer(e);
								}
							});
						}
					});

					lodgementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double balance = 0;

							if (acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(f,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) acc).getAtm().setValid(false);
										customer(e);
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(f,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);
										}

									}
								}

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(f, "Enter amount you wish to lodge:");

								if (isNumeric(balanceTest)) {

									balance = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}

								String euro = "\u20ac";
								acc.setBalance(acc.getBalance() + balance);
								// String date = new
								// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
								Date date = new Date();
								String date2 = date.toString();
								String type = "Lodgement";
								double amount = balance;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(f, balance + euro + " added do you account!", "Lodgement",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance() + euro,
										"Lodgement", JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					withdrawButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double withdraw = 0;

							if (acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(f,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) acc).getAtm().setValid(false);
										customer(e);
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(f,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										}

									}
								}

							}
							if (on == true) {
								// the isNumeric method tests to see if the string entered was numeric.
								String balanceTest = JOptionPane.showInputDialog(f,
										"Enter amount you wish to withdraw (max 500):");
								if (isNumeric(balanceTest)) {

									withdraw = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}
								if (withdraw > 500) {
									JOptionPane.showMessageDialog(f, "500 is the maximum you can withdraw at a time.",
											"Oops!", JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}
								// if the account is a Current Account
								if (acc instanceof CustomerCurrentAccount) {
									if (withdraw > acc.getBalance() + ((CustomerCurrentAccount) acc).getOverdraft()) {
										JOptionPane.showMessageDialog(f,
												"Insufficient funds overdraft amount exceeded.", "Error",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) acc).setOverdraft(withdraw);
									}
								} else {
									if (withdraw > acc.getBalance()) {
										JOptionPane.showMessageDialog(f, "Insufficient funds.", "Oops!",
												JOptionPane.INFORMATION_MESSAGE);
										withdraw = 0;
									}
								}

								String euro = "\u20ac";
								// using Overdraft to withdraw
								if (acc instanceof CustomerCurrentAccount) {
									if (acc.getBalance() == 0) {
										if (withdraw < ((CustomerCurrentAccount) acc).getOverdraft()) {
											((CustomerCurrentAccount) acc).setOverdraft(
													((CustomerCurrentAccount) acc).getOverdraft() - withdraw);
											JOptionPane.showMessageDialog(f,
													"New balance = " + acc.getBalance() + euro + "\nOverdraft = "
															+ ((CustomerCurrentAccount) acc).getOverdraft(),
													"Withdraw", JOptionPane.INFORMATION_MESSAGE);

										} else {
											acc.setBalance(acc.getBalance() - withdraw);
										}
									} else {
										acc.setBalance(acc.getBalance() - withdraw);
									}
								}
								// recording transaction:
								// String date = new
								// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
								Date date = new Date();
								String date2 = date.toString();

								String type = "Withdraw";
								double amount = withdraw;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(f, withdraw + euro + " withdrawn.", "Withdraw",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(f,
										"New balance = " + acc.getBalance() + euro + "\nOverdraft = "
												+ ((CustomerCurrentAccount) acc).getOverdraft(),
										"Withdraw", JOptionPane.INFORMATION_MESSAGE);
							}

						}

					});

					returnButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							menuStart();
						}
					});
				}
			});
		}
	}

	private void customerLogin() {
		boolean loop = true, loop2 = true;
		boolean cont = false;
		boolean found = false;
		Customer customer = null;
		while (loop) {
			Object customerID = JOptionPane.showInputDialog(f, "Enter Customer ID:");

			for (Customer aCustomer : customerList) {
				//search customer list for matching customer ID
				if (aCustomer.getCustomerID().equals(customerID))
				{
					found = true;
					customer = aCustomer;
				}
			}

			if (found == false) {
				int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					loop = true;
				} else if (reply == JOptionPane.NO_OPTION) {
					f.dispose();
					loop = false;
					loop2 = false;
					menuStart();
				}
			} else {
				loop = false;
			}

		}

		while (loop2) {
			Object customerPassword = JOptionPane.showInputDialog(f, "Enter Customer Password;");

			if (!customer.getPassword().equals(customerPassword))// check if custoemr password is correct
			{
				int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {

				} else if (reply == JOptionPane.NO_OPTION) {
					f.dispose();
					loop2 = false;
					menuStart();
				}
			} else {
				loop2 = false;
				cont = true;
			}
		}

		if (cont) {
			f.dispose();
			loop = false;
			customer(customer);
		}
	}

	private void createCustomer() {
		f.dispose();
		f1 = frame("Create New Customer");
		
		Container content = f1.getContentPane();
		content.setLayout(new BorderLayout());

		firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
		surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
		pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
		dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
		firstNameTextField = new JTextField(20);
		surnameTextField = new JTextField(20);
		pPSTextField = new JTextField(20);
		dOBTextField = new JTextField(20);
		JPanel panel = new JPanel(new GridLayout(6, 2));
		panel.add(firstNameLabel);
		panel.add(firstNameTextField);
		panel.add(surnameLabel);
		panel.add(surnameTextField);
		panel.add(pPPSLabel);
		panel.add(pPSTextField);
		panel.add(dOBLabel);
		panel.add(dOBTextField);

		panel2 = new JPanel();
		add = new JButton("Add");

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PPS = pPSTextField.getText();
				firstName = firstNameTextField.getText();
				surname = surnameTextField.getText();
				DOB = dOBTextField.getText();
				password = "";

				CustomerID = "ID" + PPS;

				ArrayList<String> custID = new ArrayList<String>();

				f1.dispose();

				boolean loop = true;
				while (loop) {
					password = JOptionPane.showInputDialog(f, "Enter 7 character Password;");

					if (password.length() != 7)// Making sure password is 7 characters
					{
						JOptionPane.showMessageDialog(null, null, "Password must be 7 charatcers long",
								JOptionPane.OK_OPTION);
					} else {
						loop = false;
					}
				}
				// Read from file
				try {
					File readFile = new File("C:/refactoring/customerID.txt");
					Scanner scan = new Scanner(readFile);
					while (scan.hasNextLine()) {
						String data = scan.nextLine();
						custID.add(data);
					}

					scan.close();
				} catch (FileNotFoundException fileNotFound) {
					fileNotFound.printStackTrace();

				}
				// validation if user already exists.
				if (!custID.contains(CustomerID)) {
					ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
					Customer customer = new Customer(PPS, surname, firstName, DOB, CustomerID, password, accounts);
					customerList.add(customer);
					// write to file
					try {
						// add customerID to file
						FileWriter myWriter = new FileWriter("C:/refactoring/customerID.txt", true);
						myWriter.write(customer.getCustomerID() + "\n");
						myWriter.close();
						// write to customerInfo to store all info of customer
						FileWriter details = new FileWriter("C:/refactoring/customerInfo.txt", true);
						details.write(customer.toString());
						details.close();
						System.out.println("Successfully wrote to the file.");

					} catch (IOException exception) {
						System.out.println("An error occurred.");
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(f, "CustomerID = " + CustomerID + "\n Password = " + password,
							"Customer created.", JOptionPane.INFORMATION_MESSAGE);

					menuStart();
				} else {
					JOptionPane.showMessageDialog(f, "Customer already exists.", "Error",
							JOptionPane.INFORMATION_MESSAGE);

					menuStart();

				}

			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f1.dispose();
				menuStart();
			}
		});

		panel2.add(add);
		panel2.add(cancel);

		content.add(panel, BorderLayout.CENTER);
		content.add(panel2, BorderLayout.SOUTH);

		f1.setVisible(true);

	}

	private void adminUser() {
		boolean loop = true, loop2 = true;
		boolean cont = false;
		while (loop) {
			Object adminUsername = JOptionPane.showInputDialog(f, "Enter Administrator Username:");

			if (!adminUsername.equals("admin"))// search admin list for admin with matching admin username
			{
				int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					loop = true;
				} else if (reply == JOptionPane.NO_OPTION) {
					f1.dispose();
					loop = false;
					loop2 = false;
					menuStart();
				}
			} else {
				loop = false;
			}
		}

		while (loop2) {
			Object adminPassword = JOptionPane.showInputDialog(f, "Enter Administrator Password;");

			if (!adminPassword.equals("admin11"))// search admin list for admin with matching admin password
			{
				int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {

				} else if (reply == JOptionPane.NO_OPTION) {
					f1.dispose();
					loop2 = false;
					menuStart();
				}
			} else {
				loop2 = false;
				cont = true;
			}
		}

		if (cont) {
			f.dispose(); // fix
			loop = false;
			admin();
		}
	}

	private void customersInfo(JTextField firstNameTextField, JTextField surnameTextField, JTextField pPSTextField,
			JTextField dOBTextField, JTextField customerIDTextField, JTextField passwordTextField, int pos) {

		firstNameTextField.setText(customerList.get(position).getFirstName());
		surnameTextField.setText(customerList.get(position).getSurname());
		pPSTextField.setText(customerList.get(position).getPPS());
		dOBTextField.setText(customerList.get(position).getDOB());
		customerIDTextField.setText(customerList.get(position).getCustomerID());
		passwordTextField.setText(customerList.get(position).getPassword());

	}
	
	public JFrame frame(String frame) {
		JFrame f = new JFrame(frame);
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		return f;
	}

	public static boolean isNumeric(String str) // a method that tests if a string is numeric
	{
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
