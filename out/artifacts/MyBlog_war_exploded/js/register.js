window.onload=function (ev) {
    document.getElementById("username").onblur=function (ev1){validData(3,15,rigisterForm["username"],username_help);}
    document.getElementById("password").onblur=function (ev1) { validData(3,15,rigisterForm["password"],password_help); }
    document.getElementById("rigister").onclick=function(evl){return checkInput();};

}
function validData(min,max,inputFiled,helpText){
    var InputType=/\w/;
    if(inputFiled.value.length==0){
        if(helpText!=null){
            helpText.innerHTML="请输入数据";
            return false;
        }
    }else if(inputFiled.value.length<min||inputFiled.value.length>max){
        if(helpText!=null){
            helpText.innerHTML="建议输入长度为"+min+"到"+max;
            return false;
        }
    }
    else if(!InputType.test(inputFiled.value)){
        if(helpText!=null){
            helpText.innerHTML="只能输入字母或数字";
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
    if(validData(3,15,rigisterForm["username"],username_help)&&validData(3,15,rigisterForm["password"],password_help)){
        return true;
    }else{
        alert("很抱歉，注册失败");
        return false;
    }
}

function replaceNodeText(id,newText) {
    var node=document.getElementById(id);
    while(node.firstChild){
        node.removeChild(node.firstChild);
    }
    node.appendChild(document.createTextNode(newText));
}