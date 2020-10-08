package com.br.analisadorarquivos.modulos.comum.util;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static org.springframework.util.ObjectUtils.isEmpty;

public class StringUtil {

    public static String recuperarTipoDeDados(String linha) {
        return !isEmpty(linha.substring(INDICE_INICIAL_TIPO_DADO_STRING, INDICE_FINAL_TIPO_DADO_STRING))
            ? linha.substring(INDICE_INICIAL_TIPO_DADO_STRING, INDICE_FINAL_TIPO_DADO_STRING)
            : VAZIO;
    }
}
