package com.musicmanager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.crud.CategoryCRUD;
import com.crud.SongCRUD;

import jms.Publisher;

public class FormAdd {

	protected Shell shlAdd;
	private static Combo combo;
	private String valueOfItem = null;
	private int indexCombobox;
	private int idCategory;
	private Text textName;
	private Text textSinger;
	private Text textMusican;
	private Text textLink;

	public static void main(String[] args) {
		try {
			FormAdd window = new FormAdd();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		shlAdd.open();
		shlAdd.layout();
		while (!shlAdd.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	protected void createContents() throws Exception {
		shlAdd = new Shell();
		shlAdd.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				
				shlAdd.setVisible(false);
				FormMain.FLAG = 0;
			}
		});
		shlAdd.setSize(527, 305);
		shlAdd.setText("Song");
		shlAdd.setImage(new Image(null, ".\\src\\icon\\music.ico"));
		shlAdd.setLocation(500, 200);
		Label lblCategory = new Label(shlAdd, SWT.NONE);
		lblCategory.setBounds(102, 26, 76, 15);
		lblCategory.setText("Category");

		Label lblSongName = new Label(shlAdd, SWT.NONE);
		lblSongName.setBounds(102, 63, 76, 15);
		lblSongName.setText("Song name:");

		final Label lblMusican = new Label(shlAdd, SWT.NONE);
		lblMusican.setBounds(102, 136, 55, 15);
		lblMusican.setText("Musican");
		
		Label lblSinger = new Label(shlAdd, SWT.NONE);
		lblSinger.setText("Singer");
		lblSinger.setBounds(102, 100, 55, 15);
		
		Label link = new Label(shlAdd, SWT.NONE);
		link.setText("Link");
		link.setBounds(102, 174, 55, 15);
		
		Button btnNewButton = new Button(shlAdd, SWT.NONE);

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				if (valueOfItem != null) {
					try {
						idCategory = CategoryCRUD.getKeyByValue(CategoryCRUD.getListCategory(), valueOfItem);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				} else {
					try {
						idCategory = CategoryCRUD.getKeyByValue(CategoryCRUD.getListCategory(), combo.getText().toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				try {
					String dateCr = Controls.getDateCurrent();
					String json ="{\r\n" + 
							"	\"id\": "+FormMain.ID_SONG + ",\r\n" + 
							"    \"name\": \"" + textName.getText()+"\",\r\n" + 
							"    \"singer\": \""+ textSinger.getText()+"\",\r\n" + 
							"    \"musican\": \""+ textMusican.getText()+"\",\r\n" + 
							"    \"dateCreate\": \""+dateCr+"\",\r\n" + 
							"    \"link\": \""+textLink.getText()+"\",\r\n" + 
							"    \"idCategory\": "+ idCategory+",\r\n" + 
							"    \"idUser\": "+ FormLogin.ID_USER+"\r\n" + 
							"}";
					
					if (FormMain.FLAG == 1) {
						FormMain.FLAG = 0;
						SongCRUD.updateSong(json);
						if(GlobalVarible.isSearch) {
							FormSearch.removeListSearch();
							FormSearch.listSearch = FormSearch.lSearch(SongCRUD.getSongValues(), FormSearch.strSearch);
						}
						FormMain.loadItem(FormSearch.listSearch);
						Controls.createMes(shlAdd, "Update Song", "Update Song complete");
						Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
								+ "just updated song: " + SongCRUD.getByID(FormMain.ID_SONG).getName());
						shlAdd.setVisible(false);
					}else {
						int id = SongCRUD.sendPost(json);
						try {
							FormMain.addItem();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						FormMain.table.removeAll();
						FormMain.loadItem(FormSearch.listSearch);
						Controls.createMes(shlAdd, "Add new Song", "Add new Song complete");
						Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
								+ "just added song: " + SongCRUD.getByID(id).getName());
					}
				
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(224, 231, 75, 25);
		btnNewButton.setText("OK");

		combo = new Combo(shlAdd, SWT.READ_ONLY);
		combo.setBounds(184, 23, 204, 23);
		combo.setText(CategoryCRUD.getCategoryValues().get(0).getTitle());
		for (int i = 0; i < CategoryCRUD.getCategoryValues().size(); i++) {
			combo.add(CategoryCRUD.getCategoryValues().get(i).getTitle());
		}
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexCombobox = combo.getSelectionIndex();
				valueOfItem = combo.getItem(indexCombobox);
				//System.out.println(valueOfItem);
			}
		});
		textName = new Text(shlAdd, SWT.BORDER);
		textName.setBounds(184, 63, 204, 21);

		textMusican = new Text(shlAdd, SWT.BORDER);
		textMusican.setBounds(184, 136, 204, 21);
		
		textSinger = new Text(shlAdd, SWT.BORDER);
		textSinger.setBounds(184, 100, 204, 21);
	
		textLink = new Text(shlAdd, SWT.BORDER);
		textLink.setBounds(184, 174, 204, 21);
		
		if (FormMain.FLAG == 1) {
			if(GlobalVarible.isSearch) {
				textName.setText(FormSearch.listSearch.get(Integer.valueOf(FormMain.indexx)).getName());
				textSinger.setText(FormSearch.listSearch.get(Integer.valueOf(FormMain.indexx)).getSinger());
				textMusican.setText(FormSearch.listSearch.get(Integer.valueOf(FormMain.indexx)).getMusican());
				textLink.setText(FormSearch.listSearch.get(Integer.valueOf(FormMain.indexx)).getLink());
				combo.setText(CategoryCRUD.getByID(FormMain.ID_CATE).getTitle());
			}else {
				textName.setText(SongCRUD.getSongValues().get(Integer.valueOf(FormMain.indexx)).getName());
				textSinger.setText(SongCRUD.getSongValues().get(Integer.valueOf(FormMain.indexx)).getSinger());
				textMusican.setText(SongCRUD.getSongValues().get(Integer.valueOf(FormMain.indexx)).getMusican());
				textLink.setText(SongCRUD.getSongValues().get(Integer.valueOf(FormMain.indexx)).getLink());
				combo.setText(CategoryCRUD.getByID(FormMain.ID_CATE).getTitle());
			}
		}
		else combo.setText(CategoryCRUD.getCategoryValues().get(0).getTitle());
	}
}
