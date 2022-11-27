package fr.cytech.projet.JEE.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;
import fr.cytech.projet.JEE.services.ArtistService;

@Controller("artistController")
public class ArtistController {
	
	@Autowired
	ArtistService artistService ;
	
	@GetMapping("/")
	public String test() {
		return "artistForm";
	}
	
	@GetMapping("/artist/{id}")
	public String showArtistPage(@PathVariable("id") String id,Model model) {
		Artist artist = artistService.findArtistById(Integer.parseInt(id));
		model.addAttribute("artist",artist);
		return "artist";
	}
	
	@PostMapping(path="/artist",  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	public String createArtist(@RequestParam Map<String,String> body,Model model) {
		System.out.println(body);
		Artist artist = artistService.createArtist(body);
		model.addAttribute("artist",artist);
		return "artist";
	}
	
	@PostMapping("/group")
	public String createGroup(@RequestBody() Artist body,Model model) {
		Group group = artistService.createGroup(body, null);
		model.addAttribute("group", group);
		return "artist";
	}
}
