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
        $("#forms").css("display", "inherit");
    } else {

        user.verify(function(retour) {

            if(retour === false) {
                $("#forms").css("display", "inherit");
                localStorage.removeItem('user');
            }
            else
            {
                $("#navLinks").css("display", "inherit");

                user.isAdmin(function(retour) {

                    if(retour === true)
                        $("#admin-link").css("display", "inherit");
                });
            }
        });
    }

    $("#i_submit").click(signUp);
    $("#c_submit").click(signIn);
    $("#deconnect").click(deconnect);

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
                window.location.replace('.');
            } else {

                let error = new Message('error', 'Identifiants incorrects').show();
            }
        }
    });
}

function deconnect() {

    localStorage.removeItem("user");
    window.location.replace('.');
}