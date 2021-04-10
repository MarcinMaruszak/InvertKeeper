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
        addPet();
    }
}

function addPet(){

    var invertDTO = new Object();

    let invert = {
        "type" : document.getElementById("type").value,
        "name" : document.getElementById("name").value.trim(),
        "specie": document.getElementById("specie").value,
        "sex": document.getElementById("sex").value,
        "birth": document.getElementById("birth").value,
        "acquired": document.getElementById("acquired").value,
        "alive" : document.getElementById("alive").value
    };

    let instar = {
        "l" : document.getElementById("l").value,
        "moltDate" : document.getElementById("l_date").value
    }
    var instars = [instar];

    invertDTO["invertebrate"] = invert;
    invertDTO["instars"] = [instar];

    var invertForm = new FormData();

    invertForm.append("invertDTO", new Blob([JSON.stringify(invertDTO)], {type: "application/json"}));

    var filesSize = document.getElementById("photos").files.length;

    if(filesSize>10){
        filesSize=10;
   }

    for(var i = 0 ; i<filesSize; i++ ){
        invertForm.append("photos" ,document.getElementById("photos").files[i]);
    }

        var paragraph = document.createElement("p");
        paragraph.id = "savingPar";
        paragraph.innerHTML="Saving in progress..."
        document.getElementById("wrap").appendChild(paragraph);
        document.getElementById("addButton").disabled = true;
        document.getElementById("addButton").id = "disabled";

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/addInvert' , true);
        xhr.setRequestHeader(header, token);
        xhr.onload = function (e) {
              if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                   var name =  document.getElementById("name").value;
                   var type = document.getElementById("type").value;
                   alert(type +" '" + name + "' saved.");
                   var id = document.getElementById("invert_id").value;
                   window.location.replace("/myInverts")
                } else {
                  alert("Error!!")
                }
              }
            };
        xhr.send(invertForm);

}


