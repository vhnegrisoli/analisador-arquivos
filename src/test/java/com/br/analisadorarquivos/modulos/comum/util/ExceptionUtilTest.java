package com.br.analisadorarquivos.modulos.comum.util;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ExceptionUtilTest {

    @Test
    @DisplayName("Deve lançar exception com mensagem de exception genérica quando informada")
    public void gerarExceptionParaErroDeConversaoLinhasEmObjeto_deveLancarException_quandoInformarExceptionGenerica() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> ExceptionUtil.gerarExceptionParaErroDeConversaoLinhasEmObjeto(new Exception("Exemplo")))
            .withMessage("Erro ao tentar converter linhas para objeto: Exemplo");
    }
}
