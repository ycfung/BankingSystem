package com.rcf.banking.util;

/**
 * This class is used to host the current user
 *
 * @author Chengfeng RONG
 * @version 1.0
 */

public class HostHolder {

    private static String userName;

    public static void setUserName(String userName) {
        HostHolder.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void clear() {
        userName = null;
    }



}
