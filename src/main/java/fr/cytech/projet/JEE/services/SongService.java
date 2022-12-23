package fr.cytech.projet.JEE.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.repository.SongRepository;

@Service("songService")
public class SongService {
	@Autowired
	SongRepository<Song> songRepository;

	@Autowired
	AlbumService albumService;

	@Autowired
	ArtistService artistService;

	// trouve toute les musiques
	public List<Song> findAll() {
		return songRepository.findAll();
	}

	// trouve une musique par son id
	public Song findSongById(String id) {
		try {
			Double.valueOf(id);
			if (id.split(".").length != 0)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Song ID not a integer");

		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Song ID not a number");

		}
		Song song = songRepository.findById(Long.valueOf(id)).orElse(null);
		if (song != null)
			return song;

		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song does not exist.");
	}

	// trouve les musiques d'un album avec son id
	public List<Song> findSongByAlbumId(String id) {
		return albumService.findAlbumById(id).getSongs();
	}

	// trouve les musiques qui contiennnet name dans leur nom
	public List<Song> findSongsByName(String name) {
		Stream<Song> streamSongs = songRepository.findAll().stream();
		return streamSongs.filter(f -> f.getName().contains(name)).collect(Collectors.toList());
	}

	// cree une musique
	public Song createSong(Map<String, String> songDTO) {
		Song song = new Song();
		changeAttributes(song, songDTO);
		Album album = albumService.findAlbumById(songDTO.get("album"));
		song.setAlbum(album);
		List<Artist> artists = new ArrayList<Artist>();

		artists.addAll(album.getArtist());

		Set<String> keys = songDTO.keySet();
		for (String string : keys) {
			if (string.contains("art")) {
				if (!artists.contains(artistService.findArtistById(songDTO.get(string)))) {
					artists.add(artistService.findArtistById(songDTO.get(string)));
				}
			}
		}

		song.setArtist(artists);

		Song songSaved = songRepository.save(song);
		if (songSaved != null)
			return songSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Song creation failed");
	}

	// ajoute les attributs a une musique
	public static void changeAttributes(Song song, Map<String, String> songDTO) {
		if (songDTO.get("name") != null && !songDTO.get("name").contentEquals("")) {
			song.setName(songDTO.get("name"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect name value");

		}
		if (songDTO.get("releaseDate") != null && !songDTO.get("releaseDate").contentEquals("")) {
			song.setReleaseDate(Date.valueOf(songDTO.get("releaseDate")));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect release date value");

		}

	}

	// met a jour une musique
	public Song updateSong(String id, Map<String, String> updateDTO) {
		Song song = findSongById(id);
		changeAttributes(song, updateDTO);
		Album album = albumService.findAlbumById(updateDTO.get("album"));
		song.setAlbum(album);

		List<Artist> artists = new ArrayList<Artist>();
		Set<String> keys = updateDTO.keySet();
		int counter = 0;
		for (String string : keys) {
			if (string.contains("art")) {
				counter++;
				artists.add(artistService.findArtistById(updateDTO.get(string)));
			}
		}
		
		if (counter == 0)
			artists.addAll(album.getArtist());
			song.setArtist(artists);
		Song songSaved = songRepository.save(song);

		if (songSaved != null)
			return songSaved;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Song creation failed");
	}

	// supprime musique
	public void deleteSong(String id) {
		Song song = findSongById(id);

		songRepository.delete(song);
		song = songRepository.findById(Long.valueOf(id)).orElse(null);
		if (song != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Song delete failed");
	}
}
