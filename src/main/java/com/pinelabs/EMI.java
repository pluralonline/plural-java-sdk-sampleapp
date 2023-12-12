package com.pinelabs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EMI {
    private Integer merchantId;
    private String apiAccessCode;
    private String secret;
    private boolean isTestMode;
    private String prodDomain = "https://pinepg.in/";
    private String testDomain = "https://uat.pinepg.in/";

    public EMI(Integer merchantId, String apiAccessCode, String secret, boolean isTestMode) {
        this.merchantId = merchantId;
        this.apiAccessCode = apiAccessCode;
        this.secret = secret;
        this.isTestMode = isTestMode;
    }

    public Map<String, Object> emiCalculation(Long amount, ArrayList<TreeMap<String, Object>> product_list)
            throws IOException {
        Map<String, Object> requestBody = new TreeMap<String, Object>();
        Map<String, Object> merchant_data = new TreeMap<String, Object>();
        merchant_data.put("merchant_access_code", this.apiAccessCode);
        merchant_data.put("merchant_id", this.merchantId);
        requestBody.put("merchant_data", merchant_data);
        Map<String, Object> payment_data = new TreeMap<String, Object>();
        payment_data.put("amount_in_paisa", amount);
        requestBody.put("payment_data", payment_data);
        requestBody.put("product_details", product_list);
        String response = sendPOSTRequest(getApiUrl() + "v2/emi/calculator", requestBody);
        JsonObject responseObject = JsonParser.parseString(response).getAsJsonObject();
        if (responseObject.has("response_code") && responseObject.get("response_code").getAsInt() != 1) {
            throw new IOException(
                    responseObject.has("response_message") ? responseObject.get("response_message").getAsString()
                            : "Something went wrong");
        }
        Map<String, Object> resultMap = new TreeMap<>();
        resultMap.put("status", true);
        resultMap.put("response", responseObject);
        return resultMap;
    }

    private String sendPOSTRequest(String uri, Map<String, Object> body) throws IOException {
        URL url = new URL(uri);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String json = objectMapper.writeValueAsString(body);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body2 = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body2)
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        String responseString = responseBody.string();
        return responseString;
    }

    private String getApiUrl() {
        String url = isTestMode ? testDomain : prodDomain;
        return url + "api/";
    }
}
