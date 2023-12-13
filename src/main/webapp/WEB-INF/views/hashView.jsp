<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" integrity="sha512-5A8nwdMOWrSz20fDsjczgUidUBR8liPYU+WymTZP1lmY9G6Oc7HlZv156XqnsgNUzTyMefFTcsFH/tnJE/+xBg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <title>Springboot TEST App</title>
    
        <style>
            .dropdown-menu { padding: 15px;  max-height: 200px; overflow-x: hidden; } 
            .dropdown-menu a {  display: block; } 
            .dropdown-menu a:hover { background-color: #f1f1f1; }
            /* .col-md-12.text-center a + a::before {
              content: "|";
              padding: 0 10px;
            } */
        </style>
    </head>
<body>
    <body>
        <div class="container">
            <main>
                <div class="py-5 text-center">
                    <h2>Test Form</h2>
                    <div class="text-center"> 
                        <p class="mb-0">Hash Verification</p>
                        <div class="col-md-12 text-center">
                            <a href="http://localhost:8081/form" >Create Order</a>
                            <span>|</span>
                            <a href="http://localhost:8081/fetchView" >Fetch Order Details</a>
                            <span>|</span>
                            <a href="http://localhost:8081/emiView"  >EMI Calculation</a>
                            <span>|</span>
                            <a href="http://localhost:8081/hashVerificationView2" >Hash Verification</a>
                        </div>  
                    </div>
                </div> 
    
                <div class="row">
                    <div class="col-md-6 col-lg-12">
                        <form method="post" action="hashVerification"class="needs-validation">
                            <div class="row g-3">
                                <div class="col-sm-6">
                                    <label for="mid" class="form-label">Merchant ID</label>
                                    <input type="text" name="merchant_id" class="form-control" id="mid" placeholder="Merchant ID" value="106598" >
                                </div>
    
                                <div class="col-sm-6">
                                    <label for="access_code" class="form-label">Access Code</label>
                                    <input type="text" name="access_code" class="form-control" id="access_code" placeholder="API Access Code" value="4a39a6d4-46b7-474d-929d-21bf0e9ed607" >
                                </div>
                                <div class="col-sm-6">
                                    <label for="secret" class="form-label">Secret</label>
                                    <input type="text" name="secret" class="form-control" id="secret" placeholder="Secret" value="55E0F73224EC458A8EC0B68F7B47ACAE" >
                                </div>
    
                                <div class="col-sm-6">
                                    <label for="mode" class="form-label">Gateway Mode</label>
                                    <select name="pg_mode" id="mode" class="form-control">
                                        <option value="true">Sandbox</option>
                                        <option value="false">Production</option>
                                    </select>
                                </div>
                                <div class="col-sm-12">
                                    <label for="response_data" class="form-label">Response Data</label>
                                    <textarea name="response_data" id="response_data" class="form-control" rows="10" placeholder="Enter Response Data"></textarea>
                                </div>
                            </div>
    
                            <button class="w-100 my-4 btn btn-primary btn-lg" type="submit" name="pay_now">Verify Hash</button>
                        </form>
                    </div>
                </div>
            </main>
    
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        
    </body>
</html>
