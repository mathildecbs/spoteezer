package fr.cytech.projet.JEE.services;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
		System.out.println();
		List<Artist> artists = new ArrayList<Artist>();
		Set<String> keys = albumDTO.keySet();
		for (String string : keys) {
			if(string.contains("art")) {
				artists.add(artistService.findArtistById(albumDTO.get(string)));
			}
		}
		
		album.setArtist(artists);
		album.setPicture("album.png");
		return albumRepository.save(album);
	}
	
	public void artistPictureUpload(String id, MultipartFile mpF) throws IOException {
		String fileName = StringUtils.cleanPath(mpF.getOriginalFilename());
		Album a = findAlbumById(id);
		ImageUploadService.saveFile("src/main/resources/static/album/"+a.getId(), fileName, mpF);
	}
	
	public Album changeArtistPicture(String id, String pictureName) {
		Album a = findAlbumById(id);
		a.setPicture(pictureName);
		return albumRepository.save(a);
	}
}
