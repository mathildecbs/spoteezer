package fr.cytech.projet.JEE.services;

import org.springframework.beans.factory.annotation.Autowired;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.repository.ArtistRepository;

public class ArtistService {
	@Autowired
	ArtistRepository artistRepository;
	
	public Artist findArtistById(int id) {
		return artistRepository.getById(id);
	}

}
