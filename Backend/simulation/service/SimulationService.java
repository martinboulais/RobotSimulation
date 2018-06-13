package Backend.simulation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Backend.user.model.User;

@Service
public class SimulationService {

    // URL pour le microservice
    private static final String DIALOG_BEJAVA = "http://localhost:8081";

	/*--------------------------------------------------------------------------------------------------*/
    // Méthodes BackEnd - JAVA
    /*--------------------------------------------------------------------------------------------------*/

    /**
     * Permet de demander l'allumage d'une simulation standard (sans paramètres)
     * @param user
     * @return vrai si effectuée
     */
    public boolean startSimulation(User user) {
        boolean retour = false;
        Boolean answer =false;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/simulation/on2";
            RestTemplate restTemplate = new RestTemplate();
            answer = restTemplate.getForObject(adminResourceUrl, Boolean.class);
            retour = answer;
        }
        catch(Exception e) {
            System.out.println("retour"+e);

            retour = false;
        }

        return retour;
    }

    /**
     * Permet de demander l'allumage d'une simulation avec paramètres
     * @param user
     * @return vrai si effectuée
     */
    public boolean startSimulation(User user, int x, int y, int nbObstacles, int nbSpeedBumps) {
        boolean retour = false;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/simulation/on1/"+x+"/"+y+"/"+nbObstacles+"/"+nbSpeedBumps;
            List<Integer> parameters = new ArrayList<Integer>();

            RestTemplate restTemplate = new RestTemplate();

            Boolean answer = restTemplate.getForObject(adminResourceUrl,  Boolean.class);
            retour = answer;
        }
        catch(Exception e) {
            retour = false;
        }

        return retour;
    }

/**
 * Permet de demander l'extinction de la simulation
 * @param user
 * @return fichier json
 */
    public boolean endSimulation(User user) {
        boolean retour = false;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/simulation/off";
            RestTemplate restTemplate = new RestTemplate();
            Boolean answer = restTemplate.getForObject(adminResourceUrl, Boolean.class);
            retour = answer;
        }
        catch(Exception e) {
            retour = false;
        }

        return retour;

    }
}
