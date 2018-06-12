package Backend.robot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Backend.robot.model.Coord;
import Backend.robot.service.RobotService;
import Backend.simulation.service.SimulationService;
import Backend.user.service.UserService;

@RestController
public class RobotRestController {
	
    private Integer x=25;
    private Integer y=25;

    @Autowired
    private UserService userService;

	@Autowired
    private RobotService robotService;
	
	/**
     * Move the Robot at the new position
     * @param user
     * @param direction
     * @return coord of the new position
     */
    @RequestMapping(method=RequestMethod.GET,value="/robot/deplacement/{login}/{token}/{direction}")
    public List<Integer> moveRobot(@PathVariable String login, @PathVariable long token,@PathVariable String direction) {
        List<Integer> newCoord = new ArrayList<Integer>();

            //Verify user's token / user is main
        if (userService.verifyUser(login,token) && userService.isUserMain(userService.findByLogin(login)))
        {
        	System.out.println("UC");
            newCoord = robotService.moveRobot(userService.findByLogin(login), direction);
        }

        return newCoord;
    }

    /**
     * Get the Environment seen by the Robot in String (JSON)
     * @param user
     * @return String for the JSON Map
     */
    @RequestMapping(method=RequestMethod.GET,value="/robot/environnementRobot/{login}/{token}")
    public String[][] getEnvtRobot(@PathVariable String login, @PathVariable long token) {
    	String[][] envtRobot=new String[x][y];

        //Verify user'11688s token / user is main
        if (userService.verifyUser(login,token) && userService.isUserMain(userService.findByLogin(login)))
        {
            envtRobot = robotService.getEnvtRobot(userService.findByLogin(login));
        }
        return envtRobot;
    }

    /**
     * Get the Global Environment in String (JSON)
     * @param user
     * @return String for the JSON Map
     */
    @RequestMapping(method=RequestMethod.GET,value="/robot/environnementTotal/{login}/{token}")
    public String[][] getEnvtGlobal(@PathVariable String login, @PathVariable long token) {
    	String[][] envtGlobal=new String[x][y];
		//Verify user's token / user is main
        if (userService.verifyUser(login,token))
        {
            envtGlobal = robotService.getEnvtGlobal(userService.findByLogin(login));
        }

        return envtGlobal;
    }

    /**
     * Get the Global Environment in String (JSON)
     * @param user
     * @return String for the JSON Map
     */
    @RequestMapping(method=RequestMethod.GET,value="/robot/environnementActuel/{login}/{token}")
    public String[][] getEnvtActual(@PathVariable String login, @PathVariable long token) {
    	String[][] envtActual=new String[x][y];

        //Verify user's token / user is main
        if (userService.verifyUser(login,token))
        {
            envtActual = robotService.getEnvtActual(userService.findByLogin(login));

        }

        return envtActual;
    }

    /**
     * Get the Coord of the Robot
     * @param user
     * @return String for the Coord
     */
    @RequestMapping(method=RequestMethod.GET,value="/robot/etat/{login}/{token}")
    public List<Integer> getStateRobot(@PathVariable String login, @PathVariable long token) {
    	List<Integer> actualCoord = new ArrayList<Integer>();

        //Verify user's token / user is main
        if (userService.verifyUser(login,token))
        {
            actualCoord = robotService.getStateRobot(userService.findByLogin(login));
        }

        return actualCoord;
    }

    /**
     * Get the Data
     * @param user
     * @return List of Integers with the Data
     */
    @RequestMapping(method=RequestMethod.GET,value="/robot/info/{login}/{token}")
    public List<Integer> getData(@PathVariable String login, @PathVariable long token) {
        List<Integer> data = new ArrayList<Integer>();

        //Verify user's token / user is main
       if (userService.verifyUser(login,token))
        {

            data = robotService.getData(userService.findByLogin(login));
        }
        return data;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/robot/autoNavOn/{login}/{token}")
    public boolean autoNavOn(@PathVariable String login, @PathVariable long token) {
        boolean data = false;
       
        //Verify user's token / user is main
       if (userService.verifyUser(login,token) && userService.isUserMain(userService.findByLogin(login)))
        {
    		System.out.println("UC");
            data = robotService.autoNavOn(userService.findByLogin(login));
            System.out.println("UCR"+data);
        }
        return data;
    }
    
    
    @RequestMapping(method=RequestMethod.GET,value="/robot/autoNavOff/{login}/{token}")
    public boolean autoNavOff(@PathVariable String login, @PathVariable long token) {
    	boolean data = false;
       
        //Verify user's token / user is main
       if (userService.verifyUser(login,token) && userService.isUserMain(userService.findByLogin(login)))
        {
    		
            data = robotService.autoNavOff(userService.findByLogin(login));
        }
        return data;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/robot/dumpNav/{login}/{token}")
    public List<Coord> dumpNav(@PathVariable String login, @PathVariable long token) {
        List<Coord> data = new ArrayList<Coord>();
       
        //Verify user's token / user is main
       if (userService.verifyUser(login,token) && userService.isUserMain(userService.findByLogin(login)))
        {
    		
            data = robotService.dumpNav(userService.findByLogin(login));
        }
        return data;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/robot/searchObst/{login}/{token}")
    public List<Coord> searchObst(@PathVariable String login, @PathVariable long token) {
        List<Coord> data = new ArrayList<Coord>();
       
        //Verify user's token / user is main
       if (userService.verifyUser(login,token) && userService.isUserMain(userService.findByLogin(login)))
        {
    		
            data = robotService.searchObst(userService.findByLogin(login));
        }
        return data;
    }
}
