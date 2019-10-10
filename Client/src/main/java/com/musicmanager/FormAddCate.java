package com.musicmanager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.crud.CategoryCRUD;
import com.crud.SongCRUD;

import jms.Publisher;

public class FormAddCate {

	protected Shell shell;
	private Text text;

	public static void main(String[] args) {
		try {
			FormAddCate window = new FormAddCate();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() throws Exception {
		shell = new Shell();
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				shell.setVisible(false);
				FormMain.FLAG = 0;
			}
		});
		shell.setSize(345, 216);
		shell.setText("Category");
		shell.setImage(new Image(null, ".\\src\\icon\\music.ico"));
		shell.setLocation(500, 200);
		text = new Text(shell, SWT.BORDER);
		text.setBounds(120, 60, 183, 21);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(FormMain.ID_CATE);
				String json = "{\"idCategory\":"+FormMain.ID_CATE+",\"title\":\""+text.getText()+"\"}";
				try {
					if(FormMain.FLAG == 1) {
						FormMain.FLAG = 0;
						CategoryCRUD.updateCate(json);
						FormMain.table.removeAll();
						FormMain.loadItem(FormSearch.listSearch);
						Controls.createMes(shell, "Update Category", "Update Category Complete");
						Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
								+ "just updated category: " + CategoryCRUD.getByID(FormMain.ID_CATE).getTitle());
						shell.setVisible(false);
					}else {
						int id = CategoryCRUD.sendPost(json);
						Controls.createMes(shell, "Add new Category", "Add Category Complete");
						Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
								+ "just added category: " + CategoryCRUD.getByID(id).getTitle());
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(129, 120, 75, 25);
		btnNewButton.setText("OK");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(27, 63, 86, 15);
		lblNewLabel.setText("Category");
		if(FormMain.FLAG ==1 ) {
			text.setText(CategoryCRUD.getByID(FormMain.ID_CATE).getTitle());
		}

	}
}
