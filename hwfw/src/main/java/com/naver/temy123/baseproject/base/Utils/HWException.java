package com.naver.temy123.baseproject.base.Utils;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by lhw on 2017. 3. 6..
 */

public class HWException extends Exception {

    public static HWException newInstance (String msg) {
        msg = "======== HWException ========\n" + msg;

        HWException exception = new HWException(msg);
        return exception;
    }

    private HWException(String message) {
        super(message);
    }

}
