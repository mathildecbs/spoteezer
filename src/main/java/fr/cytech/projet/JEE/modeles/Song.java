package fr.cytech.projet.JEE.modeles;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@Entity(name="Song")
public class Song {
	@Id @GeneratedValue
	private Long id;
	
	@Column @NotNull
	private String name;

	@ManyToMany
	@JoinTable( name = "Artist_Song",
    joinColumns = @JoinColumn( name = "song_id" ),
    inverseJoinColumns = @JoinColumn( name = "artist_id" ),
    uniqueConstraints = {@UniqueConstraint(columnNames = { "song_id", "artist_id" })})
	private List<Artist> artist;
	
	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;
	
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	@Column
	private String description;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String firstName) {
		this.name = firstName;
	}
	
	public List<Artist> getArtist() {
		return artist;
	}
	
	public void addArtist(Artist art) {
		this.artist.add(art);
	}
	
	public void removeArtist(Artist art) {
	  	this.artist.remove(art);
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date date) {
		this.releaseDate = date;
	}
	
	public Album getAlbum() {
		return album;
	}
	
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
