package fr.cytech.projet.JEE.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Playlist;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.services.PlaylistService;
import fr.cytech.projet.JEE.services.SongService;
import fr.cytech.projet.JEE.services.UserService;

@Controller("playlistController")
public class PlaylistController {

	@Autowired
	PlaylistService playlistService;
	@Autowired
	SongService songService;
	@Autowired
 	UserService userService;
	
	/* Affichage de la page d'une playlist */
	@GetMapping("/playlist/{id}")
	public String showPlaylist(
			@PathVariable("id") String id, 
			HttpSession session,
			Model model) {
		if(session.getAttribute("user")==null) {
			return "redirect:/dashboard";
		}
		Boolean noSong = false;
		Playlist playlist = playlistService.findPlaylistById(id);
		model.addAttribute("playlist", playlist);
		if(playlist.getSongs().isEmpty()) {
			noSong = true;
		}
		model.addAttribute("noSong", noSong);
		return "playlist";
	}
	
	/* Route pour rechercher une musique sur la page d'une playlist */
	@PostMapping("/playlist/{id}")
	public String findSong(
			@RequestParam("name") String name, 
			HttpSession session,
			@RequestParam("playlistId") String playlistId, 
			Model model) {
		List<Song> songs = songService.findSongsByName(name);
		if(songs.isEmpty()) {
			String error = "La musique n'a pas pu être trouvée ou n'existe pas";
			model.addAttribute("error", error);
		}else {
			model.addAttribute("songs", songs);
		}
		if(session.getAttribute("user")==null) {
			return "redirect:/dashboard";
		}
		Boolean noSong = false;
		Playlist playlist = playlistService.findPlaylistById(playlistId);
		model.addAttribute("playlist", playlist);
		if(playlist.getSongs().isEmpty()) {
			noSong = true;
		}
		model.addAttribute("noSong", noSong);
		return "playlist";
	}
		
	@PostMapping(path="/createPlaylist",
				consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createPlaylist(
			@RequestParam Map<String,String> body, 
			HttpSession session,
			Model model) {
		User user = (User)session.getAttribute("user");
		if(user==null) {
			return "redirect:/dashboard";
		}
		Playlist playlist = playlistService.createPlaylist(user, body);
		return "redirect:/profile";
	}
	
	/* Route pour ajouter une musique à la playlist sur laquelle on est */ 
	@PostMapping("/addSongToPlaylist")
	public String addSongToPlaylist(
			@RequestParam("songId") String songId, 
			@RequestParam("playlistId") String playlistId, 
			Model model) {
		try{
			Long id = Long.parseLong(songId);
			Song song = songService.findSongById(id);
			Playlist playlist = playlistService.findPlaylistById(playlistId);
			if(playlist != null && song != null) {
				playlistService.addSongToPlaylist(song, playlist);
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "la playlist ou la musique n'ont pas été trouvé");
			}
			return "redirect:/playlist/" + playlistId;
		}catch (Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "parse long échec");
		}		
	}
	
	/* Route pour supprimer une musique d'une playlist */
	@DeleteMapping("/deleteSongFromPlaylist")
	public String deleteSongFromPlaylist(
			@RequestParam("songId") String songId, 
			@RequestParam("playlistId") String playlistId, 
			Model model) {
		try{
			Long id = Long.parseLong(songId);
			Song song = songService.findSongById(id);
			Playlist playlist = playlistService.findPlaylistById(playlistId);
			if(playlist != null && song != null) {
				playlistService.deleteSongFromPlaylist(song, playlist);
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "la playlist ou la musique n'ont pas été trouvé");
			}
			return "redirect:/playlist/" + playlistId;
		}catch (Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "parse long échec");
		}	
	}
	
	/* Route pour supprimer une playlist */
	@DeleteMapping("/deletePlaylist")
	public String deletePlaylist(
			@RequestParam("playlistId") String playlistId, 
			HttpSession session,
			Model model) {
		Playlist playlist = playlistService.findPlaylistById(playlistId);
		User user = (User)session.getAttribute("user");
		if(playlist != null ) {
			System.out.println("playlist pas nulle");
			userService.deletePlaylist(user, playlist);
			System.out.println("still alive ?");
		}else {
			System.out.println("playlist nulle");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "la playlist n'a pas été trouvée");
		}
		return "redirect:/profile";
	}
}
