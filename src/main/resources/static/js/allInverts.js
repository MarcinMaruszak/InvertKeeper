function page(page, sortBy, direction){

     var url = "/allInverts/sort?sortBy=" + sortBy + "&direction=" + direction;


     console.log(url)

     url+="&pageNo="+page;

     filter(url);
}
function sort(sort){

    var url = "/allInverts/sort";

    url+=sort;

    filter(url)
}

function filter(url){

    var sex = document.getElementById("sex").value;
    var insectType = document.getElementById("type").value;
    var lastInstar = document.getElementById("l").value;
    var born_after = document.getElementById("born_after").value;
    var born_before = document.getElementById("born_before").value;
    var added_after = document.getElementById("added_after").value;
    var added_before = document.getElementById("added_before").value;
    var specie = document.getElementById("specie").value;


    if(!url){
        url = "/allInverts/sort";
    }


    if(sex!="null"){
        if(url.includes("?")){
            url+="&sex="+sex;
        }else{
            url+="?sex="+sex;
        }
    }

    if(insectType!="null"){

        if(url.includes("?")){
            url+="&insectType="+insectType;
        }else{
            url+="?insectType="+insectType;
        }
    }

    if(lastInstar!="null"){
        if(url.includes("?")){
            url+="&lastInstar="+lastInstar;
        }else{
            url+="?lastInstar="+lastInstar;
        }
    }

    if(born_after){
        if(url.includes("?")){
            url+="&bornAfter="+born_after;
        }else{
            url+="?bornAfter="+born_after;
        }
    }

    if(born_before){
        if(url.includes("?")){
            url+="&bornBefore="+born_before;
        }else{
            url+="?bornBefore="+born_before;
        }
    }
    if(added_after){
        if(url.includes("?")){
            url+="&addedAfter="+added_after;
        }else{
            url+="?addedAfter="+added_after;
        }
    }

    if(added_before){
        if(url.includes("?")){
            url+="&addedBefore="+added_before;
        }else{
            url+="?addedBefore="+added_before;
        }
    }
    if(specie!="null"){
        if(url.includes("?")){
             url+="&specie="+specie;
        }else{
            url+="?specie="+specie;
       }
    }

    $('#table').load(url);
}

function reset(){
    $('select').each(function() { this.selectedIndex = 0 });
    $("input[type=date]").val("")
    $('#table').load("/allInverts/sort");
}