package com.musicmanager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class FormHelp {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormHelp window = new FormHelp();
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
		shell.open();
		shell.layout();
		shell.setImage(new Image(null, ".\\src\\icon\\aboutIcon.ico"));
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
		shell.setSize(665, 300);
		shell.setText("Help");
		shell.setLocation(400, 200);
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(0, 0, 649, 261);
		String str = "\nDoubleClick to Edit or Delete"
				+ "\nIf you double click to column Name,Singer or Musican, you will edit and delete with Song"
				+ "\nOrtherwise, you will edit and delete with Category of Song"
				+ "\n\nFollow Add -> Add New Song: add new a song"
				+ "\n\nFollow Add -> Add New Category: add new a category"
				+ "\n\nMusic Manager was coded by Huynh Trong Tuyen- email: httuyen.it@gmail.com";
		lblNewLabel.setText(str);

	}
}
