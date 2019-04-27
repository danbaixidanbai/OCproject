$(function(){
  var newsId=getQueryString('newsId');
    var isEdit=newsId?true:false;
    var newsInfoUrl='/cms/news/getnewsinfo?newsId='+newsId;
    var newsAdd='/cms/news/addnews';
    var newsEdit='/cms/news/editnews';

    if(isEdit){
        getnewsbynewsid();
    }
    function getnewsbynewsid(){
        $.ajax({
            url:newsInfoUrl,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                var resp =data;
                if (resp.errCode == 1) {
                    $("#name").val(data.news.newsName);
                    $("#imageKey").val(data.news.newsImage);
                    $("#carousel-picture").attr("src", appendString(data.news.newsImage));
                    $('#carousel-picture').show();
                    $("#url").val(data.news.newsUrl);
                    $("#priority").val(data.news.newsPriority);
                    $("#enable").val(data.news.newsEnable);
                } else {
                    alert(data.errMsg);

                }
            }
        });
    }

    $('#submit').click(function () {
        var formData=new FormData();
        var news={};
        if(isEdit){
            news.newsId=newsId;
            formData.append("imageKey",$("#imageKey").val());
        }
        news.newsName=$("#name").val();
        news.newsUrl=$("#url").val();
        news.newsPriority=$("#priority").val();
        news.newsEnable=$("#enable").val();

        var newsImage=$('#pictureImg')[0].files[0];


        formData.append("newsStr",JSON.stringify(news));
        formData.append("newsImage",newsImage);

        $.ajax({
            url:isEdit?newsEdit:newsAdd,
            type:'post',
            dataType:'json',
            data:formData,
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                var resp =data;
                if (resp.errCode == 1) {
                    //还可以将img清空
                    alert("操作成功！！！");
                    window.location.href='/cms/news/pagelist';
                } else {
                    alert(data.errMsg);

                }
            }
        });

    });



    $('#doload').click(function () {
        $('#pictureImg').click();
    });

    $("#pictureImg").change(function () {
        var img = $('#pictureImg').val();
        if(oc.photoValid(img)){
            oc.previewUploadImg('pictureImg','carousel-picture');
            $('#carousel-picture').show();
            $('#imgErrSpan').html('');
            return;
        }else{
            $('#imgErrSpan').html('&nbsp;请选择png,jpeg,jpg格式图片');
            $('#pictureImg').val('');
        }
    });

});