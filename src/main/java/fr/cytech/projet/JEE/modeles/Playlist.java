package fr.cytech.projet.JEE.modeles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "Playlist")
public class Playlist {
	//Id pour que la playlist soit unique
	@Id @GeneratedValue
	private long id;
	
	//Nom de la playlist
	@Column
	private String name;
	
	//Date de création de la playlist
	@Column
	private Date creationDate;
	
	//Description de la playlist
	@Column 
	private String description;
	
	//Utilisateur associé à la playlist, une playlist n'appartient qu'à un seul utilisateur
	@ManyToOne
	@JoinColumn(name="user_id") 
	private User user;
	
	//Les chansons de la playlist, une playlist peut avoir plusieurs chansons et une chanson peut appartenir à plusieurs playlist
	@ManyToMany
	@JoinTable( name = "Playlist_Song",
    joinColumns = @JoinColumn( name = "playlist_id" ),
    inverseJoinColumns = @JoinColumn( name = "song_id" ) )
	private List<Song> songs; 
	
	/* Utilisé pour la création de la playlist favorite */
	public Playlist(User user) {
		this.user = user;
		this.name = "favoris";
		this.description = "Ceci est la playlist contenant les favoris";

	    java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
		this.creationDate = date;
		songs = new ArrayList<Song>();
	}
	
	//Constructeur de la playlist
	public Playlist() {
		songs = new ArrayList<Song>();
	}
	
	//Récupération de l'id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	//Récupération du nom
	public String getName() {
		return name;
	}

	//Modification du nom
	public void setName(String name) {
		this.name = name;
	}

	//Récupération de la date de création
	public Date getCreationDate() {
		return creationDate;
	}

	//Modification de la date de rcéation
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	//Récupération de la description
	public String getDescription() {
		return description;
	}

	//Modification de la description
	public void setDescription(String description) {
		this.description = description;
	}	
	
	//Récupération de l'utilisateur associé à la playlist
	public User getUser() {
		return user;
	}
	
	//Modification de l'utilisateur associé à la playlist
	public void setUser(User user) {
		this.user = user;
	}
	
	//Récupération de toutes les chansons de la playlist
	public List<Song> getSongs() {
		return songs;
	}
	
	//Modification de toutes les chansons de la playlist
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	//Ajout d'une chanson à la playlist
	public void addSong(Song song) {
		this.songs.add(song);
	}
	
	//Retrait d'une chanson de la playlsit
	public void removeSong(Song song) {
		this.songs.remove(song);
	}
}
