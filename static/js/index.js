import {User} from './User.js';
import {Message} from './utilities/Message.js';

$(function () {

	//Does messages exists?
    let message = localStorage.getItem('error');
	if(message !== undefined || message !== null)
    {
        new Message('error', message);
    }

    //Verify if the user is connected
    let user = User.restore();

	if(user == null)
	{
		$("#navLinks").hide(); //Do not show navLinks
	} else {
		user.verify(function(retour) {
			if(retour === false) {
				localStorage.removeItem('user');
                $("#navLinks").hide(); //Do not show navLinks
			}
			else
			{
				$("#down").hide(); //Do not show forms
				$("#navLinks").show(); //Show navLinks

				user.isAdmin(function(retour) {
					if(retour === false)
						$("#admin-link").hide(); //Do not show Admin link if not Admin
				});
			}
		});
	}

    $("#i_submit").click(signUp);
    $("#c_submit").click(signIn);

    $("#buttonDisconnect").click(deconnect);
});
    

function signUp(e)
{
    e.preventDefault();

    let login = $("#i_login").val();
    let pass = $("#i_pass").val();
    let pass_confirm = $("#i_pass_confirm").val();

    if(pass !== pass_confirm)
    {
        new Message('error', 'Le mot de passe et sa confirmation ne correspondent pas').show();
    }

    else
    {
        signUpRequest(login, pass);
    }
}

function signUpRequest(login, pass)
{
    $.ajax({
        url: "users",
        data: JSON.stringify({login:String(login),password:String(pass)}),
        contentType: "application/json",
        method: 'POST',
        success: function(result)
        {
            if(result === true) {

                new Message('info', 'Inscription réussie').show();
                signInRequest(login, pass);

            } else {

                new Message('error', 'Ce login est déjà pris').show();
            }
        }
    });
}


function signIn(e)
{
    e.preventDefault();

    let login = $("#c_login").val();
    let pass = $("#c_pass").val();

    signInRequest(login, pass);
}

function signInRequest(login, pass)
{
    $.ajax({
        url: "users/login",
        data: JSON.stringify({login:String(login),password:String(pass)}),
        contentType: "application/json",
        method: 'POST',
        success: function(result)
        {
            if(result !== 0) {

                let user = new User(login, result);
                localStorage.setItem('user', JSON.stringify(user));
                window.location.href = 'index.html';
            } else {

                let error = new Message('error', 'Identifiants incorrects').show();
            }
        }
    });
}

function deconnect()
{
    localStorage.removeItem("user");
    window.location.href = 'index.html';
}

//Function Puting User in Queue
function putUserInQueue(login, token){
	$.ajax({
        url: "users/queue/in/"+login+"/"+token,
        data: JSON.stringify({login:String(login),password:String(pass)}),
        contentType: "application/json",
        method: 'GET',
        success: function(result)
        {
        	if (answer === true){
				//TODO	Queue Management, See with Antoine
			} else {
				let error = new Message('error', 'User is not registered').show();
			}
        }
    });
}

