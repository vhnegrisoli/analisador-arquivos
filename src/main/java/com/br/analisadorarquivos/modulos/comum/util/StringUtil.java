package com.br.analisadorarquivos.modulos.comum.util;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;

import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static org.springframework.util.ObjectUtils.isEmpty;

public class StringUtil {

    public static String recuperarTipoDeDados(String linha) {
        return !isEmpty(linha.substring(INDICE_INICIAL_TIPO_DADO_STRING, INDICE_FINAL_TIPO_DADO_STRING))
            ? linha.substring(INDICE_INICIAL_TIPO_DADO_STRING, INDICE_FINAL_TIPO_DADO_STRING)
            : VAZIO;
    }

    public static void validarLinhasComApenasTresItens(List<String> dadosLinha) {
        if (isEmpty(dadosLinha) || dadosLinha.size() != TAMANHO_LINHA_DADOS) {
            throw new ValidacaoException("O tamanho da linha é inválido para gerar os dados dos arquivos.");
        }
    }
}
