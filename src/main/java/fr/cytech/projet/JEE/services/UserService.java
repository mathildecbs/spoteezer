package fr.cytech.projet.JEE.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.repository.UserRepository;

@Service("userService")
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
	
	public User createUser(Map<String,String> userDTO) {
		User user = new User();
		user.setName(userDTO.get("name"));
		user.setPassword(userDTO.get("password"));
		user.setMail(userDTO.get("mail"));
		user.setPostalCode(Integer.valueOf(userDTO.get("postalCode")));
		user.setCountry(userDTO.get("country"));
		return userRepository.save(user);
		
	}
	
}
