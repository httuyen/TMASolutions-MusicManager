package com.pojoclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackPOJO {
	
	@SerializedName("idFB")
	@Expose
	private int idFB;
	
	@SerializedName("content")
	@Expose
	private String content;
	
	@SerializedName("dateCreate")
	@Expose
	private String dateCreate;
	
	@SerializedName("idUser")
	@Expose
	private int idUser;

	public int getIdFB() {
		return idFB;
	}

	public void setIdFB(int idFB) {
		this.idFB = idFB;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public FeedbackPOJO(int idFB, String content, String dateCreate, int idUser) {
		super();
		this.idFB = idFB;
		this.content = content;
		this.dateCreate = dateCreate;
		this.idUser = idUser;
	}
	
}
