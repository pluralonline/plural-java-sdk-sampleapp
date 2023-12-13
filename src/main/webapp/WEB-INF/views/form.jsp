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
        </style>
    </head>
<body>
    <body>
        <div class="container">
            <main>
                <div class="py-5 text-center">
                    <h2>Test Form</h2>
                    <div class="text-center"> 
                        <div class="col-md-12 text-center">
                            <p class="mb-0">Create Order</p>
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
                        <form method="post" action="submit"class="needs-validation">
                            <div class="row g-3">
                                <div class="col-sm-6">
                                    <label for="mid" class="form-label">Merchant ID</label>
                                    <input type="text" name="merchant_id" class="form-control" id="mid" placeholder="Merchant ID" value="106598" required>
                                </div>
    
                                <div class="col-sm-6">
                                    <label for="access_code" class="form-label">Access Code</label>
                                    <input type="text" name="access_code" class="form-control" id="access_code" placeholder="API Access Code" value="4a39a6d4-46b7-474d-929d-21bf0e9ed607" required>
                                </div>
    
                                <div class="col-sm-6">
                                    <label for="secret" class="form-label">Secret</label>
                                    <input type="text" name="secret" class="form-control" id="secret" placeholder="Secret" value="55E0F73224EC458A8EC0B68F7B47ACAE" required>
                                </div>
    
                                <div class="col-sm-6">
                                    <label for="mode" class="form-label">Gateway Mode</label>
                                    <select name="pg_mode" id="mode" class="form-control">
                                        <option value="true">Sandbox</option>
                                        <option value="false">Production</option>
                                    </select>
                                </div>
    
                                <div class="col-sm-3">
                                    <label for="txn_id" class="form-label">Transacrtion ID</label>
                                    <input type="text" name="txn_id" class="form-control" id="txn_id" placeholder="Transacrtion ID" required>
                                </div>
    
                                <div class="col-sm-3">
                                    <label for="amount" class="form-label">Amount (In Paisa)</label>
                                    <input type="text" name="amount_in_paisa" class="form-control" id="amount_in_paisa" placeholder="Amount (In Paisa)" value="4000000" required>
                                </div>
    
                                <div class="col-sm-6">
                                    <label for="callback_url" class="form-label">Callback URL</label>
                                    <input type="text" name="callback_url" class="form-control" id="callback_url" placeholder="Callback URL" value="${callback_url}" required>
                                </div>

                                <div class="col-sm-9">
                                    <label for="response_data" class="form-label">Product Details</label>
                                    <textarea name="product_details" id="product_details" class="form-control" rows="6" >[{"product_code":"testSKU1","product_amount":"2000000"},{"product_code":"testSKU2","product_amount":"2000000"}]</textarea>
                                </div>

                                <div class="col-sm-6">
                                    <label for="callback_url" class="form-label">Payment Mode</label>
                                    <div class="dropdown">
                                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                            Select Payment Mode
                                        </button>
                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <li><input type="checkbox" name="payment_mode1" id="cards" value="cards" checked><label for="cards" style="margin-left: 5px;">Credit Debit Card</label></li>
                                            <li><input type="checkbox" name="payment_mode2" id="netbanking" value="netbanking" checked><label for="netbanking" style="margin-left: 5px;">Net Nanking</label></li>
                                            <li><input type="checkbox" name="payment_mode3" id="wallet" value="wallet" checked><label for="wallet" style="margin-left: 5px;">Wallet</label></li>
                                            <li><input type="checkbox" name="payment_mode4" id="upi" value="upi" checked><label for="upi" style="margin-left: 5px;">UPI</label></li>
                                            <li><input type="checkbox" name="payment_mode5" id="emi" value="emi"><label for="emi" style="margin-left: 5px;">EMI</label></li>
                                            <li><input type="checkbox" name="payment_mode6" id="debit_emi" value="debit_emi"><label for="debit_emi" style="margin-left: 5px;">Debit Card EMI</label></li>
                                            <li><input type="checkbox" name="payment_mode7" id="cardless_emi" value="cardless_emi"><label for="cardless_emi" style="margin-left: 5px;">Cardless EMI</label></li>
                                            <li><input type="checkbox" name="payment_mode8" id="bnpl" value="bnpl"><label for="bnpl" style="margin-left: 5px;">BNPL</label></li>
                                            <li><input type="checkbox" name="payment_mode9" id="prebooking" value="prebooking"><label for="prebooking" style="margin-left: 5px;">Pay Later</label></li>
                                        </ul>
                                    </div>
                                </div>
                               
                                <a class="text-dark text-decoration-none mt-4 mb-4 d-flex" href="#customer_info" data-bs-toggle="collapse" role="button">
                                    <i class="fa fa-chevron-down me-2 text-primary"></i>
                                    <h5 class="mb-0">Customer Details</h5>
                                </a>
    
                                <div id="customer_info" class="collapse row">
    
                                    <div class="col-sm-4">
                                        <label for="customer_id" class="form-label">Customer Id</label>
                                        <input type="text" name="customer_id" class="form-control" id="customer_id" placeholder="Customer Id" value="">
                                    </div>
    
                                    <div class="col-sm-4">
                                        <label for="first_name" class="form-label">First Name</label>
                                        <input type="text" name="first_name" class="form-control" id="first_name" placeholder="Enter First Name" value="">
                                    </div>
    
                                    <div class="col-sm-4">
                                        <label for="last_name" class="form-label">Last Name</label>
                                        <input type="text" name="last_name" class="form-control" id="last_name" placeholder="Enter Last Name" value="">
                                    </div>
    
                                    <div class="col-sm-6 mt-2">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="text" name="email" class="form-control" id="Enter email" placeholder="Enter Email Id" value="">
                                    </div>
    
                                    <div class="col-sm-6 mt-2">
                                        <label for="phone" class="form-label">Phone</label>
                                        <input type="text" name="phone" class="form-control" id="phone" placeholder="Enter Phone No" value="">
                                    </div>
    
                                </div>
    
                                <a class="text-dark text-decoration-none mt-2 mb-4 d-flex" href="#billing_address" data-bs-toggle="collapse" role="button">
                                    <i class="fa fa-chevron-down me-2 text-primary"></i>
                                    <h5 class="mb-0">Billing Address</h5>
                                </a>
    
                                <div id="billing_address" class="collapse row">
    
                                    <div class="col-sm-4">
                                        <label for="address1" class="form-label">Address 1</label>
                                        <input type="text" name="address1" class="form-control" id="billing_address1" placeholder="Address 1" value="">
                                    </div>
    
                                    <div class="col-sm-4">
                                        <label for="address2" class="form-label">Address 2</label>
                                        <input type="text" name="address2" class="form-control" id="billing_address2" placeholder="Address 2" value="">
                                    </div>
    
                                    <div class="col-sm-4">
                                        <label for="address3" class="form-label">Address 3</label>
                                        <input type="text" name="address3" class="form-control" id="billing_address3" placeholder="Address 3" value="">
                                    </div>
    
                                    <div class="col-sm-3 mt-2">
                                        <label for="city" class="form-label">City</label>
                                        <input type="text" name="city" class="form-control" id="billing_city" placeholder="City" value="">
                                    </div>
    
                                    <div class="col-sm-3 mt-2">
                                        <label for="state" class="form-label">State</label>
                                        <input type="text" name="state" class="form-control" id="billing_state" placeholder="State" value="">
                                    </div>
    
                                    <div class="col-sm-3 mt-2">
                                        <label for="country" class="form-label">Country</label>
                                        <input type="text" name="country" class="form-control" id="billing_country" placeholder="Country" value="">
                                    </div>
    
                                    <div class="col-sm-3 mt-2">
                                        <label for="pincode" class="form-label">Pin Code</label>
                                        <input type="text" name="billing_pincode" class="form-control" id="pincode" placeholder="Pin Code" value="">
                                    </div>
    
                                </div>
    
                                <a class="text-dark text-decoration-none mt-2 mb-4 d-flex" href="#shipping_address" data-bs-toggle="collapse" role="button">
                                    <i class="fa fa-chevron-down me-2 text-primary"></i>
                                    <h5 class="mb-0">Shipping Address</h5>
                                </a>
    
                                <div id="shipping_address" class="collapse row">
    
                                    <div class="mb-3 col-md-4">
                                        <label for="shipping_firstname">First Name</label>
                                        <input type="text" class="form-control" placeholder="Enter First Name" name="shipping_firstname" id="shipping_firstname">
                                    </div>
    
                                    <div class="mb-3 col-md-4">
                                        <label for="shipping_lastname">Last Name</label>
                                        <input type="text" class="form-control" placeholder="Enter Last Name" name="shipping_lastname" id="shipping_lastname">
                                    </div>
    
                                    <div class="mb-3 col-md-4">
                                        <label for="shipping_phone">Phone No</label>
                                        <input type="text" class="form-control" placeholder="Enter Phone No" name="shipping_phone" id="shipping_phone">
                                    </div>
    
                                    <div class="mb-3 col-md-4">
                                        <label for="shipping_address1">Address Line 1</label>
                                        <input type="text" class="form-control" placeholder="Enter Address Line 1" name="shipping_address1" id="shipping_address1">
                                    </div>
    
                                    <div class="mb-3 col-md-4">
                                        <label for="shipping_address2">Address Line 2</label>
                                        <input type="text" class="form-control" placeholder="Enter Address Line 2" name="shipping_address2" id="shipping_address2">
                                    </div>
    
                                    <div class="mb-3 col-md-4">
                                        <label for="shipping_address3">Address Line 3</label>
                                        <input type="text" class="form-control" placeholder="Enter Address Line 3" name="shipping_address3" id="shipping_address3">
                                    </div>
    
                                    <div class="mb-3 col-md-3 mt-2">
                                        <label for="shipping_city">City</label>
                                        <input type="text" class="form-control" placeholder="Enter City" name="shipping_city" id="shipping_city">
                                    </div>
    
                                    <div class="mb-3 col-md-3 mt-2">
                                        <label for="shipping_state">State</label>
                                        <input type="text" class="form-control" placeholder="Enter State" name="shipping_state" id="shipping_state">
                                    </div>
    
                                    <div class="mb-3 col-md-3 mt-2">
                                        <label for="shipping_pincode">Pin Code</label>
                                        <input type="text" class="form-control" placeholder="Enter Pin Code" name="shipping_pincode" id="shipping_pincode">
                                    </div>
    
                                    <div class="mb-3 col-md-3 mt-2">
                                        <label for="shipping_country">Country</label>
                                        <input type="text" class="form-control" placeholder="Enter Country" name="shipping_country" id="shipping_country">
                                    </div>
    
                                </div>
    
                                <a class="text-dark text-decoration-none mt-2 mb-4 d-flex" href="#additional_fields" data-bs-toggle="collapse" role="button">
                                    <i class="fa fa-chevron-down me-2 text-primary"></i>
                                    <h5 class="mb-0">Additional Fields </h5>
                                </a>
    
                                <div id="additional_fields" class="collapse row">
    
                                    <div class="mb-3 col-md-6">
                                        <label for="udf1">udf 1</label>
                                        <input type="text" class="form-control" id="udf1" placeholder="Enter udf 1" name="udf1">
                                    </div>
    
                                    <div class="mb-3 col-md-6">
                                        <label for="udf2">udf 2</label>
                                        <input type="text" class="form-control" id="udf2" placeholder="Enter udf 2" name="udf2">
                                    </div>
    
                                    <div class="mb-3 col-md-6">
                                        <label for="udf3">udf 3</label>
                                        <input type="text" class="form-control" id="udf3" placeholder="Enter udf 3" name="udf3">
                                    </div>
    
                                    <div class="mb-3 col-md-6">
                                        <label for="udf4">udf 4</label>
                                        <input type="text" class="form-control" id="udf4" placeholder="Enter udf 4" name="udf4">
                                    </div>
    
                                    <div class="mb-3 col-md-6">
                                        <label for="udf4">udf 5</label>
                                        <input type="text" class="form-control" id="udf5" placeholder="Enter udf 5" name="udf5">
                                    </div>
    
                                </div>
                            </div>
    
                            <button class="w-100 my-4 btn btn-primary btn-lg" type="submit" name="pay_now">Pay Now</button>
                        </form>
                    </div>
                </div>
            </main>
    
        </div>
    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        
    </body>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function() {
      const fetchLink = document.getElementById('fetchLink');
      const myForm = document.getElementById('myForm');
      fetchLink.addEventListener('click', function(event) {
        event.preventDefault();let merId=false;
        const merchant_id =  document.getElementById('mid');
        console.log(merchant_id.value);
        if(merchant_id !=null){
            merId=merchant_id.value;
        }
        const access_code =  document.getElementById('access_code');
        let access =false;
        if(access_code !=null){
            access=access_code.value;
        }
        const mode=document.getElementById('mode');
        let  mode2 =false;
        if(mode !=null){
            mode2=mode.value;
        }
        const secret=document.getElementById('secret');
        let secret2 =false;
        if(secret !=null){
            secret2=secret.value;
        }
        const txn_id=document.getElementById('txn_id');
        let txn_id2 =false;
        if(txn_id !=null){
            txn_id2=txn_id.value;
        }
        const baseUrl = 'http://localhost:8081/fetch';
        const dynamicUrl = `http://localhost:8081/fetch?merchant_id=`+merId+"&"+"access_code"+"="+access+"&"+"mode"+"="+mode2+"&"+"secret"+"="+secret2+"&"+"txn_id"+"="+txn_id2;
        window.open(dynamicUrl, '_blank');
      });
    });
    </script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const fetchLink = document.getElementById('emiLink');
            console.log("hello there");
          const myForm = document.getElementById('myForm');
          emiLink.addEventListener('click', function(event) {
            event.preventDefault();let merId=false;
            const merchant_id =  document.getElementById('mid');
            if(merchant_id !=null){
                merId=merchant_id.value;
            }
            const access_code =  document.getElementById('access_code');
            let access =false;
            if(access_code !=null){
                access=access_code.value;
            }
            const mode=document.getElementById('mode');
            let  mode2 =false;
            if(mode !=null){
                mode2=mode.value;
            }
            const secret=document.getElementById('secret');
            let secret2 =false;
            if(secret !=null){
                secret2=secret.value;
            }
            const productCode1=document.getElementById('product_code1');
            let product_code1 =false;
            if(productCode1 !=null){
                product_code1=productCode1.value;
            }
            const productAmount1=document.getElementById('product_amount1');
            let product_amount1 =false;
            if(productAmount1 !=null){
                product_amount1=productAmount1.value;
            }
            const productCode2=document.getElementById('product_code2');
            let product_code2 =false;
            if(productCode2 !=null){
                product_code2=productCode2.value;
            }
            const productAmount2=document.getElementById('product_amount2');
            let product_amount2 =false;
            if(productAmount2 !=null){
                product_amount2=productAmount2.value;
            }
            const amount=document.getElementById('amount_in_paisa');
            let amount_in_paisa =false;
            if(amount !=null){
                amount_in_paisa=amount.value;
            }
            const dynamicUrl = `http://localhost:8081/emiCalculator?merchant_id=`+merId+"&"+"access_code"+"="+access+"&"+"mode"+"="+mode2+"&"+"secret"+"="+secret2+"&"+"product_code1"+"="+product_code1+"&"+"product_code2"+"="+product_code2+"&"+"amount_in_paisa"+"="+amount_in_paisa+"&"+"product_amount1"+"="+product_amount1+"&"+"product_amount2"+"="+product_amount2;
            window.open(dynamicUrl, '_blank');
          });
        });
        </script>
</html>
