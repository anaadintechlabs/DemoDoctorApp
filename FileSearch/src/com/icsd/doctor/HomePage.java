package com.icsd.doctor;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.print.DocFlavor;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
	Connection connection =null;
	
	//Labels for Search Content
	JLabel lblCaseType,lblFatherName,lblPatientName,lblGender,lblAge,lblUpload,lblFnm,lblGen;
	JTextField txtPatientName ,txtPatientAge,txtFnm,txtFileName;
	JRadioButton btnMale,btnFem;
	ButtonGroup btngrp;
	
	//Labels for Dashboards
	JLabel lblUltraSound,lblCTScan,lblPoliceCase,lblFemalePatients,lblMalePatients;
	File selectedFile;
	String directoryPath="C:\\DOCS";
	String baseDirectoryPath="C:\\DOCS";
	JButton btnChooseFile,btnUpload,btnSearch;
	JComboBox<String> caseTypeComboBox;
	JFrame mainFrame;
	public HomePage() {
		mainFrame = new JFrame();
		initializePanel(mainFrame);
	}

	
	private void initializePanel(JFrame mainFrame) {
		
		
		//For setting menu and frame horizontally
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.X_AXIS));
		 menuPanel = new JPanel();
		//menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
		 menuPanel.setLayout(null);
		 mainFrame.setSize(new Dimension(200,600));
		 contentPanel = new JPanel();
		
    /*LOGO*/
		 JLabel lblLogo = new JLabel(new ImageIcon("Images/logo.jpg"));
		 lblLogo.setBounds(20, 0, 200, 200);
		 menuPanel.add(lblLogo); 
		menuButtons= new JButton[menuButtonStrings.length];
		//Generating Menu Button
		int i=0;
		int xAxis;
		int yAxis=180;
		for( i =0;i<menuButtons.length;i++) {
			menuButtons[i]=new JButton(menuButtonStrings[i]);
//			menuButtons[i].setPreferredSize(new Dimension(200, 70));
			menuButtons[i].setForeground(Color.white);
			menuButtons[i].setBackground(Color.decode("#cb202d"));

			menuButtons[i].setBounds(0, yAxis, 300, 70);
			yAxis+=100;
			menuButtons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				try {
					manageButtonClicked(e.getActionCommand());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}

				
			});
			menuPanel.add(menuButtons[i]);
		}
		
		menuPanel.setBackground(Color.white);
		menuPanel.setBorder(BorderFactory.createEmptyBorder());
		//Method for setting content
		//setContentPanel("Home");
		contentPanel.setBackground(Color.black);
		contentPanel.setLayout(null);
		//Adding menuPanel and Content Panel to basePanel
		menuPanel.setVisible(true);
		menuPanel.setPreferredSize(new Dimension(100,100));
		contentPanel.setPreferredSize(new Dimension(700,1000));
		contentPanel.setVisible(true);
		mainFrame.add(menuPanel);
		mainFrame.add(contentPanel);
		
		
		mainFrame.setSize(new Dimension(1200,600));
		mainFrame.setResizable(false);
		
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
		try {
//			setDashboardContentInContentPanel();
			setUploadContentInContentPanel("Upload");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	


	//Method to manage to click events
	private void manageButtonClicked(String btnName) throws SQLException {
		System.out.println("Button CLicked is"+btnName);
		switch(btnName) {
		
		case "DashBoard":
			setDashboardContentInContentPanel();
			break;
		case "Search":
			setSearchContentInContentPanel();
			break;
		case "Upload":
			setUploadContentInContentPanel("Upload");
			break;
		case "Logout":
			logoutUser();
			break;
		
		}
		
	}


	private void logoutUser() {
		 mainFrame.dispose();
		 new LoginFrame();
		 JOptionPane.showMessageDialog(null, "User Logged out!", "Success", 0);			
		 
		
	}

	//On Dashboard We will display
	//1 Todays Patient
	//2 Total Ultrasount cases
	//3 Total CTscan cases
	//4 Total Police Case
	//5 Total Female Gender
	//6 Total Male Gender

	private void setDashboardContentInContentPanel() throws SQLException {
		contentPanel.removeAll();
		//getDataForDashboard();
		
		lblUltraSound=new JLabel("Total Ultrasound Cases");
		lblUltraSound.setBounds(40, 100, 300, 40);
		lblUltraSound.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		lblCTScan=new JLabel("Total CT Scan Cases");
		lblCTScan.setBounds(40, 150, 300, 40);
		lblCTScan.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		lblPoliceCase=new JLabel("Total Police Cases");
		lblPoliceCase.setBounds(40, 200, 300, 40);
		lblPoliceCase.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		lblFemalePatients=new JLabel("Total Female Cases");
		lblFemalePatients.setBounds(40, 250, 300, 40);
		lblFemalePatients.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		lblMalePatients=new JLabel("Total Male Cases");
		lblMalePatients.setBounds(40, 300, 300, 40);
		lblMalePatients.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		contentPanel.add(lblMalePatients);
		contentPanel.add(lblFemalePatients);
		contentPanel.add(lblPoliceCase);
		contentPanel.add(lblCTScan);
		contentPanel.add(lblUltraSound);
		contentPanel.setBackground(Color.cyan);
		
	}


	private void getDataForDashboard() throws SQLException {
		 try {
			 connection = getdbConn();
			 String selectSql="";
			 if(connection!=null){
				 PreparedStatement statement = connection.prepareStatement(selectSql);
				 ResultSet rs = statement.executeQuery();
				 if(rs.next()){
					 
				 }
			 }
			 
		 }
		 catch(Exception e){
			 System.out.println("x occured");
			 connection.close();
		 	}
		
	}


	private void setSearchContentInContentPanel() {
		contentPanel.removeAll();
		setUploadContentInContentPanel("Search");
		contentPanel.setBackground(Color.blue);
	}


	private void setUploadContentInContentPanel(String From) {
		contentPanel.removeAll();
		contentPanel.setBackground(Color.gray);
		
		lblCaseType=new JLabel("File Type");
		lblCaseType.setBounds(40, 60, 300, 40);
		lblCaseType.setFont(new Font("Monospaced",Font.BOLD, 20));
		
		
		caseTypeComboBox =new JComboBox<String>(caseTypes);  

		caseTypeComboBox.setBounds(350, 100,190,40);  
		
		
		lblPatientName=new JLabel("Patient's Name");
		lblPatientName.setBounds(40, 100, 300, 40);
		lblPatientName.setFont(new Font("Monospaced",Font.BOLD, 20));
		
		
		txtPatientName = new JTextField();
		txtPatientName.setBounds(350, 100, 300, 40);
		
		lblAge=new JLabel("Paitent's Age");
		lblAge.setBounds(40, 150, 300, 40);
		lblAge.setFont(new Font("Monospaced",Font.BOLD, 20));
	
		 try {
			txtPatientAge = new JFormattedTextField(new MaskFormatter("##"));
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		txtPatientAge.setBounds(350, 150, 300, 40);
		
		lblFnm = new JLabel("Father's Name");
		lblFnm.setFont(new Font("Monospaced",Font.BOLD, 20));
		lblFnm.setBounds(40, 200, 300, 40);
		
		txtFnm = new JTextField();
		txtFnm.setBounds(350, 200, 300, 40);
		
		lblGen = new JLabel("Gender");
		lblGen.setFont(new Font("Monospaced",Font.BOLD, 20));
		lblGen.setBounds(40, 250, 300, 40);
		
		btnMale = new JRadioButton("Male");
		btnMale.setBounds(350, 250, 100, 30);
		
		btnFem = new JRadioButton("Female");
		btnFem.setBounds(450, 250, 100, 30);
		
		btngrp = new ButtonGroup();
		btngrp.add(btnMale);
		btngrp.add(btnFem);
		
		 btnChooseFile= new JButton("Choose File");
		 btnChooseFile.setBounds(40,300,100,40);
		 btnChooseFile.setBackground(Color.CYAN);
		 btnChooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				callFileChooser();
			}
		});
		
//		 lblUpload=new JLabel("No file Selected");
//		 lblUpload.setBounds(160, 380, 160, 40);
//		 lblUpload.setFont(new Font("Monospaced",Font.BOLD, 20));
		 txtFileName= new JTextField();
		 txtFileName.setText("No file Selected");
		 txtFileName.setBounds(350,300,300,40);
		 txtFileName.setEnabled(false);
		 
		 btnSearch = new JButton("Search");
		 btnSearch.setBounds(40,420,100,40);	
		 btnSearch.setBackground(Color.CYAN);
		 
		 btnUpload= new JButton("Upload");
		 btnUpload.setBounds(40,420,100,40);

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
		 
		 btnSearch.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					searchFiles();
				}
			});
		
		contentPanel.add(lblCaseType);
		contentPanel.add(lblFnm);
		
		contentPanel.add(caseTypeComboBox);
		contentPanel.add(lblPatientName);
		
		contentPanel.add(txtFnm);
		contentPanel.add(txtPatientName);
	
		if("Upload".equalsIgnoreCase(From)){
			contentPanel.add(btnUpload);
			contentPanel.add(lblGen);
			contentPanel.add(btnMale);
			contentPanel.add(btnFem);
			contentPanel.add(lblAge);
			contentPanel.add(txtPatientAge);
			contentPanel.add(btnChooseFile);
			//contentPanel.add(lblUpload);
			contentPanel.add(txtFileName);
		}
		if("Search".equalsIgnoreCase(From)){
		  contentPanel.add(btnSearch);
		}
	}
	
	public void callFileChooser() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION) {
			selectedFile = jfc.getSelectedFile();
			//System.out.println("selectedFile"+selectedFile);
			
			txtFileName.setText(selectedFile.getName());
		}
	}
	
	private void validateAndSaveDataToDatabase() {
		// TODO Auto-generated method stub
		
	}
	
	private void renameAndUploadFile() throws IOException {
		
		String patientName =txtPatientName.getText();
		String patientAge=txtPatientAge.getText();
		String fileType=caseTypeComboBox.getSelectedItem().toString();
		System.out.println("Patient Age"+patientAge);
		
		//File name to stored, It will always be unique as we used timestamp
		String finalName=patientName+"_"+patientAge+"_"+new Date().getTime();
		//New Folder Will be created with FILETYPE Like CTSCAN, ULTRASOUND,POLICECASE
		directoryPath=directoryPath+"\\"+fileType;
		File mainDirectory = new File(directoryPath);
		File serverFile = null;
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
			 serverFile = new File(mainDirectory + "/" + finalName+"."+extension);
			copyFileUsingStream(selectedFile,serverFile);				
		}

		directoryPath=baseDirectoryPath;

		String fatherName = txtFnm.getText().toString();
		String gen = "";
		if(btnMale.isSelected()){
			gen = "M";
		}else if(btnFem.isSelected()){
			gen = "F";
		}
		  
		  try {
			 connection = getdbConn();
			 System.out.println("connection success");
			 String selectSql = "Insert into PatientDetails values (?,?,?,?,?,?,?,?,?)";
			 
			 if(connection!=null){
			 PreparedStatement statement = connection.prepareStatement(selectSql);
			 statement.setInt(1, 1);
			 statement.setString(2, patientName);
			 statement.setInt(3, Integer.parseInt(patientAge));
			 statement.setString(4, fatherName);
			 statement.setString(5, gen);
			 statement.setString(6, fileType);
			 statement.setString(7, selectedFile.toString());
			 statement.setDate(8, new java.sql.Date(new Date().getTime()));
			 statement.setString(9, finalName);
			 statement.executeQuery();
			 
	           // if(statement.execute()){
	            	 System.out.println("data inserted successfully");
	             	JOptionPane.showMessageDialog(null, "File Uploaded Successfully");
	             	setDashboardContentInContentPanel();
//	            }
//	            else
//	            {
//	            	JOptionPane.showMessageDialog(null, "Erro Uploading File");
//	            }
	           
	            // Print results from select statement
            	//Now go to dashboard
	            
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(patientAge+patientName+fileType);
		
	}

	private void  searchFiles(){
		String patientName =txtPatientName.getText();
		String patientAge=txtPatientAge.getText();
		String fileType=caseTypeComboBox.getSelectedItem().toString();
		String fatherName = txtFnm.getText().toString();
		System.out.println("patientName"+patientName+"fileType"+fileType+"fatherName"+fatherName);
		 String selectSql = "select * from PatientDetails where PatientName like ? and FileType like ? and FatherName like ?";
		  try {
				 connection = getdbConn();
				 System.out.println("connection success");
				 	 
				 if(connection!=null){
				 PreparedStatement statement = connection.prepareStatement(selectSql);
				 
				 statement.setString(1, "%"+patientName+"%");
//				 statement.setInt(2, Integer.parseInt(patientAge));
				 statement.setString(2, "%"+fileType+"%");
				 statement.setString(3, "%"+fatherName+"%");
				 
				 JTable tbl = null ;
		            ResultSet rs=statement.executeQuery();
		            //System.out.println(rs.next()+"rss");
		            System.out.println(rs.getFetchSize()+"rss");
//		            if(rs.next()){
		            	System.out.println("Paras");
		            	tbl = new JTable();
		            	 DefaultTableModel dtm = new DefaultTableModel(0, 0);
		            	 String header[] =  { "Patient Name", "Father Name",
		            	            "File Type", "File Name","Button"};
		            	 dtm.setColumnIdentifiers(header);
		            	 JButton btn;
		            	 while(rs.next()){
		            		 System.out.println("testppp");
		            		 dtm.addRow(new Object[]{
		            			rs.getString("PatientName"),
		            			rs.getString("FatherName"),
		            			rs.getString("FileType"),
		            			rs.getString("FileName"),
		            			rs.getString("FilePath"),
		            		 });
		            	//	 tbl.getColumn("Action").setCellRenderer(new ButtonRenderer());
		            //		 System.out.println(rs.getString("PatientName"));
		            	 }
		            	// tbl.setBounds(40,320,600,400);
		            	 tbl.setModel(dtm);
		            	 tbl.getColumn("Button").setCellRenderer(new ButtonRenderer());
		            	 tbl.addMouseListener(new JTableButtonMouseListener(tbl)); 
		            	 tbl.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
		            	   TableColumn column = tbl.getColumnModel().getColumn(3);
		            	   column.setPreferredWidth(150);
                           tbl.setEnabled(false);
		            	 JScrollPane scrollPane = new JScrollPane(tbl);
		            	 scrollPane.setBounds(200, 400, 600, 200);
		            	 contentPanel.add(scrollPane);
		          //  }
		            System.out.println("Search successfully"+rs);
		            
		            System.out.println("Table added");
	            	//JOptionPane.showMessageDialog(null, "File Uploaded Successfully");
		            // Print results from select statement
		            
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private Connection getdbConn() {
		String connectionUrl =
                "jdbc:sqlserver://localhost:1433;user=sa;password=enter;database=Test";
		Connection conn = null;
		try {
			 conn = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
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