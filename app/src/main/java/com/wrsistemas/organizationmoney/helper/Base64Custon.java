package com.wrsistemas.organizationmoney.helper;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Custon {

    public static String codificarBase64(String texto){

       return Base64.getEncoder().encodeToString(texto.getBytes()).replaceAll("(\\n|\\r)", "");


    }

    public static String decodificarBase64(String textoCodificado){

        return new String (Base64.getDecoder().decode(textoCodificado));
    }
}
