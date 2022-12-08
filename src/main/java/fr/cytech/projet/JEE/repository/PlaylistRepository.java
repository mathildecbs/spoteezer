package fr.cytech.projet.JEE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Playlist;

@Repository("playlistRepository")
public interface PlaylistRepository extends JpaRepository<Playlist,Long>{
	public List<Playlist> findAllPlaylistByUserId(long userId);
}
