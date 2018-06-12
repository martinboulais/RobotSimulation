package Simulation.controller;

 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Simulation.model.Coord;
import Simulation.model.simulation.Simulation;
import Simulation.model.simulation.autoNavigation.AutoNav;


//classe contenant les methode utilisées par le webservice
@Service
public class SimulationService {
	
  // URL pour le microservice
   
    private boolean simulationEnCours=false;
    private boolean autoNavEnCours=false;
    private Simulation simulation;
    private AutoNav autoNav;
    private Integer x=25;
    private Integer y=25;
    /*--------------------------------------------------------------------------------------------------*/
    // Méthodes  JAVA - BE
    /*--------------------------------------------------------------------------------------------------*/
    
    /**
     * 
     * Ces deux fonctions permettent de changer la valeur de x et y quand l'environnement à une taille choisie
     *  par l'utilisateur
     */
    public Integer setX(int x) {
    	this.x=x;
    	return x;
    }
    
    public Integer setY(int y) {
    	this.y=y;
    	return y;
    }
    /**
     * 
     * @return true si la simulation est lance
     */
    public boolean simulationStart (int x, int y, int nbObstacles, int nbBumpUp) {
		boolean retour = false;    
		if (!simulationEnCours) {			   		
    		this.simulation = new Simulation (x,y,nbObstacles,nbBumpUp);    
    		simulationEnCours=true;		
    		retour=true;

		}
		return retour;
	
    }
    
    /**
     * 
     * @return true si la simulation est lance
     */
    public boolean simulationStart () {
		boolean retour = false; 
		if (!simulationEnCours) {
			
    		this.simulation = new Simulation ();
    		simulationEnCours=true;		    	
    		retour=true;
		}								
		return retour;
    }
    
    /**
     * Permet d'arreter la simulation
     * retourne true si la simulation est arretée
     * @return
     */
    public boolean simulationEnd () {
    	boolean retour = false;

		if (simulationEnCours) {			
    		simulationEnCours=false;	
    		// Permet d'annuler l'instance qui sera supprimé par le ramasse miette
    		this.simulation=null;
    		retour=true;
		}					
		return retour;
    }
    
    
    /**
     * Renvoi la matrice correspondant à l'environnement du robot grâce à la méthode getMatrixKnownEnvironment() contenu dans Simulation
     * * @return un string correspondant à l'environnement découvert par le robot
     */
    public String[][] getMatrixKnownEnvironment(){
    	String[][] envoi=new String[x][y];
		if (simulationEnCours) {
	
			envoi=this.simulation.getMatrixKnownEnvironment();
		}
		return envoi;   	
    }
       

    /**
     * Renvoi la matrice correspondant à l'environnement total grâce à la méthode getMatrixEnvironment() contenu dans Simulation
     * * @return un string correspondant à l'environnement total
     */
    public String[][] getMatrixEnvironment(){

    	String[][] envoi=new String[x][y];   
		if (simulationEnCours) {
			envoi=this.simulation.getMatrixEnvironment();
		}			   	
		return envoi;
    	
    }
    
    /**
     * Renvoi la matrice correspondant à l'environnement que voit à l'instant t le robot
     *  grâce à la méthode getMatrixViewRobot() contenu dans Simulation
     * * @return un string correspondant à l'environnement vu à l'instant t par le robot
     */
    public  String[][] getMatrixViewRobot(){
     	String[][] envoi=new String[x][y];   
     	System.out.println("SS");
		if (simulationEnCours) {
			envoi=simulation.getMatrixViewRobot();
			System.out.println("SSR "+envoi);
		}
				
		return envoi;
    	
    }
      
    /**
     * 
     * Renvoi les coordonnées du robot grâce à la fonction getCoord() de Simulation
     * @return une liste d'entier correspondant aux coordonnées
     */
    public List<Integer> getCoordRobot(){
    	List<Integer> envoi = new ArrayList<Integer>();
		if (simulationEnCours) {
			envoi=this.simulation.getCoordRobot();    			
		}	
		return envoi; 	
    }
    
    /**
     * Envoi une liste de string qui contient des infos sur le robot
     * Utilisation des fonction getDistanceTraveled(),getNbCmd(),getNbSeeingObst(),getNbMetObst()
     * @return une liste d'entier composé de la distance parcourue, du nombre de commande effectué, 
     * du nombre obstacle vu à l'instant t et du nombre d'obstacle rencontré
     */
    public List<Integer> getInfos(){
    	List<Integer> envoi = new ArrayList<Integer>();  	
		if (simulationEnCours) {
			envoi.add(simulation.getDistanceTraveled());
			envoi.add(simulation.getNbCmd());
			envoi.add(simulation.getNbSeeingObst());
			envoi.add(simulation.getNbMetObst());
		}	   	
		return envoi; 	
    }
    
  
   /**
    * Envoi le résultat de la fonction down()
    * @return true si le déplacement à été effectué
    */
    public Boolean down() {
    	Boolean retour = false;
  	
		if (simulationEnCours) {
			retour=simulation.down();			
		}
		return retour; 	
   	
    }
    

    /**
     * Envoi le résultat de la fonction up()
     * @return true si le déplacement à été effectué
     */
    public Boolean up() {
    	Boolean retour = false;
		if (simulationEnCours) {
			retour=simulation.up(); 		
		}

		return retour; 	
    	
    }
    
    /**
     * Envoi le résultat de la fonction right()
     * @return true si le déplacement à été effectué
     */
    public Boolean right() {
    	Boolean retour = false;
		if (simulationEnCours) {
			retour=simulation.right();
		}
		return retour; 	
    	
    }
    
    /**
     * Envoie le résultat de l'appel à la fonction left()
     * @return true si le déplacement à été effectué
     */
    public Boolean left() {
    	Boolean retour = false;

    	if (simulationEnCours) {
			retour=simulation.left();
		}		
		return retour; 	   		
    }

	public boolean autoNavOn() {
		boolean retour = false;    
		if (!autoNavEnCours) {	
			System.out.println("SS");
    		this.autoNav = new AutoNav (this.simulation);    
    		autoNavEnCours=true;
    		System.out.println("SSR"+retour);
    		retour=true;
		}
		return retour;
		
	}

	public boolean autoNavOff() {
		boolean retour = false;

		if (autoNavEnCours) {			
			autoNavEnCours=false;	
    		// Permet d'annuler l'instance qui sera supprimé par le ramasse miette
    		this.autoNav=null;
    		retour=true;
		}					
		return retour;
	}

	public List<Coord> dumpNav() {
		List<Coord> envoi = new ArrayList<Coord>();   
		if (autoNavEnCours) {
			envoi=this.autoNav.dumpNav();
		}
		return envoi;
	}

	public List<Coord> searchObst() {
		List<Coord> envoi = new ArrayList<Coord>();  
		if (autoNavEnCours) {
			envoi=this.autoNav.searchObst();
		}
		return envoi;
	}
    
    		
}
