<#macro header title>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我的收藏 - 程序员日报</title>

    <link rel="stylesheet" href="/layui/css/layui.css">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/sidebars.css" rel="stylesheet">
    <link href="/css/headers.css" rel="stylesheet">
    <script src="/js/jquery-3.6.0.min.js"></script>
    <script src="/js/bootstrap.bundle.min.js"></script>
    <script src="/layui/layui.all.js"></script>

    <style type="text/css">
        [v-cloak] {
            display: none !important;
        }
    </style>

</head>
<body>

<div class="container" style="max-width: 1500px">

    <#include "/include/loginBatton.ftl">
    <#nested>
</div>
</body>
</html>

</#macro>
