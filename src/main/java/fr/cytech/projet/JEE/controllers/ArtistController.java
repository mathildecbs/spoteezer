package fr.cytech.projet.JEE.controllers;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import fr.cytech.projet.JEE.modeles.Artist;
import fr.cytech.projet.JEE.modeles.Group;
import fr.cytech.projet.JEE.services.ArtistService;
import fr.cytech.projet.JEE.services.ImageUploadService;

@Controller("artistController")
public class ArtistController {
	@Autowired
	ArtistService artistService;

	// affiche le formulaire de creation
	@GetMapping("/createArtist")
	public String artistForm() {
		return "form/artistForm";
	}

	// affiche tous les artistes
	@GetMapping("/artist")
	public String showAllArtist(Model model) {
		List<Artist> artists = artistService.findAll();
		model.addAttribute("artists", artists);
		return "indexOfPages/artists";
	}

	// affiche un artist
	@GetMapping("/artist/{id}")
	public String showArtistPage(@PathVariable("id") String id, Model model) {
		Artist artist = artistService.findArtistById(id);
		if (artist.getType().equals("Group")) {
			List<Artist> members = artistService.findGroupMembers(artist.getId().toString());
			model.addAttribute("members", members);
		}
		model.addAttribute("artist", artist);
		return "pageElement/artist";
	}

	// cree un artist
	@PostMapping(path = "/artist", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createArtist(@RequestParam Map<String, String> body, @RequestParam("type") String type, Model model) {
		if (type.contentEquals("group")) {
			Group group = artistService.createGroup(body);
			model.addAttribute("artist", group);
			return "redirect:/changeMembers/" + group.getId();

		} else {
			Artist artist = artistService.createArtist(body);
			model.addAttribute("artist", artist);
			return "redirect:artist/" + artist.getId();
		}
	}

	// affiche le formulaire pour changer les membres d'un group
	@GetMapping("/changeMembers/{id}")
	public String changeMembersForm(@PathVariable("id") String id, Model model) {
		List<Artist> artists = artistService.findAll();
		System.out.println(artists);
		System.out.println(artists.size());
		if (artists != null && artists.size() > 1) {
			model.addAttribute("artists", artistService.findAll());
			model.addAttribute("group", artistService.findArtistById(id));
			return "form/changeMembersForm";
		} else
			return "redirect:/artist/" + id;
	}

	// change les membres d'un group
	@PostMapping(path = "/changeMembers/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String changeMembers(@PathVariable("id") String id, @RequestParam Map<String, String> body, Model model) {
		Group group = artistService.changeGroupMembers(body, id);
		model.addAttribute("artist", group);
		return "redirect:/artist/" + group.getId();
	}

	// supprime un artist
	@DeleteMapping(path = "/artist/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String deleteArtist(@PathVariable("id") String id, Model model) {
		artistService.deleteArtist(id);
		return "redirect:/artist";

	}

	// affiche le formulaire pour modifier un artist
	@GetMapping("/updateArtist/{id}")
	public String updateArtistForm(@PathVariable("id") String id, Model model) {
		Artist artist = artistService.findArtistById(id);
		model.addAttribute("artist",artist);
		return "form/updateArtistForm";
	}

	// modifie un artist
	@PutMapping(path = "/artist/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateArtist(@PathVariable("id") String id, @RequestParam Map<String, String> body) {
		artistService.updateArtist(id, body);
		return "redirect:/artist/" + id;
	}

	// affiche le formulaire pour changer de photo
	@GetMapping("/artist/{id}/picture")
	public String uploadForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("pictures", ImageUploadService.directoryFiles("src/main/resources/static/artist/" + id));
		model.addAttribute("artist", artistService.findArtistById(id));
		return "/upload/artistUpload";
	}

	// change la photo d'un artist
	@PostMapping(path = "/artistChangePicture", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String changePictureWithOld(@RequestParam("id") String id, @RequestParam("picture") String picture) {
		artistService.changeArtistPicture(id, picture);
		return "redirect:/artist/" + id;
	}

	// ajoutune photo a l'artist
	@PostMapping(path = "/artistPictureUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String upload(@RequestParam("id") String id, @RequestParam("image") MultipartFile image, Model model)
			throws IOException {
		artistService.artistPictureUpload(id, image);
		return "redirect:/artist/" + id + "/picture";
	}
}
