function edit(id){
    location.href = window.location.origin + "/myInverts/edit/"+(id);
}
function remove(id){
    if(confirm("Are you sure you want to remove this Invert?")){
         var token = document.querySelector('meta[name="_csrf"]').content;
         var header = document.querySelector('meta[name="_csrf_header"]').content;

         var xhr = new XMLHttpRequest();
         xhr.open("DELETE", '/api/deleteInvert/'+id , false);
         xhr.setRequestHeader(header, token);
         xhr.send();

         window.location.replace("/myInverts")
    }
}

function markDead(id){
    location.href = window.location.origin + "/myInverts/markDead/"+id;
}



