package com.ouxuxi.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeExpected =(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCode =HttpServletRequestUtil.getString(request,"verifyCodeActual");
        if(verifyCode==null) return false;
        else{
            verifyCodeExpected=verifyCodeExpected.toLowerCase();
            verifyCode=verifyCode.toLowerCase();
            if(!verifyCode.equals(verifyCodeExpected)) return false;
            return true;
        }
    }
}
