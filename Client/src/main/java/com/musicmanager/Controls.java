package com.musicmanager;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Controls {
	public static void createMes(Shell shell, String text, String meString) {
		MessageBox messageBox = new MessageBox(shell);
		messageBox.setMessage(meString);
		messageBox.setText(text);
		messageBox.open();
	}
	public static String getDateCurrent() {
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);
		String dateCr = String.valueOf(date);
		return  dateCr;
	}
}
