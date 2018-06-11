package Backend.robot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        	System.out.println("US");
            String adminResourceUrl = DIALOG_BEJAVA+"/robot/environnementActuel";
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(adminResourceUrl, String[][].class);

        }
        catch(Exception e) {
            System.out.println("Erreur : "+e);
        }
        System.out.println("USR"+result);
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

        System.out.println("US");
        switch(direction)
        {
            case "up":
                String adminResourceUrlUp=DIALOG_BEJAVA+"/robot/up";
                RestTemplate restTemplateUp = new RestTemplate();
                Boolean okUp = restTemplateUp.getForObject(adminResourceUrlUp, Boolean.class);
                System.out.println("USUP");
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
                System.out.println("USDOWN");
                if (okDown) {
                    String adminResourceUrl=DIALOG_BEJAVA+"/robot/etat";
                    RestTemplate restTemplate = new RestTemplate();
                    result = restTemplate.getForObject(adminResourceUrl, List.class);
                    System.out.println("USDOW if retour"+ result);
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

            default :
                System.out.println("Direction non lue");
        }

        System.out.println("USR"+result);
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
}
