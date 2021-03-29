function validation(user){
    var name = document.getElementById("name").value;

    var valid = true;

    if(!name){
        valid = false;
        document.getElementById("p_name").innerHTML = "name can't be empty";
    }else{
        document.getElementById("p_name").innerHTML = "";
    }
    var birthDate =  new Date(document.getElementById("birth").value);
    var purchaseDate = new Date(document.getElementById("acquired").value);
    var instarDate = new Date(document.getElementById("l_date").value);

    if(birthDate>purchaseDate){
        document.getElementById("p_purchaseDate").innerHTML = "Purchase date before Date of birth!";
        valid=false;
    }else{
        document.getElementById("p_purchaseDate").innerHTML = "";
    }

    if(birthDate>instarDate){
         document.getElementById("p_instarDate").innerHTML = "Instar date before Date of birth!";
         valid=false;
    }else{
        document.getElementById("p_instarDate").innerHTML = "";
    }

    if(valid){
        addPet(user);
    }
}

function addPet(user){

    let invert = {
        "type" : document.getElementById("type").value,
        "name" : document.getElementById("name").value,
        "specie": document.getElementById("specie").value,
        "sex": document.getElementById("sex").value,
        "birth": document.getElementById("birth").value,
        "acquired": document.getElementById("acquired").value
    };

    let instar = {
        "l" : document.getElementById("l").value,
        "date" : document.getElementById("l_date").value
    }

    invert["user"] = user;

    console.log(invert);

    let json = JSON.stringify(invert);

    console.log(json);

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    instar["invertebrate"] = invert;

    console.log(instar);

    let json1 = JSON.stringify(instar);
    console.log(json1);

    let xhr = new XMLHttpRequest();
        xhr.open("POST", '/api/addInvert' , false);
        xhr.setRequestHeader(header, token);
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(json1);

    if(xhr.status = 200){
        var name =  document.getElementById("name").value;
        var type = document.getElementById("type").value;
        alert(type +" '" + name + "' added.")
        window.location.replace(window.location.origin + "/myInverts")
    }
}