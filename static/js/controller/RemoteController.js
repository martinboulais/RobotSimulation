import {SceneController} from './SceneController.js';
import {RemoteView} from '../view/RemoteView.js';
import * as dir from '../model/directions.js';
import {correspondances} from "../model/directions.js";

class RemoteController {
    
    constructor(sceneController, user) {
        
        this.sceneController = sceneController;

        this.user = user;
        
        this.remoteView = new RemoteView(this);

        this.move = this.move.bind(this);
        this.moveRequest = this.moveRequest.bind(this);
        this.refreshScene = this.refreshScene.bind(this);
    }

    move(direction) {
        this.user.perfMain(this.moveRequest, direction);
    }

    moveRequest(direction)
    {
        this.user.setMain(true);
        $.ajax({
            url: "/robot/deplacement/" + this.user.getLogin() + "/" + this.user.getToken() + "/" + correspondances[direction-1],
            method: 'GET',
            success: this.refreshScene
        });
    }

    refreshScene(coords) {
        if(coords[0] !== -1 && coords[1] !== -1) {

            this.sceneController.moveRobot(coords[1], coords[0]);
        }
    }
}

export {RemoteController};