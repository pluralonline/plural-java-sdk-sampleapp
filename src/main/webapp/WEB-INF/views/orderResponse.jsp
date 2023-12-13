<!DOCTYPE html>
<html>
<head>
    <title>Springboot TEST App</title>
</head>
<body>
    <script type="text/javascript">
        function showMessage() {
            var resultData = '${resultData}';
            resultData=JSON.parse(resultData);
            if (resultData !== "false") {
            var pre = document.createElement("pre");
            pre.style.whiteSpace = "pre-wrap";
            pre.style.overflowX = "auto";
            pre.style.maxWidth = "100%";
            pre.textContent = JSON.stringify(resultData, null, 2);
            document.body.appendChild(pre);
            }
            else{
            var h1 = document.createElement("h1");
            h1.textContent = "Error while processing request";
            document.body.appendChild(h1);
            }
        }
        window.onload = showMessage;
    </script>
</body>

</html>