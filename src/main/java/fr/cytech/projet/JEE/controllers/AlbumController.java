package fr.cytech.projet.JEE.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Song;
import fr.cytech.projet.JEE.services.AlbumService;
import fr.cytech.projet.JEE.services.ArtistService;

@Controller("albumController")
public class AlbumController {
	@Autowired
 	AlbumService albumService;
	@Autowired
 	ArtistService artistService;
	
	@GetMapping("/albums")
	public String showAllAlbum(Model model) {
		List<Album> albums = albumService.findAll();
		model.addAttribute("albums", albums);
		return "albums";
	}
	
	@GetMapping("/createAlbum")
	public String albumForm(Model model) {
		model.addAttribute("artists", artistService.findAll());
		return "albumForm";
	}

	@GetMapping("/album/{id}")
	public String showAlbumPage(@PathVariable("id") String id, Model model) {
		Album album = albumService.findAlbumById(id);
		model.addAttribute("album", album);
		return "album";
	}

	
	@PostMapping(path = "/album", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createAlbum(@RequestParam Map<String, String> body, Model model) {
		Album album = albumService.createAlbum(body);
		model.addAttribute("album", album);
		return "redirect:album/" + album.getId();
	}
	
	@GetMapping("/updateAlbum/{id}")
	public String updateAlbumForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("artists", artistService.findAll());
		Album album = albumService.findAlbumById(id);
		model.addAttribute("album",album);
		return "updateAlbumForm";
	}

	@PutMapping(path = "/album/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateAlbum(@PathVariable("id") String id, @RequestParam Map<String, String> body) {
		albumService.updateAlbum(id, body);
		return "redirect:/album/" + id;
	}
	
	@DeleteMapping(path = "/album/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String deleteAlbum(@PathVariable("id") String id, Model model) {
		boolean test = albumService.deleteAlbum(id);
		System.out.println(test);
		return "redirect:/albums";

	}
}
