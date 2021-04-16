function valid(){
    var password = document.getElementById("pass1").value;
    var password2 = document.getElementById("pass2").value;

    var valid = true;

    if(password!=password2){
        valid=false;
        document.getElementById("p_pass").innerHTML = "passwords don't match";
    }else{
        document.getElementById("p_pass").innerHTML = "";
    }

    if(password.length<8||password2.length<8){
        valid = false;
        document.getElementById("p_pass").innerHTML = "min 8 characters";
    }else{
        document.getElementById("p_pass").innerHTML = "";
    }


    if(valid){
        reset();
    }
}

function reset(){

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    let passwordDTO = {
        "newPass" : document.getElementById("pass1").value,
        "token" : document.getElementById("token").value
    }

    var json = JSON.stringify(passwordDTO)

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/resetPassword', true);
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function (e) {
       if (xhr.readyState === 4) {
           if (xhr.status === 200) {
                   alert("Password has been reset");
                   window.location.replace("/login")


                } else {
                 /*   var err = JSON.parse(xhr.responseText);
                    console.log(xhr.responseText)*/
                }
              }
           };
        xhr.send(json);
}