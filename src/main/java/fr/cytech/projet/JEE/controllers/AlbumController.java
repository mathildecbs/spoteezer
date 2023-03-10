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

import fr.cytech.projet.JEE.modeles.Album;
import fr.cytech.projet.JEE.services.AlbumService;
import fr.cytech.projet.JEE.services.ArtistService;
import fr.cytech.projet.JEE.services.ImageUploadService;

@Controller("albumController")
public class AlbumController {
	@Autowired
 	AlbumService albumService;
	@Autowired
 	ArtistService artistService;
	
	@GetMapping("/")
	public String showAccueil() {
		return "accueil";
	}
	
	//affiche tous les albums
	@GetMapping("/albums")
	public String showAllAlbum(Model model) {
		List<Album> albums = albumService.findAll();
		model.addAttribute("albums", albums);
		return "indexOfPages/albums";
	}
	
	//affiche formulaire pour creer une album
	@GetMapping("/createAlbum")
	public String albumForm(Model model) {
		model.addAttribute("artists", artistService.findAll());
		return "form/albumForm";
	}

	//affiche un album
	@GetMapping("/album/{id}")
	public String showAlbumPage(@PathVariable("id") String id, Model model) {
		Album album = albumService.findAlbumById(id);
		model.addAttribute("album", album);
		return "pageElement/album";
	}

	//cree un album
	@PostMapping(path = "/album", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createAlbum(@RequestParam Map<String, String> body, Model model) {
		Album album = albumService.createAlbum(body);
		model.addAttribute("album", album);
		return "redirect:/album/" + album.getId();
	}
	
	//affiche le formulaire pour changer l'album de photo
	@GetMapping("/album/{id}/picture")
	public String uploadForm(@PathVariable("id") String id,Model model) {
		model.addAttribute("pictures",ImageUploadService.directoryFiles("src/main/resources/static/album/"+id));
		model.addAttribute("album", albumService.findAlbumById(id));
		return "/upload/albumUpload";
	}
	
	//change la photo d'un album
	@PostMapping(path="/albumChangePicture", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String changePictureWithOld(@RequestParam("id") String id, @RequestParam("picture") String picture) {
		albumService.changeAlbumPicture(id, picture);
		return "redirect:/album/"+id;
	}
	
	//ajout une photo a l'album
	@PostMapping(path="/albumPictureUpload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
	public String upload(@RequestParam("id") String id,@RequestParam("image") MultipartFile image,Model model) throws IOException {
		albumService.albumPictureUpload(id, image);
		return "redirect:/album/"+id+"/picture";
	}
	
	//affiche le formulaire de mise a jour d'un album
	@GetMapping("/updateAlbum/{id}")
	public String updateAlbumForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("artists", artistService.findAll());
		Album album = albumService.findAlbumById(id);
		model.addAttribute("album",album);
		return "form/updateAlbumForm";
	}

	//met a jour un album
	@PutMapping(path = "/album/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateAlbum(@PathVariable("id") String id, @RequestParam Map<String, String> body) {
		albumService.updateAlbum(id, body);
		return "redirect:/album/" + id;
	}
	
	//supprime un album
	@DeleteMapping(path = "/album/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String deleteAlbum(@PathVariable("id") String id, Model model) {
		albumService.deleteAlbum(id);
		return "redirect:/albums";
	}
}
