package com.icsd.doctor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.filechooser.FileSystemView;

/**
 * 
 * @author Sanchit
 *
 */
public class LoginFrame extends JFrame{

	
	private static final long serialVersionUID = 1L;

	//Login frame labels and fields 
	JLabel lblWelcome,lblMessage,lblUsername,lblPassword,lblLogo;
	JTextField txtUsername;
	JPasswordField txtPassword;
	JButton btnLogin,btnUpload;
	JPanel pnl1,pnl2,mainPanel;
	Connection connection =null;
	
	//three entries for dropdown
	
	String caseTypes[]= {"ULTRASOUND","CTSCAN","POLICECASE"};
	Container c;
	public LoginFrame() {
			c=getContentPane();
			this.pack();
			mainPanel = new JPanel(new BoxLayout(c, BoxLayout.X_AXIS));
			System.out.println("height is"+c.getHeight()+"widht is"+c.getWidth());
		
		 lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(80, 60, 300, 60);
		lblWelcome.setFont(new Font("Monospaced",Font.BOLD, 48));
		
		 lblMessage = new JLabel("Login to access your account");
		lblMessage.setBounds(30, 140, 450,60);
		lblMessage.setFont(new Font("Monospaced",Font.ITALIC,20));
		
		 lblUsername=new JLabel("Username");
		lblUsername.setBounds(30, 220, 300, 40);
		lblUsername.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		 txtUsername = new JTextField();
		txtUsername.setBounds(30, 280, 350, 40);
		
		 lblPassword=new JLabel("Password");
		lblPassword.setBounds(30, 340, 300, 40);
		lblPassword.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		 txtPassword = new JPasswordField();
		txtPassword.setBounds(30, 400, 350, 40);
		
		
		 btnLogin = new JButton("Login");
		btnLogin.setBounds(30,460,100,40);
		btnLogin.setBackground(Color.CYAN);
	
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkCredentials();
			}

		
		});
		
		 btnUpload= new JButton("Upload File");
		 btnUpload.setBounds(150,760,100,40);
		 btnUpload.setBackground(Color.CYAN);
		 btnUpload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				callFileChooser();
			}
		});
			
		lblLogo = new JLabel(new ImageIcon("Images/demo.jpg"));
		lblLogo.setBounds(400, 0, 800,800);
		
		c.add(lblWelcome);
		c.add(lblMessage);
		c.add(lblUsername);
		c.add(txtUsername);
		c.add(lblPassword);
		c.add(txtPassword);
		c.add(btnLogin);
		c.add(lblLogo);
		c.add(btnUpload);
		
		c.setBackground(Color.white);
		
		this.setTitle("demo");
		this.setLayout(null);
		//for full screen frame
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.setSize(new Dimension(1200,600));
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	/**
	 * @description Method to check the credentials of User
	 */
	private void checkCredentials() {
		
		String username=txtUsername.getText();
		String password=txtPassword.getText();
		System.out.println("Username is "+username);
		System.out.println("Password is "+password);
		
		String selectSql = "select * from users where unm=? and pwd=?";
		  try {
			  connection = getdbConn();
			  if(connection!=null){
					 PreparedStatement statement = connection.prepareStatement(selectSql);
					 statement.setString(1, username);
					 statement.setString(2, password);
					 ResultSet rs=statement.executeQuery();
					 System.out.println("Resule set is "+rs);
					 if(rs.next()){
						 this.dispose();
						 new HomePage();
						 JOptionPane.showMessageDialog(null, "Login Successfull", "Success", 0);			
						 
					 }
					 else
					 {
						 JOptionPane.showMessageDialog(null, "Invalid Credentials", "Oops!", 0);
					 }
					
			  }
			  
		  }
		  catch(Exception e){
			  System.out.println("Exception occured"+e);

		  }
		
	}
	
	public void callFileChooser() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println("selectedFile"+selectedFile);
			openPopup();
			
		}
	}
	public void openPopup() {
		PopupFactory popupfactory = new PopupFactory();
		Popup sendOptionPopup;
		
		JPanel popupPanel = new JPanel();
		popupPanel.setBackground(Color.red);
		JComboBox caseTypeComboBox=new JComboBox(caseTypes);    
		caseTypeComboBox.setBounds(50, 50,90,20);  
		
	    popupPanel.add(caseTypeComboBox);
	    
	    sendOptionPopup=popupfactory.getPopup(this, popupPanel, 300, 400);
	    sendOptionPopup.show();
	}
	//Mychanges function
	
	public void fun3Sachin()
	{
		System.out.println("fun3 callled ");  
	}
	public void myfunsanchit() {
		
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
}
