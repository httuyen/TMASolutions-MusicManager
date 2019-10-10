package com.musicmanager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import com.crud.UserCRUD;

import org.eclipse.swt.widgets.Button;

public class FormChangePass extends GlobalVarible{

	protected Shell shlChangePassword;
	private Text textOldPWD;
	private Text textNewPWD;
	private Text textConfirmPWD;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormChangePass window = new FormChangePass();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlChangePassword.open();
		shlChangePassword.layout();
		while (!shlChangePassword.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlChangePassword = new Shell();
		shlChangePassword.setSize(411, 230);
		shlChangePassword.setText("Change Password");
		shlChangePassword.setImage(GlobalVarible.ICON_CHANGE_PWD);
		shlChangePassword.setLocation(420, 250);
		Label lblNewLabel = new Label(shlChangePassword, SWT.NONE);
		lblNewLabel.setBounds(65, 38, 86, 15);
		lblNewLabel.setText("Old Password:");
		
		textOldPWD = new Text(shlChangePassword, SWT.BORDER | SWT.PASSWORD);
		textOldPWD.setBounds(157, 35, 177, 21);
		
		textNewPWD = new Text(shlChangePassword, SWT.BORDER | SWT.PASSWORD);
		textNewPWD.setBounds(157, 70, 177, 21);
		
		Label lblNewPassword = new Label(shlChangePassword, SWT.NONE);
		lblNewPassword.setText("New Password:");
		lblNewPassword.setBounds(60, 73, 91, 15);
		
		textConfirmPWD = new Text(shlChangePassword, SWT.BORDER | SWT.PASSWORD);
		textConfirmPWD.setBounds(157, 104, 177, 21);
		
		Label lblConfirmPassword = new Label(shlChangePassword, SWT.NONE);
		lblConfirmPassword.setText("Confirm Password:");
		lblConfirmPassword.setBounds(39, 107, 112, 18);
		
		Button btnConfirmP = new Button(shlChangePassword, SWT.NONE);
		btnConfirmP.setBounds(165, 144, 75, 25);
		btnConfirmP.setText("Confirm");
		
		btnConfirmP.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(textOldPWD.getText().isEmpty()) {
					Controls.createMes(shlChangePassword, "Notification", "Old Password can't be empty !!!");
					return;
				} else if(textNewPWD.getText().isEmpty()) {
					Controls.createMes(shlChangePassword, "Notification", "New Password can't be empty !!!");
					return;
				} else if(textConfirmPWD.getText().isEmpty()) {
					Controls.createMes(shlChangePassword, "Notification", "Confirm Password can't be empty !!!");
					return;
				} else if(!textOldPWD.getText().equals(FormMain.PASSWORD)) {
					Controls.createMes(shlChangePassword, "Notification", "Old Password incorrect !!!");					
					return;
				}else if(!textNewPWD.getText().equals(textConfirmPWD.getText().toString())) {
					Controls.createMes(shlChangePassword, "Notification", "New Password and Confirm Password does not match !!!");
					return;
				} else {
					String json = "{\r\n" + 
							"    \"idUser\": "+FormLogin.ID_USER+",\r\n" + 
							"    \"username\": \""+FormMain.USERNAME+"\",\r\n" + 
							"    \"password\": \""+textNewPWD.getText()+"\",\r\n" + 
							"    \"name\": \""+TXT_NAME+"\",\r\n" + 
							"    \"phone\": \""+TXT_PHONE+"\",\r\n" + 
							"    \"email\": \""+TXT_EMAIL+"\",\r\n" + 
							"    \"address\": \""+TXT_ADDRESS+"\",\r\n" + 
							"    \"admin\": false\r\n" + 
							"}";
					try {
						UserCRUD.updateUser(json);
						Controls.createMes(shlChangePassword, "Notification", "Password was changed !!!");
						shlChangePassword.setVisible(false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
