package fr.cytech.projet.JEE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cytech.projet.JEE.modeles.Playlist;
import fr.cytech.projet.JEE.repository.PlaylistRepository;

@Service("playlistService")
public class PlaylistService {
	@Autowired
 	PlaylistRepository playlistRepository;
	
	public List<Playlist> findAllPlaylistByUserId(long userId){
		List<Playlist> playlists = playlistRepository.findAllPlaylistByUserId(userId);
		return playlists;
	}
}
