package fr.cytech.projet.JEE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Playlist;

@Repository("playlistRepository")
public interface PlaylistRepository extends JpaRepository<Playlist,Long>{
	//Création d'une méthode pour récupérer toutes les playlist d'un utilisateur grace à son id
	public List<Playlist> findAllPlaylistByUserId(long userId);
	
	//Création d'une méthode pour récupérer une playlist grâce à son id
	public Playlist findPlaylistById(long id);
}
