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
	@Id @GeneratedValue
	private long id;
		
	@Column
	private String name;
	
	@Column
	private Date creationDate;
	
	@Column 
	private String description;
	
	@ManyToOne
	@JoinColumn(name="user_id") 
	private User user;
	
	@ManyToMany
	@JoinTable( name = "Playlist_Song",
    joinColumns = @JoinColumn( name = "playlist_id" ),
    inverseJoinColumns = @JoinColumn( name = "song_id" ) )
	private List<Song> songs = new ArrayList<Song>(); 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
