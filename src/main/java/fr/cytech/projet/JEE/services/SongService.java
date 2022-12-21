package fr.cytech.projet.JEE.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.repository.SongRepository;

@Service("songService")
public class SongService {
	@Autowired
	SongRepository songRepository;
	
	@Autowired
	AlbumService albumService;
	
	public Song findSongById(Long id) {
		Song song = songRepository.findById(Long.valueOf(id)).orElse(null);
		return song;
	}
	
	public List<Song> findSongByAlbumId(Long id) {
		return songRepository.findByAlbumId(id);
	}
	
	public Song createSong(Map<String, String> songDTO) {
		Song song = new Song();
		song.setName(songDTO.get("name"));
		song.setReleaseDate(Date.valueOf(songDTO.get("releaseDate")));
		Album album = albumService.findAlbumById(songDTO.get("album"));
		song.setAlbum(album);
		
		return songRepository.save(song);
	}
}
