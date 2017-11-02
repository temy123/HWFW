package com.naver.temy123.baseproject.base.Utils;

import android.util.Base64;

import java.security.MessageDigest;

// 소켓 통신시 암호화 , 복호화 클래스 
public class Comm_Security {

	public static String EncryptSHA1(String Phrase) {
		String msgDigest = null;

		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			msgDigest = Base64.encodeToString(sha1.digest(Phrase.getBytes()), Base64.DEFAULT);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return msgDigest;
	}

}
