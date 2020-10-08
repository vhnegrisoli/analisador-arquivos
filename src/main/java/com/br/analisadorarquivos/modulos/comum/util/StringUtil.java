package com.br.analisadorarquivos.modulos.comum.util;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.VAZIO;
import static org.springframework.util.ObjectUtils.isEmpty;

public class StringUtil {

    public static String recuperarTipoDeDados(String linha) {
        return !isEmpty(linha.substring(0, 3)) ? linha.substring(0, 3) : VAZIO;
    }
}
