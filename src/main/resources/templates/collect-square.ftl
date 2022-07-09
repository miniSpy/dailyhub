<#import "include/header.ftl" as Header>
<@Header.header "收藏廣場">

	<div id="app" class="row justify-content-md-center">
		<!---->
		<p>${message}</p>
		<div class="col col-9" id="collects-col">
			<div class="row" id="masonry">
			</div>

		</div>
	</div>



	<script>
		function flowLoad() {
			<#--var q=1;-->
			<#--var openId = "${openId}";-->
			$.get('/collects/all', {
				page: 1,
				size: 10,
				openId: "${openId}",
				q: "${q}"
			}, function (result) {
				var html;
				for (i in result.data){
					console.log(i);

					html += '<div class="col-sm-6 col-lg-6 mb-4 masonry-item" id="masonry-item-"+i>'+
							'<div class="card p-3">'+
							'<div class="card-body">'+
							'<blockquote class="blockquote">';

					var url = result.data[i].url;
					var openId = result.data[i].openId;
					console.log(openId)
					console.log("heiehi")
					console.log('${openId}')
					// var avatar = result.data[i].avatar;
					html+=  '<a target="_blank" class="text-decoration-none" href='+url+'>'+
							'<span class="card-title text-black">'
							+result.data[i].title + '</span></a></blockquote>'+

							'<p class="card-text text-muted">'+
							'<img src="" alt="mdo" width="32" height="32" class="rounded-circle">'
							+'<span>'+ result.data[i].openId+'</span></a>';

					var id =result.data[i].id;
					if (openId == '${openId}')
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
			flowLoad()
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
