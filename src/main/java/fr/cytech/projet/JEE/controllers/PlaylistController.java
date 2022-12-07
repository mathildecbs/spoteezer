package fr.cytech.projet.JEE.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.services.PlaylistService;

@Controller("playlistController")
public class PlaylistController {

	@Autowired
 	PlaylistService playlistService;
	
	public String showPlaylist(
			HttpSession session,
			Model model) {
		User user = (User)session.getAttribute("user");
		if(user == null) {
			return "redirect:login";
		}
		//List<Song> songs = ;
		//model.addAttribute("songs", songs)
		return "";
	}
	
}
