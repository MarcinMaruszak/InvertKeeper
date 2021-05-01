function validate(receiverId){
     var subject = document.getElementById("subject").value;
     var text = document.getElementById("text").value;

    var valid = true;

    if(subject.length<1){
        valid=false;
        document.getElementById("info_subject").innerHTML = "Empty subject!"
    }else{
        document.getElementById("info_subject").innerHTML = ""
    }
    if(text.length<1){
        valid=false;
        document.getElementById("info_text").innerHTML = "Empty message!"
    }else{
        document.getElementById("info_text").innerHTML = ""
    }

    if(valid){
        sendMessage(receiverId);
    }
}


function sendMessage(receiverId){
    let message = {
        "subject" : document.getElementById("subject").value,
        "message" : document.getElementById("text").value,
        "receiver" : receiverId
    }

     let json = JSON.stringify(message);
    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;


        let xhr = new XMLHttpRequest();
        xhr.open("POST", '/message/send' , true);
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8')
        xhr.setRequestHeader(header, token);
        xhr.onload = function (e) {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    alert("Message send");
                     window.location.replace("/profile")
                } else {
                    var err = JSON.parse(xhr.responseText);
                    alert(err.message);
                }
            }
        };
        xhr.send(json);
}