var index = 0;

function validation(user){
    var name = document.getElementById("name").value.trim();

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
        edit(user);
    }
}

function edit(user){
    let invert = {
            "id" : document.getElementById("invert_id").value,
            "type" : document.getElementById("type").value,
            "name" : document.getElementById("name").value.trim(),
            "specie": document.getElementById("specie").value,
            "sex": document.getElementById("sex").value,
            "birth": document.getElementById("birth").value,
            "acquired": document.getElementById("acquired").value
        };

         invert["user"] = user;



        var instars = document.getElementsByClassName("instar_input");

        var array = [];



        for(var i = 0; i < instars.length ; i++){

            var id = instars[i].children.instar_id.value;

            let instar = {
                  "id" : instars[i].children.instar_id.value,
                  "l" : instars[i].children.l.value,
                  "moltDate" : instars[i].children.l_date.value
            }
            instar["invertebrate"] = invert;
            array[i] = instar;

        }

         let json = JSON.stringify(array);


         var token = document.querySelector('meta[name="_csrf"]').content;
         var header = document.querySelector('meta[name="_csrf_header"]').content;


        let xhr = new XMLHttpRequest();
             xhr.open("POST", '/api/updateInvert' , false);
             xhr.setRequestHeader(header, token);
             xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
             xhr.send(json);
        if(xhr.status = 200){
            var name =  document.getElementById("name").value;
            var type = document.getElementById("type").value;
            alert(type +" '" + name + "' updated.")
            window.location.replace(window.location.origin + "/myInverts")
        }
}

function add(){

    var instars = document.getElementsByClassName("instar_input");

    if(index==0){
        index = instars.length;
    }

    var div = instars[instars.length-1];

    var delButton = document.createElement("BUTTON");
    delButton.type = "button";
    delButton.id = "del_button";
    delButton.className = "btn btn-default";
    delButton.setAttribute("onclick", "del(" + (index-1) + ")");

    var span = document.createElement('span');
    span.className = "glyphicon glyphicon-minus";
    delButton.appendChild(span);

    div.appendChild(delButton);

    var newDiv = document.createElement("div");
    newDiv.id = "instar"+index;
    newDiv.classList.add("instar_input");

    var label = document.createElement("label");
    label.setAttribute("for","l");
    label.innerHTML = "Instar:";

    newDiv.appendChild(label);

    var id = document.getElementById("instar_id").cloneNode(true);
    id.value = null;

    newDiv.appendChild(id);

    var selectL = document.getElementById("l").cloneNode(true);

    selectL.value = "UNKNOWN";

    newDiv.appendChild(selectL);

     var labelDate = document.createElement("label");
     labelDate.setAttribute("for","l_date");
     labelDate.innerHTML = "Date:";

     newDiv.appendChild(labelDate);

     var selectD = document.getElementById("l_date").cloneNode(true);
     selectD.value = "";

     newDiv.appendChild(selectD);

     newDiv.appendChild(document.getElementById("add_button"));

    document.getElementById("instars").appendChild(newDiv);

    index++;

}
function del(i){
   var div = document.getElementById("instar"+i).remove();

}