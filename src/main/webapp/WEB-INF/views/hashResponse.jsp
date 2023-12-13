<!DOCTYPE html>
<html>
<head>
    <title>Springboot TEST App</title>
</head>
<body>
    <script type="text/javascript">
        function showMessage() {
            var status="${status}";

            var p1 = document.createElement("p");
            p1.textContent = "Verification Status: ";
            var span1 = document.createElement("span");
            if(status=="true"){
                status="True";
            }
            else{
                status="False";
            }
            span1.textContent = status
            span1.style.fontWeight = "bold";
            p1.appendChild(span1);
            document.body.appendChild(p1);
        }
        window.onload = showMessage;
    </script>
</body>

</html>