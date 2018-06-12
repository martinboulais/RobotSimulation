import {Message} from "./utilities/Message.js";

class User{

    constructor(login, token) {

        this.login = login;
        this.token = token;

        this.verify = this.verify.bind(this);
        this.isAdmin = this.isAdmin.bind(this);
        this.perfMain = this.perfMain.bind(this);

        this.getLogin = this.getLogin.bind(this);
        this.getToken = this.getToken.bind(this);

        this.setMain = this.setMain.bind(this);
        this.isMain = this.isMain.bind(this);

        this.main = false;
    }
    
    static restore() {

        let data = localStorage.getItem('user');
        data = JSON.parse(data);

        if(data === undefined || data === null) {

            return null;
        } else if(data.login === undefined || data.token === undefined) {

            return null;
        }

        return new User(data.login, data.token);
    }
    
    verify(callback) {
        $.ajax({
            url: "users/" + this.getLogin() + "/" + this.getToken(),
            method: 'GET',
            success: callback
        });
    }
    
    isAdmin(callback) {
        //Ajax : is the user administrator?
        $.ajax({
            url: "users/admin/" + this.getLogin() + "/" + this.getToken(),
            method: 'GET',
            success: callback
        });
    }

    perfMain(callback, param) {
        $.ajax({
            url: "/users/action/" + this.getLogin() + "/" + this.getToken(),
            method: 'GET',
            success: function(r) {
                if(r === 0)
                {
                    callback(param);
                }
            }
        });
    }

    setMain(val) {
        this.main = val;
    }
    isMain() {
        return this.main;
    }

    getLogin() {

        return this.login;
    }

    getToken() {

        return this.token;
    }
}

export {User};