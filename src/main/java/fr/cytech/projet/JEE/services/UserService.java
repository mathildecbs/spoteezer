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
	
	public User findByName(String name) {
		User user =	userRepository.findByName(name);
		return user;
	}
	
	public User createUser(Map<String,String> userDTO) {
		User user = new User();
		UserService.changeAttributesUser(user, userDTO);
		return userRepository.save(user);
	}
	
	public User modifyUser(User user, Map<String,String> userDTO) {
		UserService.changeAttributesUser(user, userDTO);
		return userRepository.save(user);
	}

	public static void changeAttributesUser(User user, Map<String,String> userDTO) {
		if(userDTO.get("name")!=null) {
			user.setName(userDTO.get("name"));
		}
		if(userDTO.get("password")!=null) {
			user.setPassword(userDTO.get("password"));
		}
		if(userDTO.get("mail")!=null) {
			user.setMail(userDTO.get("mail"));
		}
		if(userDTO.get("postalCode")!=null) {
			user.setPostalCode(Integer.valueOf(userDTO.get("postalCode")));
		}
		if(userDTO.get("country")!=null) {
			user.setCountry(userDTO.get("country"));
		}
	}
}
