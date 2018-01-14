package com.malina.auth;


import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by pawel on 12.01.18.
 */
public class AuthUtils {

    public static String Sha256(String messageToHash) {
        return Hashing.sha256()
                .hashString(messageToHash,StandardCharsets.UTF_8).toString();
    }
}
