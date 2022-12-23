package fr.cytech.projet.JEE.modeles;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name = "Users")
public class User {
	@Id
	@GeneratedValue
	private long id;

	@Column
	private String name;

	@Column
	private String password;

	@Column
	private String mail;

	@Column
	private int postalCode;

	@Column
	private String country;
	
	@OneToMany(mappedBy="user")
	private List<Playlist> playlists = new ArrayList<Playlist>();
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="favorite")
	private Playlist favorite = new Playlist(this);

	@Column(nullable = true, length = 64)
	private String picture;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}
	
	public void removePlaylist(Playlist playlist) {
		this.playlists.remove(playlist);
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	public void addPlaylist(Playlist playlist) {
		this.playlists.add(playlist);
	}

	public Playlist getFavorite() {
		return favorite;
	}

	public void setFavorite(Playlist favorite) {
		this.favorite = favorite;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Transient
	public String getPhotosImagePath() {
		if (picture.contentEquals("user.png"))
			return "/basic/" + picture;
		return "/user/" + id + "/" + picture;
	}
}
