package com.icsd.doctor;

/**
 * 
 * @author Sanchit
 *
 */
public class Entry {
	
	public static void main(String[] args) {
		
      /* SPLASH CODE*/
		
		Splash s=new Splash();
	      s.setVisible(true);
	      Thread t=Thread.currentThread();
	      try {
			t.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      s.dispose();
		//Entry Point of application
		LoginFrame loginFrame = new LoginFrame();
		// new HomePage();
		// Paras Miglani Entry
		//Sanchit Khurana Entry
		//ok
		//HomePage homePage = new HomePage();
	}
}
