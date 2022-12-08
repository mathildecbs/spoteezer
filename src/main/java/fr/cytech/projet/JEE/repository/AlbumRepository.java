package fr.cytech.projet.JEE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Song;

@Repository("albumRepository")
public interface AlbumRepository<T extends Album> extends JpaRepository<T, Long>  {
	public Album findByName(String name);
}
