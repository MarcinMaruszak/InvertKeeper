function send(){
    var select = document.getElementById("type");
    let contactDTO ={
        "type" : select.options[ select.selectedIndex ].value,
        "message" : document.getElementById("message").value

    };

    let json = JSON.stringify(contactDTO);

    var token = document.querySelector('meta[name="_csrf"]').content;
    var header = document.querySelector('meta[name="_csrf_header"]').content;

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/contact' , true);
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8')
    xhr.onload = function (e) {
       if (xhr.readyState === 4) {
           if (xhr.status === 200) {
                alert("Message sent");
                window.location.replace("/")
           } else {
               var err = JSON.parse(xhr.responseText);
               alert(err.message);
           }
        }
    };
    xhr.send(json);
}