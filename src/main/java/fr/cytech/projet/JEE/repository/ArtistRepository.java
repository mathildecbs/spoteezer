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
	@Query("SELECT g.members FROM Group g WHERE id_group= :groupId")
	public List<Artist> findMembersByGroupId(Long groupId);
	
	@Query(value="SELECT m.id_artist FROM group_members m",nativeQuery = true)
	public List<Long> findArtistInGroup();
	
	@Query(value="SELECT m.id_group FROM group_members m WHERE id_artist=:id",nativeQuery = true)
	public List<Long> findArtistGroups(@Param("id") Long id);
	
	
}

