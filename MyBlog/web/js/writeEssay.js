window.onload=function (ev) {
    document.getElementById("title").onblur=function (ev1){validDataTitle(1,15,writeForm["create_title"],title_help);}
    document.getElementById("content").onblur=function (ev1) { validDataContent(writeForm["create_article"],content_help); }
    document.getElementById("write").onclick=function(evl){return checkInput();};

}
function validDataTitle(min,max,inputFiled,helpText){
    if(inputFiled.value.length==0){
        if(helpText!=null){
            helpText.innerHTML="题目不能为空";
            return false;
        }
    }else if(inputFiled.value.length<min||inputFiled.value.length>max){
        if(helpText!=null){
            helpText.innerHTML="建议输入长度为"+min+"到"+max;
            return false;
        }
    } else{
        if(helpText!=null){
            helpText.innerHTML="";
            return true;
        }
    }
}
function validDataContent(inputFiled,helpText){
    if(inputFiled.value.length==0){
        if(helpText!=null){
            helpText.innerHTML="文章不能为空";
            return false;
        }
    }else{
        if(helpText!=null){
            helpText.innerHTML="";
            return true;
        }
    }
}
function checkInput(){
    if(validDataTitle(1,15,writeForm["create_title"],title_help)&&validDataContent(writeForm["create_article"],content_help)){
        return true;
    }else{
        alert("很抱歉，不能发表文章");
        return false;
    }
}