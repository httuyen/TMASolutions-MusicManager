package com.pojoclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongPOJO {

	@SerializedName("id")
	@Expose
	private Integer id;
	
	@SerializedName("name")
	@Expose
	private String name;
	
	@SerializedName("singer")
	@Expose
	private String singer;
	
	@SerializedName("musican")
	@Expose
	private String musican;
	
	@SerializedName("link")
	@Expose
	private String link;
	
	@SerializedName("dateCreate")
	@Expose
	private String dateCreate;
	
	@SerializedName("idCategory")
	@Expose
	private int id_category;
	
	@SerializedName("idUser")
	@Expose
	private int id_user;
	
	public int getId_category() {
		return id_category;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public void setId_category(int id_category) {
		this.id_category = id_category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMusican() {
		return musican;
	}
	public void setMusican(String musican) {
		this.musican = musican;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public SongPOJO(Integer id, String name, String singer, String musican, String link, String dateCreate,
			int id_category, int id_user) {
		super();
		this.id = id;
		this.name = name;
		this.singer = singer;
		this.musican = musican;
		this.link = link;
		this.dateCreate = dateCreate;
		this.id_category = id_category;
		this.id_user = id_user;
	}
	
}
