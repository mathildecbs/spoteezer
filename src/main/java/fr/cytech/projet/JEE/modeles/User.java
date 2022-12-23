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
	//Id pour avoir un utilisateur unique
	@Id
	@GeneratedValue
	private long id;

	//Nom de l'utilisateur
	@Column
	private String name;

	
	//Mot de passe de l'utilisateur
	@Column
	private String password;

	//Adresse mail de l'utilisateur
	@Column
	private String mail;

	//Code postal de l'utilisateur
	@Column
	private int postalCode;

	//Pays de l'utilisateur
	@Column
	private String country;
	
	//Liste des playlist de l'utilisateur, un utilisateur peut avoir plusieurs playlist, mais une playlist appartient à un utilisateur
	@OneToMany(mappedBy="user")
	private List<Playlist> playlists = new ArrayList<Playlist>();
	
	//Playlist favoris de l'utilisateur, il n'en a qu'une, et la playlist favorite n'appartient qu'à l'utilisateur
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="favorite")
	private Playlist favorite = new Playlist(this);

	//Image de l'utilisateur
	@Column(nullable = true, length = 64)
	private String picture;

	//Récupération de l'id
	public long getId() {
		return id;
	}

	//Récupération du nom
	public String getName() {
		return name;
	}

	//Modification du nom
	public void setName(String name) {
		this.name = name;
	}

	//Récupération du mot de passe
	public String getPassword() {
		return password;
	}

	//Modification du mot de passe
	public void setPassword(String password) {
		this.password = password;
	}

	//Récupération de l'adresse mail
	public String getMail() {
		return mail;
	}

	//Modification de l'adresse mail
	public void setMail(String mail) {
		this.mail = mail;
	}

	//Récupération du code postal
	public int getPostalCode() {
		return postalCode;
	}

	//Modification du code postal
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	//Récupération du pays
	public String getCountry() {
		return country;
	}

	//Modification du pays
	public void setCountry(String country) {
		this.country = country;
	}

	//Récupération de toutes les playlists de l'utilisateur
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	
	//Suppression d'une des playlist de l'utilisateur
	public void removePlaylist(Playlist playlist) {
		this.playlists.remove(playlist);
	}

	//Modification de toutes les playlists de l'utilisateur
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	//Ajoute d'une playlist à l'utilisateur
	public void addPlaylist(Playlist playlist) {
		this.playlists.add(playlist);
	}

	//Récupération de la playlsit favoris
	public Playlist getFavorite() {
		return favorite;
	}

	//Modification de la playlist favoris
	public void setFavorite(Playlist favorite) {
		this.favorite = favorite;
	}

	//Récupération de l'image de l'utilisateur
	public String getPicture() {
		return picture;
	}

	//Modification de l'image de l'utilisateur
	public void setPicture(String picture) {
		this.picture = picture;
	}

	//Récupération du chemin de l'image de l'utilisateur
	@Transient
	public String getPhotosImagePath() {
		//Si l'utilisateur n'a pas changé d'image, on utilise celle par défaut
		if (picture.contentEquals("user.png"))
			return "/basic/" + picture;
		//Sinon on récupère le chemin de l'image choisi par l'utilisateur
		return "/user/" + id + "/" + picture;
	}
}
