package com.musicmanager;

import java.util.List;

import javax.jms.JMSException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.resteasy.spi.Link;

import com.crud.CategoryCRUD;
import com.crud.SongCRUD;
import com.pojoclass.SongPOJO;

import jms.Publisher;
import jms.Subcribers;
import swing2swt.layout.BorderLayout;

public class FormMain extends GlobalVarible {

	public static Shell shlMusicManager;
	public static Table table;
	public static TableItem tableItem;
	private String indexItem = null;
	public static String indexx = null;
	private final int COLUMN_COUNT = 6;
	public static int ID_CATE = 0;
	public static int ID_SONG = 0;
	public static int FLAG = 0;
	public static String USERNAME = "";
	public static String PASSWORD = "";
	public static ToolItem itemNot;
	public static ToolItem itemSearch;
	public static void main(String[] args) throws NumberFormatException, JMSException {
		
		try {
			FormMain window = new FormMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		//shlMusicManager.setMaximized(true);
		shlMusicManager.setImage(ICON_MUSICICON);
		shlMusicManager.open();
		shlMusicManager.layout();
		while (!shlMusicManager.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public static void loadItem(List<SongPOJO> list) throws Exception {
		table.removeAll();
		if(isSearch && !list.isEmpty()) {
			System.out.println("search");
			for (int i = 0; i < list.size(); i++) {
				tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] { 
						list.get(i).getName(),
						list.get(i).getSinger(),
						list.get(i).getMusican(),
						CategoryCRUD.getByID(list.get(i).getId_category()).getTitle(),
						list.get(i).getLink(),
						list.get(i).getDateCreate()
				});
			}
		} else {
			for (int i = 0; i < SongCRUD.getSongValues().size(); i++) {
				tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] { 
						SongCRUD.getSongValues().get(i).getName(),
						SongCRUD.getSongValues().get(i).getSinger(),
						SongCRUD.getSongValues().get(i).getMusican(),
						CategoryCRUD.getByID(SongCRUD.getSongValues().get(i).getId_category()).getTitle(),
						SongCRUD.getSongValues().get(i).getLink(),
						SongCRUD.getSongValues().get(i).getDateCreate()
				});
			}
		}
	}

	public static void addItem() throws Exception {
		int i = SongCRUD.getSongValues().size();
		tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(new String[] { SongCRUD.getSongValues().get(i - 1).getName(),
				SongCRUD.getSongValues().get(i - 1).getSinger(), SongCRUD.getSongValues().get(i - 1).getMusican(),
				SongCRUD.getSongValues().get(i - 1).getLink(), SongCRUD.getSongValues().get(i - 1).getDateCreate(),
				CategoryCRUD.getByID(SongCRUD.getSongValues().get(i - 1).getId_category()).getTitle() });
	}

	protected void createContents() throws Exception {
		shlMusicManager = new Shell();
		// int screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
		shlMusicManager.setSize(920, 600);
		// shlMusicManager.setMaximized(true);
		shlMusicManager.setText("Music Manager");
		shlMusicManager.setLocation(200, 50);
		shlMusicManager.setLayout(new BorderLayout());
		ToolBar toolBar = new ToolBar(shlMusicManager, SWT.NONE | SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(BorderLayout.NORTH);
		shlMusicManager.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				try {
					Subcribers.stop();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		Menu menuAdd = new Menu(toolBar);
		MenuItem aboutItem = new MenuItem(menuAdd, SWT.PUSH);
		aboutItem.setText("About");
		aboutItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				FormHelp formHelp = new FormHelp();
				formHelp.open();
			}
		});
		
		MenuItem itemFeedback = new MenuItem(menuAdd, SWT.PUSH);
		itemFeedback.setText("Send feedback");
		itemFeedback.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				FormFeedBack feedBack = new FormFeedBack();
				feedBack.open();
			}
		});
		
		Menu menuAbout = new Menu(toolBar);
		MenuItem addSong = new MenuItem(menuAbout, SWT.PUSH);
		addSong.setText("Add New Song");
		MenuItem addCate = new MenuItem(menuAbout, SWT.PUSH);
		addCate.setText("Add New Category");
		
		Menu menuUser = new Menu(toolBar);
		MenuItem changePass = new MenuItem(menuUser, SWT.PUSH);
		changePass.setText("Change password");
		MenuItem updateInfo = new MenuItem(menuUser, SWT.PUSH);
		updateInfo.setText("Update infomation");
		
		ToolItem itemAdd = new ToolItem(toolBar, SWT.DROP_DOWN);
		itemAdd.setImage(ICON_MUSIC);
		itemAdd.setText("Add");
		
		ToolItem itemAccount = new ToolItem(toolBar, SWT.DROP_DOWN);
		itemAccount.setImage(ICON_LOGIN);
		itemAccount.setText("User");
		itemAccount.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (arg0.detail == SWT.ARROW) {
					Rectangle rect = itemAccount.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					pt = toolBar.toDisplay(pt);	
					menuUser.setLocation(pt.x, pt.y);
					menuUser.setVisible(true);
				}
			}
		});

		ToolItem itemHelp = new ToolItem(toolBar, SWT.DROP_DOWN);
		itemHelp.setImage(ICON_HELP);
		itemHelp.setText("Help");
		
		
		itemSearch = new ToolItem(toolBar, SWT.PUSH);
		itemSearch.setImage(ICON_SEARCH);
		itemSearch.setText("Search");
		itemSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(!isSearch){
					//isSearch = true;
					itemSearch.setText("Stop search");
					itemSearch.setImage(ICON_STOP);
					FormSearch formSearch = new FormSearch();
					formSearch.open();				
				} else {
					isSearch = false;
					table.removeAll();
					try {
						loadItem(SongCRUD.getSongValues());
					} catch (Exception e) {
						e.printStackTrace();
					}
					itemSearch.setText("Search");
					itemSearch.setImage(ICON_SEARCH);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		itemNot = new ToolItem(toolBar, SWT.PUSH);
		itemNot.setImage(ICON_ALERT);
		if(isEnable) {
			itemNot.setEnabled(true);
		} else itemNot.setEnabled(false);
		
		itemNot.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox msBox = new MessageBox(shlMusicManager, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				msBox.setText("NOTIFICATION");
				msBox.setMessage( MESS + "\r\nDo you want to update list ?");
				int respone = msBox.open();
				if (respone == SWT.YES) {
					table.removeAll();
					try {
						loadItem(FormSearch.listSearch);
						isEnable = false;
						itemNot.setText("");
						itemNot.setEnabled(false);
						MESS = "";
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		itemHelp.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle rect = itemHelp.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					pt = toolBar.toDisplay(pt);
					menuAdd.setLocation(pt.x, pt.y);
					menuAdd.setVisible(true);
				}

			}
		});
		itemAdd.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle rect = itemAdd.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					pt = toolBar.toDisplay(pt);
					menuAbout.setLocation(pt.x, pt.y);
					menuAbout.setVisible(true);
				}
			}
		});
		addSong.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(isSearch) {
						Controls.createMes(shlMusicManager, "Notification", "Please stop searching to add new song!!!");
						return;
					}
					if (CategoryCRUD.getCategoryValues().size() == 0) {
						Controls.createMes(shlMusicManager, "", "Category is empty!\nAdd new category to add new song");
					} 
					else {
						FormAdd frm = new FormAdd();
						try {
							frm.open();

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		addCate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isSearch) {
					Controls.createMes(shlMusicManager, "Notification", "Please stop searching to add new category!!!");
					return;
				}
				FormAddCate frm = new FormAddCate();
				try {
					frm.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		
		changePass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isSearch) {
					Controls.createMes(shlMusicManager, "Notification", "Please stop searching to add new category!!!");
					return;
				}
				FormChangePass frm = new FormChangePass();
				try {
					frm.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		updateInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isSearch) {
					Controls.createMes(shlMusicManager, "Notification", "Please stop searching to add new category!!!");
					return;
				}
				FormUser formUser = new FormUser();
				try {
					formUser.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		table = new Table(shlMusicManager, SWT.BORDER | SWT.FULL_SELECTION | SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		table.setLayoutData(BorderLayout.CENTER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		TableColumn musicanColumn = new TableColumn(table, SWT.NONE);
		TableColumn singerColumn = new TableColumn(table, SWT.NONE);
		TableColumn categoryColumn = new TableColumn(table, SWT.NONE);
		TableColumn link = new TableColumn(table, SWT.NONE);
		TableColumn dateCreate = new TableColumn(table, SWT.NONE);

		nameColumn.setText("NAME");
		nameColumn.setWidth(200);
		singerColumn.setText("SINGER");
		singerColumn.setWidth(150);
		musicanColumn.setText("MUSICAN");
		musicanColumn.setWidth(150);
		categoryColumn.setText("CATEGORY");
		categoryColumn.setWidth(100);
		link.setWidth(200);
		link.setText("LINK");
		dateCreate.setWidth(120);
		dateCreate.setText("DATE CREATE");

		loadItem(FormSearch.listSearch);
		
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				Point pt = new Point(arg0.x, arg0.y);
				TableItem item1 = table.getItem(pt);
				if (item1 == null) {
					return;
				}

				for (int i = 0; i < COLUMN_COUNT; i++) {
					Rectangle rect = item1.getBounds(i);
					if (rect.contains(pt)) {
						int index = table.indexOf(item1);
						indexItem = index + "-" + i;
					}
				}
				//System.out.println(indexItem);
				indexx = String.valueOf(indexItem.charAt(0));
				try {
					if(!isSearch) {
						ID_CATE = SongCRUD.getSongValues().get(Integer.valueOf(indexx)).getId_category();
						ID_SONG = SongCRUD.getSongValues().get(Integer.valueOf(indexx)).getId();						
					}else {
						ID_CATE = FormSearch.listSearch.get(Integer.valueOf(indexx)).getId_category();
						ID_SONG = FormSearch.listSearch.get(Integer.valueOf(indexx)).getId();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				Menu menu2 = new Menu(table);
				table.setMenu(menu2);
				menu2.setVisible(true);
				MenuItem edItem = new MenuItem(menu2, SWT.DROP_DOWN);
				edItem.setText("Edit");
				edItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						//System.out.println(String.valueOf(indexItem.charAt(2)));
						if (indexItem.charAt(2) == '3') {
							FLAG = 1;
							FormAddCate formAddCate = new FormAddCate();
							try {
								formAddCate.open();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						} else {
							FLAG = 1;
							try {
								FormAdd formAdd = new FormAdd();
								formAdd.open();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}

					}

				});

				MenuItem delItem2 = new MenuItem(menu2, SWT.DROP_DOWN);
				delItem2.setText("Delete");

				delItem2.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						if (indexItem.charAt(2) == '3') {
							MessageBox msBox = new MessageBox(shlMusicManager, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
							msBox.setText("DELETE");
							msBox.setMessage("If you delete it, the songs belonging to it will also be deleted."
									+ "\nDo you want to delete!!!");
							int respone = msBox.open();
							if (respone == SWT.YES) {
								if(!isSearch) {
									try {
										for (int i = SongCRUD.getSongValues().size() - 1; i >= 0; i--) {
											if (SongCRUD.getSongValues().get(i).getId_category() == ID_CATE) {
												table.remove(i);
											}
										}
										Controls.createMes(shlMusicManager, "Delete Category", "Delete Complete");
										Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
												+ "just deleted categoty: " + CategoryCRUD.getByID(ID_CATE).getTitle());
										CategoryCRUD.deleteCate(ID_CATE);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}else {
									try {
										for (int i = FormSearch.listSearch.size() - 1; i >= 0; i--) {
											if (FormSearch.listSearch.get(i).getId_category() == ID_CATE) {
												table.remove(i);
											}
										}
										Controls.createMes(shlMusicManager, "Delete Category", "Delete Complete");
										Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
												+ "just deleted categoty: " + CategoryCRUD.getByID(ID_CATE).getTitle());
										CategoryCRUD.deleteCate(ID_CATE);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							} else
								return;
						} else {
							MessageBox msBox = new MessageBox(shlMusicManager, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
							msBox.setText("DELETE");
							msBox.setMessage("DO you want to delete this song?");
							int respone = msBox.open();
							if (respone == SWT.YES) {
								if(!isSearch) {
									try {
										table.remove(Integer.valueOf(indexx));
										Controls.createMes(shlMusicManager, "Delete Song", "Delete Complete");
										Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
												+ "just deleted song: " + SongCRUD.getSongValues().get(Integer.valueOf(indexx)).getName());
										SongCRUD.deleteSong(ID_SONG);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}else {
									try {
										table.remove(Integer.valueOf(indexx));
										Controls.createMes(shlMusicManager, "Delete Song", "Delete Complete");
										Publisher.sender(FormLogin.ID_USER + "-" + FormMain.USERNAME + "-"
												+ "just deleted song: " + FormSearch.listSearch.get(Integer.valueOf(indexx)).getName());
										SongCRUD.deleteSong(ID_SONG);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							} else
								return;
						}
					}
				});
			}
		});
	}
}
