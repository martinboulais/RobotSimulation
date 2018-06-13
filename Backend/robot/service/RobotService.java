package Backend.robot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Backend.robot.model.Coord;
import Backend.user.model.User;

@Service
public class RobotService {

    // URL pour le microservice
    private static final String DIALOG_BEJAVA = "http://localhost:8081";

    private Integer x=25;
    private Integer y=25;

    /**
     * Permet de demander l'environnement du robot
     * @param user
     * @return fichier json
     */
    public String[][] getEnvtRobot(User user) {
    	String[][] result=new String[x][y];
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/environnementRobot";
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(adminResourceUrl, String[][].class);

        }
        catch(Exception e) {
            System.out.println("Erreur : "+e);
        }
        return result;

    }

    /**
     * Permet de demander l'environnement global
     * @param user
     * @return fichier json
     */
    public String[][] getEnvtGlobal(User user) {
    	String[][] result=new String[x][y];
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/environnementTotal";
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(adminResourceUrl, String[][].class);

        }
        catch(Exception e) {
            System.out.println("Erreur : "+e);
        }

        return result;
    }

    /**
     * Ask for the environment seen by the robot at the actual moment
     * @param user
     * @return the map in a String
     */
    public String[][] getEnvtActual(User user) {
    	String[][] result=new String[x][y];
        try
        {
            String adminResourceUrl = DIALOG_BEJAVA+"/robot/environnementActuel";
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(adminResourceUrl, String[][].class);

        }
        catch(Exception e) {
            System.out.println("Erreur : "+e);
        }
        return result;
    }

    /**
     * Permet de demander l'etat du robot
     * @param user
     * @return
     */
    public List<Integer> getStateRobot(User user) {
        List<Integer> result = new ArrayList<Integer>();
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/etat";
            RestTemplate restTemplate = new RestTemplate();
            List answer = restTemplate.getForObject(adminResourceUrl, List.class);
            result = answer;
        }
        catch(Exception e)
        {
            System.out.println("Erreur : "+e);

        }

        return result;
    }

    /**
     * Demande au robot de se déplacer
     * @param user
     * @param direction
     * @return un json avec le deplacement du robot
     */

    public List<Integer> moveRobot(User user, String direction) {
        List<Integer> result = new ArrayList<Integer>();

        switch(direction)
        {
            case "up":
                String adminResourceUrlUp=DIALOG_BEJAVA+"/robot/up";
                RestTemplate restTemplateUp = new RestTemplate();
                Boolean okUp = restTemplateUp.getForObject(adminResourceUrlUp, Boolean.class);

                if (okUp) {
                    String adminResourceUrl=DIALOG_BEJAVA+"/robot/etat";
                    RestTemplate restTemplate = new RestTemplate();
                    result = restTemplate.getForObject(adminResourceUrl, List.class);
                }
                else
                {
                    result.add(-1);
                    result.add(-1);
                }
                break;

            case "down":
                String adminResourceUrlDown=DIALOG_BEJAVA+"/robot/down";
                RestTemplate restTemplateDown = new RestTemplate();
                Boolean okDown = restTemplateDown.getForObject(adminResourceUrlDown, Boolean.class);

                if (okDown) {
                    String adminResourceUrl=DIALOG_BEJAVA+"/robot/etat";
                    RestTemplate restTemplate = new RestTemplate();
                    result = restTemplate.getForObject(adminResourceUrl, List.class);

                }
                else
                {
                    result.add(-1);
                    result.add(-1);
                }
                break;

            case "left":
                String adminResourceUrlLeft=DIALOG_BEJAVA+"/robot/left";
                RestTemplate restTemplateLeft = new RestTemplate();
                Boolean okLeft = restTemplateLeft.getForObject(adminResourceUrlLeft, Boolean.class);

                if (okLeft) {
                    String adminResourceUrl=DIALOG_BEJAVA+"/robot/etat";
                    RestTemplate restTemplate = new RestTemplate();
                    result = restTemplate.getForObject(adminResourceUrl, List.class);
                }
                else
                {
                    result.add(-1);
                    result.add(-1);
                }
                break;

            case "right":
                String adminResourceUrlRight=DIALOG_BEJAVA+"/robot/right";
                RestTemplate restTemplateRight = new RestTemplate();
                Boolean okRight = restTemplateRight.getForObject(adminResourceUrlRight, Boolean.class);

                if (okRight) {
                    String adminResourceUrl=DIALOG_BEJAVA+"/robot/etat";
                    RestTemplate restTemplate = new RestTemplate();
                    result = restTemplate.getForObject(adminResourceUrl, List.class);
                }
                else
                {
                    result.add(-1);
                    result.add(-1);
                }
                break;
        }


        return result;
    }

    /**
     * Ask for the Data of the exploration
     * @param user
     * @return the Data
     */
    public List<Integer> getData(User user) {
        List<Integer> result = new ArrayList<Integer>();
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/info";
            RestTemplate restTemplate = new RestTemplate();
            List answer = restTemplate.getForObject(adminResourceUrl, List.class);
            result = answer;
        }
        catch(Exception e)
        {
            System.out.println("Erreur : "+e);

        }
        return result;
    }
    
	public List<Coord> dumpNav(User user) {
		List<Coord> result = new ArrayList<Coord>();
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/dumpNav";
            RestTemplate restTemplate = new RestTemplate();
            List answer = restTemplate.getForObject(adminResourceUrl, List.class);
            result = answer;
        }
        catch(Exception e)
        {
            System.out.println("Erreur : "+e);

        }
        return result;
	
	}

	public List<Coord> searchObst(User user) {
		List<Coord> result = new ArrayList<Coord>();
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/searchObst";
            RestTemplate restTemplate = new RestTemplate();
            List answer = restTemplate.getForObject(adminResourceUrl, List.class);
            result = answer;
        }
        catch(Exception e)
        {
            System.out.println("Erreur : "+e);

        }
        return result;
	}

	public boolean autoNavOn(User user) {
		boolean retour=false;
		try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/autoNavON";
            RestTemplate restTemplate = new RestTemplate();
            retour = restTemplate.getForObject(adminResourceUrl, Boolean.class);
        }
        catch(Exception e) {
            System.out.println("retour"+e);

            retour = false;
        }
		return retour;
	}

	public boolean autoNavOff(User user) {
		boolean retour=false;
		try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/autoNavOff";
            RestTemplate restTemplate = new RestTemplate();
            retour = restTemplate.getForObject(adminResourceUrl, Boolean.class);
        }
            
        catch(Exception e) {
            System.out.println("retour"+e);

            retour = false;
        }
		return retour;
        
	}	
}
