package fr.cytech.projet.JEE.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.Playlist;
import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.services.ImageUploadService;
import fr.cytech.projet.JEE.services.UserService;

@Controller("userController")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/dashboard")
	public String showDashboard(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			return "dashboard";
		}
		return "redirect:/login";
	}

	/* Logout */
	@GetMapping("/logout")
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}

	/* Connection */
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model,
			HttpSession session) {
		User user = userService.findByName(name);
		if (user == null || !user.getPassword().equals(password)) {
			model.addAttribute("error", "Le mot de passe/le pseudo est incorrect");
			return "login";
		}
		session.setAttribute("user", user);
		return "redirect:/";
	}

	/* Registration */
	@GetMapping("/registration")
	public String showRegistrationForm() {
		return "registrationForm";
	}

	@PostMapping(path = "/registration", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String registerUser(@RequestParam Map<String, String> body, Model model, HttpSession session) {
		User user = userService.createUser(body);
		session.setAttribute("user", user);
		return "redirect:/";
	}

	/* Profile */
	@GetMapping("/modifyProfile")
	public String showProfileForm(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("name", user.getName());
		model.addAttribute("password", user.getPassword());
		model.addAttribute("mail", user.getMail());
		model.addAttribute("postalCode", user.getPostalCode());
		model.addAttribute("country", user.getCountry());
		return "profileForm";
	}

	@PostMapping(path = "/updateProfile", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateProfile(@RequestParam Map<String, String> body, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		User modifiedUser = userService.modifyUser(user, body);
		session.setAttribute("user", modifiedUser);
		return "redirect:/modifyProfile";
	}

	@GetMapping("/deleteUser")
	public String deleteUser(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		userService.deleteUser(user);
		if (user == null) {
			model.addAttribute("suppr", "erreur lors de la suppression du profil");
			return "redirect:/modifyProfile";
		}
		session.removeAttribute("user");
		return "redirect:/logout";
	}

	@GetMapping("/profile")
	public String showProfile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Playlist> playlists = user.getPlaylists();
		Playlist favorite = user.getFavorite();
		return "profile";
	}

	@GetMapping("/profile/picture")
	public String uploadForm(Model model, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			model.addAttribute("pictures",
					ImageUploadService.directoryFiles("src/main/resources/static/user/" + user.getId()));
			return "/upload/userUpload";
		} catch (NullPointerException eo) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		}
	}

	@PostMapping(path = "/userChangePicture", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String changePictureWithOld(HttpSession session, @RequestParam("picture") String picture) {
		try {
			User user = (User) session.getAttribute("user");
			userService.changeUserPicture(user, picture);
			return "redirect:/profile";
		} catch (NullPointerException eo) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		}
	}

	@PostMapping(path = "/userPictureUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String testUpload(@RequestParam("image") MultipartFile image, Model model, HttpSession session)
			throws IOException {
		try {
			User user = (User) session.getAttribute("user");
			userService.userPictureUpload(user, image);
			return "redirect:/profile/picture";
		} catch (NullPointerException eo) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		}
	}

}
