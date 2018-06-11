package Backend.user.controller;
 
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import Backend.user.model.User;
import Backend.user.service.UserService;
 
 
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;
    /**
     *
     * @return la liste de tous les users avec leurs attributs
     */
    @RequestMapping(method=RequestMethod.GET,value="/users")
    private List<User>getAllUsers() {
        return userService.getAllUsers();
 
    }

    /**
     *
     * @param id
     * @return l'utilisateur dont l'id est donner en parametre
     */
    @RequestMapping("/users/{id}")
    private User getUser(@PathVariable String id) {
        return userService.getUser(id);
 
    }
    /**
     * Ajoute un utilisateur passé en POST
     * @param user
     * @return true si ok
     */
    @RequestMapping(method=RequestMethod.POST,value="/users")
    public boolean addUser(@RequestBody User user)
    {
	 	if (userService.findByLogin(user.getLogin())==null) {
	 		userService.addUser(user);
	 		return true;
	 	}
        return false;
    }
    /**
     * update l'user
     * @param user
     * @param id
     */
    @RequestMapping(method=RequestMethod.PUT,value="/users/{id}")
    public void updateUser(@RequestBody User user,@PathVariable String id) {
        user.setId(Integer.valueOf(id));
        userService.updateUser(user);
    }
    /**
     * Delete l'user
     * @param id
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
 
    /**
     *
     * @param user
     * @return true ou false si on trouve le user
     */
    @RequestMapping(method=RequestMethod.POST,value="/users/login")
    public long isLoginIn(@RequestBody User user) {
        if (userService.isLoginIn(user)) {
        	return (userService.giveToken(user));
        }
        return -1;
        
    }
    /**
     * Logout l'user
     * @param login
     * @param token
     * @return true si l'user etait loggé et vient de se déconnecter
     */
    @RequestMapping(method=RequestMethod.GET,value="/users/logout/{login}/{token}")
	public boolean userLogOut(@PathVariable String login,@PathVariable long token) {
    	if (userService.isLoginIn(getUser(userService.findByLogin(login).getLogin()))) {
    		userService.removeQueue(getUser(userService.findByLogin(login).getLogin()));
    		return true;
    	}
    	return false;
    }

    //URL pour mettre dans la queue
    /**
     * Met l'utilisateur dans la queue
     * @param login
     * @param token
     * @return la place dans la queue
     */
    @RequestMapping(method=RequestMethod.GET, value="/users/queue/in/{login}/{token}")
    public int putUserInQueue(@PathVariable String login,@PathVariable long token) {
        return userService.putUserInQueue(login, token);
    }
    
    /**
     * Sort un user de la queue
     * @param login
     * @param token
     * @return -1 si il a bien été sortie de la queue
     */
    @RequestMapping(method=RequestMethod.GET, value="/users/queue/out/{login}/{token}")
    public int putUserOutQueue(@PathVariable String login,@PathVariable long token) {
        return userService.putUserOutQueue(login, token);
    }
/**
 * Verifie l'utilisateur en question
 * @param login
 * @param token
 * @return true si user ok
 */

  @RequestMapping(method=RequestMethod.GET, value="/users/{login}/{token}")
    public boolean verifyUser(@PathVariable String login,@PathVariable long token) {
        return userService.verifyUser(login, token);
    }
  /**
   * Verifie que l'utilisateur est un admin
   * @param login
   * @param token
   * @return true si admin
   */
    @RequestMapping(method=RequestMethod.GET, value="/users/admin/{login}/{token}")
    public boolean verifyAdmin(@PathVariable String login,@PathVariable long token) {
        return userService.verifyAdmin(login, token);
    }
    /**
     * Verifie regulièrement si la connection est viable
     * verifie si l'user est bon
     * verifie si le main est pas afk
     * verifie si  les users dans la queues sont pas AFK
     * @param login
     * @param token
     * @return -2 si pas de main/ ou main pas co -1 si user pas bon sinon la place dans la queue
     */
    @RequestMapping(method=RequestMethod.GET, value="/users/demande/{login}/{token}")
    public int askingConnection(@PathVariable String login,@PathVariable long token) {
        return userService.askingConnection(login, token);
    }
	/**
	 * Renvoie la place dans la queue sans faire un appel a une action
	 * @param login
	 * @param token
	 * @return place dans la queue
	 */
    @RequestMapping(method=RequestMethod.GET, value="/users/state/{login}/{token}")
    public int userState(@PathVariable String login,@PathVariable long token) {
        if(!userService.isMainUser())
        {
        	return -2;
        }
    	
    	return userService.userState(login, token);
    }
}





