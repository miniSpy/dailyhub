<#--<script id="collect-card-tpl" type="text/html">-->
<#--    {{# layui.each(d.content, function(index, item) { }}-->
<#--    <div class="col-sm-6 col-lg-6 mb-4 masonry-item" id="masonry-item-{{item.id}}">-->
<#--        <div class="card p-3">-->
<#--            <div class="card-body">-->
<#--                <blockquote class="blockquote">-->
<#--                    {{# if(item.personal == 1){ }}-->
<#--                    <span class="badge bg-info text-dark">私有</span>-->
<#--                    {{# } }}-->

<#--                    <a target="_blank" class="text-decoration-none" href="{{ item.url }}"><span-->
<#--                                class="card-title text-black">{{ item.title }}</span></a>-->
<#--                </blockquote>-->

<#--                <p class="card-text text-muted">-->

<#--                    <a href="/collect-user/{{item.openId}}">-->
<#--                        <img src="{{ item.avatar }}" alt="mdo" width="32" height="32"-->
<#--                             class="rounded-circle">-->
<#--                        <span>{{ item.openId }}</span>-->
<#--                    </a>-->

<#--                    {{# if(item.openId == ${openId}) { }}-->
<#--                    <a class="text-reset" href="/collect/edit?id={{item.id}}">编辑</a>-->
<#--                    <a class="text-reset" href="javascript:handleDel('{{item.id}}')">删除</a>-->
<#--                    {{# } }}-->

<#--                </p>-->
<#--                <p class="card-text text-muted">{{ item.info }}</p>-->
<#--                <figcaption class="blockquote-footer mb-0 text-muted text-end">-->
<#--                    {{ item.collected }}-->
<#--                </figcaption>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    {{# }); }}-->
<#--</script>-->
