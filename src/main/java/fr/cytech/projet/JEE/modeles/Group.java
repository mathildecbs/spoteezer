package fr.cytech.projet.JEE.modeles;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

@Entity(name="Group")
public class Group extends Artist {
	//Liste des membres du groupe, il peut y avoir plusieurs membres dans un groupe, et un artiste peut faire parti de plusieurs groupe
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable( name = "Group_Members",
    joinColumns = @JoinColumn( name = "id_group" ),
    inverseJoinColumns = @JoinColumn( name = "id_artist" ),
    		uniqueConstraints = {@UniqueConstraint(columnNames = { "id_group", "id_artist" }) })
	private List<Artist> members;

	//Récupération des membres du groupe
	public  List<Artist> getMembers() {
		return members;
	}

	//Modification des membres du groupe
	public  void setMembers(List<Artist> members) {
		this.members = members;
	}
	
	
}
