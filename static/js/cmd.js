import {SceneController} from './controller/SceneController.js';
import {RemoteController} from './controller/RemoteController.js';
import {User} from './User.js';
import {Tempo} from './utilities/Tempo.js';
import {QueueController} from './controller/QueueController.js';

$(function() 
{
    let period = 25;

    //Verify if the user is connected
    let user = User.restore();

    if(user == null)
    {
        window.location.replace("index.html");
        localStorage.setItem('error', 'Vous n\'êtes pas connectés');
    }
    else {

        user.verify(isConnected());
    }

    let c = $("canvas")[0].getContext("2d");

    let sceneController = new SceneController(c);
    let remoteController = new RemoteController(sceneController);

    let queueController = new QueueController(user);
    queueController.refresh();

    let queueTempo = new Tempo(period, 500);

    let userVerifTempo = new Tempo(period, 200);

    setInterval(function(){
        sceneController.drawRobot();

        if(userVerifTempo)
            user.verify(isConnected());

        if(queueTempo.refresh())
           queueController.refresh()
    }, period);
});

function isConnected(r) {
    if(r === false) {
        window.location.replace("index.html");
        localStorage.setItem('error', 'Vous n\'êtes pas connectés');
    }
}