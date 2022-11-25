package fr.cytech.projet.JEE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>  {

}
