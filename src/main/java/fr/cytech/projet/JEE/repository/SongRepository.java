package fr.cytech.projet.JEE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Song;

@Repository("songRepository")
public interface SongRepository<T extends Song> extends JpaRepository<Song, Long>  {
	//Création d'une méthode pour récupérer une musique grâce à son nom
	public Song findByName(String name);
	
	//Création d'une méthode pour récupérer toutes les chansons avec le même nom
	public List<Song> findAllByName(String name);
	
	//Création d'une méthode pour récupérer les chansons grace à l'id de l'album
	public List<Song> findByAlbumId(Long AlbumId);
}
