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
	
	public Song findSongById(String id) {
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
		
		List<Artist> artists = new ArrayList<Artist>();
		
		artists.addAll(album.getArtist());
		
		Set<String> keys = songDTO.keySet();
		for (String string : keys) {
			if(string.contains("art")) {
				if (!artists.contains(artistService.findArtistById(songDTO.get(string))))
					{artists.add(artistService.findArtistById(songDTO.get(string)));}
			}
		}
		
		song.setArtist(artists);
		
		return songRepository.save(song);
	}
	
	public Song updateSong(String id,Map<String,String>  updateDTO) {
		Song song = findSongById(id);
		
		if(updateDTO.containsKey("name"))
			song.setName(updateDTO.get("name"));
		
		if(updateDTO.containsKey("releaseDate"))
			song.setReleaseDate(Date.valueOf(updateDTO.get("releaseDate")));
		
		Album album = albumService.findAlbumById(updateDTO.get("album"));
		song.setAlbum(album);
		
		List<Artist> artists = new ArrayList<Artist>();
		Set<String> keys = updateDTO.keySet();
		for (String string : keys) {
			if(string.contains("art")) {
				artists.add(artistService.findArtistById(updateDTO.get(string)));
			}
		}
		song.setArtist(artists);
		
		return songRepository.save(song);
	}
	
	public boolean deleteSong(String id) {
		Song song = findSongById(id);
		
		songRepository.delete(song);
		song = songRepository.findById(Long.valueOf(id)).orElse(null);
		if(song!=null)
			return false;
		else 
			return true;
	}
}
