package com.pine_lab.api;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Hash {
    private String apiKey;
    private String secret;

    public Hash(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public String hex2Str(String hex) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String hexPair = hex.substring(i, i + 2);
            string.append((char) Integer.parseInt(hexPair, 16));
        }
        return string.toString();
    }

    public String jsonHash(String request)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(DatatypeConverter.parseHexBinary(secret), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(request.getBytes("UTF-8"));
        String ss = String.format("%02x", new BigInteger(1, hmacBytes));
        return bytesToHex(hmacBytes).toUpperCase();
    }

    public String encodedHash(Map<String, Object> request)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

        StringBuilder dataString = new StringBuilder();
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            dataString.append(key).append("=").append(value).append("&");
        }
        if (dataString.length() > 0) {
            dataString.setLength(dataString.length() - 1);
        }
        String resultString = dataString.toString();
        return calculateHmacSha256(resultString, this.secret).toUpperCase();
    }

    public boolean verifyHash(String receivedHash, Map<String, Object> request)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        StringBuilder dataString = new StringBuilder();
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Append key and value to dataString
            dataString.append(key).append("=").append(value).append("&");
        }
        // Remove the trailing "&" if it exists
        if (dataString.length() > 0) {
            dataString.setLength(dataString.length() - 1);
        }
        String resultString = dataString.toString();
        String calculatedHash = calculateHmacSha256(resultString, secret).toUpperCase();
        return receivedHash.equals(calculatedHash);
    }

    private String calculateHmacSha256(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(DatatypeConverter.parseHexBinary(secret), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes("UTF-8"));
        return bytesToHex(hmacBytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

}
