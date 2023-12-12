# Java SDK

## Basic Usage

Supported Features

- 1. Create Order
- 2. Fetch Order
- 3. EMI Calculator
- 4. Hash Verification

####  Only Non Seamless Integration Api Supported

    This sdk only supports non seamless integration, by non seamless we mean the the use will alway need to redirect the end user to payment gateways where he'll select his preferred payment method and complete payment.

API ENDPOINT :   
```java
[ UAT ] https://uat.pinepg.in/api/

[ PROD ] https://pinepg.in/api/

```

#### Test Merchant Details

```java
Integer mid= 106598;
String apiAccessCode="4a39a6d4-46b7-474d-929d-21bf0e9ed607";
String secret_key="55E0F73224EC458A8EC0B68F7B47ACAE";
boolean isTest=true; //flase for production
```

#### Create an API instance 
```java
 Api api = new Api(mid, apiAccessCode, secret_key, isTest);
 Payment apiPayment = api.payment();
 EMI emiCalculaor=api.emi();
```
 ## 1. Create Order Api

#### Body Parameters
#
Amount(Mandatory)
```java
Long amount=1000l;
```
Unique Transaction Id(Mandatory)
```java
String txnId="txnID17ass1aassdd6r6r";
```
Callback Url(Mandatory)
```java
String merchant_return_url="http://localhost:8000/api/callback/result";
String convertedUrl = merchant_return_url.replace("/", "\\/");
```

Customer Data(Optional/Null)
```java
Map<String,Object> customerData=new TreeMap<String,Object>();
customerData.put("email_id","");customerData.put("first_name","");customerData.put("last_name","");customerData.put("customer_id","");
customerData.put("mobile_no","");
Map<String,Object> billing_data=new TreeMap<String,Object>();
billing_data.put("address1","");billing_data.put("address2","");billing_data.put("address3","");billing_data.put("pincode","");
billing_data.put("city","");billing_data.put("state","");billing_data.put("country","");
Map<String,Object> shipping_data=new TreeMap<String,Object>();
shipping_data.put("first_name","");shipping_data.put("last_name","");shipping_data.put("mobile_no","");shipping_data.put("address1","");
shipping_data.put("address2","");shipping_data.put("address3","");shipping_data.put("pincode","");shipping_data.put("city","");
shipping_data.put("state","");shipping_data.put("country","");
customerData.put("billing_data",billing_data);customerData.put("shipping_data",shipping_data);
```
Udf Data(Optional/Null)
```java
Map<String,Object> udf_data=new TreeMap<String,Object>();
udf_data.put("udf_field_1","");udf_data.put("udf_field_2","");udf_data.put("udf_field_3","");udf_data.put("udf_field_4","");
udf_data.put("udf_field_5","");
```
Payment Modes(Mandatory)
```java
String[] paymentModes=["4","3","1"];
```
Create a order 
```java
 Map<String,Object> paymentResponse = apiPayment.create(txnId, amount,convertedUrl,customerData,udf_data,paymentModes);
 System.out.println(paymentResponse.toString());
```
### Response 

Success Response
```java
{response={"token":"S01Nox%2f6%2bge3%2fZLU10i35LmRBFO8jX8RKaa5K1J%2fwkyg7s%3d","redirect_url":"https://uat.pinepg.in/pinepg/v2/process/payment?token=S01Nox%2f6%2bge3%2fZLU10i35LmRBFO8jX8RKaa5K1J%2fwkyg7s%3d","response_code":1,"response_message":"SUCCESS"}}
```
Failure Response : 

``` java
{response={"respone_message":"SECURE HASH RETURNED FROM MERCHANT NOT MATCH","respone_code":-34}}
```

## 2. Fetch Order Api

#### Body Parameters
#
Transaction Id(Mandatory)
```java
String txnId="txnID17ass1aassdd6r6r";
```
Transaction Type(Mandatory)
```java
Integer txnType=3;//For Enquiry
```
Fetch Order Method Call
```Java
Map<String,Object> enquiryResponse = apiPayment.fetch("txnID17as1aassdd6r6r", 3);
System.out.println(enquiryResponse.toString());
```
Success Response
```java
{response={"ppc_MerchantID":"106598","ppc_MerchantAccessCode":"4a39a6d4-46b7-474d-929d-21bf0e9ed607","ppc_PinePGTxnStatus":"7","ppc_TransactionCompletionDateTime":"21/09/2023 05:44:07 PM","ppc_UniqueMerchantTxnID":"650acb67d3752","ppc_Amount":"10000","ppc_TxnResponseCode":"1","ppc_TxnResponseMessage":"SUCCESS","ppc_PinePGTransactionID":"12071550","ppc_CapturedAmount":"10000","ppc_RefundedAmount":"0","ppc_AcquirerName":"BILLDESK","ppc_DIA_SECRET":"CFACB73375C4602F21EBEDF5288AF4A85FE74C47D8FF3509D4CAF091E73D3985","ppc_DIA_SECRET_TYPE":"SHA256","ppc_PaymentMode":"3","ppc_Parent_TxnStatus":"4","ppc_ParentTxnResponseCode":"1","ppc_ParentTxnResponseMessage":"SUCCESS","ppc_CustomerMobile":"9876543210","ppc_UdfField1":"","ppc_UdfField2":"","ppc_UdfField3":"","ppc_UdfField4":"","ppc_AcquirerResponseCode":"0300","ppc_AcquirerResponseMessage":"NA"}, status=true}
```

## 3. Emi Calculator Api

#### Body Parameters
#
Amount(Mandatory)
```java
Long amount=1000l;
```
Product Details (Optional/Null)
```java
ArrayList<TreeMap<String,Object>> product_details=new ArrayList<TreeMap<String,Object>>();
TreeMap<String,Object> products=new TreeMap<String,Object>();
products.put("product_code","testprod01");products.put("product_amount",500000);
product_details.add(products);
```
Emi Calculator Method Call
```Java
Map<String,Object> emiResponse = emiCalculaor.emiCalculation(x,product_details);
System.out.println(emiResponse.toString());
```

### Response 

Success Response
```java
{response={"issuer":[{"list_emi_tenure":[{"offer_scheme":{"product_details":[{"schemes":[],"product_code":"testprod01","product_amount":500000,"subvention_cashback_discount":0,"product_discount":0,"subvention_cashback_discount_percentage":0,"product_discount_percentage":0,"subvention_type":3,"bank_interest_rate_percentage":160000,"bank_interest_rate":13390}],"emi_scheme":{"scheme_id":47353,"program_type":105,"is_scheme_valid":true}},"tenure_id":"3","tenure_in_month":"3","monthly_installment":171130,"bank_interest_rate":160000,"interest_pay_to_bank":13390,"total_offerred_discount_cashback_amount":0,"loan_amount":500000,"auth_amount":500000},{"offer_scheme":{"product_details":[{"schemes":[],"product_code":"testprod01","product_amount":500000,"subvention_cashback_discount":0,"product_discount":0,"subvention_cashback_discount_percentage":0,"product_discount_percentage":0,"subvention_type":3,"bank_interest_rate_percentage":160000,"bank_interest_rate":23590}],"emi_scheme":{"scheme_id":47353,"program_type":105,"is_scheme_valid":true}},"tenure_id":"6","tenure_in_month":"6","monthly_installment":87265,"bank_interest_rate":160000,"interest_pay_to_bank":23590,"total_offerred_discount_cashback_amount":0,"loan_amount":500000,"auth_amount":500000},{"offer_scheme":{"product_details":[{"schemes":[],"product_code":"testprod01","product_amount":500000,"subvention_cashback_discount":0,"product_discount":0,"subvention_cashback_discount_percentage":0,"product_discount_percentage":0,"subvention_type":3,"bank_interest_rate_percentage":160000,"bank_interest_rate":33916}],"emi_scheme":{"scheme_id":47353,"program_type":105,"is_scheme_valid":true}},"tenure_id":"9","tenure_in_month":"9","monthly_installment":59324,"bank_interest_rate":160000,"interest_pay_to_bank":33916,"total_offerred_discount_cashback_amount":0,"loan_amount":500000,"auth_amount":500000},{"offer_scheme":{"product_details":[{"schemes":[],"product_code":"testprod01","product_amount":500000,"subvention_cashback_discount":0,"product_discount":0,"subvention_cashback_discount_percentage":0,"product_discount_percentage":0,"subvention_type":3,"bank_interest_rate_percentage":160000,"bank_interest_rate":44380}],"emi_scheme":{"scheme_id":47353,"program_type":105,"is_scheme_valid":true}},"tenure_id":"12","tenure_in_month":"12","monthly_installment":45365,"bank_interest_rate":160000,"interest_pay_to_bank":44380,"total_offerred_discount_cashback_amount":0,"loan_amount":500000,"auth_amount":500000},{"offer_scheme":{"product_details":[{"schemes":[],"product_code":"testprod01","product_amount":500000,"subvention_cashback_discount":0,"product_discount":0,"subvention_cashback_discount_percentage":0,"product_discount_percentage":0,"bank_interest_rate_percentage":0,"bank_interest_rate":0}],"emi_scheme":{"scheme_id":47353,"program_type":105,"is_scheme_valid":true}},"tenure_id":"96","tenure_in_month":"1","monthly_installment":0,"bank_interest_rate":0,"interest_pay_to_bank":0,"total_offerred_discount_cashback_amount":0,"loan_amount":500000,"auth_amount":500000}],"issuer_name":"HDFC","is_debit_emi_issuer":false},{"list_emi_tenure":[{"offer_scheme":{"product_details":[{"schemes":[],"product_code":"testprod01","product_amount":500000,"subvention_cashback_discount":0,"product_discount":0,"subvention_cashback_discount_percentage":0,"product_discount_percentage":0,"bank_interest_rate_percentage":0,"bank_interest_rate":0}],"emi_scheme":{"scheme_id":47249,"program_type":105,"is_scheme_valid":true}},"tenure_id":"96","tenure_in_month":"1","monthly_installment":0,"bank_interest_rate":0,"interest_pay_to_bank":0,"total_offerred_discount_cashback_amount":0,"loan_amount":500000,"auth_amount":500000}],"issuer_name":"ICICI","is_debit_emi_issuer":false},{"list_emi_tenure":[{"offer_scheme":{"product_details":[{"schemes":[],"product_code":"testprod01","product_amount":500000,"subvention_cashback_discount":0,"product_discount":0,"subvention_cashback_discount_percentage":0,"product_discount_percentage":0,"bank_interest_rate_percentage":0,"bank_interest_rate":0}],"emi_scheme":{"scheme_id":47314,"program_type":105,"is_scheme_valid":true}},"tenure_id":"96","tenure_in_month":"1","monthly_installment":0,"bank_interest_rate":0,"interest_pay_to_bank":0,"total_offerred_discount_cashback_amount":0,"loan_amount":500000,"auth_amount":500000}],"issuer_name":"Kotak Debit","is_debit_emi_issuer":true}],"response_code":1,"response_message":"SUCCESS"}, status=true}
```
Failure Response
```java
{Exception:Invalid Data}
```
## 4. Hash Verification Api

#### Body Parameters
#
RequestObject(Mandatory)
```java
TreeMap<String,Object> dataMap=new TreeMap<>();
dataMap.put("ppc_MerchantID", "106600");dataMap.put("ppc_MerchantAccessCode", "bcf441be-411b-46a1-aa88-c6e852a7d68c");dataMap.put("ppc_PinePGTxnStatus", "7");
dataMap.put("ppc_TransactionCompletionDateTime", "20/09/2023 04:07:52 PM");dataMap.put("ppc_UniqueMerchantTxnID", "650acb67d3752");
dataMap.put("ppc_Amount", "1000");dataMap.put("ppc_TxnResponseCode", "1");dataMap.put("ppc_TxnResponseMessage", "SUCCESS");dataMap.put("ppc_PinePGTransactionID", "12069839");dataMap.put("ppc_CapturedAmount", "1000");dataMap.put("ppc_RefundedAmount", "0");dataMap.put("ppc_AcquirerName", "BILLDESK");dataMap.put("ppc_PaymentMode", "3");dataMap.put("ppc_Parent_TxnStatus", "4");dataMap.put("ppc_ParentTxnResponseCode", "1");dataMap.put("ppc_ParentTxnResponseMessage", "SUCCESS");dataMap.put("ppc_CustomerMobile", "7737291210");dataMap.put("ppc_UdfField1", "");dataMap.put("ppc_UdfField2", "");dataMap.put("ppc_UdfField3", "");dataMap.put("ppc_UdfField4", "");dataMap.put("ppc_AcquirerResponseCode", "0300");dataMap.put("ppc_AcquirerResponseMessage", "NA");
```
Hash(Mandatory)
```java
String hash="D640CFF0FCB8D42B74B1AFD19D97A375DAF174CCBE9555E40CC6236964928896";
```
 Hash Verification Method Call
 ```java
 boolean hashVerification=apiPayment.verifyHash(hash,dataMap);
 System.out.println(hashVerification);
 ```
### Response 
Success
```Java
true 
```
Failure
```Java
false 
```
