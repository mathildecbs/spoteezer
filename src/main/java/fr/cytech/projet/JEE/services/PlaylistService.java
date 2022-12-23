package fr.cytech.projet.JEE.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Playlist;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.repository.PlaylistRepository;

@Service("playlistService")
public class PlaylistService {
	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	SongService songService;

	//trouve ensemble playlist depuis le id du user
	public List<Playlist> findAllPlaylistByUserId(long userId) {
		List<Playlist> playlists = playlistRepository.findAllPlaylistByUserId(userId);
		return playlists;
	}

	//trouve playlist avec son id
	public Playlist findPlaylistById(String id) {
		try {
			Long idL = Long.parseLong(id);
			Playlist playlist = playlistRepository.findPlaylistById(idL);
			if (playlist == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist does not exist.");
			}
			return playlist;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not an integer");
		}
	}

	//cree une playlist
	public Playlist createPlaylist(User user, Map<String, String> playlistDTO) {
		Playlist playlist = new Playlist();
		Date date = new Date(System.currentTimeMillis());
		playlist.setCreationDate(date);
		PlaylistService.changeAttributesPlaylist(playlist, playlistDTO);
		playlist.setUser(user);
		user.addPlaylist(playlist);
		Playlist p = playlistRepository.save(playlist);
		if (p != null)
			return p;
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist creation failed");

		}
	}

	//ajout les attribut a une playlist
	public static void changeAttributesPlaylist(Playlist playlist, Map<String, String> playlistDTO) {
		if (playlistDTO.get("name") != null && playlistDTO.get("name") != "") {
			playlist.setName(playlistDTO.get("name"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect name value");

		}
		if (playlistDTO.get("description") != null && playlistDTO.get("description") != "") {
			playlist.setDescription(playlistDTO.get("description"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect description value");

		}
	}
	
	//supprime une playlist
	public void deletePlaylist(User user, String playlistId) {
		Playlist playlist = findPlaylistById(playlistId);
		user.removePlaylist(playlist);
		playlistRepository.deleteById(playlist.getId());
	}

	//ajoute une musique a la playlist
	public void addSongToPlaylist(String songId, String playlistId) {
		Song song = songService.findSongById(songId);
		Playlist playlist = findPlaylistById(playlistId);
		playlist.addSong(song);
		playlistRepository.save(playlist);
	}
	
	
	//eneleve une musique a la playlist
	public void deleteSongFromPlaylist(String songId, String playlistId) {
		Song song = songService.findSongById(songId);
		Playlist playlist = findPlaylistById(playlistId);
		playlist.removeSong(song);
		playlistRepository.save(playlist);
	}
}
