window.onload=function (ev) {
    document.getElementById("writeComment").onclick=function (ev1){return checkInput();}
}
function validDataComment(inputFiled){
    if(inputFiled.value.length==0){
            return false;
    }else{
            return true;
    }
}
function checkInput(){
    if(validDataComment(writeComForm["comment"])){
        return true;
    }else{
        alert("评论不能为空");
        return false;
    }
}