function validation(){


    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var password2 = document.getElementById("password2").value;

    var valid = true;

    if(password!=password2){
        valid=false;
        document.getElementById("p_password2").innerHTML = "passwords don't match";
    }else{
        document.getElementById("p_password2").innerHTML = "";
    }


    var nameRegex = /[a-zA-Z0-9_]{6,}/
    if(username.length<6 && !nameRegex.test(name)){
        valid = false;
        document.getElementById("p_username").innerHTML =
        "Only letters, numbers, underscore and min 6 characters"
    }else{
        document.getElementById("p_username").innerHTML = "";
    }

    var emailRegex = /\S+@\S+\.\S+/;
    if(!emailRegex.test(email)){
        valid = false;
        document.getElementById("p_email").innerHTML = "email not valid"
    }else{
        document.getElementById("p_email").innerHTML = ""
    }

    if(password.length<8){
        valid = false;
        document.getElementById("p_password").innerHTML = "min 8 characters";
    }else{
        document.getElementById("p_password").innerHTML = "";
    }

    if(password2.length<8){
            valid = false;
            document.getElementById("p_password").innerHTML = "min 8 characters";
        }else{
            document.getElementById("p_password").innerHTML = "";
        }

    if(valid){
        register();
    }
}

function register(){
    let object = {
        "username" : document.getElementById("username").value,
        "email": document.getElementById("email").value,
        "password": document.getElementById("password").value
    };
    let json = JSON.stringify(object);

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/register' , true)
    xhr.setRequestHeader(header, token)
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8')
    xhr.onload = function (e) {
      if (xhr.readyState === 4) {
           if (xhr.status === 200) {
                alert("Registration successful! Email with activation link was sent.")
                window.location.replace(window.location.origin + "/login")
           } else {
                var err = JSON.parse(xhr.responseText);
                alert(err.message)
           }
       }
    };
    xhr.send(json);
}