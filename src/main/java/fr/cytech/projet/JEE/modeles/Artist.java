package fr.cytech.projet.JEE.modeles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity(name = "Artist")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Artist")
public class Artist {
	//Id pour avoir des artistes uniques
	@Id
	@GeneratedValue
	protected Long id;

	//Nom del l'artiste
	@Column
	@NotNull
	protected String name;

	//Date de début de l'artiste
	@Temporal(TemporalType.DATE)
	protected Date debutDate;

	//Type pour savoir s'il s'agit d'un groupe
	@Column(name = "type", insertable = false, updatable = false)
	protected String type;

	//La liste des albums que l'artiste a fait
	//Un artiste peut avoir plusieurs albums, et un album peut avoir plusieurs artistes
	@ManyToMany
	@JoinTable(name = "Artist_Album", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "album_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "album_id", "artist_id" }) })
	protected List<Album> albums = new ArrayList<Album>();

	//Chanson de l'artsite
	//Un artiste peut avoir plusieurs chansons, et une chanson peut avoir été faite par plusieurs artistes
	@ManyToMany
	@JoinTable(name = "Artist_Song", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "song_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "song_id", "artist_id" }) })
	protected List<Song> songs = new ArrayList<Song>();

	//Image associé à l'artiste
	@Column(nullable = true, length = 64)
	protected String picture;

	//Récupération de l'image de l'artiste
	public String getPicture() {
		return picture;
	}

	//Modification de l'image
	public void setPicture(String picture) {
		this.picture = picture;
	}

	//Récupération de l'id
	public Long getId() {
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

	//Récupération de la date de début
	public Date getDebutDate() {
		return debutDate;
	}

	//Modification de la date de début
	public void setDebutDate(Date debutDate) {
		this.debutDate = debutDate;
	}

	//Récupération du type (groupe ou non)
	public String getType() {
		return type;
	}

	//Récupération de la liste d'album
	public List<Album> getAlbums() {
		return albums;
	}

	//Modification de la liste d'album complète de l'artiste
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	//Récupération de toutes les chansons de l'artiste
	public List<Song> getSongs() {
		return songs;
	}

	//Modification de toutes les chansons de l'artiste
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	//Affichage du nom et de la date de début
	@Override
	public String toString() {
		return "Artist : " + name + " debut le " + debutDate;
	}

	//Récupération du chemin de l'image
	@Transient
	public String getPhotosImagePath() {
		//Si on a pas ajouté d'image, par défaut c'est soit une image d'artiste seule si ce n'est pas un groupe, sinon une image pour dire que c'est un groupe
		if (picture.contentEquals("singer.png") || picture.contentEquals("band.png"))
			return "/basic/" + picture;
		//Sinon on récupère le chemin de l'image qu'on a ajouté
		return "/artist/" + id + "/" + picture;
	}
}
