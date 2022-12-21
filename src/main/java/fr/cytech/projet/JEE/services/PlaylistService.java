package fr.cytech.projet.JEE.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Playlist;
import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.repository.PlaylistRepository;

@Service("playlistService")
public class PlaylistService {
	@Autowired
 	PlaylistRepository playlistRepository;
	
	public List<Playlist> findAllPlaylistByUserId(long userId){
		List<Playlist> playlists = playlistRepository.findAllPlaylistByUserId(userId);
		return playlists;
	}
	
	public Playlist findPlaylistById(String id) {
		try{
			Long idL = Long.parseLong(id);
			Playlist playlist = playlistRepository.findPlaylistById(idL);
			return playlist;
		}catch (Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	
	public Playlist createPlaylist(User user, Map<String,String> playlistDTO) {
		Playlist playlist = new Playlist();
	    java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
		playlist.setCreationDate(date);
		PlaylistService.changeAttributesPlaylist(playlist, playlistDTO);
		playlist.setUser(user);
		user.addPlaylist(playlist);
		return playlistRepository.save(playlist);
	}
	
	public static void changeAttributesPlaylist(Playlist playlist, Map<String,String> playlistDTO) {
		if(playlistDTO.get("name")!=null) {
			playlist.setName(playlistDTO.get("name"));
		}
		if(playlistDTO.get("description")!=null) {
			playlist.setDescription(playlistDTO.get("description"));
		}
	}
}
