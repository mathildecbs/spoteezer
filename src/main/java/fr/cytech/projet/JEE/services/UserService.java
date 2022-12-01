package fr.cytech.projet.JEE.services;

import org.springframework.beans.factory.annotation.Autowired;

import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.repository.UserRepository;

public class UserService {
	@Autowired
 	UserRepository userRepository;
	
	public User findByName(String name, String password) {
		User user =	userRepository.findByName(name);
		if(password.equals(user.getPassword())) {
			return user;
		}else {
			return null;
		}
	}
	
}
