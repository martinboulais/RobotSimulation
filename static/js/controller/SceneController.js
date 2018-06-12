import {MapView} from '../view/MapView.js';
import {RobotView} from '../view/RobotView.js';

export const TOTAL_MAP = 0;
export const ROBOT_MAP = 1;
export const CURRENT_MAP = 2;

class SceneController {

    constructor(canvas, user) {

        this.canvas = canvas;
        this.user = user;
    	
        this.mapView = new MapView(canvas, '..\/img\/environnement.png');
    
        this.robotView = new RobotView(
            canvas,                      //Canvas context
            '..\/img\/robot.png',   //Sprite src
            64,                     //Sprite width
            64,                     //Sprite height
            12,                     //Number of frames for the animation
            0,                      //X coord of the case
            0,                      //Y coord of the case
            1,                      //Scale of the sprite
            3                       //Speed of the robot
        );

        $.ajax({
            url: "/robot/etat/" + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.placeRobot.bind(this)
        });

        this.map = TOTAL_MAP;

        $("#robotViewTab").click(this.showRobotMap.bind(this));
        $("#totalViewTab").click(this.showTotalMap.bind(this));
        $("#currentViewTab").click(this.showCurrentMap.bind(this));
        //$("#autoViewTab").click(this.autoNavigationOn.bind(this));

        this.showTotalMap = this.showTotalMap.bind(this);
        this.showRobotMap = this.showRobotMap.bind(this);
        this.setMap = this.setMap.bind(this);


        this.drawRobot = this.drawRobot.bind(this);
        this.moveRobot = this.moveRobot.bind(this);
        this.placeRobot = this.placeRobot.bind(this);
        this.refreshRobot = this.refreshRobot.bind(this);
        this.compareRobotPos = this.compareRobotPos.bind(this);

        this.resizeCanvas = this.resizeCanvas.bind(this);

        this.showTotalMap = this.showTotalMap.bind(this);
        this.showRobotMap = this.showRobotMap.bind(this);
        this.showCurrentMap = this.showCurrentMap.bind(this);
        this.refreshMapView = this.refreshMapView.bind(this);
    }

    resizeCanvas() {

        this.canvas.canvas.width = this.mapView.getWidth();
        this.canvas.canvas.height = this.mapView.getHeight();
    }

    showTotalMap() {

        this.map = TOTAL_MAP;

        $.ajax({
            url: "/robot/environnementTotal/" + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.setMap
        });
    }

    showRobotMap() {

        this.map = ROBOT_MAP;

        $.ajax({
            url: "/robot/environnementRobot/" + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.setMap
        });
    }

    showCurrentMap() {

        this.map = CURRENT_MAP;

        $.ajax({
            url: "/robot/environnementActuel/" + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.setMap
        });
    }
    
    // autoNavigationOn() {
    //
    // 	$.ajax({
    // 		url: "/robot/autoNavOn/" + this.user.getLogin() + "/" + this.user.getToken(),
    //         method: 'GET',
    //         success: this.getAutoNavCoords
    // 	})
    // }
    // getAutoNavCoords() {
    // 	$.ajax({
    // 		url: "/robot/dumpNav/" + this.user.getLogin() + "/" + this.user.getToken(),
    //         method: 'GET',
    //         success: this.launchAutoNav
    // 	})
    // }
    //
    // launchAutoNav(coords) {
    // 	this.robot.clearSteps();
    // 	for(let i = 0; i < coords.length; i++) {
    //
    // 	    this.robotView.goTo(coords[i].y, coords[i].x)
    //     }
    // }

    setMap(data) {
        if(this.mapView.load(data))
        {
            this.resizeCanvas();
        }
    }

    drawRobot() {
        
        this.mapView.rerenderall();
        this.robotView.draw();
    }
    
    moveRobot(x, y)
    {
        this.robotView.goTo(x,y);
        this.refreshMapView();
    }

    refreshMapView() {
        switch(this.map) {
            case ROBOT_MAP:
                this.showRobotMap();
                break;
            case CURRENT_MAP:
                this.showCurrentMap();
                break;
        }
    }

    placeRobot(coords)
    {
        this.robotView.setPos(coords[1], coords[0]);
    }

    refreshRobot() {
        $.ajax({
            url: "/robot/etat/" + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.compareRobotPos
        });
    }

    compareRobotPos(coords)
    {
        let coordsRobot = this.robotView.getNextPosition();

        if(coordsRobot.x !== coords[1] || coordsRobot.y !== coords[0])
        {
            this.refreshMapView();
            this.robotView.goTo(coords[1], coords[0]);
        }
    }
}

export {SceneController};