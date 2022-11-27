package fr.cytech.projet.JEE.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;
import fr.cytech.projet.JEE.repository.ArtistRepository;

@Service("artistService")
public class ArtistService {
	
	@Autowired
	ArtistRepository artistRepository;
	
	public Artist findArtistById(Long id) {
		return artistRepository.getById(id);
	}
	
	public List<Artist> findAll(){
		return artistRepository.findAll();
	}
	
	public Artist createArtist(Map<String,String>  artistDTO) {
		System.out.println(artistDTO);
		Artist artist = new Artist();
		artist.setName(artistDTO.get("name"));
		artist.setDebutDate(Date.valueOf(artistDTO.get("debutDate")));
		return artistRepository.save(artist);
	}

	public Group createGroup(Artist group, List<Long> members) {
		Group groupEntity = new Group();
		groupEntity.setDebutDate(group.getDebutDate());
		groupEntity.setName(group.getName());
		for (Long id : members) {
			groupEntity.getMembers().add(findArtistById(id));
			
		}
		return artistRepository.save(groupEntity);
	}
}
