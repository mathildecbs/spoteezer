package fr.cytech.projet.JEE.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.services.AlbumService;
import fr.cytech.projet.JEE.services.ArtistService;
import fr.cytech.projet.JEE.services.SongService;

@Controller("songController")
public class SongController {
	@Autowired
	SongService songService;

	@Autowired
	AlbumService albumService;

	@Autowired
	ArtistService artistService;

	/*
	 * @GetMapping("/") public String redirectIndex() { return "redirect:song"; }
	 */

	//affiche toutes les musiques
	@GetMapping("/songs")
	public String showAllSong(Model model) {
		List<Song> songs = songService.findAll();
		model.addAttribute("songs", songs);
		return "songs";
	}

	//affiche formulaire pour creer une musique
	@GetMapping("/createSong")
	public String songForm(Model model) {
		List<Album> albums = albumService.findAll();
		if (albums.size() != 0) {
			model.addAttribute("albums", albums);
			model.addAttribute("artists", artistService.findAll());
			return "songForm";
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Vous ne pouvez pas créer de musique sans avoir créer d'album avant.");
		}
	}

	//affiche les musiques d'un album
	@GetMapping("/album_{album_id}/songs")
	public String showSongFromAlbum(@PathVariable("album_id") String id, Model model) {
		List<Song> songs = songService.findSongByAlbumId(id);
		model.addAttribute("songs", songs);
		return "song";
	}

	//affiche la page d'une musique
	@GetMapping("/song/{id}")
	public String showSongPage(@PathVariable("id") String id, Model model) {
		Song song = songService.findSongById(id);
		model.addAttribute("song", song);
		return "song";
	}

	//cree une musique
	@PostMapping(path = "/song", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createSong(@RequestParam Map<String, String> body, Model model) {
		Song song = songService.createSong(body);
		model.addAttribute("song", song);
		return "redirect:song/" + song.getId();
	}

	//affiche formulaire pour modifier une musique
	@GetMapping("/updateSong/{id}")
	public String updateSongForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("artists", artistService.findAll());
		model.addAttribute("albums", albumService.findAll());
		Song song = songService.findSongById(id);
		model.addAttribute("song", song);
		return "updateSongForm";
	}

	//met a jour une musique
	@PutMapping(path = "/song/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateSong(@PathVariable("id") String id, @RequestParam Map<String, String> body) {
		songService.updateSong(id, body);
		return "redirect:/song/" + id;
	}
	
	//supprime une musqiue
	@DeleteMapping(path = "/song/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String deleteSong(@PathVariable("id") String id, Model model) {
		songService.deleteSong(id);
		return "redirect:/albums";

	}
}
