package fr.cytech.projet.JEE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{
	//Création d'une méthode pour récupérer l'utilisateur grace à son nom
	public User findByName(String name);
	
	//Création d'une méthode pour supprimer l'utilisateur grace à son id
	public void deleteById(long id);
}
