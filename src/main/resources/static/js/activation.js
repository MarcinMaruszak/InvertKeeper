function valid(){

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
        resend();
    }
}

function resend(){
    var email = document.getElementById("email").value;
    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/resendToken?email='+ email, true);
    xhr.setRequestHeader(header, token);
    xhr.onload = function (e) {
       if (xhr.readyState === 4) {
           if (xhr.status === 200) {
                   alert("Verification token sent to " + email);
                   window.location.replace("/")
                } else {
                    var err = JSON.parse(xhr.responseText);
                    alert(err.message);
                }
              }
           };
        xhr.send();
}