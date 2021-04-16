function validation(){

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var valid = true;

    var nameRegex = /[a-zA-Z0-9_]{6,}/
    if(username.length<6 && !nameRegex.test(name)){
        valid = false;
        document.getElementById("p_username").innerHTML =
        "Only letters, numbers, underscore and min 6 characters"
    }else{
        document.getElementById("p_username").innerHTML = "";
    }

    if(password.length<8){
        valid = false;
        document.getElementById("p_password").innerHTML = "min 8 characters";
    }else{
        document.getElementById("p_password").innerHTML = "";
    }


    if(valid){
        login();
    }
}

function login(){

    var loginForm = new FormData();

    loginForm.append("username" , document.getElementById("username").value);
    loginForm.append("password", document.getElementById("password").value);

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/login' , true)
    xhr.setRequestHeader(header, token)
    xhr.onload = function (e) {
      if (xhr.readyState === 4) {
           if (xhr.status === 200) {
            document.getElementById("info_login").innerHTML="";
                window.location.replace(xhr.responseURL)
           } else {
                var err = JSON.parse(xhr.responseText);
                document.getElementById("info_login").innerHTML=err.message;
           }
       }
    };
    xhr.send(loginForm);
}

function showFields(){

    document.getElementById("forgotten_pass").remove();

    var body = document.getElementById("body");
    var div = document.createElement("div");

    var input = document.createElement("input");
    input.id = "email";
    input.setAttribute("type" , "email");
    input.placeholder = "Email";

    div.appendChild(input);

    var button = document.createElement("BUTTON");
    button.type = "button";
    button.innerHTML = "Reset"
    button.id = "reset";
    button.setAttribute("onclick", "validEmail()");

    div.appendChild(button);

    var parDiv = document.createElement("div");

    var par = document.createElement("p");
    par.id = "p_email"

    parDiv.appendChild(par);

    div.appendChild(parDiv);

    body.appendChild(div);

}

function validEmail(){

    var email = document.getElementById("email").value;

    var valid = true;

    var emailRegex = /\S+@\S+\.\S+/;
    if(!emailRegex.test(email)){
        valid = false;
        document.getElementById("p_email").innerHTML = "email not valid"
    }else{
        document.getElementById("p_email").innerHTML = ""
    }

    if(valid){
        resetPassword();
    }
}

function resetPassword(){
    var email = document.getElementById("email").value;

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/requestPasswordReset?email=' +email , true)
    xhr.setRequestHeader(header, token);
    xhr.onload = function (e) {
      if (xhr.readyState === 4) {
           if (xhr.status === 200) {
                 alert("Email with reset link has been sent")
                 window.location.replace("/")
           } else {
                var err = JSON.parse(xhr.responseText);
                alert(err.message);
           }
       }
    };
    xhr.send();
}