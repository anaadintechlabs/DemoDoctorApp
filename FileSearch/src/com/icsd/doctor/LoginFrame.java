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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
	
	Container c;
	public LoginFrame() {
			c=getContentPane();
			this.pack();
			System.out.println("height is"+c.getHeight()+"widht is"+c.getWidth());
		
		 lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(180, 300, 300, 60);
		lblWelcome.setFont(new Font("Monospaced",Font.BOLD, 48));
		
		 lblMessage = new JLabel("Login to access your account");
		lblMessage.setBounds(150, 380, 450,60);
		lblMessage.setFont(new Font("Monospaced",Font.ITALIC,24));
		
		 lblUsername=new JLabel("Username");
		lblUsername.setBounds(150, 460, 300, 40);
		lblUsername.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		 txtUsername = new JTextField();
		txtUsername.setBounds(150, 520, 450, 40);
		
		 lblPassword=new JLabel("Password");
		lblPassword.setBounds(150, 560, 300, 40);
		lblPassword.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		 txtPassword = new JPasswordField();
		txtPassword.setBounds(150, 620, 450, 40);
		
		
		 btnLogin = new JButton("Login");
		btnLogin.setBounds(150,680,100,40);
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
		lblLogo.setBounds(650, 0, 1300,1050);
		
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
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	/**
	 * @description Method to check the credentials of User
	 */
	private void checkCredentials() {
		
		String username=txtUsername.getText();
		String password=txtPassword.getPassword().toString();
		System.out.println("Username is "+username);
		System.out.println("Password is "+password);
		JOptionPane.showMessageDialog(null, "User name is "+username+"Password is"+password, "Demo", 0);
			
	}
	
	public void callFileChooser() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println("selectedFile"+selectedFile);
		}
	}
}
