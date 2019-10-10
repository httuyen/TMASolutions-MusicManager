package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "song")
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "singer", nullable = false)
	private String singer;
	
	@Column(name = "musican", nullable = false)
	private String musican;
	
	@Column(name = "link", nullable = false)
	private String link;
	
	@Column(name = "dateCreate", nullable = false)
	private String dateCreate;
	
	@Column(name = "idCategory", nullable = false,
			insertable = false, updatable = false)
	private int idCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategory")
	@JsonIgnore
	private Category category;
	
	@Column(name = "idUser", nullable = false,
			insertable = false, updatable = false)
	private int idUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUser")
	@JsonIgnore
	private User user;

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

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
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

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCate) {
		this.idCategory = idCate;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
