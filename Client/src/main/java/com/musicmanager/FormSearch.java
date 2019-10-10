package com.musicmanager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.crud.SongCRUD;
import com.pojoclass.SongPOJO;

public class FormSearch extends GlobalVarible {

	protected static Shell shlSearch;
	private static Text textSearch;
	public static String strSearch = "";
	public static List<SongPOJO> listSearch = new ArrayList<SongPOJO>();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormSearch window = new FormSearch();
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
		shlSearch.setLocation(500, 300);
		shlSearch.open();
		shlSearch.layout();
		while (!shlSearch.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSearch = new Shell();
		shlSearch.setSize(302, 162);
		shlSearch.setText("Search");
		shlSearch.setImage(GlobalVarible.ICON_SEARCH);
		shlSearch.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				shlSearch.setVisible(false);
				if (!isSearch) {
					FormMain.itemSearch.setText("Search");
					FormMain.itemSearch.setImage(ICON_SEARCH);
				}
			}
		});
		textSearch = new Text(shlSearch, SWT.BORDER);
		textSearch.setBounds(36, 31, 214, 21);
		Button btnSearch = new Button(shlSearch, SWT.NONE);
		btnSearch.setBounds(102, 73, 75, 25);
		btnSearch.setText("Search");
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (textSearch.getText().isEmpty()) {
					Controls.createMes(shlSearch, "", "Keyword is not empty !!!");
				} else {
					strSearch = removeAccent(textSearch.getText().toString());
					try {
						removeListSearch();
						showListS(strSearch);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public static void showListS(String str) throws Exception {
		if(!isSearch) removeListSearch();
		List<SongPOJO> ls = new ArrayList<SongPOJO>();
		ls = lSearch(SongCRUD.getSongValues(), str);
		if (ls.isEmpty()) {
			Controls.createMes(shlSearch, "Notification", "Song was not found !!!");
			return;
		}else {
			isSearch = true;
			FormMain.loadItem(ls);
			Controls.createMes(shlSearch, "Notification", "Song was found complete!!!");
			shlSearch.setVisible(false);
		}
	}
	public static List<SongPOJO> lSearch(List<SongPOJO> list, String str) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			if (str.equalsIgnoreCase(removeAccent(list.get(i).getName()))) {
				listSearch.add(list.get(i));
			}
		}
		return listSearch;
	}

	public static void removeListSearch() {
		for (int i = 0; i < listSearch.size(); i++) {
			listSearch.remove(i);
		}
	}
}
