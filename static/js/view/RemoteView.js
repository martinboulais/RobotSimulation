import {RemoteController} from '../controller/RemoteController.js';
import * as dir from '../model/directions.js';

class RemoteView {
    
    constructor(remoteController) {
        this.remoteController = remoteController;
        
        $('#upArrow').click(this.moveUp.bind(this));
        $('#downArrow').click(this.moveDown.bind(this));
        $('#leftArrow').click(this.moveLeft.bind(this));
        $('#rightArrow').click(this.moveRight.bind(this));
        
        $(document).keyup($.proxy(this.bindKey, this));

        this.bindKey = this.bindKey.bind(this);
        this.moveUp = this.moveUp.bind(this);
        this.moveDown = this.moveDown.bind(this);
        this.moveLeft = this.moveLeft.bind(this);
        this.moveRight = this.moveRight.bind(this);
    }
    
    bindKey(e)
    {
        switch(e.which) {
                
            case 37:
                this.moveLeft();
                break;
                
            case 38:
                this.moveUp();
                break;
                
            case 39:
                this.moveRight();
                break;
                
            case 40:
                this.moveDown();
                break;
        }
    }
    
    moveUp()
    {
        this.remoteController.move(dir.NORTH);
    }
    moveDown()
    {
        this.remoteController.move(dir.SOUTH);
    }
    moveLeft()
    {
        this.remoteController.move(dir.WEST);
    }
    moveRight()
    {
        this.remoteController.move(dir.EAST);
    }
}

export {RemoteView};