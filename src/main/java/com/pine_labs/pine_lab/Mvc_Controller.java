package com.pine_labs.pine_lab;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pine_lab.api.Api;
import com.pine_lab.api.EMI;
import com.pine_lab.api.Payment;
import java.lang.reflect.Type;

@Controller
public class Mvc_Controller implements ErrorController {

    @RequestMapping("/")
    public String home() {
    return "index";
    }
    @RequestMapping("/emiView")
    public String emiViewPage() {
    return "emiView";
    }
     @RequestMapping("/fetchView")
    public String fetchViewPage() {
    return "fetchView";
    }

    @RequestMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("callback_url", "http://localhost:8081/orderResponse");
        return "form";
    }

    @RequestMapping("/hashVerificationView")
    public String hashVerificationView(Model model) {
        model.addAttribute("callback_url", "http://localhost:8081/orderResponse");
        return "hashView";
    }

    @RequestMapping("/hashVerificationView2")
    public RedirectView hashVerificationView2(Model model) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8081/hashVerificationView");
        return redirectView;
    }

    @RequestMapping("/orderResponse")
    public ModelAndView orderResponse(@RequestParam Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView("orderResponse");
        Gson gson = new Gson();
        String jsonData = gson.toJson(map);
        modelAndView.addObject("resultData", jsonData);
        return modelAndView;
    }

    @PostMapping("/fetch")
    public ModelAndView fetchData(@RequestParam Map<String, Object> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("fetchResponse");
        try {
            Api apip = new Api(Integer.parseInt((String)map.get("merchant_id")), (String)map.get("access_code"), (String)map.get("secret"), Boolean.parseBoolean((String)map.get("pg_mode")));
            Payment obj = apip.payment();
            Map<String, Object> enquiryResponse = obj.fetch((String)map.get("txn_id"), 3);
            Gson gson = new Gson();
            Object enquiryResponse2  = enquiryResponse.get("response");
            String jsonData = gson.toJson(enquiryResponse2);
            modelAndView.addObject("resultData", jsonData);
            return modelAndView;
        } catch (Exception e) {
             System.out.println(e.toString());
            modelAndView.addObject("resultData", "false");
            return modelAndView;
        }
    }

    @PostMapping("/emiCalculator")
    public ModelAndView emiCalculator(@RequestParam Map<String, Object> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("fetchResponse");
        try {
            String jsonInput = (String) map.get("product_details");
             Gson gson1 = new Gson();
            Type listType = List.class; 
            ArrayList<TreeMap<String, Object>> productList = gson1.fromJson(jsonInput, listType);
            Api apip = new Api(Integer.parseInt((String)map.get("merchant_id")),(String)map.get("access_code"), 
            (String)map.get("secret"), Boolean.parseBoolean((String)map.get("pg_mode")));
            EMI obj = apip.emi();
            Map<String, Object> emiResponse = obj.emiCalculation(Long.parseLong((String)map.get("amount_in_paisa")), productList);
            Gson gson = new Gson();
            String jsonResponse = emiResponse.get("response").toString();
            Object enquiryResponse2 = gson.fromJson(jsonResponse, Map.class);
            String jsonData = gson.toJson(enquiryResponse2);
            modelAndView.addObject("resultData", jsonData);
            return modelAndView;
        } catch (Exception e) {
             System.out.println(e.toString());
            modelAndView.addObject("resultData", "false");
            return modelAndView;
        }
    }

    @PostMapping("/hashVerification")
    public ModelAndView hashVerification(@RequestParam Map<String, Object> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("hashResponse");
        try {
            Gson gson = new Gson();
            String jsonInput = (String) map.get("response_data");
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> resultMap = gson.fromJson(jsonInput, type);
            TreeMap<String, Object> dataMap = new TreeMap<>();
            for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                dataMap.put(entry.getKey(),entry.getValue());
            }
            Object secret_dia="";          
            if(dataMap.containsKey("ppc_DIA_SECRET")){
                secret_dia=dataMap.get("ppc_DIA_SECRET");
                dataMap.remove("ppc_DIA_SECRET");
            }
            else if(dataMap.containsKey("dia_secret")){
                secret_dia=dataMap.get("dia_secret");
                dataMap.remove("dia_secret");
            }
            if(dataMap.containsKey("ppc_DIA_SECRET_TYPE")){
                dataMap.remove("ppc_DIA_SECRET_TYPE");
            }
            else if(dataMap.containsKey("dia_secret_type")){
                dataMap.remove("dia_secret_type");
            }
            Boolean pg_mode = Boolean.parseBoolean((String) map.get("pg_mode"));
            String secret = (String) map.get("secret");
            String access_code = (String) map.get("access_code");
            Integer merchant_id = Integer.parseInt((String) map.get("merchant_id"));
            Api apip = new Api(merchant_id, access_code, secret, pg_mode);
            Payment obj = apip.payment();
            boolean hashVerification = obj.verifyHash((String)secret_dia, dataMap);
            modelAndView.addObject("status", hashVerification);
            return modelAndView;
        } catch (Exception e) {
             System.out.println(e.toString());
            modelAndView.addObject("resultData", "false");
            return modelAndView;
        }
    }

    @PostMapping("/submit")
    public RedirectView postForm(@RequestParam Map<String, Object> map) throws Exception {
        try {
            Boolean pg_mode = Boolean.parseBoolean((String) map.get("pg_mode"));
            String secret = (String) map.get("secret");
            String access_code = (String) map.get("access_code");
            Integer merchant_id = Integer.parseInt((String) map.get("merchant_id"));
            ArrayList<String> paymentModes = new ArrayList<String>();
            TreeMap<String, Object> customerData = new TreeMap<>();
             TreeMap<String, String> billing_data = new TreeMap<>();
            if (map.get("payment_mode1") != null) {
                paymentModes.add((String)map.get("payment_mode1"));
            }
            if (map.get("payment_mode2") != null) {
                paymentModes.add((String)map.get("payment_mode2"));
            }
            if (map.get("payment_mode3") != null) {
                paymentModes.add((String)map.get("payment_mode3"));
            }
            if (map.get("payment_mode4") != null) {
                paymentModes.add((String)map.get("payment_mode4"));
            }
            if (map.get("payment_mode5") != null) {
                paymentModes.add((String)map.get("payment_mode5"));
            }
            if (map.get("payment_mode6") != null) {
                paymentModes.add((String)map.get("payment_mode6"));
            }
            if (map.get("payment_mode7") != null) {
                paymentModes.add((String)map.get("payment_mode7"));
            }
            if (map.get("payment_mode8") != null) {
                paymentModes.add((String)map.get("payment_mode8"));
            }
            if (map.get("payment_mode9") != null) {
                paymentModes.add((String)map.get("payment_mode9"));
            }
            String txnId = (String) map.get("txn_id");
            String callback_url = (String) map.get("callback_url");
            Long amount = Long.parseLong((String) map.get("amount_in_paisa"));
            String customerId = (String) map.get("customer_id");
            customerId.trim();
            if (customerId.length() != 0) {
                customerData.put("customer_id", customerId);
            }
            String email_id = (String) map.get("email");
            email_id.trim();
            if (email_id.length() != 0) {
                customerData.put("email_id", email_id);
            }
            String first_name = (String) map.get("first_name");
            first_name.trim();
            if (first_name.length() != 0) {
                customerData.put("first_name", first_name);
            }
            String last_name = (String) map.get("last_name");
            last_name.trim();
            if (last_name.length() != 0) {
                customerData.put("last_name", last_name);
            }
            String mobile_no = (String) map.get("phone");
            mobile_no.trim();
            if (mobile_no.length() != 0) {
                customerData.put("mobile_no", mobile_no);
            }
            String address1 = (String) map.get("address1");
            address1.trim();
            if (address1.length() != 0) {
                billing_data.put("address1", address1);
            }
            String address2 = (String) map.get("address2");
            address2.trim();
            if (address2.length() != 0) {
                billing_data.put("address2", address2);
            }
            String address3 = (String) map.get("address3");
            address3.trim();
            if (address3.length() != 0) {
                billing_data.put("address3", address3);
            }
            String city = (String) map.get("city");
            city.trim();
            if (address3.length() != 0) {
                billing_data.put("city", city);
            }
            String state = (String) map.get("state");
            state.trim();
            if (state.length() != 0) {
                billing_data.put("state", state);
            }
            String country = (String) map.get("country");
            country.trim();
            if (country.length() != 0) {
                billing_data.put("country", country);
            }
            String billing_pincode = (String) map.get("billing_pincode");
            billing_pincode.trim();
            if (billing_pincode.length() != 0) {
                billing_data.put("billing_data", billing_pincode);
            }
            TreeMap<String, String> shipping_data = new TreeMap<>();
            String shipping_firstname = (String) map.get("shipping_firstname");
            shipping_firstname.trim();
            if (shipping_firstname.length() != 0) {
                shipping_data.put("shipping_firstname", shipping_firstname);
            }
            String shipping_phone = (String) map.get("shipping_firstname");
            shipping_phone.trim();
            if (shipping_phone.length() != 0) {
                shipping_data.put("shipping_phone", shipping_phone);
            }
            String shipping_address1 = (String) map.get("shipping_address1");
            shipping_address1.trim();
            if (shipping_address1.length() != 0) {
                shipping_data.put("shipping_address1", shipping_address1);
            }
            String shipping_address2 = (String) map.get("shipping_address2");
            shipping_address2.trim();
            if (shipping_address2.length() != 0) {
                shipping_data.put("shipping_address2", shipping_address2);
            }
            String shipping_address3 = (String) map.get("shipping_address3");
            shipping_address3.trim();
            if (shipping_address3.length() != 0) {
                shipping_data.put("shipping_address3", shipping_address3);
            }
            String shipping_city = (String) map.get("shipping_city");
            shipping_city.trim();
            if (shipping_city.length() != 0) {
                shipping_data.put("shipping_city", shipping_city);
            }
            String shipping_state = (String) map.get("shipping_state");
            shipping_state.trim();
            if (shipping_state.length() != 0) {
                shipping_data.put("shipping_state", shipping_state);
            }
            String shipping_pincode = (String) map.get("shipping_pincode");
            shipping_pincode.trim();
            if (shipping_pincode.length() != 0) {
                shipping_data.put("shipping_pincode", shipping_pincode);
            }
            String shipping_country = (String) map.get("shipping_country");
            shipping_country.trim();
            if (shipping_country.length() != 0) {
                shipping_data.put("shipping_country", shipping_country);
            }
            TreeMap<String, Object> udfData = new TreeMap<>();
            String udf1 = (String) map.get("udf1");
            udf1.trim();
            if (udf1.length() != 0) {
                udfData.put("udf1", udf1);
            }
            String udf2 = (String) map.get("udf2");
            udf2.trim();
            if (udf2.length() != 0) {
                udfData.put("udf2", udf2);
            }
            String udf3 = (String) map.get("udf3");
            udf3.trim();
            if (udf3.length() != 0) {
                udfData.put("udf3", udf3);
            }
            String udf4 = (String) map.get("udf4");
            udf4.trim();
            if (udf4.length() != 0) {
                udfData.put("udf4", udf4);
            }
            String jsonInput = (String) map.get("product_details");
            Gson gson1 = new Gson();
            Type listType = List.class; 
            ArrayList<HashMap<String, Object>> productList = gson1.fromJson(jsonInput, listType);
            customerData.put("billing_data",billing_data);
            customerData.put("shipping_data",shipping_data);
            Api apip = new Api(merchant_id, access_code, secret, pg_mode);
            Payment obj = apip.payment();
            Map<String, Object> paymentResponse = obj.create(txnId, amount, callback_url, customerData, udfData,paymentModes,productList);
            String jsonResponse = paymentResponse.get("response").toString();
            Gson gson = new Gson();
            Map<String, Object> jsonMap = gson.fromJson(jsonResponse, Map.class);
            String token = (String) jsonMap.get("redirect_url");
            ModelAndView modelAndView = new ModelAndView("result");
            RedirectView redirectView = new RedirectView();
            if (token != null) {
                redirectView.setUrl(token);
                return redirectView;
            } else {
                modelAndView.addObject("resultData", "false");
            }
            if (token == null) {
                modelAndView.addObject("error", "Error while processing request");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://localhost:8081/form");
            return redirectView;
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8081/form");
        return redirectView;
    }

}
