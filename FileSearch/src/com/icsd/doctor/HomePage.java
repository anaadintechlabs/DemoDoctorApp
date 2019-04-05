package com.icsd.doctor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Date;

import javax.print.DocFlavor;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.MaskFormatter;


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
	JTextField txtPatientName ;
	JFormattedTextField txtPatientAge;
	
	JButton btnChooseFile,btnUpload;
	JComboBox<String> caseTypeComboBox;
	
	//Path for File Stroage
	private  Path fileStoragePath;
	private Path fileStorageLocation;
	private String directoryPath="C:\\DOCS";
	private String baseDirectoryPath="C:\\DOCS";
	private File selectedFile;
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
		
		 try {
			txtPatientAge = new JFormattedTextField(new MaskFormatter("###"));
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
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
		 lblUpload.setBounds(160, 280, 360, 40);
		 lblUpload.setFont(new Font("Monospaced",Font.BOLD, 20));
		 
		 btnUpload= new JButton("Upload File");
		 btnUpload.setBounds(40,380,100,40);
		 btnUpload.setBackground(Color.CYAN);
		 btnUpload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//Validate Data, Save Into Database
					validateAndSaveDataToDatabase();
					renameAndUploadFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
			selectedFile = jfc.getSelectedFile();
			System.out.println("selectedFile"+selectedFile);
			
			lblUpload.setText(selectedFile.getName());
		}
	}
	
	private void validateAndSaveDataToDatabase() {
		// TODO Auto-generated method stub
		
	}
	
	private void renameAndUploadFile() throws IOException {
		
		String patientName =txtPatientName.getText();
		String patientAge=txtPatientAge.getText();
		String fileType=caseTypeComboBox.getSelectedItem().toString();
		
		//File name to stored, It will always be unique as we used timestamp
		String finalName=patientName+"_"+patientAge+"_"+new Date().getTime();
		//New Folder Will be created with FILETYPE Like CTSCAN, ULTRASOUND,POLICECASE
		directoryPath=directoryPath+"\\"+fileType;
		File mainDirectory = new File(directoryPath);
		//If directory is not created , Make is
		if(!mainDirectory.exists())
		{
			mainDirectory.mkdirs();			
		}

		//For getting extension,there are other method alse
		if(selectedFile!=null) {
			System.out.println("select file is"+selectedFile.getName());
			System.out.println("select file size is"+selectedFile.length());
			long space=selectedFile.length()/1024*1024*10;
			//PUT LOGIC FOR SPACE HERE, IF SIZE EXCEED SHOW ERROR MESSAGE
			System.out.println("Size in kb is"+space);
			System.out.println("free file is"+selectedFile.getFreeSpace() +"total space "+selectedFile.getTotalSpace()+"Usable space"+selectedFile.getUsableSpace());
			
			String extension = "";
			int i = selectedFile.getName().lastIndexOf('.');
			if (i > 0) {
			    extension = selectedFile.getName().substring(i+1);
			}
			//File to be saved
			File serverFile = new File(mainDirectory + "/" + finalName+"."+extension);
			copyFileUsingStream(selectedFile,serverFile);				
		}

		directoryPath=baseDirectoryPath;

	}
	
	
	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
	
	
}


//String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
//        "August", "September", "October", "November", "December" };
//Calendar cal = Calendar.getInstance();
//String month = monthName[cal.get(Calendar.MONTH)];