package com.musicmanager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.crud.UserCRUD;
import com.musicmanager.Controls;

import jms.Subcribers;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

public class FormLogin extends GlobalVarible {
	public static int ID_USER = 0;
	protected Shell shell;
	private Text textUser;
	private Text textPass;
	private Text textConf;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread thread = new Thread(new Subcribers());
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.setDaemon(true);
		thread.start();
		FormLogin window = new FormLogin();
		window.open();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.setImage(ICON_LOGIN);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(398, 393);
		shell.setText("Login");
		shell.setLocation(400, 200);
		//shell.setBackground(new Color(null, new RGB(158,224,230)));
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(55, 57, 55, 15);
		lblNewLabel.setText("username:");
		textUser = new Text(shell, SWT.BORDER);
		textUser.setBounds(130, 54, 167, 21);

		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setText("Password:");
		lblPassword.setBounds(55, 102, 55, 15);

		textPass = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		textPass.setBounds(130, 99, 167, 21);
		
		Label lblOr = new Label(shell, SWT.NONE);
		lblOr.setBounds(190, 230, 30, 15);
		lblOr.setText("or");

		Label lblConfirmPassword = new Label(shell, SWT.NONE);
		lblConfirmPassword.setText("Confirm password:");
		lblConfirmPassword.setBounds(10, 143, 110, 15);
		lblConfirmPassword.setVisible(false);
		textConf = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		textConf.setBounds(130, 140, 167, 21);
		textConf.setVisible(false);

		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.setBounds(146, 201, 101, 25);
		btnLogin.setText("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				lblConfirmPassword.setVisible(false);
				textConf.setVisible(false);
				try {
					if (isLogin) {
						logIn();
					} else {
						textConf.setText("");
						isLogin = true;
						isCreate = true;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		Button btnCreateAccount = new Button(shell, SWT.NONE);
		btnCreateAccount.setText("Create Account");
		btnCreateAccount.setBounds(146, 251, 101, 25);
		btnCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				lblConfirmPassword.setVisible(true);
				textConf.setVisible(true);
				isLogin = false;
				try {
					createAcc();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isCreate = false;
			}
		});
	}

	private void createAcc() throws Exception {
		if (!isCreate) {
			try {
				if (textUser.getText().equals("")) {
					Controls.createMes(shell, "Error", "username can't be empty!!!");
					return;
				}
				for (int i = 0; i < UserCRUD.getListUser().size(); i++) {
					if (textUser.getText().equals(String.valueOf(UserCRUD.getUser().get(i).getUsername()))) {
						Controls.createMes(shell, "Error", "Username is exist");
						return;
					}
				}
				if (textPass.getText().equals("")) {
					Controls.createMes(shell, "Error", "password can't be empty !!!");
					return;
				} else if (textConf.getText().equals("")) {
					Controls.createMes(shell, "Error", "confirm password can't be empty !!!");
					return;
				} else if (!textConf.getText().equals(textPass.getText())) {
					Controls.createMes(shell, "Error", "Password and Confirm Password does not match!");
				} else {
					String json = "{\r\n" + "    \"username\": \"" + textUser.getText() + "\",\r\n"
							+ "    \"password\": \"" + textPass.getText() + "\"\r\n" + "}";
					UserCRUD.postUser(json);
					Controls.createMes(shell, "Complete", "Your account was created !");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void logIn() throws Exception {
		if (textUser.getText().equals("")) {
			Controls.createMes(shell, "Error", "username can't be empty !!!");
			return;
		} else if (textPass.getText().equals("")) {
			Controls.createMes(shell, "Error", "password can't be empty !!!");
			return;
		} else if (UserCRUD.getUser().isEmpty()){
			Controls.createMes(shell, "Notification", "User is not exist");
		} else {
			for (int i = 0; i < UserCRUD.getUser().size(); i++) {
				if (textUser.getText().equals(String.valueOf(UserCRUD.getUser().get(i).getUsername().toString()))) {
					FormLogin.ID_USER = UserCRUD.getUser().get(i).getIdUser();
					FormMain.USERNAME = UserCRUD.getUser().get(i).getUsername();
					FormMain.PASSWORD = UserCRUD.getUser().get(i).getPassword();
					if (textPass.getText().toString().equals(FormMain.PASSWORD)) {
						System.out.println(ID_USER);
						FormMain formMain = new FormMain();
						shell.setVisible(false);
						formMain.open();
					} else {
						Controls.createMes(shell, "", "Password is incorrect");
						return;
					}
				}
				else if(i == UserCRUD.getUser().size()-1){
					Controls.createMes(shell, "", "User is not exist");
					return;
				}
			}
		}
	}
}
