package br.com.iuapp.android.sdk;

import android.util.Log;

/**
 * Created by victorlsn on 05/11/18.
 */

class FormatUtil {

    static String setToPhonePattern(String phone, String defaultDdd) {

        int[] ddd = {11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 22, 24, 27, 28, 91, 93, 94, 92, 97, 95,
                96, 98, 99, 31, 32, 33, 34, 35, 37, 38, 71, 73, 74, 75, 77, 79, 81, 87, 82, 83, 84,
                85, 88, 86, 89, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 53, 54, 55, 61, 62, 64, 63,
                65, 66, 67, 68, 69};
        if (phone != null) {
            phone = phone.trim();
            phone = FormatUtil.stripPhoneNumber(phone);
            if (null != phone && phone.length() >= 8) {
                if (phone.length() == 8) {
                    if (phone.indexOf("5") == 0 || phone.indexOf("6") == 0 || phone.indexOf("7") == 0 || phone.indexOf("8") == 0 || phone.indexOf("9") == 0) {
                        phone = defaultDdd + "9" + phone;
                    }
                }
                if (phone.length() == 9) {
                    phone = defaultDdd + phone;
                }
                if (phone.length() == 10) {
                    String phoneDdd = null;

                    for (int aDdd : ddd) {
                        if (phone.indexOf(String.valueOf(aDdd)) == 0) {
                            phoneDdd = String.valueOf(aDdd);
                            break;
                        }
                    }
                    if (phoneDdd != null) {
                        phone = phone.substring(2);

                        if (phone.indexOf("5") == 0 || phone.indexOf("6") == 0 || phone.indexOf("7") == 0 || phone.indexOf("8") == 0 || phone.indexOf("9") == 0) {
                            phone = phoneDdd + "9" + phone;
                        }
                    }
                }
                if (phone.indexOf("+55") == 0) {
                    phone = phone.substring(3);
                }
                if (phone.length() > 16) {
                    phone = phone.substring(phone.length() - 16, phone.length());
                }
                if (phone.indexOf("55") == 0) {
                    phone = phone.substring(2);
                }
                if (phone.length() == 13 || phone.length() == 14) {
                    for (int i = 11; i < 100; i++) {
                        if (i % 10 != 0) {
                            if (phone.indexOf("0" + i) == 0) {
                                phone = phone.substring(3);
                            }
                        }
                    }
                }
                if (phone.length() == 12 || phone.length() == 13) {
                    for (int i = 11; i < 100; i++) {
                        if (i % 10 != 0) {
                            if (phone.indexOf(String.valueOf(i)) == 0) {
                                phone = phone.substring(2);
                            }
                        }
                    }
                }
                if (phone.length() == 11 || phone.length() == 12) {
                    if (phone.indexOf("0") == 0) {
                        phone = phone.substring(1);
                    }
                }
                boolean valid = false;
                for (int aDdd : ddd) {
                    if (phone.indexOf(String.valueOf(aDdd)) == 0) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    phone = "#";
                }
                if (phone.length() > 11) {
                    phone = phone.substring(phone.length() - 11);
                }
            } else {
                phone = "#";
            }
        } else {
            phone = "#";
        }
        return phone;
    }

    private static String stripPhoneNumber(String phoneNumber) {
        try {
            return phoneNumber.replace(" ", "").replace("(", "").replace(")", "").replace("-", "");
        } catch (NullPointerException e) {
            Log.e(FormatUtil.class.getSimpleName(), "Strip phone number error: phone number is null ");
        }
        return null;
    }

}
