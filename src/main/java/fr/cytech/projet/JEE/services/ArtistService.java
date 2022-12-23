package fr.cytech.projet.JEE.services;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.repository.AlbumRepository;
import fr.cytech.projet.JEE.repository.ArtistRepository;
import fr.cytech.projet.JEE.repository.SongRepository;

@Service("artistService")
public class ArtistService {
	
	@Autowired
	ArtistRepository<Artist> artistRepository;
	
	@Autowired
	ArtistRepository<Group> groupRepository;
	
	@Autowired
	AlbumRepository<Album> albumRepository;

	@Autowired
	SongRepository<Song> songRepository;

	//trouve un artist avec son id
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
	
	//trouve un group avec son id
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
	
	//trouve tous les artists (artist et group confondu)
	public List<Artist> findAll(){
		return artistRepository.findAll();
	}
	
	//cree un artist
	public Artist createArtist(Map<String,String>  artistDTO) {
		Artist artist = new Artist();
		changeAttributes(artist, artistDTO);
		artist.setPicture("singer.png");
		Artist artistSaved= artistRepository.save(artist);
		if(artistSaved!=null)
			return artistSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Artist update failed");
	}
	

	//cree un group
	public Group createGroup(Map<String,String>  groupDTO) {
		Group group = new Group();
		changeAttributes(group, groupDTO);
		group.setPicture("band.png");
		Group groupSaved= artistRepository.save(group);
		if(groupSaved!=null)
			return groupSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Group membre change failed");
	}
	
	//ajoute les attributs a un artist
	public static void changeAttributes(Artist artist, Map<String, String> artistDTO) {
		if (artistDTO.get("name") != null&&!artistDTO.get("name").contentEquals("")) {
			artist.setName(artistDTO.get("name"));
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect name value");
			
		}
		if (artistDTO.get("debutDate") != null&&!artistDTO.get("debutDate").contentEquals("")) {
			artist.setDebutDate(Date.valueOf(artistDTO.get("debutDate")));
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect debut date value");
			
		}
		
	}
	
	//change les membres d'un group
	public Group changeGroupMembers(Map<String,String> groupMembers, String id) {
		Group group = findGroupById(id);
		List<Artist> members = new ArrayList<Artist>();
		for (String artistId : groupMembers.keySet()) {
			members.add(findArtistById(artistId));
		}
		group.setMembers(members);
		
		Group groupSaved= artistRepository.save(group);
		if(groupSaved!=null)
			return groupSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Group membre change failed");

	}
	
	//trouve les membres d'un group
	public List<Artist> findGroupMembers(String id){
		
		return findGroupById(id).getMembers();
	}
	
	//met a jour un artist
	public Artist updateArtist(String id,Map<String,String>  updateDTO) {
		Artist artist = findArtistById(id);
		changeAttributes(artist, updateDTO);
		Artist artistSaved= artistRepository.save(artist);
		if(artistSaved!=null)
			return artistSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Artist update failed");

	}
	
	//supprime un artist
	public void deleteArtist(String id) {
		Artist artist = findArtistById(id);
		if(isGroupMember(artist)) {
			for (Long groupId : artistGroups(artist.getId())) {
				Group group = groupRepository.findById(groupId).orElse(null);
				group.getMembers().remove(artist);
				groupRepository.save(group);
			}
		}
		for (Album album : artist.getAlbums()) {
			if(album.getArtist().size()==1) {
				List<Song> songs = album.getSongs();
				for (int i = 0; i < songs.size(); i++) {
					songRepository.delete(songs.get(i));
				}
				
				albumRepository.delete(album);
				album = albumRepository.findById(Long.valueOf(id)).orElse(null);
				if (album != null)
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "album delete failed");
			
			
			}
			
		}
		artistRepository.delete(artist);
		artist = artistRepository.findById(Long.valueOf(id)).orElse(null);
		if(artist!=null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "artist delete failed");
		
	}
	
	//verifie si un artist fait parti d'un group
	public boolean isGroupMember(Artist artist) {
		List<Long> groupMembers = artistRepository.findArtistInGroup();
		return groupMembers.contains(artist.getId());
	}
	
	//trouve les id des groups dont l'artist fait partir
	public List<Long> artistGroups(Long id){
		
		return artistRepository.findArtistGroups(id);
	}
	
	//ajout une photo a un artist
	public void artistPictureUpload(String id, MultipartFile mpF) throws IOException {
		String fileName = StringUtils.cleanPath(mpF.getOriginalFilename());
		Artist a = findArtistById(id);
		ImageUploadService.saveFile("src/main/resources/static/artist/"+a.getId(), fileName, mpF);
	}
	
	//change la photo d'un artist
	public Artist changeArtistPicture(String id, String pictureName) {
		Artist a = findArtistById(id);
		a.setPicture(pictureName);
		return artistRepository.save(a);
	}
	
	
	
}
