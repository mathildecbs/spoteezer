package fr.cytech.projet.JEE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;

@Repository("artistRepository")
public interface ArtistRepository<T extends Artist> extends JpaRepository<T, Long>  {
	//Création d'une méthode pour récupérer les membres d'un groupe grace à l'id du groupe, grâce à la requête SQL associée
	@Query("SELECT g.members FROM Group g WHERE id_group= :groupId")
	public List<Artist> findMembersByGroupId(Long groupId);
	
	//Création d'une méthode pour trouver les artistes qui font parti d'un group, grâce à la requête SQL associée
	@Query(value="SELECT m.id_artist FROM group_members m",nativeQuery = true)
	public List<Long> findArtistInGroup();
	
	//Création d'une méthode pour récupérer le groupe duquel fait parti l'artiste grace à son id, grâce à la requête SQL associée
	@Query(value="SELECT m.id_group FROM group_members m WHERE id_artist=:id",nativeQuery = true)
	public List<Long> findArtistGroups(@Param("id") Long id);
	
	
}

