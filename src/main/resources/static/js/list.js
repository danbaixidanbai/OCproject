$(function(){
    var classifyId=getQueryString('classifyId');
    var parentId='';
    var pageSize=10;
    var pageIndex=1;
    var searchDivUrl='/courseclassify/getclassify';
    var url='';
    getClassify();
    function getClassify() {
        //判断classifyId是否为空
        if(oc.isEmpty(classifyId)){
            url='/courseclassify/getallclassify';
            getALLClassify(url);
            //todo 加载课程
        }else{
            url = searchDivUrl + '?' + 'classifyId=' + classifyId;

            //todo 加载课程
        }

    }
   //获取全部分类
    function getALLClassify(url) {
        $.ajax({
            url:url,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if (data.errCode == 1) {

                    var classifyParent=data.classifyParent;
                    console.log(classifyParent);
                    var html='<li class="course-nav-item cur-course-nav">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classifyParent.length;i++){
                        html+='<li class="course-nav-item " >'
                            + '<a id="'+classifyParent[i].classifyId+'">'+classifyParent[i].classifyName+'</a>'
                            + '</li>';
                    }
                    var classify=data.classify;
                    var html2='<li class="course-nav-item cur-course-nav">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classify.length;i++){
                        html2+='<li class="course-nav-item ">'
                            + '<a id="'+classify[i].classifyId+'">'+classify[i].classifyName+'</a>'
                            + '</li>';
                    }
                    $('#first').html(html);
                    $('#second').html(html2);
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }
    //获取二级分类
    function getSecondClassify(parentId) {
        $.ajax({
            url:'/courseclassify/getsecondclassify?parentId='+parentId,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if (data.errCode == 1) {
                    var classify=data.classify;
                    var html2='<li class="course-nav-item cur-course-nav">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classify.length;i++){
                        html2+='<li class="course-nav-item ">'
                            + '<a id="'+classify[i].classifyId+'">'+classify[i].classifyName+'</a>'
                            + '</li>';
                    }
                    $('#second').html(html2);
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }
    function getCourse(){

    }


    $("#first").on("click",".course-nav-item",function (e) {
        parentId=$(e.target).attr('id');
        if(oc.isEmpty(parentId)){
            parentId='';
            url='/courseclassify/getallclassify';
            getALLClassify(url);
            return;
        }
        $(e.target).parent().addClass('cur-course-nav').siblings()
            .removeClass('cur-course-nav');
        pageIndex=1;
        //加载二级目录
        getSecondClassify(parentId);
        //todo 加载课程
    });

    $("#second").on("click",".course-nav-item",function (e) {
        classifyId=$(e.target).attr('id');
        if(oc.isEmpty(classifyId)){
            classifyId='';
        }
        $(e.target).parent().addClass('cur-course-nav').siblings()
            .removeClass('cur-course-nav');
        pageIndex=1;
        //todo 加载课程
    });
});