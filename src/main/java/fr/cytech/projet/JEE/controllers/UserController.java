package fr.cytech.projet.JEE.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.services.UserService;

@Controller("userController")
public class UserController {
	
	@Autowired
 	UserService userService;
	
	@GetMapping("/logout")
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:login";
	}
	
	@GetMapping("/dashboard")
	public String showDashboard(Model model,HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(user!=null) {
			return "dashboard";
		}
		return "redirect:login";
	}
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(
			@RequestParam("name") String name, 
			@RequestParam("password") String password,
			Model model,
			HttpSession session) {
		User user = userService.findByName(name,password);
		if(user==null) {
			model.addAttribute("error", "Le mot de passe/le pseudo est incorrect");
			return "login";
		}
		session.setAttribute("user", user);
		return "redirect:test";
	}
	
	@GetMapping("/registration")
	public String showRegistrationForm() {
		return "registrationForm";
	}
	
	@PostMapping(path = "/registration", 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String registerUser(
			@RequestParam Map<String,String> body, 
			Model model,
			HttpSession session) {
		User user = userService.createUser(body);
		session.setAttribute("user", user);
		return "redirect:test";
		
	}
	
	
}
