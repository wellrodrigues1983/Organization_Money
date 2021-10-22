package com.wrsistemas.organizationmoney.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual(){

        Long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }

    public static String mesAnoDataEscolhida(String data){

        String retornodata[] = data.split("/"); //Quebra uma string e coloca em um array de acordo com o parametro informado"/"
        String dia = retornodata[0];
        String mes = retornodata[1];
        String ano = retornodata[2];

        String mesAno = mes + ano;
        return mesAno;
    }
}
