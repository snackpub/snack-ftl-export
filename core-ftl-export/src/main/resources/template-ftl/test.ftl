<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>test</title>
    <style type="text/css">
        @page {
            size: A4 landscape;
            margin: 0;
        }

        body {
            font-family: 'SimSun', serif;
        }
        div{
            margin: 50px 5px 0 5px;
        }
    </style>
</head>

<body>
<div>
    ${hello}

    <#list list as item>
        ${item}
    </#list>
</div>
</body>
</html>
