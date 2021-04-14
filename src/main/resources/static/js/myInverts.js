function edit(id){
    location.href = window.location.origin + "/myInverts/edit/"+(id);
}
function remove(id){
    if(confirm("Are you sure you want to remove this Invert?")){
         var token = document.querySelector('meta[name="_csrf"]').content;
         var header = document.querySelector('meta[name="_csrf_header"]').content;

         var xhr = new XMLHttpRequest();
         xhr.open("DELETE", '/deleteInvert/'+id , true);
         xhr.setRequestHeader(header, token);
         xhr.onload = function (e) {
         if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                 window.location.replace("/myInverts")
            } else {
                var err = JSON.parse(xhr.responseText);
                alert(err.message);
                }
            }
         };
         xhr.send();
    }
}

function markDead(id){
    location.href = window.location.origin + "/myInverts/markDead/"+id;
}



