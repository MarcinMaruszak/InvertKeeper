function markDead(id){
    if(confirm("Are you sure you want to mark this Invert as dead?")){
        var token = document.querySelector('meta[name="_csrf"]').content;
        var header = document.querySelector('meta[name="_csrf_header"]').content;

        var date = document.getElementById("date").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", '/saveAsDead/'+id +'?date='+date, false);
        xhr.setRequestHeader(header, token);
        xhr.send();

        if(xhr.status == 200){
            location.href = window.location.origin + "/myInverts";
        }
    }
}