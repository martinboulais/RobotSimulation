package Simulation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//controller gérant la methode REST des url
@RestController
public class SimulationRestController {

	@Autowired //lie automatiquement simulationService avec la classe SimulationService
	private SimulationService simulationService;
    private Integer x=25;
    private Integer y=25;
	
	/**
	 * Méthode pour allumer la simulation quand l'utilisateur entre des paramètres
	 * @param x
	 * @param y
	 * @param nbObst
	 * @param nbSpeedBump
	 * @return
	 */
	 @RequestMapping(method=RequestMethod.GET,value="/simulation/on1/{x}/{y}/{nbObstacles}/{nbSpeedBumps}")
	 private boolean simulationStart1(@PathVariable Integer x,
	    		@PathVariable Integer y,@PathVariable Integer nbObstacles, @PathVariable Integer nbSpeedBumps) {
		 boolean retour =false;
		 retour= simulationService.simulationStart(x, y, nbObstacles, nbSpeedBumps);
		 return retour;
	 }
	 
	 /**
	  * Méthode pour allumer la simulation si l'utilisateur ne rentre pas de paramètre et utilise donc la simulation par défaut
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/simulation/on2")
	 private boolean simulationStart() {
		 boolean retour;
		 retour=simulationService.simulationStart();
		 return retour;
	 }
	 
	 /**
	  * Méthode pour éteindre la simulation
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/simulation/off")
	 private boolean simulationEnd() {
		 boolean retour=false;
		 retour=simulationService.simulationEnd();
		 return retour;
	 }
	 
	 /**
	  * Méthode pour récupérer l'environnement découvert du robot
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/environnementRobot")
	 private String [][] getMatrixKnownEnvironment() {
		 String[][] retour=new String[x][y];
		 System.out.println("SC");
		 retour= simulationService.getMatrixKnownEnvironment();
		 return retour;
	 }
	 
	 /**
	  * Méthode pour récupérer l'environnement total
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/environnementTotal")
	 private String[][] getMatrixEnvironment() {
		 String[][] retour=new String[x][y];
		 retour= simulationService.getMatrixEnvironment();
		 return retour;
	 }
	 
	 /**
	  * Méthode pour récupérer l'environnement vu par le robot à l'instant t
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/environnementActuel")
	 private boolean getMatrixViewRobot() {
		 return simulationService.getMatrixViewRobot();
	 }
	 
	 /**
	  * Méthode qui récupère les coordonnées du robot
	  * @return coord robot
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/etat")
	 private List<Integer> getCoordRobot() {
		 List<Integer> retour = new ArrayList<Integer>();
		 retour= simulationService.getCoordRobot();
		 return retour;
	 }
	 
	 /**
	  * Méthode qui fait un déplacement vers le haut si possible
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/up")
	 private boolean up() {
		 return simulationService.up();
	 }
	 
	 /**
	  * Méthode qui fait un déplacement vers le bas si possible
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.POST,value="/robot/down")
	 private boolean down() {
		 return simulationService.down();
	 }
	 
	 /**
	  * Méthode qui fait un déplacement vers la gauche si possible
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/left")
	 private boolean left() {
		 return simulationService.left();
	 }
	 
	 /**
	  * Méthode qui fait un déplacement vers la droite si possible
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/right")
	 private boolean right() {
		 return simulationService.right();
	 }
	 
	 /**
	  * Méthode qui donne des infos sur le robot: distance parcourue depuis le début du parcours,
	  *  nb de commande tapé, nombre d'obstacle rencontré, nombre d'obstacle vu à l'instant t
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="/robot/info")
	 private List getInfos() {
		 List<Integer> result = new ArrayList<Integer>();
		 result= simulationService.getInfos();
		 return result;
	 }
	 
	 
		

}
