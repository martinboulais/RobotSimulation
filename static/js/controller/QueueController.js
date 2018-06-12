import {QueueView} from "../view/QueueView.js";

class QueueController {

    constructor(user) {

        this.user = user;

        this.queueView = new QueueView(this);

        this.join = this.join.bind(this);
        this.leave = this.leave.bind(this);

        this.refresh = this.refresh.bind(this);
        this.refreshTreatment = this.refreshTreatment.bind(this);

        this.actif = false;
    }

    //Waiting queue

    /**
     * Ask to the server to take the hand on the robot
     */
    join()
    {
        $.ajax({
            url: "/users/queue/in/" + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.refreshTreatment
        });
    }

    leave()
    {
        $.ajax({
            url: "/users/queue/out/" + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.refreshTreatment
        });
    }

    refresh()
    {
        $.ajax({
            url: "/users/" + ((this.actif) ? "demande/" : 'state/') + this.user.getLogin() + "/" + this.user.getToken(),
            method: 'GET',
            success: this.refreshTreatment
        });
    }

    refreshTreatment(r) {
        this.actif = r > 0;
        this.user.setMain(r === 0);
        this.queueView.refresh(r);
    }
}

export {QueueController};