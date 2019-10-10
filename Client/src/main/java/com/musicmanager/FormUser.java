package com.musicmanager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.crud.UserCRUD;

public class FormUser extends GlobalVarible {

	protected Shell shlUser;
	public static Text textName;
	public static Text textPhone;
	public static Text textAddress;
	public static Text textEmail;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormUser window = new FormUser();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws Exception 
	 */
	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		shlUser.open();
		shlUser.layout();
		while (!shlUser.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @throws Exception 
	 */
	protected void createContents() throws Exception {
		shlUser = new Shell();
		shlUser.setSize(450, 280);
		shlUser.setText("User");
		shlUser.setImage(GlobalVarible.ICON_USER_);
		shlUser.setLocation(400, 200);
		Label lblNewLabel = new Label(shlUser, SWT.NONE);
		lblNewLabel.setBounds(71, 23, 35, 15);
		lblNewLabel.setText("Name:");
		
		textName = new Text(shlUser, SWT.BORDER);
		textName.setBounds(121, 20, 238, 21);
		
		Label lblPhoneNumber = new Label(shlUser, SWT.NONE);
		lblPhoneNumber.setText("Phone Number:");
		lblPhoneNumber.setBounds(20, 63, 86, 15);
		
		textPhone = new Text(shlUser, SWT.BORDER);
		textPhone.setBounds(121, 60, 238, 21);
		
		Label lblAdress = new Label(shlUser, SWT.NONE);
		lblAdress.setText("Address:");
		lblAdress.setBounds(61, 142, 45, 15);
		
		textAddress = new Text(shlUser, SWT.BORDER);
		textAddress.setBounds(121, 139, 238, 21);
		
		Label lblEmail = new Label(shlUser, SWT.NONE);
		lblEmail.setText("Email:");
		lblEmail.setBounds(71, 104, 45, 15);
		
		textEmail = new Text(shlUser, SWT.BORDER);
		textEmail.setBounds(121, 101, 238, 21);
		
		load();
		
		Button btnUpdate = new Button(shlUser, SWT.NONE);
		btnUpdate.setBounds(184, 187, 75, 25);
		btnUpdate.setText("Update");
		btnUpdate.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String json = "{\r\n" + 
						"    \"idUser\": "+FormLogin.ID_USER+",\r\n" + 
						"    \"username\": \""+FormMain.USERNAME+"\",\r\n" + 
						"    \"password\": \""+FormMain.PASSWORD+"\",\r\n" + 
						"    \"name\": \""+textName.getText()+"\",\r\n" + 
						"    \"phone\": \""+textPhone.getText().toString()+"\",\r\n" + 
						"    \"email\": \""+textEmail.getText()+"\",\r\n" + 
						"    \"address\": \""+textAddress.getText()+"\",\r\n" + 
						"    \"admin\": false\r\n" + 
						"}";
				try {
					UserCRUD.updateUser(json);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Controls.createMes(shlUser, "Notification", "Update complete !!!");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
	}
	private void load() throws Exception {
		TXT_NAME = UserCRUD.getByID(FormLogin.ID_USER).getName();
		TXT_PHONE = UserCRUD.getByID(FormLogin.ID_USER).getPhone();
		TXT_EMAIL = UserCRUD.getByID(FormLogin.ID_USER).getEmail();
		TXT_ADDRESS = UserCRUD.getByID(FormLogin.ID_USER).getAddress();
		textName.setText(TXT_NAME);
		textPhone.setText(TXT_PHONE);
		textEmail.setText(TXT_EMAIL);
		textAddress.setText(TXT_ADDRESS);
	}
}
