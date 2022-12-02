package fr.cytech.projet.JEE.controllers;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("errorController")
public class CustomErrorController implements ErrorController {
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String exception = (String) request.getAttribute("javax.servlet.error.message");
		Enumeration<String> e = request.getAttributeNames();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		System.out.println(request.getAttribute("javax.servlet.error.message"));
		model.addAttribute("statusCode", statusCode);
		model.addAttribute("exception", exception);
		return "error";
	}

	public String getErrorPath() {
		return "/error";
	}
}
