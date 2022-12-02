package fr.cytech.projet.JEE.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;
import fr.cytech.projet.JEE.services.ArtistService;

@Controller("artistController")
public class ArtistController {
	@Autowired
	ArtistService artistService;

	@GetMapping("/")
	public String redirectIndex() {
		return "redirect:artist";
	}

	@GetMapping("/createArtist")
	public String artistForm() {
		return "artistForm";
	}

	@GetMapping("/artist")
	public String showAllArtist(Model model) {
		List<Artist> artists = artistService.findAll();
		model.addAttribute("artists", artists);
		return "artists";
	}

	@GetMapping("/artist/{id}")
	public String showArtistPage(@PathVariable("id") String id, Model model) {
		Artist artist = artistService.findArtistById(id);
		if (artist.getType().equals("Group")) {
			List<Artist> members = artistService.findGroupMembers(artist.getId().toString());
			model.addAttribute("members", members);
		}
		model.addAttribute("artist", artist);
		return "artist";
	}

	@PostMapping(path = "/artist", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createArtist(@RequestParam Map<String, String> body, Model model) {
		System.out.println(body);

		if (body.containsKey("group")) {
			Group group = artistService.createGroup(body);
			model.addAttribute("artist", group);
			return "redirect:/changeMembers/" + group.getId();

		} else {
			Artist artist = artistService.createArtist(body);
			System.out.println(artist.getType());
			model.addAttribute("artist", artist);
			return "redirect:artist/" + artist.getId();
		}
	}

	@GetMapping("/changeMembers/{id}")
	public String changeMembersForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("artists", artistService.findAll());
		model.addAttribute("group", artistService.findArtistById(id));
		return "changeMembersForm";
	}

	@PostMapping(path = "/changeMembers/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String changeMembers(@PathVariable("id") String id, @RequestParam Map<String, String> body, Model model) {
		System.out.println(body);
		Group group = artistService.changeGroupMembers(body, id);
		model.addAttribute("artist", group);
		return "redirect:/artist/" + group.getId();
	}

	@DeleteMapping(path = "/deleteArtist/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String deleteArtist(@PathVariable("id") String id, Model model) {
		artistService.deleteArtist(id);
		return "redirect:/artist";

	}

	@GetMapping("/updateArtist/{id}")
	public String updateArtistForm(@PathVariable("id") String id, Model model) {
		Artist artist = artistService.findArtistById(id);
		model.addAttribute(artist);
		return "updateArtistForm";
	}

	@PutMapping(path = "/updateArtist/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateArtist(@PathVariable("id") String id, @RequestParam Map<String, String> body) {
		artistService.updateArtist(id, body);
		return "redirect:/artist/" + id;
	}
}
