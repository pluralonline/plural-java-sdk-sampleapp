package com.pine_lab.api;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Test RUN
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            startProcess();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    public static void startProcess() throws Exception{
         System.out.println("Hi! Welcome to Pine Labs Apis");
            System.out.println("Which process do you want to follow?");
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter 1 to create order"+'\n'+"Enter 2 for fetch Order"+'\n'+"Enter 3 for Emi Calculation"+'\n'+"Enter 4 for Hash Verification"+'\n');
            Integer x= sc.nextInt();
            switch(x){
                case 1:
                createOrder();
                break;
                case 2:
                fetchOrder();
                break;
                case 3:
                emiCalculator();
                break;
                case 4:
                hashVerification();
                break;
                default:
                System.out.println("Wrong input value");
                return;
            }
            System.out.println("Do you still want to continue?"+'\n'+"Yes/No");
            String str=sc.next();
             switch(str){
                case "Yes":
                startProcess();
                break;
                case "No":
                return;
                default:
                System.out.println("Wrong input value");
                return;
            }
    }
    public static void createOrder() throws Exception{
            Scanner sc=new Scanner(System.in);
            System.out.println("Do you want to run test mode"+'\n'+"True for test mode"+'\n'+"False for prod mode");
            boolean isTestMode=sc.nextBoolean();
            Api apip = new Api(106600,"bcf441be-411b-46a1-aa88-c6e852a7d68c","9A7282D0556544C59AFE8EC92F5C85F6",isTestMode);
            Payment obj=apip.payment();
            // String merchant_return_url="http://localhost:8000/api/callback/result";
            System.out.println("Add Payemnt Modes");
             ArrayList<String> list=new ArrayList<>();boolean check=true;
             sc.nextLine();
            while(check){
                System.out.println("Enter Mode");
                String s=sc.next();
                list.add(s);
                System.out.println("Do you want to add more modes"+'\n'+"Yes/No");
                String modeContinue=sc.next();
                if(modeContinue.equals("No")){
                    check=false;
                }
            }
            System.out.println("Enter unique txn Id");
            String txnId=sc.next();
            System.out.println("Enter amount");
            Long amount=sc.nextLong();
            System.out.println("Enter merchant return url");
            String merchant_return_url=sc.next();
            System.out.println(merchant_return_url);
            System.out.println("Do you want to add customer Data"+'\n'+"Yes/No");
            String resp=sc.next();
            TreeMap<String,Object> customerData=new TreeMap<>();
            if(resp.equals("Yes")){
                 System.out.println("Enter email of customer");
                 String email_id=sc.next();
                 System.out.println("Enter first name of customer");
                 String first_name=sc.next();
                 System.out.println("Enter last name of customer");
                 String last_name=sc.next();
                 System.out.println("Enter cusomer id");
                 String customer_id=sc.next();
                 System.out.println("Enter mobile no of customer");
                 String mobile_no=sc.next();
                 customerData.put("email_id",email_id);customerData.put("first_name",first_name);customerData.put("last_name",last_name);customerData.put("customer_id",customer_id);
                 customerData.put("mobile_no",mobile_no);
                  System.out.println("Do you want to add Billing Data"+'\n'+"Yes/No");
            String respBilling=sc.next();
            TreeMap<String,String> billing_data=new TreeMap<>();
            if(respBilling.equals("Yes")){
                 System.out.println("Enter address1 of customer");
                 String address1=sc.next();
                System.out.println("Enter address2 of customer");
                 String address2=sc.next();
                System.out.println("Enter address3 of customer");
                 String address3=sc.next();
                 System.out.println("Enter pincode of customer");
                 String pincode=sc.next();
                 System.out.println("Enter city of customer");
                 String city=sc.next();
                 System.out.println("Enter state of customer");
                 String state=sc.next();
                 System.out.println("Enter country of customer");
                 String country=sc.next();
                 billing_data.put("address1",address1);billing_data.put("address2",address2);billing_data.put("address3",address3);billing_data.put("pincode",pincode);
                 billing_data.put("city",city);billing_data.put("state",state);billing_data.put("country",country);
                 customerData.put("billing_data",billing_data);
          }
          }
          else{
          customerData=null;
          }
          ArrayList<HashMap<String,Object>> product_list=new ArrayList<>();
          Map<String,Object> paymentResponse = obj.create(txnId, amount, merchant_return_url, customerData, null,list,product_list);
          Object responseObject=paymentResponse.get("response");
          String jsonString=responseObject.toString();
          JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
          System.out.println(json);
            return;
    }
    public static void fetchOrder() throws Exception{
        Scanner sc=new Scanner(System.in);
            System.out.println("Do you want to run test mode"+'\n'+"True for test mode"+'\n'+"False for prod mode");
            boolean isTestMode=sc.nextBoolean();
        Api apip = new Api(106600,"bcf441be-411b-46a1-aa88-c6e852a7d68c","9A7282D0556544C59AFE8EC92F5C85F6",isTestMode);
        Payment obj=apip.payment();
        System.out.println("Enter txn Id");
        String txnId=sc.next();
        Map<String,Object> enquiryResponse = obj.fetch(txnId, 3);
        System.out.println(enquiryResponse.toString());
        return;
    }
     public static void emiCalculator() throws Exception{
           Scanner sc=new Scanner(System.in);
            System.out.println("Do you want to run test mode"+'\n'+"True for test mode"+'\n'+"False for prod mode");
            boolean isTestMode=sc.nextBoolean();
        Api apip = new Api(106600,"bcf441be-411b-46a1-aa88-c6e852a7d68c","9A7282D0556544C59AFE8EC92F5C85F6",isTestMode);
        EMI obj=apip.emi();ArrayList<TreeMap<String,Object>> product_details=new ArrayList<TreeMap<String,Object>>();
        TreeMap<String,Object> products=new TreeMap<String,Object>();
        System.out.println("Enter product code");
        String productCode=sc.next();
        products.put("product_code",productCode);
         System.out.println("Enter amount");
        Long amount=sc.nextLong();
        products.put("product_amount",amount);
        product_details.add(products);
         Map<String,Object> emiResponse = obj.emiCalculation(amount,product_details);
        System.out.println(emiResponse.toString());
        return;
    }
    public static void hashVerification() throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException, ParseException{
        Scanner sc=new Scanner(System.in);
            System.out.println("Do you want to run test mode"+'\n'+"True for test mode"+'\n'+"False for prod mode");
            boolean isTestMode=sc.nextBoolean();
     Api apip = new Api(106600,"bcf441be-411b-46a1-aa88-c6e852a7d68c","9A7282D0556544C59AFE8EC92F5C85F6",isTestMode);
     System.out.println("Enter Hash you want to verify");
     String hash=sc.next();
     Payment obj=apip.payment();
     TreeMap<String,Object> dataMap=new TreeMap<>();
     System.out.println("Enter Merchant Id");
     String ppc_MerchantID=sc.next();System.out.println("Enter Merchant Access Code");
     String ppc_MerchantAccessCode=sc.next();
     System.out.println("Enter Pine Pg txn Status");
     String ppc_PinePGTxnStatus=sc.next();
     System.out.println("Transaction Completion date");
     sc.nextLine(); 
     String ppc_TransactionCompletionDateTime=sc.nextLine();
     System.out.println("Enter unique merchant txn id");
     String ppc_UniqueMerchantTxnID=sc.next();
     System.out.println("Enter PPC amount");
     String ppc_Amount=sc.next();
     System.out.println("Enter Txn Response Code");String ppc_TxnResponseCode=sc.next();System.out.println("Enter Txn Response Message");String ppc_TxnResponseMessage=sc.next();
     System.out.println("Enter Pine Pg Txn Id");String ppc_PinePGTransactionID=sc.next();System.out.println("Enter captured Amount");String ppc_CapturedAmount=sc.next();
     System.out.println("Enter Refunded amount");String ppc_RefundedAmount=sc.next();System.out.println("Enter acquirer name");String ppc_AcquirerName=sc.next();
     System.out.println("Enter Payment mode");String ppc_PaymentMode=sc.next();System.out.println("Enter parent txn status");String ppc_Parent_TxnStatus=sc.next();
     System.out.println("Enter Parent txn response code");String ppc_ParentTxnResponseCode=sc.next();
     System.out.println("Enter parent txn response message");String ppc_ParentTxnResponseMessage=sc.next();System.out.println("Enter customer mobile no");String ppc_CustomerMobile=sc.next();
       System.out.println("Do you want to Enter udf field 1"+'\n'+"Yes/No");
       String udfCheck=sc.next();String ppc_UdfField1="";
       if(udfCheck.equals("Yes")){
        ppc_UdfField1=sc.next();
       }
         System.out.println("Do you want to Enter udf field 2"+'\n'+"Yes/No");
        udfCheck=sc.next();String ppc_UdfField2="";
       if(udfCheck.equals("Yes")){
        ppc_UdfField2=sc.next();
       }
         System.out.println("Do you want to Enter udf field 3"+'\n'+"Yes/No");
       udfCheck=sc.next();String ppc_UdfField3="";
       if(udfCheck.equals("Yes")){
        ppc_UdfField3=sc.next();
       }
         System.out.println("Do you want to Enter udf field 4"+'\n'+"Yes/No");
     udfCheck=sc.next();String ppc_UdfField4="";
       if(udfCheck.equals("Yes")){
        ppc_UdfField4=sc.next();
       }
     System.out.println("Enter acquirer response code");
     String ppc_AcquirerResponseCode=sc.next(); System.out.println("Acquirer response message");String ppc_AcquirerResponseMessage=sc.next();
     dataMap.put("ppc_MerchantID", ppc_MerchantID);dataMap.put("ppc_MerchantAccessCode", ppc_MerchantAccessCode);dataMap.put("ppc_PinePGTxnStatus", ppc_PinePGTxnStatus);
     dataMap.put("ppc_TransactionCompletionDateTime", ppc_TransactionCompletionDateTime);dataMap.put("ppc_UniqueMerchantTxnID", ppc_UniqueMerchantTxnID);
     dataMap.put("ppc_Amount", ppc_Amount);dataMap.put("ppc_TxnResponseCode", ppc_TxnResponseCode);dataMap.put("ppc_TxnResponseMessage", ppc_TxnResponseMessage);
     dataMap.put("ppc_PinePGTransactionID", ppc_PinePGTransactionID);dataMap.put("ppc_CapturedAmount", ppc_CapturedAmount);dataMap.put("ppc_RefundedAmount", ppc_RefundedAmount);
     dataMap.put("ppc_AcquirerName", ppc_AcquirerName);dataMap.put("ppc_PaymentMode", ppc_PaymentMode);dataMap.put("ppc_Parent_TxnStatus", ppc_Parent_TxnStatus);
     dataMap.put("ppc_ParentTxnResponseCode", ppc_ParentTxnResponseCode);dataMap.put("ppc_ParentTxnResponseMessage", ppc_ParentTxnResponseMessage);dataMap.put("ppc_CustomerMobile", ppc_CustomerMobile);dataMap.put("ppc_UdfField1", ppc_UdfField1);
     dataMap.put("ppc_UdfField2", ppc_UdfField2);dataMap.put("ppc_UdfField3", ppc_UdfField3);dataMap.put("ppc_UdfField4", ppc_UdfField4);dataMap.put("ppc_AcquirerResponseCode", ppc_AcquirerResponseCode);dataMap.put("ppc_AcquirerResponseMessage", ppc_AcquirerResponseMessage);
    System.out.println(dataMap.toString());
     boolean hashVerification=obj.verifyHash(hash,dataMap);
     System.out.println(hashVerification);
    }
}
