export const ROBOT_FREE = 0;        //The robot is able
export const ROBOT_OCCUPED = 1;     //Another user is currently using the robot but the user doesn't wait
export const ROBOT_CONTROLLED = 2;  //The current user is using the robot
export const ROBOT_WAITING = 3;     //The current user is waiting for the robot

class QueueView {

    constructor(queueController) {

        this.queueController = queueController;

        this.joinButton = $("#queueJoinButton");
        this.leaveButton = $("#queueLeaveButton");
        this.text = $("#queueText");

        this.lockedIcon = $("#queueIcon-locked");
        this.runingIcon = $("#queueIcon-runing");
        this.freeIcon = $("#queueIcon-free");

        this.remoteDispMask = $("#remoteMask");

        this.joinButton.click(this.join.bind(this));

        this.leaveButton.click(this.leave.bind(this));

        this.changeState=this.changeState.bind(this);
        this.refresh=this.refresh.bind(this);
        
        this.join=this.join.bind(this);
        this.leave=this.leave.bind(this);
    }

    refresh(retour) {

        switch(retour) {

            case -2:
                this.changeState(ROBOT_FREE);
                break;
            case -1:
                this.changeState(ROBOT_OCCUPED);
                break;
            case 0:
                this.changeState(ROBOT_CONTROLLED);
                break;
            default:
                this.changeState(ROBOT_WAITING);

        }
    }

    join() {

        this.queueController.join();
    }

    leave() {

        this.queueController.leave();
    }

    changeState(state) {

        switch(state) {

            case ROBOT_FREE:
                this.lockedIcon.hide();
                this.runingIcon.hide();
                this.freeIcon.show();

                this.joinButton.show();
                this.leaveButton.hide();

                this.remoteDispMask.show();
                break;


            case ROBOT_OCCUPED:
                this.lockedIcon.show();
                this.runingIcon.hide();
                this.freeIcon.hide();

                this.joinButton.show();
                this.leaveButton.hide();

                this.remoteDispMask.show();
                break;


            case ROBOT_CONTROLLED:
                this.lockedIcon.hide();
                this.runingIcon.show();
                this.freeIcon.hide();

                this.joinButton.hide();
                this.leaveButton.show();

                this.remoteDispMask.hide();
                break;


            case ROBOT_WAITING:
                this.lockedIcon.show();
                this.runingIcon.hide();
                this.freeIcon.hide();

                this.joinButton.hide();
                this.leaveButton.show();

                this.remoteDispMask.show();
        }
    }
}

export {QueueView};