package com.br.analisadorarquivos.modulos.comum.util;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;

public class ExceptionUtil {

    public static void gerarExceptionParaErroDeConversaoLinhasEmObjeto(Exception ex) {
        throw new ValidacaoException("Erro ao tentar converter linhas para objeto: ".concat(ex.getMessage()));
    }
}
