package fr.cytech.projet.JEE.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.services.ArtistService;

@Controller
public class ArtistController {
	
	ArtistService artistService;
	
	
	@GetMapping("/artist/{id}")
	public String showArtistPage(@RequestParam("id") String id,Model model) {
		Artist artist = artistService.findArtistById(Integer.parseInt(id));
		model.addAttribute("artist",artist);
		return "artist";
	}
}
