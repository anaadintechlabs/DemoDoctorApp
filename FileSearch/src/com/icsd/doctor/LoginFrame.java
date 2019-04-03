package com.icsd.doctor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Sanchit
 *
 */
public class LoginFrame extends JFrame{

	
	private static final long serialVersionUID = 1L;

	Container c;
	public LoginFrame() {
			c=getContentPane();
			this.pack();
			System.out.println("height is"+c.getHeight()+"widht is"+c.getWidth());
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(180, 300, 300, 60);
		lblWelcome.setFont(new Font("Monospaced",Font.BOLD, 48));
		
		JLabel lblMessage = new JLabel("Login to access your account");
		lblMessage.setBounds(150, 380, 450,60);
		lblMessage.setFont(new Font("Monospaced",Font.ITALIC,24));
		
		JLabel lblUsername=new JLabel("Username");
		lblUsername.setBounds(150, 460, 300, 40);
		lblUsername.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		
		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(150, 520, 450, 40);
		
		JLabel lblPassword=new JLabel("Password");
		lblPassword.setBounds(150, 560, 300, 40);
		lblPassword.setFont(new Font("Monospaced",Font.BOLD, 24));
		
		JTextField txtPassword = new JTextField();
		txtPassword.setBounds(150, 620, 450, 40);
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(150,680,100,40);
		btnLogin.setBackground(Color.CYAN);
		
		
		JLabel lblLogo = new JLabel(new ImageIcon("Images/demo.jpg"));
		lblLogo.setBounds(650, 0, 1300,1050);
		
		c.add(lblWelcome);
		c.add(lblMessage);
		c.add(lblUsername);
		c.add(txtUsername);
		c.add(lblPassword);
		c.add(txtPassword);
		c.add(btnLogin);
		c.add(lblLogo);
		
		c.setBackground(Color.white);
		
		this.setTitle("demo");
		this.setLayout(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
