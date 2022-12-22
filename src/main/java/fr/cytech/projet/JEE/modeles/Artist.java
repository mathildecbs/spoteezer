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
	@Id
	@GeneratedValue
	protected Long id;

	@Column
	@NotNull
	protected String name;

	@Temporal(TemporalType.DATE)
	protected Date debutDate;

	@Column(name = "type", insertable = false, updatable = false)
	protected String type;

	@ManyToMany
	@JoinTable(name = "Artist_Album", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "album_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "album_id", "artist_id" }) })
	protected List<Album> albums = new ArrayList<Album>();

	@ManyToMany
	@JoinTable(name = "Artist_Song", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "song_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "song_id", "artist_id" }) })
	protected List<Song> songs = new ArrayList<Song>();

	@Column(nullable = true, length = 64)
	protected String picture;

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDebutDate() {
		return debutDate;
	}

	public void setDebutDate(Date debutDate) {
		this.debutDate = debutDate;
	}

	public String getType() {
		return type;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Artist : " + name + " debut le " + debutDate;
	}

	@Transient
	public String getPhotosImagePath() {
		if (picture.contentEquals("singer.png") || picture.contentEquals("band.png"))
			return "/basic/" + picture;
		return "/artist/" + id + "/" + picture;
	}
}
