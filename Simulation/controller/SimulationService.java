package Simulation.controller;

 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Simulation.model.simulation.Simulation;


//classe contenant les methode utilisées par le webservice
@Service
public class SimulationService {
	
  // URL pour le microservice
   
    private boolean simulationEnCours=false;
    private Simulation simulation;
    private Integer x=25;
    private Integer y=25;
    /*--------------------------------------------------------------------------------------------------*/
    // Méthodes  JAVA - BE
    /*--------------------------------------------------------------------------------------------------*/
    
    /**
     * 
     * @return true si la simulation est lance
     */
    public boolean simulationStart (int x, int y, int nbObstacles, int nbBumpUp) {
		boolean retour = false;
    	try 
		{
    		if (!simulationEnCours) {
    			   		
	    		this.simulation = new Simulation (x,y,nbObstacles,nbBumpUp);    		
	    		simulationEnCours=true;		
	    		retour=true;

			}
   								
		}
		catch(Exception e) {
			retour = false;
		}
    	
		return retour;
	
    }
    
    /**
     * 
     * @return true si la simulation est lance
     */
    public boolean simulationStart () {
		boolean retour = false;
    	try 
		{
    		if (!simulationEnCours) {
    			
	    		this.simulation = new Simulation ();
	    		simulationEnCours=true;		    	
	    		retour=true;
    		}								
		}
		catch(Exception e) {
			retour = false;
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
    	try 
		{
    		if (simulationEnCours) {
    			
	    		simulationEnCours=false;	
	    		// Permet d'annuler l'instance qui sera supprimé par le ramasse miette
	    		this.simulation=null;
	    		retour=true;
			}					
		}
    	
		catch(Exception e) {
			retour = false;
		}
    	
		return retour;
    }
    
    
    /**
     * Renvoi la matrice correspondant à l'environnement du robot grâce à la méthode getMatrixKnownEnvironment() contenu dans Simulation
     * * @return un string correspondant à l'environnement découvert par le robot
     */
    public String[][] getMatrixKnownEnvironment(){
    	String[][] envoi=new String[x][y];
    	try 
		{
    		if (simulationEnCours) {
    			System.out.println("SS");
    			envoi=this.simulation.getMatrixKnownEnvironment();
			}
			
		}
		catch(Exception e) {
			envoi = null ;
		}
  
		return envoi;
    	
    }
       

    /**
     * Renvoi la matrice correspondant à l'environnement total grâce à la méthode getMatrixEnvironment() contenu dans Simulation
     * * @return un string correspondant à l'environnement total
     */
    public String[][] getMatrixEnvironment(){
    	String[][] envoi=new String[x][y];
 
    	try 
		{
    		if (simulationEnCours) {
    			envoi=this.simulation.getMatrixEnvironment();
			}
			
		}
		catch(Exception e) {
			envoi = null ;
		}
    	
		return envoi;
    	
    }
    
    /**
     * Renvoi la matrice correspondant à l'environnement que voit à l'instant t le robot
     *  grâce à la méthode getMatrixViewRobot() contenu dans Simulation
     * * @return un string correspondant à l'environnement vu à l'instant t par le robot
     */
    public Boolean getMatrixViewRobot(){
     	String[][] envoi=new String[x][y];
    	Boolean retour=false;
    	try 
		{
    		if (simulationEnCours) {
    			envoi=simulation.getMatrixViewRobot();
			}
			
		}
		catch(Exception e) {
			envoi = null ;
		}
    	
		
		return retour;
    	
    }
      
    /**
     * 
     * Renvoi les coordonnées du robot grâce à la fonction getCoord() de Simulation
     * @return une liste d'entier correspondant aux coordonnées
     */
    public List<Integer> getCoordRobot(){
    	List<Integer> envoi = new ArrayList<Integer>();
    	Boolean retour = false;
  
    	try 
		{
    		if (simulationEnCours) {
    			envoi=this.simulation.getCoordRobot();    			
			}	
		}
		catch(Exception e) {
			envoi = null ;
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
    	try 
		{
    		if (simulationEnCours) {
    			envoi.add(simulation.getDistanceTraveled());
    			envoi.add(simulation.getNbCmd());
    			envoi.add(simulation.getNbSeeingObst());
				envoi.add(simulation.getNbMetObst());
			}	
		}
		catch(Exception e) {
			envoi = null ;
		}
    	
		return envoi; 	
    }
    
  
   /**
    * Envoi le résultat de la fonction down()
    * @return true si le déplacement à été effectué
    */
    public Boolean down() {
    	Boolean retour = false;
    	try 
		{
    		if (simulationEnCours) {
    			retour=simulation.down();
    			
			}
			
		}
		catch(Exception e) {
			retour = false ;
		}
    	
		
		return retour; 	
    	
    }
    

    /**
     * Envoi le résultat de la fonction up()
     * @return true si le déplacement à été effectué
     */
    public Boolean up() {
    	Boolean retour = false;
    	try 
		{
    		if (simulationEnCours) {
    			retour=simulation.up();
    			
			}
			
		}
		catch(Exception e) {
			retour = false ;
		}
    	
		return retour; 	
    	
    }
    

    /**
     * Envoi le résultat de la fonction right()
     * @return true si le déplacement à été effectué
     */
    public Boolean right() {
    	Boolean retour = false;
    	try 
		{
    		if (simulationEnCours) {
    			retour=simulation.right();

			}
			
		}
		catch(Exception e) {
			retour = false ;
		}
    	
		
		return retour; 	
    	
    }
    

    /**
     * Envoie le résultat de l'appel à la fonction left()
     * @return true si le déplacement à été effectué
     */
    public Boolean left() {
    	Boolean retour = false;
    	try 
		{
    		if (simulationEnCours) {
    			retour=simulation.left();
    	
			}
			
		}
		catch(Exception e) {
			retour = false ;
		}
    	
		
		return retour; 	   		
    }
    
    
		
}
