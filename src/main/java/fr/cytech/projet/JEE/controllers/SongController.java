package fr.cytech.projet.JEE.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.services.AlbumService;
import fr.cytech.projet.JEE.services.SongService;

@Controller("songController")
public class SongController {
	@Autowired
	SongService songService;
	
	@Autowired
	AlbumService albumService;

	/*@GetMapping("/")
	public String redirectIndex() {
		return "redirect:song";
	}*/

	@GetMapping("/createSong")
	public String songForm(Model model) {
		model.addAttribute("albums", albumService.findAll());
		return "songForm";
	}

	@GetMapping("/album_{album_id}/songs")
	public String showSongFromAlbum(@PathVariable("album_id") String id, Model model) {
		List<Song> songs = songService.findSongByAlbumId(Long.valueOf(id));
		model.addAttribute("songs", songs);
		return "song";
	}
	
	@GetMapping("/song/{id}")
	public String showSongPage(@PathVariable("id") String id, Model model) {
		Song song = songService.findSongById(Long.valueOf(id));
		model.addAttribute("song", song);
		return "song";
	}
	
	@PostMapping(path = "/song", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createSong(@RequestParam Map<String, String> body, Model model) {
		Song song = songService.createSong(body);
		model.addAttribute("song", song);
		return "redirect:song/" + song.getId();
	}
}
