<!DOCTYPE html>
<html>
<head>
    <title>Springboot TEST App</title>
</head>
<body>
    <script type="text/javascript">
        function showMessage() {
            var resultData = "${resultData}";

            if (resultData !== "false") {
                var anchor = document.createElement("a");
                anchor.href = resultData;
                anchor.textContent = "Redirect to Payment Page";
                document.body.appendChild(anchor);
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