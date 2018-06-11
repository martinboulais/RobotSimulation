import {SceneController} from './SceneController.js';
import {RemoteView} from '../view/RemoteView.js';
import * as dir from '../model/directions.js';

class RemoteController {
    
    constructor(sceneController, user) {
        
        this.sceneController = sceneController;
        
        this.remoteView = new RemoteView(this);

        this.move = this.move.bind(this);


    }
    
    move(direction)
    {
        //Suposing we have te position of the robot from ajax request
        switch(direction)
        {
            case dir.NORTH:
                this.robotY -= 1;
                break;
                
            case dir.SOUTH:
                this.robotY += 1;
                break;
                
            case dir.EAST:
                this.robotX += 1;
                break;
            
            case dir.WEST:
                this.robotX -= 1;
                break;
        }
        
            this.sceneController.moveRobot(this.robotX, this.robotY);
    }
}

export {RemoteController};