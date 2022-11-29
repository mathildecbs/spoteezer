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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.persistence.UniqueConstraint;

@Entity(name="Artist")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="type", discriminatorType = DiscriminatorType.INTEGER )
@DiscriminatorValue("0")
public class Artist {
	@Id @GeneratedValue
	protected Long id;
	
	@Column @NotNull
	protected String name;
	
	@Temporal(TemporalType.DATE)
	protected Date debutDate;
	
	@Column(name="type", insertable = false, updatable = false)
	protected int type;	
	
	@ManyToMany
	@JoinTable( name = "Artist_Album",
    joinColumns = @JoinColumn( name = "artist_id" ),
    inverseJoinColumns = @JoinColumn( name = "album_id" ) )
	protected List<Album> album = new ArrayList<Album>();
	
	@ManyToMany
	@JoinTable( name = "Artist_Song",
    joinColumns = @JoinColumn( name = "artist_id" ),
    inverseJoinColumns = @JoinColumn( name = "song_id" ),
    uniqueConstraints = {@UniqueConstraint(columnNames = { "song_id", "artist_id" })})
	protected List<Song> Song = new ArrayList<Song>();

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
	
	public int getType() {
		return type;
	}
}
