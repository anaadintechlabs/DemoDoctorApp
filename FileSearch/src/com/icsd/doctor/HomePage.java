package com.icsd.doctor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class HomePage 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton menuButtons[];
	String[] menuButtonStrings= {"DashBoard","Search","Upload","Logout"};
	JPanel menuPanel,contentPanel;
	String caseTypes[]= {"ULTRASOUND","CTSCAN","POLICECASE"};
	
	//Labels for Search Content
	JLabel lblCaseType,lblFatherName,lblPatientName,lblGender,lblAge,lblUpload;
	JTextField txtPatientName ,txtPatientAge;
	JButton btnChooseFile,btnUpload;
	JComboBox<String> caseTypeComboBox;
	
	
	public HomePage() {
		JFrame mainFrame = new JFrame();
		initializePanel(mainFrame);
	}

	
	private void initializePanel(JFrame mainFrame) {
		
		
		//For setting menu and frame horizontally
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.X_AXIS));
		 menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
		 contentPanel = new JPanel();
		
		menuButtons= new JButton[menuButtonStrings.length];
		//Generating Menu Button
		int i=0;
		for( i =0;i<menuButtons.length;i++) {
			menuButtons[i]=new JButton(menuButtonStrings[i]);
			menuButtons[i].setPreferredSize(new Dimension(200, 70));
			menuButtons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				manageButtonClicked(e.getActionCommand());
				}

				
			});
			menuPanel.add(menuButtons[i]);
		}
		
		//Method for setting content
		//setContentPanel("Home");
		contentPanel.setBackground(Color.red);
		contentPanel.setLayout(null);
		//Adding menuPanel and Content Panel to basePanel
		menuPanel.setVisible(true);
		contentPanel.setVisible(true);
		mainFrame.add(menuPanel);
		mainFrame.add(contentPanel);
		

		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setMinimumSize(new Dimension(800,800));
		mainFrame.setSize(800, 800);
	}
	
	


	//Method to manage to click events
	private void manageButtonClicked(String btnName) {
		System.out.println("Button CLicked is"+btnName);
		switch(btnName) {
		
		case "DashBoard":
			setDashboardContentInContentPanel();
			break;
		case "Search":
			setSearchContentInContentPanel();
			break;
		case "Upload":
			setUploadContentInContentPanel();
			break;
		case "Logout":
			break;
		
		}
		
	}


	private void setDashboardContentInContentPanel() {
		// TODO Auto-generated method stub
		
	}


	private void setSearchContentInContentPanel() {
		contentPanel.removeAll();
		contentPanel.setBackground(Color.blue);
	}


	private void setUploadContentInContentPanel() {
		contentPanel.removeAll();
		contentPanel.setBackground(Color.gray);
		
		lblCaseType=new JLabel("File Type");
		lblCaseType.setBounds(40, 100, 300, 40);
		lblCaseType.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		caseTypeComboBox =new JComboBox<String>(caseTypes);  

		caseTypeComboBox.setBounds(350, 100,190,40);  
		
		
		lblPatientName=new JLabel("Patient's Name");
		lblPatientName.setBounds(40, 160, 300, 40);
		lblPatientName.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		txtPatientName = new JTextField();
		txtPatientName.setBounds(350, 160, 300, 40);
		
		lblAge=new JLabel("Paitent's Age");
		lblAge.setBounds(40, 220, 300, 40);
		lblAge.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		 txtPatientAge = new JTextField();
		txtPatientAge.setBounds(350, 220, 450, 40);
		
		
		 btnChooseFile= new JButton("Choose File");
		 btnChooseFile.setBounds(40,280,100,40);
		 btnChooseFile.setBackground(Color.CYAN);
		 btnChooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				callFileChooser();
			}
		});
		
		 lblUpload=new JLabel("No file Selected");
		 lblUpload.setBounds(160, 280, 160, 40);
		 lblUpload.setFont(new Font("Monospaced",Font.BOLD, 20));
		 
		 btnUpload= new JButton("Choose File");
		 btnUpload.setBounds(40,380,100,40);
		 btnUpload.setBackground(Color.CYAN);
		 btnUpload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				renameAndUploadFile();
			}


		});
		
		contentPanel.add(lblCaseType);
		contentPanel.add(caseTypeComboBox);
		contentPanel.add(lblPatientName);
		contentPanel.add(txtPatientAge);
		contentPanel.add(lblAge);
		contentPanel.add(txtPatientName);
		contentPanel.add(btnChooseFile);
		contentPanel.add(lblUpload);
		contentPanel.add(btnUpload);
		
	}
	
	public void callFileChooser() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println("selectedFile"+selectedFile);
			
			lblUpload.setText(selectedFile.getName());
		}
	}
	
	private void renameAndUploadFile() {
		
		String patientName =txtPatientName.getText();
		String patientAge=txtPatientAge.getText();
		String fileType=caseTypeComboBox.getSelectedItem().toString();
		
		System.out.println(patientAge+patientName+fileType);
		
	}
	
}


//String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
//        "August", "September", "October", "November", "December" };
//Calendar cal = Calendar.getInstance();
//String month = monthName[cal.get(Calendar.MONTH)];