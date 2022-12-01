package fr.cytech.projet.JEE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.projet.JEE.modeles.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{
	public User findByName(String name);
}
