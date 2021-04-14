function showPassFields(){
    var wrap = document.getElementById("wrap");

    var passDiv = document.createElement("div");
    passDiv.id = "password_fields"

    var newDiv = document.createElement("div");

    var label = document.createElement("label");
    label.innerHTML = "  Old password:";

    var input = document.createElement("input");
    input.id = "oldPass";
    input.className = "pass";
    input.setAttribute("type" , "password");
    input.placeholder = "Old password";

    newDiv.appendChild(label)
    newDiv.appendChild(input);

    var newDiv2 = newDiv.cloneNode(true);
    newDiv2.children[0].innerHTML = " New Password"
    newDiv2.children[1].id = "newPass"
    newDiv2.children[1].placeholder = "New password";

    var newDiv3 = newDiv.cloneNode(true);
    newDiv3.children[0].innerHTML = "Repeat Password:"
    newDiv3.children[1].id = "newPass2"
    newDiv3.children[1].placeholder = "Repeat password";


    document.getElementById("passButton").remove();

    var par =  document.createElement("p");
    par.id = "pass_error";


    passDiv.appendChild(newDiv);
    passDiv.appendChild(newDiv2);
    passDiv.appendChild(newDiv3);
    passDiv.appendChild(par);

    var saveButton = document.createElement("BUTTON");
    saveButton.type = "button";
    saveButton.innerHTML = "Save"
    saveButton.className = "save_button";
    saveButton.setAttribute("onclick", "validate()");

    passDiv.appendChild(saveButton);

    wrap.appendChild(passDiv);
}

function validate(){
    var pass = document.getElementById("newPass").value;
    var pass2 = document.getElementById("newPass2").value;

    var valid = true;

    if(pass!=pass2){
       valid=false;
       console.log("1")
       document.getElementById("pass_error").innerHTML = "passwords don't match";
    }else{
        document.getElementById("pass_error").innerHTML = "";
    }

     if(pass.length<8){
     console.log("2")
        valid = false;
        document.getElementById("pass_error").innerHTML = "min 8 characters";
    }else{
        document.getElementById("pass_error").innerHTML = "";
    }

    if(pass2.length<8){
    console.log("3")
         valid = false;
        document.getElementById("pass_error").innerHTML = "min 8 characters";
    }else{
      document.getElementById("pass_error").innerHTML = "";
    }
    if(valid){
        saveNewPass()
    }
}


function saveNewPass(){

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

   let object = {
           "oldPass" : document.getElementById("oldPass").value,
           "newPass": document.getElementById("newPass").value

   };
   let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
     xhr.open("POST", '/changePass' , true);
     xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8')
     xhr.setRequestHeader(header, token);
     xhr.onload = function (e) {
          if (xhr.readyState === 4) {
            if (xhr.status === 200) {
               alert("Password changed.");
                window.location.replace("/profile")
            } else {
                var err = JSON.parse(xhr.responseText);
                alert(err.message);
            }
         }
     };
     xhr.send(json);


}