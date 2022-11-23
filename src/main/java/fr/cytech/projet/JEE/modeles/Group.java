package fr.cytech.projet.JEE.modeles;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="Group")
public class Group extends Artist {
	
	@ManyToMany
	@JoinTable( name = "Group_Members",
    joinColumns = @JoinColumn( name = "id_group" ),
    inverseJoinColumns = @JoinColumn( name = "id_artist" ) )
	private List<Artist> members;

	public  List<Artist> getMembers() {
		return members;
	}

	public  void setMembers(List<Artist> members) {
		this.members = members;
	}
	
	
}
