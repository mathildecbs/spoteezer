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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;
import fr.cytech.projet.JEE.services.ArtistService;

@Controller("artistController")
public class ArtistController {

	@Autowired
	ArtistService artistService;

	@GetMapping("/")
	public String artistForm() {
		return "artistForm";
	}

	@GetMapping("/group")
	public String groupForm(Model model) {
		model.addAttribute("artists", artistService.findAll());
		return "groupForm";
	}

	@GetMapping("/artist")
	public String showAllArtist(Model model) {
		List<Artist> artists = artistService.findAll();
		model.addAttribute("artists", artists);
		return "artists";
	}

	@GetMapping("/artist/{id}")
	public String showArtistPage(@PathVariable("id") String id, Model model) {
		Artist artist = artistService.findArtistById(Long.valueOf(id));
		model.addAttribute("artist", artist);
		return "artist";
	}

	@PostMapping(path = "/artist", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createArtist(@RequestParam Map<String, String> body, Model model) {
		Artist artist = artistService.createArtist(body);
		model.addAttribute("artist", artist);
		return "artist";
	}

	@PostMapping(path = "/group", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createGroup(@RequestParam Map<String, String> body, Model model) {
		System.out.println(body);
		Group group = artistService.createGroup(body);
		model.addAttribute("artist", group);
		return "redirect:addMembers/"+group.getId();
	}
	
	@GetMapping("/addMembers/{id}")
	public String addMembersForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("artists", artistService.findAll());
		model.addAttribute("groupId",id);
		return "addMembersForm";
	}
	
	@PostMapping(path="/addMembers/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addMembers(@PathVariable("id") String id,@RequestParam Map<String, String> body, Model model) {
		System.out.println(body);
		Group group = artistService.addGroupMembers(body, Long.valueOf(id));
		model.addAttribute("artist", group);
		return "artist";
	}
}
