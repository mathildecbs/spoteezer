package fr.cytech.projet.JEE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Artist;

@Repository("artistRepository")
public interface ArtistRepository<T extends Artist> extends JpaRepository<T, Long>  {
	@Query("SELECT g.members FROM Group g WHERE id_group= :groupId")
	public List<Artist> findMembersByGroupId(Long groupId);
	
}

