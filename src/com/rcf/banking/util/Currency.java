package com.rcf.banking.util;

/**
 * This class defines the currency used in the banking system
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public enum Currency {

    /* Currently, this system only supports HKD, SGD and USD */
    HKD("HKD", "Hong Kong Dollar"),
    SGD("SGD", "Singapore Dollar"),
    USD("USD", "United States Dollar");

    private String code;
    private String description;

    /**
     * Constructor
     *
     * @param code        the currency code
     * @param description the currency description
     */
    Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /* Getters and setters */

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}