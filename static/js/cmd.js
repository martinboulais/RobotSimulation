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
        window.location.href = 'index.html';
        localStorage.setItem('error', 'Vous n\'êtes pas connectés');
    }
    else {

        user.verify(isConnected());
    }

    //Timer for y in graphs
    let start = new Date().getTime();

    let c = $("canvas")[0].getContext("2d");

    let sceneController = new SceneController(c, user);
    sceneController.showTotalMap(sceneController.resizeCanvas);

    let remoteController = new RemoteController(sceneController, user);

    let queueController = new QueueController(user);
    queueController.refresh();


    let autoStats = false;
    $("#buttonReload").click(function() {
        autoStats = false;
        updatedata(user);
    });
    $("#buttonAutoReload").click(function() {
        autoStats = true;
    });

    let queueTempo = new Tempo(period, 500);

    let userVerifTempo = new Tempo(period, 200);


    let statsTempo = new Tempo(period, 1000);
    updatedata(user);

    setInterval(function(){
        sceneController.drawRobot();

        if(userVerifTempo.refresh())
        {
            user.verify(isConnected);
            if(!user.isMain()) {

                sceneController.refreshRobot();
            }
        }

        if(queueTempo.refresh())
           queueController.refresh();


        if(statsTempo.refresh() && autoStats)
            updatedata(user);

    }, period);
});

function isConnected(r) {
    if(r === false) {
        window.location.href = 'index.html';
        localStorage.setItem('error', 'Vous n\'êtes pas connectés');
    }
}

//Function updating the graphs
function updatedata(user)
{
    $.ajax({
        url: "robot/info/"+ user.getLogin() + "/" + user.getToken(),
        method: 'GET',
        success: function(result)
        {
            console.log(result);
            $("#h3_id1").html(result[0]);
            $("#h3_id2").html(result[1]);
            $("#h3_id3").html(result[2]);
            $("#h3_id4").html(result[3]);
        }
    });
}