$(function(){
    getMenu();
    getParent();
    function getMenu(){
        $.ajax({
            url:'/index/getmenu',
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.errCode == 1) {
                    var list = data.list;
                    var html = '';
                    var html2 = '';
                    list.map(function (item,value) {
                        var courseClassifyId=item.courseClassify.classifyId;
                        html+='<tr class="tr-bg-gray" code="'+courseClassifyId+'">'
                            +'<td>'+item.courseClassify.classifyName+'</td>'
                            +'<td>'+item.courseClassify.classifyId+'</td>'
                            +'<td>'
                            +'<a class="link-a" href="javascript:void(0)">删除</a></td>'
                            + '</tr>';
                        listSeond=item.list;
                        //二级分类
                        listSeond.map(function (demo,value) {
                            html2+='<tr parentCode="'+courseClassifyId+'" code="'+demo.classifyId+'">'
                                 +'<td>'+demo.classifyName+'</td>'
                                 +'<td>'+demo.classifyId+'</td>'
                                 +'<td>'+demo.parent+'</td>'
                                 +'<td>'
                                 +'<a class="link-a" href="javascript:void(0)">删除</a></td>'
                                 + '</tr>';
                        });
                    });
                    $('#parent').html(html);
                    $('#classify').append(html2);
                }else{
                    alert(data.errMsg);
                }
            }
        });
    }

    //获取一级分类
    function getParent(){
        $.ajax({
            url:'/courseclassify/getparent',
            type:'get',
            dataType:'json',
            success:function(resp){
                var errcode = resp.errCode;
                if(errcode == 1){
                    var html='';
                    var  list=resp.list;
                    for(var i=0;i<list.length;i++){
                        html+='<option value="'+list[i].classifyId+'">'+list[i].classifyName+'</option>';
                    }
                    $('#parentSelect').append(html);
                }else{
                    alert(resp.errMsg);
                }
            }
        });
    }
    $("#submit").click(function () {
        var classify={}
        classify.classifyName=$("#name").val();
        classify.parent=$("#parentSelect").val();
        var formData=new FormData();
        formData.append("classify",JSON.stringify(classify));
        $.ajax({
            url:'/courseclassify/addclassify',
            type:'post',
            data:formData,
            contentType : false,
            processData : false,
            cache : false,
            dataType:'json',
            success:function(data){
                if (data.errCode == 1) {
                    $('#myModal').modal('hide');
                    window.location.reload();
                }else{
                    alert(data.errMsg);
                }
            }
        });
    });
    //添加分类
    /*$("#addclassify").click(function () {
        getParent();
        console.log(hello);
        Modal.show('myModal');
        /!*$('#code').removeAttr('disabled');*!/
    });*/
    //一级分类点击，过滤二级分类
    $('#parent').on("click",".tr-bg-gray",function(){
        var code = $(this).attr('code');
        $('#subClassifysTable').find('tr').each(function(i,item){
            if($(item).attr('parentCode') == code){
                $(item).show();
            }else{
                if($(item).attr('name') != 'th'){
                    $(item).hide();
                }
            }
        });
    });
    //删除
    function doDelete(id){
        Modal.confirm('确定删除?',function(){
            $.ajax({
                url:'${s.base}/classify/deleteLogic.html',
                type:'POST',
                dataType:'json',
                data:{"id":id},
                success:function(resp){
                    var errcode = resp.errcode;
                    if(errcode == 0){
                        /* Modal.alert('删除成功!',function(){
                            window.location.reload();//刷新
                        }); */
                        window.location.reload();//刷新
                    }
                }
            });
        });
    }
    //添加 & 编辑
    /*function toEdit(id){
        if(id == undefined){//添加

            $('#code').removeAttr('disabled');
        }else{
            $('#code').attr('disabled','disabled');
            $.ajax({
                url:'${s.base}/classify/getById.html',
                type:'POST',
                dataType:'json',
                data:{"id":id},
                success:function(resp){
                    var errcode = resp.errcode;
                    if(errcode == 0){
                        Modal.show('myModal');
                        $("#myForm").fill(resp.data);
                    }
                }
            });
        }
    }*/


});