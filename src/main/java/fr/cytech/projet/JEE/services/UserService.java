package fr.cytech.projet.JEE.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.cytech.projet.JEE.modeles.User;
import fr.cytech.projet.JEE.repository.PlaylistRepository;
import fr.cytech.projet.JEE.repository.UserRepository;

@Service("userService")
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	PlaylistRepository playlistRepository;

	// trouve un user par son nom
	public User findByName(String name) {
		User user = userRepository.findByName(name);
		if (user != null)
			return user;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	}

	// creer un user
	public User createUser(Map<String, String> userDTO) {
		try {
			User user = new User();
			UserService.changeAttributesUser(user, userDTO);
			user.setPicture("user.png");
			User uSaved = userRepository.save(user);
			return uSaved;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User creation failed");
		}
	}

	// modifier un user
	public User modifyUser(User user, Map<String, String> userDTO) {

		UserService.changeAttributesUser(user, userDTO);
		return userRepository.save(user);

	}

	// ajoute les attributs a un user
	public static void changeAttributesUser(User user, Map<String, String> userDTO) {
		if (userDTO.get("name") != null && !userDTO.get("name").contentEquals("")) {
			user.setName(userDTO.get("name"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect name value");

		}
		if (userDTO.get("password") != null && !userDTO.get("password").contentEquals("")) {
			user.setPassword(userDTO.get("password"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect password value");

		}
		if (userDTO.get("mail") != null && !userDTO.get("mail").contentEquals("")) {
			user.setMail(userDTO.get("mail"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect mail value");

		}
		if (userDTO.get("postalCode") != null) {
			user.setPostalCode(Integer.valueOf(userDTO.get("postalCode")));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect postalCode value");

		}
		if (userDTO.get("country") != null && !userDTO.get("country").contentEquals("")) {
			user.setCountry(userDTO.get("country"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorect country value");

		}
	}

	// supprime un user
	public void deleteUser(User user) {
		userRepository.deleteById(user.getId());
		User u = findByName(user.getName());
		if (u != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User delete failed");

	}

	// ajout une photo au user
	public void userPictureUpload(User user, MultipartFile mpF) throws IOException {
		String fileName = StringUtils.cleanPath(mpF.getOriginalFilename());
		ImageUploadService.saveFile("src/main/resources/static/user/" + user.getId(), fileName, mpF);
	}

	// modifie la photo d'un user
	public User changeUserPicture(User user, String pictureName) {
		user.setPicture(pictureName);
		return userRepository.save(user);
	}
}
