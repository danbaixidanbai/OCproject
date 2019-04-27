function changeVerifyCode(img) {
    img.src="/kaptcha?"+Math.floor(Math.random()*100);
}
function getQueryString(name) {
    var reg= new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r=window.location.search.substr(1).match(reg);
    if(r!=null){
        return decodeURIComponent(r[2]);
    }
    return '';
}
//截取字符串
function cutString(key){
    var img=key.split("http://cdn.poipoipo.top/")[1];
    return img;
}
function appendString(key){
    var img='http://cdn.poipoipo.top/'+key;
    return img;
}

Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth() + 1, // 月份
        "d+" : this.getDate(), // 日
        "h+" : this.getHours(), // 小时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
        "S" : this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

OC = function() {};
OC.prototype = {
    //判断是否为空,如果为空返回true，否则返回false
    isEmpty : function(text){
        if(text == undefined || text == null || text == '' || text == 'null' || text == 'undefined'){
            return true;
        }else{
            text = text.replace(/(\s*$)/g, '');
            if(text == ''){
                return true;
            }
        }
        return false;
    },
    //英文、数字正则表达式，验证通过返回 true；
    numValid : function(text){
        var patten = new RegExp(/^[0-9]+$/);
        return patten.test(text);
    },
    //英文、数字正则表达式
    enNumValid : function(text){
        var patten = new RegExp(/^[a-zA-Z0-9]+$/);
        return patten.test(text);
    },
    //英文、数字、-、_验证
    cValid : function(text){
        var patten = new RegExp(/^[a-zA-Z][\w-_]{5,19}$/);
        return patten.test(text);
    },
    //中文、英文、数字、-、_验证
    zcValid : function(text){
        var patten = RegExp(/^[\u4E00-\u9FA5A-Za-z0-9_-]+$/);
        return patten.test(text);
    },
    //以字母开头正则表达式，英文、数字、-、_验证
    enStartValid : function(text){
        var patten = new RegExp(/^[a-zA-Z][a-zA-Z0-9_]*$/);
        return patten.test(text);
    },
    //中文_验证
    cnValid : function(text){
        var patten = RegExp(/^[\u4E00-\u9FA5]+$/);
        return patten.test(text);
    },
    //mobile
    mobileValid : function(text){
        var patten = RegExp(/^1\d{10}$/);
        return patten.test(text);
    },
    //email
    emailValid : function(text){
        var patten = RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
        return patten.test(text);
    },
    //字母&符号&数字至少2种;8-16位数
    pwdValid : function(text){
        var patten = RegExp(/^((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$/);
        return patten.test(text);
    },
    //图片验证
    photoValid : function(text){
        var photos = ['.jpg','.png','.jpeg'];
        photoExt=text.substr(text.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        var flag = false;
        for(var i = 0; i < photos.length; i++){
            if(photos[i] == photoExt){
                flag = true;
                break;
            }
        }
        return flag;
    },
    //excel valid
    excelValid : function(text){
        var excels = ['.xlsx'];
        excelExt=text.substr(text.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        var flag = false;
        for(var i = 0; i < excels.length; i++){
            if(excels[i] == excelExt){
                flag = true;
                break;
            }
        }
        return flag;
    },
    //日期格式化
    dateFormat : function(date,fmt){
        if(fmt == undefined){
            fmt = 'yyyy-MM-dd';
        }
        var o = {
            "M+" : date.getMonth()+1,                 //月份
            "d+" : date.getDate(),                    //日
            "h+" : date.getHours(),                   //小时
            "m+" : date.getMinutes(),                 //分
            "s+" : date.getSeconds(),                 //秒
            "q+" : Math.floor((date.getMonth()+3)/3), //季度
            "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)){
            fmt = fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    },

    //json数据的日期格式化
    jsonDateFormat : function(value,format){
        if (value == null || value == '') {
            return '';
        } else {
            return this.dateFormat(new Date(value.time),format);
        }
    },

    /**
     * 客户端js实现图片预览
     * fileElId 选择文件的input type=file的id
     * imgElId 预览的image 的id
     */
    previewUploadImg : function(fileElId,imgElId){
        var file = document.getElementById(fileElId);
        var pic = document.getElementById(imgElId);
        var isIE = navigator.userAgent.match(/MSIE/)!= null;
        var isIE6 = navigator.userAgent.match(/MSIE 6.0/)!= null;
        if(isIE) {
            file.select();
            var reallocalpath = document.selection.createRange().text;
            // IE6浏览器设置img的src为本地路径可以直接显示图片
            if (isIE6) {
                pic.src = reallocalpath;
            }else {
                // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
                pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
                // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
                pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
            }
        }else{
            var reader = new FileReader();
            reader.readAsDataURL(file.files[0]);
            reader.onload = function(e){
                pic.src = this.result;
            };
        }
    }
};
oc = new OC();
