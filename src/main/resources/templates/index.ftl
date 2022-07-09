<#import "include/header.ftl" as Header>
<@Header.header "我的收藏">

    <div id="app" class="row justify-content-md-center">

        <!--侧边日期-->
        <div class="col col-3">
            <div class="flex-shrink-0 p-3 bg-white" style="width: 280px;">
                <ul class="list-unstyled ps-0">
                    <#list  timeTitleList as title>
                    <li class="mb-1">
                        <button class="dateline btn btn-toggle align-items-center rounded collapsed"
                                data-bs-toggle="collapse"
                                data-bs-target="#collapse-${title.firstTitle}" aria-expanded="true">
                            ${title.firstTitle}
                        </button>
                        <div class="collapse show" id="collapse-${title.firstTitle}">
                            <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                <#list title.secondTitle as child >
                                    <li><a href="javascript:handleDateline('${child.firstTitle}')" class="link-dark rounded">${child.firstTitle}</a></li>
                                </#list>
                            </ul>
                        </div>
                    </li>
                </ul>
                </#list>
            </div>
        </div>

        <!---->
        <div class="col col-9" id="collects-col">
            <div class="row" id="masonry">
<#--                <#list userCollect as item>-->
<#--                <div class="col-sm-6 col-lg-6 mb-4 masonry-item" id="masonry-item-${item.id}">-->
<#--                    <div class="card p-3">-->
<#--                        <div class="card-body">-->
<#--                            <blockquote class="blockquote">-->
<#--                                <#if item.personal == 1>-->
<#--                                <span class="badge bg-info text-dark">私有</span>-->
<#--                                </#if>-->

<#--                                <a target="_blank" class="text-decoration-none" href="${item.url }"><span-->
<#--                                            class="card-title text-black">{{ item.title }}</span></a>-->
<#--                            </blockquote>-->

<#--                            <p class="card-text text-muted">-->

<#--                                <a href="/collect-user/${item.openId}">-->
<#--                                    <img src="${ item.avatar }" alt="mdo" width="32" height="32"-->
<#--                                         class="rounded-circle">-->
<#--                                    <span>${ item.openId }</span>-->
<#--                                </a>-->

<#--                                <#if item.openId == openId>-->
<#--                                <a class="text-reset" href="/collect/edit?id={{item.id}}">编辑</a>-->
<#--                                <a class="text-reset" href="javascript:handleDel('{{item.id}}')">删除</a>-->
<#--                                </#if>-->

<#--                            </p>-->
<#--                            <p class="card-text text-muted">${ item.info }</p>-->
<#--                            <figcaption class="blockquote-footer mb-0 text-muted text-end">-->
<#--                                ${ item.collected }-->
<#--                            </figcaption>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--                </#list>-->
            </div>

        </div>
    </div>



<script>
    var openId = '${openId}'
    console.log(openId)
    var avatar = "${useravatar}"
    console.log(avatar)

    <#--var laytpl,flow-->
    <#--layui.use(['laytpl','flow'],function () {-->
    <#--    laytpl = layui.laytpl;-->
    <#--    flow = layui.flow;-->
    <#--})-->

    function flowLoad(timeTitle) {

        $.get('/collects/' + openId + '/' + timeTitle, {
            page: 1,
            size: 10
        }, function (result) {
            var html;
            for (i in result.data){
                console.log(i);
            html += '<div class="col-sm-6 col-lg-6 mb-4 masonry-item" id="masonry-item-"+i>'+
                                    '<div class="card p-3">'+
                                        '<div class="card-body">'+
                                            '<blockquote class="blockquote">';
           if (result.data[i].personal == 1)
               html+='<span class="badge bg-info text-dark" >私有</span>'
                var url = result.data[i].url;
                // var avatar = result.data[i].avatar;
                                              html+=  '<a target="_blank" class="text-decoration-none" href='+url+'>'+
                                                    '<span class="card-title text-black">'
                +result.data[i].title + '</span></a></blockquote>'+

                                            '<p class="card-text text-muted">'+

                                                '<a href="/collects/${openId}/all">'+
                                                    '<img src="images/1653505103(1).png" alt="mdo" width="32" height="32" class="rounded-circle">'
                                                    +'<span>'+ result.data[i].openId+'</span></a>';

                                                  var id =result.data[i].id;
                                if (result.data[i].openId == "${openId}")
                                    html += '<a class="text-reset" href="/collects/edit?id='+id+'">编辑</a>'+
                                            '<a class="text-reset" href="javascript:handleDel('+id+')">删除</a>'

                                        html +='</p>'+
                                            +'<p class="card-text text-muted">'+result.data[i].openId+'</p>'+
                                            '<figcaption class="blockquote-footer mb-0 text-muted text-end">'+
                                                result.data[i].title
                                            +'</figcaption>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>'

            }
            $("#masonry").append(html)
            // console.log(result)
            // var lis = [];
            // var gettpl = $('#collect-card-tpl').html();
            // laytpl(gettpl).render(result.data,function (html) {
            //     $(".layui-flow-more").before(html);
            // })
            // next(lis.join(''), page < result.totalPages);
        })

    }
    // 点击时间筛选，重新刷新瀑布流数据
    // function headleTimeTitle(timeTitle) {
    //     $('#masonry').html('');
    //     flowLoad(timeTitle)
    // }
    // 初始化全部加载
    $(function () {
        flowLoad("all")
    });

    function handleDateline(dateLine) {
        console.log(dateLine);
        $("#masonry").html("");
        flowLoad(dateLine);
    }

    function handleDel(id) {
        console.log(id);
        layer.confirm('是否確認刪除',function (index) {
            $.post('/collects/delete/?id='+id,function (result) {
                if(result.code == 200)
                    $("#masonry-item-"+id).remove();
                layer.msg(result.message)
            })
        })
    }

    function handleUpdateDateline(id) {
        console.log(id);
        $.get('/collects/edit/?id='+id);
    }
</script>
</body>
</html>
</@Header.header>
