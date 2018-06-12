import {User} from './User.js';
import {Message} from './utilities/Message.js';

$(function () {

	let user =  User.restore();


    if(user == null)
    {
        window.location.href = 'index.html';
        localStorage.setItem('error', 'Vous n\'êtes pas connectés');
    }
    // Verify is the user is Admin - If not, return to Index Page
    else {
    	user.isAdmin(function(retour) {
            if(retour === false)
            {
                localStorage.setItem('error', 'Vous devez être administrateur');
                window.location.href = 'index.html';
            }
        });
    }

	//Does messages exists?
    let message = localStorage.getItem('error');
	if(message !== undefined || message !== null)
    {
        new Message('error', message).show();
        localStorage.removeItem('error');
    }
	
	// Verify is the user is Admin - If not, return to Index Page 
	user.isAdmin(function(retour) {
		if(retour === false)
		{
        	localStorage.setItem('error', 'Vous devez être administrateur');
			window.location.href = 'index.html';
		}
	});



//Fonction Starting Simulation Without Parameters
    $("#simpleStart").click(function() {

        $.ajax({
            url: "/simulation/on/" + user.getLogin() + "/" + user.getToken(),
            method: 'GET',
            success: function () {
                $("#imgAdminStop").show();
                $("#imgAdminRun").hide();
            }
        });
    });

//Fonction Starting Simulation With Parameters
    $("#start").click(function() {

        let x = $("#x").val();
        let nbObstacles = $("#nbObstacles").val();
        let nbSpeedBumps = $("#nbSpeedBumps").val();

    	$.ajax({
			url: "/simulation/on/" + user.getLogin() + "/" + user.getToken() + "/" + x + "/" + x + "/" + nbObstacles + "/" + nbSpeedBumps,
            method: 'GET',
            success: function (r) {
                $("#imgAdminStop").show();
                $("#imgAdminRun").hide();
            }
    	});
	});

//Function Ending Simulation
    $("#stop").click( function(){

        $.ajax({
            url: "/simulation/off/" + user.getLogin() + "/" + user.getToken(),
            method: 'GET',
            success: function () {
                $("#imgAdminStop").hide();
                $("#imgAdminRun").show();
            }
        });
    });
});