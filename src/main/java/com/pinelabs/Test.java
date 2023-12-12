package com.pinelabs;

/**
 * Test RUN
 *
 */
public class Test {
    public static void main(String[] args) {
        try {

            Api apip = new Api(
                    106600,
                    "bcf441be-411b-46a1-aa88-c6e852a7d68c",
                    "9A7282D0556544C59AFE8EC92F5C85F6",
                    true);

            EMI output = apip.emi();

            System.out.println(output.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

    }
}
