package fr.cytech.projet.JEE.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;
import fr.cytech.projet.JEE.repository.ArtistRepository;

@Service("artistService")
public class ArtistService {
	
	@Autowired
	ArtistRepository<Artist> artistRepository;
	
	@Autowired
	ArtistRepository<Group> groupRepository;
	
	public Artist findArtistById(String id) {
		try {
			Double.valueOf(id);
			if(id.split(".").length!=0)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Artist ID not a integer");
			
		} catch(NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Artist ID not a number");
			
		}
		Artist artist = artistRepository.findById(Long.valueOf(id)).orElse(null);
		
		if(artist!=null)
			return artist;
		else 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Artist does not exist.");
	}
	
	public Group findGroupById(String id) {
		try {
			Double.valueOf(id);
			if(id.split(".").length!=0)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Group ID not a integer");
			
		} catch(NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Group ID not a number");

		}
		Group group = groupRepository.findById(Long.valueOf(id)).orElse(null);
		
		if(group!=null)
			return group;
		else 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Group does not exist.");
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
	
	public Group changeGroupMembers(Map<String,String> groupMembers, String id) {
		if(Double.isNaN(Double.parseDouble(id)))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		Group group = findGroupById(id);
		List<Artist> members = new ArrayList<Artist>();
		for (String artistId : groupMembers.keySet()) {
			members.add(findArtistById(artistId));
		}
		group.setMembers(members);
		
		return artistRepository.save(group);
	}
	
	public List<Artist> findGroupMembers(String id){
		if(Double.isNaN(Double.parseDouble(id)))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		
		return artistRepository.findMembersByGroupId(Long.valueOf(id));
	}
	
	public Artist updateArtist(String id,Map<String,String>  updateDTO) {
		Artist artist = findArtistById(id);
		
		if(updateDTO.containsKey("name"))
			artist.setName(updateDTO.get("name"));
		
		if(updateDTO.containsKey("debutDate"))
			artist.setDebutDate(Date.valueOf(updateDTO.get("debutDate")));
		
		return artistRepository.save(artist);
	}
	
	public boolean deleteArtist(String id) {
		Artist artist = findArtistById(id);
		if(isGroupMember(artist)) {
			for (Long groupId : artistGroups(artist.getId())) {
				Group group = groupRepository.findById(groupId).orElse(null);
				group.getMembers().remove(artist);
				groupRepository.save(group);
			}
		}
		artistRepository.delete(artist);
		artist = artistRepository.findById(Long.valueOf(id)).orElse(null);
		if(artist!=null)
			return false;
		else 
			return true;
	}
	
	public boolean isGroupMember(Artist artist) {
		List<Long> groupMembers = artistRepository.findArtistInGroup();
		return groupMembers.contains(artist.getId());
	}
	
	public List<Long> artistGroups(Long id){
		
		return artistRepository.findArtistGroups(id);
	}
}
