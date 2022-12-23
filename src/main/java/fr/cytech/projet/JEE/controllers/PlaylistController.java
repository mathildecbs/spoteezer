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

@Controller("playlistController")
public class PlaylistController {

	@Autowired
	PlaylistService playlistService;
	@Autowired
	SongService songService;

	// affichage de la liste des playlists
	@GetMapping("/playlist")
	public String showPlaylistPage(HttpSession session, Model model) {
		try {
			User user = (User) session.getAttribute("user");
			List<Playlist> playlists = playlistService.findAllPlaylistByUserId(user.getId());
			model.addAttribute("playlists", playlists);
			return "playlists";
		} catch (NullPointerException eo) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Veuillez vous connecter");
		}
	}

	/* Affichage de la page d'une playlist */
	@GetMapping("/playlist/{id}")
	public String showPlaylist(@PathVariable("id") String id, HttpSession session, Model model) {
		try {
			Boolean noSong = false;
			Playlist playlist = playlistService.findPlaylistById(id);
			model.addAttribute("playlist", playlist);
			if (playlist.getSongs().isEmpty()) {
				noSong = true;
			}
			model.addAttribute("noSong", noSong);
			return "playlist";
		} catch (NullPointerException eo) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Veuillez vous connecter");
		}
	}

	/* Route pour rechercher une musique sur la page d'une playlist */
	@PostMapping("/playlist/{id}")
	public String findSong(@RequestParam("name") String name, HttpSession session,
			@RequestParam("playlistId") String playlistId, Model model) {
		List<Song> songs = songService.findSongsByName(name);
		if (songs.isEmpty()) {
			String error = "La musique n'a pas pu être trouvée ou n'existe pas";
			model.addAttribute("error", error);
		} else {
			model.addAttribute("songs", songs);
		}
		if (session.getAttribute("user") == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Veuillez vous connecter");
		}
		Boolean noSong = false;
		Playlist playlist = playlistService.findPlaylistById(playlistId);
		model.addAttribute("playlist", playlist);
		if (playlist.getSongs().isEmpty()) {
			noSong = true;
		}
		model.addAttribute("noSong", noSong);
		return "playlist";
	}

	// renvoie a la page profil pour créer une playlist
	@GetMapping("/createPlaylist")
	public String redirectCreatePlaylist() {
		return "redirect:/profile";
	}

	// creer une playlist
	@PostMapping(path = "/createPlaylist", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createPlaylist(@RequestParam Map<String, String> body, HttpSession session, Model model) {
		try {
			User user = (User) session.getAttribute("user");
			playlistService.createPlaylist(user, body);
			return "redirect:/profile";
		} catch (NullPointerException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Veuillez vous connecter");
		}
	}

	/* Route pour ajouter une musique à la playlist sur laquelle on est */
	@PostMapping("/addSongToPlaylist")
	public String addSongToPlaylist(@RequestParam("songId") String songId,
			@RequestParam("playlistId") String playlistId, Model model) {

		playlistService.addSongToPlaylist(songId, playlistId);

		return "redirect:/playlist/" + playlistId;

	}

	/* Route pour supprimer une musique d'une playlist */
	@DeleteMapping("/deleteSongFromPlaylist")
	public String deleteSongFromPlaylist(@RequestParam("songId") String songId,
			@RequestParam("playlistId") String playlistId, Model model) {

		playlistService.deleteSongFromPlaylist(songId, playlistId);

		return "redirect:/playlist/" + playlistId;

	}

	/* Route pour supprimer une playlist */
	@DeleteMapping("/deletePlaylist")
	public String deletePlaylist(@RequestParam("playlistId") String playlistId, HttpSession session, Model model) {
		try {
			User user = (User) session.getAttribute("user");
			playlistService.deletePlaylist(user, playlistId);
			return "redirect:/profile";
		} catch (NullPointerException eo) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Veuillez vous connecter");

		}
	}
}
