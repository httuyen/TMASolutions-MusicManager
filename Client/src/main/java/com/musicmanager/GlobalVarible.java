package com.musicmanager;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.eclipse.swt.graphics.Image;

public class GlobalVarible {
	public static Image ICON_USER =  new Image(null, ".\\src\\icon\\user.ico");
	public static Image ICON_DELETE =  new Image(null, ".\\src\\icon\\Delete.ico");
	public static Image ICON_SAVE =  new Image(null, ".\\src\\icon\\Save.ico");
	public static Image ICON_ALERT =  new Image(null, ".\\src\\icon\\alert.ico");
	public static Image ICON_HELP =  new Image(null, ".\\src\\icon\\help.ico");
	public static Image ICON_MUSIC =  new Image(null, ".\\src\\icon\\music.ico");
	public static Image ICON_MUSICICON =  new Image(null, ".\\src\\icon\\musicicon.ico");
	public static Image ICON_ABOUT =  new Image(null, ".\\src\\icon\\aboutIcon.ico");
	public static Image ICON_LOGIN =  new Image(null, ".\\src\\icon\\Login.ico");
	public static Image ICON_FB =  new Image(null, ".\\src\\icon\\feedback.ico");
	public static Image ICON_PASSWORD =  new Image(null, ".\\src\\icon\\password.ico");
	public static Image ICON_LOGOUT =  new Image(null, ".\\src\\icon\\logout.ico");
	public static Image ICON_SEARCH =  new Image(null, ".\\src\\icon\\searchicon.ico");
	public static Image ICON_STOP =  new Image(null, ".\\src\\icon\\stopicon.ico");
	public static Image ICON_USER_ =  new Image(null, ".\\src\\icon\\usericon.ico");
	public static Image ICON_CHANGE_PWD =  new Image(null, ".\\src\\icon\\changpassicon.ico");
	public static String MESS = "";
	public static String TXT_NAME = "";
	public static String TXT_PHONE = "";
	public static String TXT_EMAIL = "";
	public static String TXT_ADDRESS = "";
	public static boolean isEnable = false;
	public static boolean isLogin = true;
	public static boolean isCreate = true;
	public static boolean isSearch = false;
	
	public static String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
}
