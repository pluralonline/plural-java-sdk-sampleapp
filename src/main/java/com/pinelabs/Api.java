package com.pinelabs;

public class Api {
    private Integer merchantId;
    private String apiAccessCode;
    private String secret;
    private boolean isTestMode;

    public Api(Integer merchantId, String apiAccessCode, String secret, boolean isTestMode) {
        this.merchantId = merchantId;
        this.apiAccessCode = apiAccessCode;
        this.secret = secret;
        this.isTestMode = isTestMode;
    }

    public Payment payment() {
        return new Payment(merchantId, apiAccessCode, secret, isTestMode);
    }

    public EMI emi() {
        return new EMI(merchantId, apiAccessCode, secret, isTestMode);
    }
}
