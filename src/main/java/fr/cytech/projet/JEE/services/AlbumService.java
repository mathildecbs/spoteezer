package fr.cytech.projet.JEE.services;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.repository.AlbumRepository;
import fr.cytech.projet.JEE.repository.SongRepository;

@Service("albumService")
public class AlbumService {
	@Autowired
	AlbumRepository<Album> albumRepository;

	@Autowired
	SongRepository<Song> songRepository;

	@Autowired
	ArtistService artistService;

	// trouve un album par son id
	public Album findAlbumById(String id) {
		try {
			Double.valueOf(id);
			if (id.split(".").length != 0)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album ID not a integer");

		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album ID not a number");

		}
		Album album = albumRepository.findById(Long.valueOf(id)).orElse(null);
		if (album != null)
			return album;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album does not exist.");
	}

	// trouve tous les albums
	public List<Album> findAll() {
		return albumRepository.findAll();
	}

	// cree un album
	public Album createAlbum(Map<String, String> albumDTO) {
		Album album = new Album();
		changeAttributes(album, albumDTO);
		List<Artist> artists = new ArrayList<Artist>();
		Set<String> keys = albumDTO.keySet();
		for (String string : keys) {
			if (string.contains("art")) {
				artists.add(artistService.findArtistById(albumDTO.get(string)));
			}
		}
		album.setArtist(artists);
		album.setPicture("album.png");
		Album albumSaved = albumRepository.save(album);
		if (albumSaved != null)
			return albumSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album creation failed");
	}

	// ajoute les attributs a un album
	public static void changeAttributes(Album album, Map<String, String> albumDTO) {
		if (albumDTO.get("name") != null && !albumDTO.get("name").contentEquals("")) {
			album.setName(albumDTO.get("name"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect name value");

		}
		if (albumDTO.get("releaseDate") != null && !albumDTO.get("releaseDate").contentEquals("")) {
			album.setReleaseDate(Date.valueOf(albumDTO.get("releaseDate")));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect release date value");

		}

	}

	// met a jour l'album
	public Album updateAlbum(String id, Map<String, String> updateDTO) {
		Album album = findAlbumById(id);

		changeAttributes(album, updateDTO);
		List<Artist> formerArtist = album.getArtist();

		List<Artist> artists = new ArrayList<Artist>();
		Set<String> keys = updateDTO.keySet();
		for (String string : keys) {
			if (string.contains("art")) {
				artists.add(artistService.findArtistById(updateDTO.get(string)));
				formerArtist.remove(artistService.findArtistById(updateDTO.get(string)));
			}
		}
		album.setArtist(artists);

		if (updateDTO.containsKey("updateSong")) {
			List<Song> songs = album.getSongs();
			for (int i = 0; i < songs.size(); i++) {
				for (int k = 0; k < artists.size(); k++) {
					songs.get(i).addArtist(artists.get(k));
				}
				for (int j = 0; j < formerArtist.size(); j++) {
					if (songs.get(i).getArtist().contains(formerArtist.get(j))) {
						songs.get(i).removeArtist(formerArtist.get(j));
					}
				}
			}
		}

		Album albumSaved = albumRepository.save(album);
		if (albumSaved != null)
			return albumSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album update failed");
	}

	// ajout une photo a l'album
	public void albumPictureUpload(String id, MultipartFile mpF) throws IOException {
		String fileName = StringUtils.cleanPath(mpF.getOriginalFilename());
		Album a = findAlbumById(id);
		ImageUploadService.saveFile("src/main/resources/static/album/" + a.getId(), fileName, mpF);
	}

	// change la photo d'un album
	public Album changeAlbumPicture(String id, String pictureName) {
		Album a = findAlbumById(id);
		a.setPicture(pictureName);
		return albumRepository.save(a);
	}

	// supprime album
	public void deleteAlbum(String id) {
		Album album = findAlbumById(id);

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
