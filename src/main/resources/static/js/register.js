function register(){
    let object = {
        "email": document.getElementById("email").value,
        "password": document.getElementById("password").value
    };
    let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/register' , false )
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8')
    xhr.send(json);


    if (xhr.status == 200) {
          alert("registration successful, email with confirmation link has been sent.");
    }else{
        var err = JSON.parse(xhr.responseText);
        alert(err.message)
    }
}