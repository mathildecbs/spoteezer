package fr.cytech.projet.JEE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Song;

@Repository("songRepository")
public interface SongRepository extends JpaRepository<Song, Long>  {
	public Song findByName(String name);
	public List<Song> findByAlbumId(Long AlbumId);
}
