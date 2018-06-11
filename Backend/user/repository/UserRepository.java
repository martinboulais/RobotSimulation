package Backend.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import Backend.user.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	//Cette fonction appelée dans le service permet de récupérer le login et le password
	List<User> findByLoginAndPassword(String login, String password);	
	
	List<User> findByQueue(int queue);
	User findByLogin(String login);
	
	@Query(value="SELECT * FROM user_majeur e WHERE e.queue >= 0", nativeQuery = true)
	List<User> findQueue(); //renvoi la liste des utilisateurs qui sont dans la liste d'attente
	
	@Query(value="SELECT MAX(e.queue) FROM user_majeur e", nativeQuery = true)
	int findMaxQueue(); //renvoi la liste des utilisateurs qui sont dans la liste d'attente

	@Query(value="SELECT * FROM user_majeur e WHERE e.queue = 0", nativeQuery = true)
	User findMainUser(); //renvoi la liste des utilisateurs qui sont dans la liste d'attente
}
