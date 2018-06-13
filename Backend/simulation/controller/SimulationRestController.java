package Backend.simulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Backend.simulation.service.SimulationService;
import Backend.user.service.UserService;

@RestController
public class SimulationRestController {

	@Autowired
    private UserService userService;

	@Autowired
    private SimulationService simulationService;
	
    private Integer x=25;
    private Integer y=25;

    /**
     * Start the Simulation without parameters
     * @param
     * @return boolean to verify the good execution
     */
    @RequestMapping(method=RequestMethod.GET,value="/simulation/on/{login}/{token}")
    public boolean startSimulation(@PathVariable String login, @PathVariable long token) {
        boolean ret = false;

        //Verify user's token/ user is admin
        if (userService.verifyUser(login,token) && userService.isUserAdmin(userService.findByLogin(login)))
        {
        	ret = simulationService.startSimulation(userService.findByLogin(login));
        }
        return ret;
    }
    
    /**
     * Start the Simulation with specific parameters
     * @param
     * @return boolean to verify the good execution
     */
    @RequestMapping(method=RequestMethod.GET,value="/simulation/on/{login}/{token}/{x}/{y}/{nbObstacle}/{nbSpeedBumps}")
    public boolean startSimulation(@PathVariable String login,@PathVariable long token,@PathVariable Integer x,
    		@PathVariable Integer y,@PathVariable Integer nbObstacle, @PathVariable Integer nbSpeedBumps) {

    	boolean ret = false;
        //Verify user's token/ user is admin
        if (userService.verifyUser(login,token) && userService.isUserAdmin(userService.findByLogin(login)))
        {
            ret = simulationService.startSimulation(userService.findByLogin(login), x, y, nbObstacle, nbSpeedBumps);
        }
        return ret;
    }
    

    /**
     * End the Simulation
     * @param user
     * @return boolean to verify the good execution
     */
    @RequestMapping(method=RequestMethod.GET,value="/simulation/off/{login}/{token}")
    public boolean endSimulation(@PathVariable String login,@PathVariable long token) {
        boolean ret = false;

        //Verify user's token/ user is admin
        if (userService.verifyUser(login,token) && userService.isUserAdmin(userService.findByLogin(login)))
        {
            ret = simulationService.endSimulation(userService.findByLogin(login));
        }
		return ret;
    }
}
