<#import "include/header.ftl" as Header>

<@Header.header "编辑收藏">

	<div id="app" class="row justify-content-md-center">

		<div class="alert alert-info" role="alert">
			请把此链接：<a href="${js}" class="alert-link">立即收藏</a>，固定到浏览器的书签栏。
		</div>

		<form class="row g-3" id="collect-form" style="width: 500px;">
			<input type="hidden" name="id" value="${collect.id}">
			<div class="col-12">
				<label for="title" class="form-label">标题 *</label>
				<input type="text" name="title" class="form-control" id="title" value="${collect.title}" required>
			</div>

			<div class="col-12">
				<label for="url" class="form-label">链接 *</label>
				<input type="text" name="url" class="form-control" id="url" value="${collect.url}" required>
			</div>

			<div class="col-12">
				<label for="validationDefault04" class="form-label">笔记</label>
				<input type="text" name="info" class="form-control" id="info" value="${collect.info}" required>
			</div>

			<div class="col-12">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" name="personal" value="1" id="personal" <#if collect.personal ==1>checked</#if>>
					<label class="form-check-label" for="personal">
						私有的，不在收藏广场中展示此收藏！
					</label>
				</div>
			</div>
			<div class="col-12">
				<button class="btn btn-primary" type="submit">提交收藏</button>
			</div>
		</form>
	</div>

	<script>
		$(function () {
			$("#collect-form").submit(function (event) {
				//阻止提交
				event.preventDefault();
				$.ajax({
					type: 'POST',
					url: '/collects/save',
					data: $("#collect-form").serialize(),
					success: function (result) {
						console.log(result)
							layer.msg(result.message, {
								time: 2000
							},function () {
								if(result.code == 200){
									location.href = "/"
							}
				})
			}
		});
		});
		});

		// javascript:(function () {var site='http://jtqcep.natappfree.cc/collects/edit?title='+encodeURIComponent(document.title)+'&url='+encodeURIComponent(document.URL);var win=window.open(site,'_blank');win.focus();})()
	</script>
</@Header.header>

