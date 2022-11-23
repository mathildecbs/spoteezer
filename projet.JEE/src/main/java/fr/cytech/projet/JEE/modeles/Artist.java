package fr.cytech.projet.JEE.modeles;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity(name="Artist")
public class Artist {
	@Id @GeneratedValue
	private Long id;
	
	@Column @NotNull
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date debutDate;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final Date getDebutDate() {
		return debutDate;
	}

	public final void setDebutDate(Date debutDate) {
		this.debutDate = debutDate;
	}
	
	


}
