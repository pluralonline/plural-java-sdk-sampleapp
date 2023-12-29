package com.pine_lab.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Payment {
    private Integer merchantId;
    private String apiAccessCode;
    private String secret;
    private boolean isTestMode;
    private String prodDomain = "https://pinepg.in/";
    private String testDomain = "https://uat.pinepg.in/";
    private Hash hash;

    public Payment(Integer merchantId, String apiAccessCode, String secret, boolean isTestMode) {
        this.merchantId = merchantId;
        this.apiAccessCode = apiAccessCode;
        this.secret = secret;
        this.isTestMode = isTestMode;
        this.hash = new Hash(apiAccessCode, secret);
    }

    private String getApiUrl() {
        String url = isTestMode ? testDomain : prodDomain;
        return url + "api/";
    }

    public Map<String, Object> create(String txnID, Long amount, String callbackUrl, Map<String, Object> customerData,
            Map<String, Object> udfData, ArrayList<String> paymentModes,ArrayList<HashMap<String,Object>> product_list)
            throws Exception {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        Map<String, Object> merchant_data = new LinkedHashMap<>();
        merchant_data.put("merchant_id", this.merchantId);
        merchant_data.put("merchant_access_code", this.apiAccessCode);
        merchant_data.put("unique_merchant_txn_id", txnID);
        merchant_data.put("merchant_return_url", callbackUrl);
        requestBody.put("merchant_data", merchant_data);
        if (customerData != null) {
            requestBody.put("customer_data", customerData);
        }
        Map<String, Object> paymentDataObject = new LinkedHashMap<>();
        paymentDataObject.put("amount_in_paisa", amount);
        requestBody.put("payment_data", paymentDataObject);
        Map<String, Object> txnObject = new LinkedHashMap<>();
        String paymentModesConcat = "";
        Map<String,Integer> product_codes=this.productCodes();
        
        for (int i = 0; i < paymentModes.size(); i++) {
        if(product_codes.containsKey(paymentModes.get(i))){
        paymentModesConcat = paymentModesConcat + product_codes.get(paymentModes.get(i));
        if (i < (paymentModes.size() - 1)) {
        paymentModesConcat = paymentModesConcat + ",";
        }
        }
        }
        txnObject.put("navigation_mode", 2);
        txnObject.put("payment_mode", paymentModesConcat);
        txnObject.put("transaction_type", 1);
        requestBody.put("txn_data", txnObject);
        requestBody.put("product_details", product_list);
        if (udfData != null) {
            requestBody.put("udf_data", udfData);
        }
        Map<String, Object> requestBodyCleaned = clean(requestBody);
        Map<String, Object> mapStatus = new HashMap<>();
        mapStatus.put("result", requestBodyCleaned);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonString2 = gson.toJson(requestBodyCleaned);
        jsonString2 = jsonString2.replace("\\/", "/");
        String encodedRequestBody = Base64.getEncoder().encodeToString(jsonString2.getBytes());
        Map<String, Object> request = new TreeMap<String, Object>();
        request.put("request", encodedRequestBody);
        Map<String, String> headers = new TreeMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-VERIFY", hash.jsonHash(encodedRequestBody));
        String response = sendPOSTRequest(getApiUrl() + "v2/accept/payment", request, headers);
        JsonObject responseObject = JsonParser.parseString(response).getAsJsonObject();
        if (responseObject.has("response_code") && responseObject.get("response_code").getAsInt() != 1) {
            throw new Exception(
                    responseObject.has("response_message") ? responseObject.get("response_message").getAsString()
                            : "Something went wrong");
        }
        Map<String, Object> resultMap = new TreeMap<>();
        resultMap.put("status", true);
        resultMap.put("response", responseObject);
        return resultMap;
    }

    private Map<String, Object> clean(Map<String, Object> arr) {
        return arr.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().toString().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public Map<String,Integer> productCodes(){
        HashMap<String,Integer> product_codes=new HashMap<>();
        product_codes.put("cards",1);product_codes.put("netbanking",3);product_codes.put("emi",4);product_codes.put("upi",10);
        product_codes.put("wallet",11);product_codes.put("debit_emi",14);product_codes.put("prebooking",16);product_codes.put("bnpl",17);
        product_codes.put("cardless_emi",19);
        return product_codes;
    }

    public Map<String, Object> fetch(String txnID, int txnType) throws Exception {
        Map<String, Object> requestBody = new TreeMap<>();
        requestBody.put("ppc_MerchantID", merchantId);
        requestBody.put("ppc_MerchantAccessCode", apiAccessCode);
        requestBody.put("ppc_TransactionType", txnType);
        requestBody.put("ppc_UniqueMerchantTxnID", txnID);
        String returnHash = this.hash.encodedHash(requestBody);
        requestBody.put("ppc_DIA_SECRET", returnHash);
        requestBody.put("ppc_DIA_SECRET_TYPE", "sha256");
        final URL url = new URL(getApiUrl() + "PG/V2");
        RequestBody formBody = new FormBody.Builder()
                .add("ppc_DIA_SECRET", "" + requestBody.get("ppc_DIA_SECRET"))
                .add("ppc_DIA_SECRET_TYPE", "" + requestBody.get("ppc_DIA_SECRET_TYPE"))
                .add("ppc_MerchantID", "" + requestBody.get("ppc_MerchantID"))
                .add("ppc_TransactionType", "" + requestBody.get("ppc_TransactionType"))
                .add("ppc_UniqueMerchantTxnID", "" + requestBody.get("ppc_UniqueMerchantTxnID"))
                .add("ppc_MerchantAccessCode", "" + requestBody.get("ppc_MerchantAccessCode"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "x-www-form-urlencoded")
                .post(formBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        String responseString = responseBody.string();
        String response2 = responseString;
        JsonObject responseObject = JsonParser.parseString(response2).getAsJsonObject();

        if (responseObject.has("response_code") && responseObject.get("response_code").getAsInt() != 1) {
            throw new Exception(
                    responseObject.has("response_message") ? responseObject.get("response_message").getAsString()
                            : "Something went wrong");
        }
        Map<String, Object> resultMap = new TreeMap<>();
        resultMap.put("status", true);
        resultMap.put("response", responseObject);
        return resultMap;
    }

    public boolean verifyHash(String hash, Map<String, Object> body)
            throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
        return this.hash.verifyHash(hash, body);
    }

    private String sendPOSTRequest(String uri, Map<String, Object> body, Map<String, String> headers)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        final URL url = new URL(uri);
        String json = objectMapper.writeValueAsString(body);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body2 = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-VERIFY", headers.get("X-VERIFY"))
                .post(body2)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        String responseString = responseBody.string();
        return responseString;
    }
}
