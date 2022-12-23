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
	@Id @GeneratedValue
	private Long id;
	
	@Column @NotNull
	private String name;
	
	@ManyToMany
	@JoinTable( name = "Artist_Album",
    joinColumns = @JoinColumn( name = "album_id" ),
    inverseJoinColumns = @JoinColumn( name = "artist_id" ) )
	private List<Artist> artist;
	
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	@OneToMany(mappedBy="album")
	private List<Song> songs = new ArrayList<>();
	
	@Column
	private String description;
	
	@Column(nullable = true, length = 64)
	private String picture;

	
	public Long getId() {
		return id;
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
	
	public void setArtist(List<Artist> artist) {
		this.artist = artist;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date date) {
		this.releaseDate = date;
	}

	public List<Song> getSongs() {
		return songs;
	}
	
	public void addSong(Song song) {
		songs.add(song);
	}
	
	public void removeSong(Song song) {
		songs.remove(song);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Transient
	public String getPhotosImagePath() {
		if (picture.contentEquals("album.png") )
			return "/basic/" + picture;
		return "/album/" + id + "/" + picture;
	}

}
