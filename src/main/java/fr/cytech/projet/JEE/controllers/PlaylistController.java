package fr.cytech.projet.JEE.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cytech.projet.JEE.modeles.Playlist;
import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.services.PlaylistService;

@Controller("playlistController")
public class PlaylistController {

	@Autowired
 	PlaylistService playlistService;
	@Autowired
 	SongService songService;
	
	@GetMapping("/playlist/{id}")
	public String showPlaylist(
			@PathVariable("id") String id, 
			Model model) {
		Playlist playlist = playlistService.findPlaylistById(id);
		model.addAttribute("playlist", playlist);
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
			return "redirect:dashboard";
		}
		Playlist playlist = playlistService.createPlaylist(user, body);
		return "redirect:/profile";
	}
	
	@PostMapping("/findSong")
	public String findSong(
			@RequestParam("name") String name, 
			Model model) {
		
	}
}
