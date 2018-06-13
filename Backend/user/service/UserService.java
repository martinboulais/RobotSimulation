package Backend.user.service;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Backend.user.model.User;
import Backend.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private Hashtable<String, Long> loginToken = new Hashtable<String, Long>();
    private Hashtable<String, Calendar> loginLastRequestUser = new Hashtable<String, Calendar>();
    private Calendar dateLastResquest;
    private Integer x=25;
    private Integer y=25;
    
    public void setUpQueue() {
    	
    	User mainUser = this.userRepository.findMainUser();
    	if(mainUser != null)
    	{
    		this.loginLastRequestUser.put(mainUser.getLogin(), new GregorianCalendar());
    	}
    }

    /**
     *
     * @return liste users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
    /**
     * Cherche l'utilisateur pas son login dans la base de donnée
     * @param login
     * @return User
     */
    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }
    /**
     * Cherche l'utilisateur par son id
     * @param id
     * @return User
     */
    public User getUser(String id) {
        return userRepository.findOne(Integer.valueOf(id));
    }
    /**
     * Ajoute un utilisateur dans la base de donnée
     * @param user
     */
    public void addUser(User user) {
        userRepository.save(user);
    }
    /**
     * Met a jour l'utilisateur actuel dans la base de donnée
     * @param user
     */
    public void updateUser(User user) {
        userRepository.save(user);

    }
    /**
     * Supprime l'utilisateur avec son id dans la base de donnée
     * @param id
     */
    public void deleteUser(String id) {
        userRepository.delete(Integer.valueOf(id));
    }
    /**
     * Update la liste d'attente en enlevant 1 a toute les personnes etant dans la liste d'attente
     */
    public void updateQueue() {
        List<User> listqueue=userRepository.findQueue();
        for (User u:listqueue) {
            u.setQueue(u.getQueue()-1);        // enlever 1 a tout les queue de cette liste
            updateUser(u);//update la liste en la revoyant dans update user
            
            if(loginLastRequestUser.get(u.getLogin()) == null)
            {
        		this.loginLastRequestUser.put(u.getLogin(), new GregorianCalendar());
            }
        }
        
    }
    /**
     * Ajout un utilisateur dans la queue
     * @param user
     */
    public void addQueue(User user) { //ajoute un user dans la queue
        user.setQueue(userRepository.findMaxQueue()+1);
        userRepository.save(user);
    }
    /**
     * Supprime un utilisateur dans la queue
     * @param user
     */
    public void removeQueue(User user) {
    	for (User u : userRepository.findAll()) {
    		if (u.getQueue()>user.getQueue()) {
    			u.setQueue(u.getQueue()-1);
    			userRepository.save(u);
    		}
    	}
    	user.setQueue(-1);
    	userRepository.save(user);
    }
    /**
     * Savoir si l'utilisateur est l'utilisateur principal
     * @param user
     * @return vrai si principal
     */
    public boolean isUserMain(User user){
        return user.getQueue()==0; //return true si l'user est le principal
    }
    /**
     * Savoir si l'utilisateur est l'utilisateur admin
     * @param user
     * @return vrai si admin
     */
    public boolean isUserAdmin(User user) {
        return user.getAdmin(); //Return true si l'user est admin
    }
    /**
     * Savoir si l'utilisateur est l'utilisateur log
     * @param user
     * @return vrai si log
     */
    public boolean isLoginIn (User user) {
        return !userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()).isEmpty();
    }

    /**
     * Permet de donner un token à l'utilisateur qui s'est connecté
     * @param user
     * @return token ou 0 si pas connecté
     */
    public long giveToken(User user) {
    	List<User> user2=userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        user= user2.get(0);
    	Integer id=user.getId();
        long token;
        long retour = 0;
    
        //Si user est connecté
        if (isLoginIn(user))
        {
            Double randomnb=(Math.random()*10000);
            token=randomnb.intValue();
            loginToken.put(user.getLogin(), token);
            retour = token;

        }
        return retour;
    
    }
/**
 * verifie que l'utilisateur existe dans la BD
 * @param login
 * @param token
 * @return true si existe
 */
    public boolean verifyUser(String login, long token) {
        boolean retour = false;
        //On regarde si le login existe
        if (loginToken.containsKey(login)) {
            //Puis on vérikie que le token correspond bien à l'id
            if (token==loginToken.get(login)) {
            	
                retour = true;
            }
            
        }
        
        return retour;
    }
    /**
     * Verifie si l'user est admin 
     * @param login
     * @param token
     * @return true si admin
     */
    public boolean verifyAdmin(String login, long token) {
    	if (verifyUser(login, token)) {
    	User user = userRepository.findByLogin(login);
    	return this.isUserAdmin(user);
    	}
    	return false;
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
    public int askingConnection(String login, long token) {
    	if (verifyUser(login, token)) {
			Calendar currentDate= new GregorianCalendar();
			loginLastRequestUser.put(login, currentDate);
			
			if(!this.isMainUser())
			{
				return -2;
			}
    		
			afkMainUser();
    		verifyQueue();
    		return userRepository.findByLogin(login).getQueue();
    	}
    	return -1;

    }
    
    public boolean isMainUser() {
    	
    	//return true si pas de main user
		if (userRepository.findMainUser() == null) {
			return false;
		}

		return true;
    }
    
    /**
     * Si l'utilisateur a fait une action ajoute la date de son action dans la liste
     * @param login
     * @param token
     */
    public void userAction(String login, long token) {
    	Calendar currentDate= new GregorianCalendar();
    	loginLastRequestUser.put(login, currentDate);
    }
    /**
     * Verifie que le main user a fait une action il y a moins de 30sec
     * @return
     */
	public boolean afkMainUser(){
		boolean ret=false;
		if (userRepository.findMainUser().getLogin()!=null && loginLastRequestUser.get((userRepository.findMainUser().getLogin()))!=null) 
		{
			loginLastRequestUser.get((userRepository.findMainUser().getLogin())).add(Calendar.SECOND, 30);
			Calendar currentDate= new GregorianCalendar();
			
			if (currentDate.compareTo(loginLastRequestUser.get(userRepository.findMainUser().getLogin()))>0) {
				updateQueue();
				ret=true;
			}
			else {
				loginLastRequestUser.get((userRepository.findMainUser().getLogin())).add(Calendar.SECOND, -30);
			}
		}
    	return ret;
    }
	/**
	 * Verifie que les users dans la queue sont pas afk
	 * @return
	 */
	public boolean verifyQueue() {
		boolean ret=false;
		List<String> listRemove = new ArrayList<String>();
		for (String login : loginLastRequestUser.keySet()) {
			loginLastRequestUser.get(login).add(Calendar.SECOND, 30);
			Calendar currentDate= new GregorianCalendar();

			if (currentDate.compareTo(loginLastRequestUser.get(login))>0) { //Si plus de co depuis 30sec	    		
				listRemove.add(login);
	    		ret=true;
	    	}
			else {
			loginLastRequestUser.get(login).add(Calendar.SECOND, -30);
			}
		}
		if (ret==true){
			for (String login :listRemove) {
				loginLastRequestUser.remove(login);
	    		removeQueue(findByLogin(login));
			}
		}
		return ret;
	}
	/**
	 * Ajoute un utilisateur dans la queue
	 * @param login
	 * @param token
	 * @return numero dans la queue
	 */
	public int putUserInQueue(String login, long token) {
		addQueue(userRepository.findByLogin(login));
		return userRepository.findByLogin(login).getQueue();
	}
	/**
	 * Enleve un user de la queue
	 * @param login
	 * @param token
	 * @return -1 si l'user est parti de la queue
	 */
	public int putUserOutQueue(String login, long token) {
		removeQueue(userRepository.findByLogin(login));
		return userRepository.findByLogin(login).getQueue();
	}
	/**
	 * Renvoie la place dans la queue sans faire un appel a une action
	 * @param login
	 * @param token
	 * @return place dans la queue
	 */
	public int userState(String login, long token) {
		return findByLogin(login).getQueue();
	}
}




