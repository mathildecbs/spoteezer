package fr.cytech.projet.JEE.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.repository.AlbumRepository;

@Service("albumService")
public class AlbumService {
	@Autowired
	AlbumRepository<Album> albumRepository;
	
	@Autowired
	ArtistService artistService;
	
	public Album findAlbumById(String id) {
		Album album = albumRepository.findById(Long.valueOf(id)).orElse(null);
		return album;
	}
	
	public List<Album> findAll() {
		return albumRepository.findAll();
	}
	
	public Album createAlbum(Map<String, String> albumDTO) {
		Album album = new Album();
		album.setName(albumDTO.get("name"));
		album.setReleaseDate(Date.valueOf(albumDTO.get("releaseDate")));
		List<Artist> artists = new ArrayList<Artist>();
		Set<String> keys = albumDTO.keySet();
		for (String string : keys) {
			if(string.contains("art")) {
				artists.add(artistService.findArtistById(albumDTO.get(string)));
			}
		}
		album.setArtist(artists);
		return albumRepository.save(album);
	}
	
	public Album updateAlbum(String id,Map<String,String>  updateDTO) {
		Album album = findAlbumById(id);
		
		if(updateDTO.containsKey("name"))
			album.setName(updateDTO.get("name"));
		
		if(updateDTO.containsKey("releaseDate"))
			album.setReleaseDate(Date.valueOf(updateDTO.get("releaseDate")));
		
		List<Artist> artists = new ArrayList<Artist>();
		Set<String> keys = updateDTO.keySet();
		for (String string : keys) {
			if(string.contains("art")) {
				artists.add(artistService.findArtistById(updateDTO.get(string)));
			}
		}
		album.setArtist(artists);
		
		return albumRepository.save(album);
	}
}
