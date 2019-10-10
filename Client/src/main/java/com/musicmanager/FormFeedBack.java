package com.musicmanager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.crud.FeedBackCRUD;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class FormFeedBack {

	protected Shell shlFeedback;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormFeedBack window = new FormFeedBack();
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
		shlFeedback.open();
		shlFeedback.layout();
		while (!shlFeedback.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlFeedback = new Shell();
		shlFeedback.setSize(493, 316);
		shlFeedback.setText("Feedback");
		shlFeedback.setLayout(new GridLayout(1, false));
		shlFeedback.setImage(GlobalVarible.ICON_FB);
		shlFeedback.setLocation(400, 250);
		text = new Text(shlFeedback, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_text.heightHint = 224;
		text.setLayoutData(gd_text);
		Button btnFeedBack = new Button(shlFeedback, SWT.NONE);
		btnFeedBack.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnFeedBack.setText("Send Feedback");
		btnFeedBack.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(text.getText().isEmpty()) {
					Controls.createMes(shlFeedback, "Notification", "Content can't be empty!!!");
				}else {
					String json = "{\r\n" + 
							"	\"content\":\""+text.getText().toString()+"\",\r\n" + 
							"	\"dateCreate\":\""+Controls.getDateCurrent()+"\",\r\n" + 
							"	\"idUser\":"+FormLogin.ID_USER+"\r\n" + 
							"}";
					try {
						FeedBackCRUD.post(json);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Controls.createMes(shlFeedback, "Notification", "Send complete");
					shlFeedback.setVisible(false);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
