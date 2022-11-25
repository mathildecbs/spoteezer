package fr.cytech.projet.JEE.modeles;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

public class Album {
	@Id @GeneratedValue
	private Long id;
	
	@Column @NotNull
	private String name;
	
	@Column @NotNull
	private List<Artist> artist;
	
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	@Column @NotNull
	private List<Song> songs;
	
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

}
