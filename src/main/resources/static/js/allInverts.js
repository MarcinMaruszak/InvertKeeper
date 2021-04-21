function sort(sort){

    var url = "/allInverts";

    url+=sort;

    filter(url)
}

function filter(url){

    var sex = document.getElementById("sex").value;
    var insectType = document.getElementById("type").value;
    var lastInstar = document.getElementById("l").value;

    if(!url){
        url = "/allInverts";
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
    console.log(url)
    if(lastInstar!="null"){
        if(url.includes("?")){
            url+="&lastInstar="+lastInstar;
        }else{
            url+="?lastInstar="+lastInstar;
        }
    }

    window.location.replace(url)
}