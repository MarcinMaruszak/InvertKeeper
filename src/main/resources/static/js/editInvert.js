var index = 0;
var ids = [];

function validation(){
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
        edit();
    }
}

function edit(){

    var invertDTO = new Object();

    let invert = {
            "id" : document.getElementById("invert_id").value,
            "type" : document.getElementById("type").value,
            "name" : document.getElementById("name").value.trim(),
            "specie": document.getElementById("specie").value,
            "sex": document.getElementById("sex").value,
            "birth": document.getElementById("birth").value,
            "acquired": document.getElementById("acquired").value,
            "alive" : document.getElementById("alive").value
        };

        var instars = document.getElementsByClassName("instar_input");

        var instarArray = [];

        for(var i = 0; i < instars.length ; i++){

            var id = instars[i].children.instar_id.value;

            let instar = {
                  "id" : instars[i].children.instar_id.value,
                  "l" : instars[i].children.l.value,
                  "moltDate" : instars[i].children.l_date.value
            }
            instarArray[i] = instar;
        }

        invertDTO["invertebrate"] = invert;
        invertDTO["instars"] = instarArray;

        var invertForm = new FormData();

        invertForm.append("invertDTO", new Blob([JSON.stringify(invertDTO)], {type: "application/json"}));


        var photosToUpload = document.getElementById("photos_button").files.length;

        var photosCurrent = document.getElementsByClassName("img_wrap").length;

        if(photosToUpload>10-photosCurrent){
            photosToUpload=10-photosCurrent;
       }

        for(var i = 0 ; i<photosToUpload; i++ ){
            invertForm.append("photos" ,document.getElementById("photos_button").files[i]);
        }

        invertForm.append("removePhotos",  new Blob([JSON.stringify(ids)], {type: "application/json"}));

        var avatarId;

        var radios = document.getElementsByName("photo");

        if(radios!== null&&radios!==undefined){
            for (var i = 0, length = radios.length; i < length; i++) {
                if (radios[i].checked) {
                    avatarId = radios[i].value;
                    break;
                }
            }
        }

        if(avatarId!==null&&avatarId!==undefined){
            invertForm.append("avatarID", new Blob([JSON.stringify(avatarId)], {type: "application/json"}));
        }

         var paragraph = document.createElement("p");
        paragraph.id = "savingPar";
        paragraph.innerHTML="Saving in progress..."
        document.getElementById("wrap").appendChild(paragraph);
        document.getElementById("saveButton").disabled = true;
        document.getElementById("saveButton").id = "disabled";

        var token = document.querySelector('meta[name="_csrf"]').content;
        var header = document.querySelector('meta[name="_csrf_header"]').content;

        let xhr = new XMLHttpRequest();
        xhr.open("POST", '/api/updateInvert' , true);
        xhr.setRequestHeader(header, token);
        xhr.onload = function (e) {
          if (xhr.readyState === 4) {
            if (xhr.status === 200) {
               var name =  document.getElementById("name").value;
               var type = document.getElementById("type").value;
               alert(type +" '" + name + "' updated.");
               var id = document.getElementById("invert_id").value;
               window.location.replace("/myInverts/details/"+id)
            } else {
                var err = JSON.parse(xhr.responseText);
                alert(err.message);
                 document.getElementById("savingPar").remove();
                 document.getElementById("disabled").disabled =false;
                 document.getElementById("disabled").id = "saveButton";
            }
          }
        };
        xhr.send(invertForm);
}

function add(){

    var instars = document.getElementsByClassName("instar_input");

    if(index==0){
        index = instars.length;
    }

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

     newDiv.appendChild(document.getElementById("plusButton").cloneNode(true));

    var delButton = document.createElement("BUTTON");
    delButton.type = "button";
    delButton.className = "del_button";
    delButton.setAttribute("onclick", "del(" + (index) + ")");

    var span = document.createElement('i');
    span.className = "fas fa-minus";
    delButton.appendChild(span);

    newDiv.appendChild(delButton);

    document.getElementById("instars").appendChild(newDiv);

    index++;

}
function del(i){
   var div = document.getElementById("instar"+i).remove();

}

function removePhoto(id, i){
    ids.push(id);
    document.getElementById("photo"+i).remove();
}
