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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity(name="Album")
public class Album {
	//Création d'un id, pour qu'un album soit unique
	@Id @GeneratedValue
	private Long id;
	
	//Nom associé
	@Column @NotNull
	private String name;
	
	//Un album peut avoir plusieurs artistes, de même pour artist
	@ManyToMany
	@JoinTable( name = "Artist_Album",
    joinColumns = @JoinColumn( name = "album_id" ),
    inverseJoinColumns = @JoinColumn( name = "artist_id" ) )
	private List<Artist> artist;
	
	//Date de réalisaiton de l'album
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	//Un album possède une liste de chanson
	@OneToMany(mappedBy="album")
	private List<Song> songs = new ArrayList<>();
	
	// Description de l'album
	@Column
	private String description;
	
	//Jaquette de l'album
	@Column(nullable = true, length = 64)
	private String picture;

	//Récupération de l'id
	public Long getId() {
		return id;
	}

	//Récupartion du nom
	public String getName() {
		return name;
	}

	//Modification du nom
	public void setName(String firstName) {
		this.name = firstName;
	}

	//Récupération des artistes
	public List<Artist> getArtist() {
		return artist;
	}
	
	//Ajout d'un artiste
	public void addArtist(Artist art) {
		this.artist.add(art);
	}
	
	//Retrait d'un artiste
	public void removeArtist(Artist art) {
		this.artist.remove(art);
	}
	
	//Modification complète des artistes
	public void setArtist(List<Artist> artist) {
		this.artist = artist;
	}
	
	//Récupération date de réalisation
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	//Modification de la date de réalsation
	public void setReleaseDate(Date date) {
		this.releaseDate = date;
	}

	//Récupération des chansons de l'album
	public List<Song> getSongs() {
		return songs;
	}
	
	//Ajout d'une chanson à l'album
	public void addSong(Song song) {
		songs.add(song);
	}
	
	//Retrait d'une chanson de l'album
	public void removeSong(Song song) {
		songs.remove(song);
	}
	
	//Récupération de la description
	public String getDescription() {
		return description;
	}
	
	//Modification de la description
	public void setDescription(String description) {
		this.description = description;
	}
	
	//Récupération de l'image de l'album
	public String getPicture() {
		return picture;
	}

	//Modification de l'image de l'album
	public void setPicture(String picture) {
		this.picture = picture;
	}

	//Récupération du chemin de l'image
	@Transient
	public String getPhotosImagePath() {
		//Si on a pas ajouté d'image à l'album, on prend celle par défaut
		if (picture.contentEquals("album.png") )
			return "/basic/" + picture;
		//Sinon on retourne l'image qui a été ajoutée
		return "/album/" + id + "/" + picture;
	}

}
