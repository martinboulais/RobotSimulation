package Simulation.model.simulation;

import java.util.List;

import Simulation.model.Coord;
import Simulation.model.simulation.autoNavigation.AutoNav;

public class Launcher {

	public static void main(String[] args) {
			Simulation sim = new Simulation(7, 7, 30,0);
			AutoNav autoNav = new AutoNav(sim);
			//List<Coord> listMove = autoNav.dumpNav();
			
			List<Coord> listMove = autoNav.searchObst();
			sim.affEnvironnement();
			sim.affView();
			sim.affRobotDiscoveredMap();

	}

}
