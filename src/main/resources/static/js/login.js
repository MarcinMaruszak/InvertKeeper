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
    /*let object = {
        "username" : document.getElementById("username").value,
        "password": document.getElementById("password").value
    };
    let json = JSON.stringify(object);*/

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
                console.log(xhr.responseText)
                window.location.replace(xhr.responseURL)
           } else {
                console.log(e)
                var err = JSON.parse(xhr.responseText);
                alert(err.message)
           }
       }
    };
    xhr.send(loginForm);
}