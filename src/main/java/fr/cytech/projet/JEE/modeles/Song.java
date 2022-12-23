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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@Entity(name="Song")
public class Song {
	//Id pour avoir une chanson unique
	@Id @GeneratedValue
	private Long id;
	
	//Nom de la chanson
	@Column @NotNull
	private String name;

	//Liste des artistes de la chanson, une chanson peut avoir plusieurs artistes, un artiste peut avoir plusieurs chanson
	@ManyToMany
	@JoinTable( name = "Artist_Song",
    joinColumns = @JoinColumn( name = "song_id" ),
    inverseJoinColumns = @JoinColumn( name = "artist_id" ),
    uniqueConstraints = {@UniqueConstraint(columnNames = { "song_id", "artist_id" })})
	private List<Artist> artist = new ArrayList<Artist>();
	
	//Album associé à la chanson, une chanson ne peut appartenir qu'à un seul album
	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;
	
	//Date de réalisation de la chanson
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	//Description de la chanson
	@Column
	private String description;

	//Récupération de l'id de la chanson
	public Long getId() {
		return id;
	}

	//Récupération du nom de la chanson
	public String getName() {
		return name;
	}

	//Modification du nom
	public void setName(String firstName) {
		this.name = firstName;
	}
	
	//Récupération de la liste d'artistes de la chanson
	public List<Artist> getArtist() {
		return artist;
	}
	
	//Ajout d'un artiste à la liste des artistes de la chanson
	public void addArtist(Artist art) {
		this.artist.add(art);
	}
	
	//Retrait d'un artiste à la liste des artistes de la chanson
	public void removeArtist(Artist art) {
		this.artist.remove(art);
	}
	
	//Modification de tout les artistes de la chanosn
	public void setArtist(List<Artist> artists) {
		this.artist = artists;
	}
	
	//Récupération de la date de réalisation
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	//Modification de la date de réalisation
	public void setReleaseDate(Date date) {
		this.releaseDate = date;
	}
	
	//Récupération de l'album associé à la chanson
	public Album getAlbum() {
		return album;
	}
	
	//Modification de l'album associé à la chanson
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	//Récupération de la description
	public String getDescription() {
		return description;
	}
	
	//Modification de la description
	public void setDescription(String description) {
		this.description = description;
	}

	//Récupération de l'image de la chanson (qui est celle de l'album)
	@Transient
	public String getPhotosImagePath() {
		//Si on a pas ajouté d'image à l'album, on retourne l'image par défaut
		if(album.getPicture().contentEquals("album.png"))
			return "/basic/song.png";
		//sinon on retourne celle qui a été ajouté
		return album.getPhotosImagePath();
	}
}
