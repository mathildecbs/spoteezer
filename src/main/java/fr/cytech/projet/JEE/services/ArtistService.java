package fr.cytech.projet.JEE.services;

import java.sql.Date;
import java.util.ArrayList;
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
	ArtistRepository<Artist> artistRepository;
	
	@Autowired
	ArtistRepository<Group> groupRepository;
	
	public Artist findArtistById(Long id) {
		return artistRepository.getById(id);
	}
	
	public List<Artist> findAll(){
		return artistRepository.findAll();
	}
	
	public Artist createArtist(Map<String,String>  artistDTO) {
		Artist artist = new Artist();
		artist.setName(artistDTO.get("name"));
		artist.setDebutDate(Date.valueOf( artistDTO.get("debutDate")));
		return artistRepository.save(artist);
	}

	public Group createGroup(Map<String,String>  groupDTO) {
		Group groupEntity = new Group();
		groupEntity.setDebutDate(Date.valueOf(groupDTO.get("debutDate")));
		groupEntity.setName( groupDTO.get("name"));
		return artistRepository.save(groupEntity);
	}
	
	public Group addGroupMembers(Map<String,String> groupMembers, Long id) {
		Group group = groupRepository.findById(id).orElse(null);
		List<Artist> members = new ArrayList<Artist>();
		for (String artistId : groupMembers.keySet()) {
			System.out.println(findArtistById(Long.valueOf(artistId)));
			members.add(findArtistById(Long.valueOf(artistId)));
		}
		group.setMembers(members);
		
		return artistRepository.save(group);
	}
	
	public List<Artist> findGroupMembers(Long id){
		return artistRepository.findMembersByGroupId(id);
	}
}
