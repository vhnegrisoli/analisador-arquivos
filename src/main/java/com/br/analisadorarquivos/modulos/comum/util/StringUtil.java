package com.br.analisadorarquivos.modulos.comum.util;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;

import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static org.springframework.util.ObjectUtils.isEmpty;

public class StringUtil {

    public static String recuperarTipoDeDados(String linha) {
        if (isEmpty(linha)) {
            throw new ValidacaoException("A linha não pode ser vazia.");
        }
        var tipoDados = linha.substring(INDICE_INICIAL_TIPO_DADO_STRING, INDICE_FINAL_TIPO_DADO_STRING);
        if (!List.of(VENDEDOR, CLIENTE, VENDA).contains(tipoDados)) {
            throw new ValidacaoException("O início da linha não pertence a um tipo de dados válido.");
        }
        return tipoDados;
    }

    public static void validarLinhasComApenasTresItens(List<String> dadosLinha) {
        if (isEmpty(dadosLinha) || dadosLinha.size() != TAMANHO_LINHA_DADOS) {
            throw new ValidacaoException("O tamanho da linha é inválido para gerar os dados dos arquivos.");
        }
    }
}
