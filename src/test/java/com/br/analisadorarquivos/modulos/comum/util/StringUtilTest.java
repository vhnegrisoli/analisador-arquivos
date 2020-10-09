package com.br.analisadorarquivos.modulos.comum.util;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class StringUtilTest {

    @Test
    @DisplayName("Deve recuperar tipo de dados de vendedor quando linha iniciar com 001")
    public void recuperarTipoDeDados_deveRecuperarTipoVendedor_quandoIniciarCom001() {
        assertThat(StringUtil.recuperarTipoDeDados("001çinfo")).isEqualTo(VENDEDOR);
    }

    @Test
    @DisplayName("Deve recuperar tipo de dados de vendedor quando linha iniciar com 002")
    public void recuperarTipoDeDados_deveRecuperarTipoCliente_quandoIniciarCom002() {
        assertThat(StringUtil.recuperarTipoDeDados("002çinfo")).isEqualTo(CLIENTE);
    }

    @Test
    @DisplayName("Deve recuperar tipo de dados de vendedor quando linha iniciar com 003")
    public void recuperarTipoDeDados_deveRecuperarTipoVenda_quandoIniciarCom003() {
        assertThat(StringUtil.recuperarTipoDeDados("003çinfo")).isEqualTo(VENDA);
    }

    @Test
    @DisplayName("Deve lançar exception quando linha não iniciar com 001, 002  e 003")
    public void recuperarTipoDeDados_deveLancarException_quandoNaoaIniciarCom001Nem002Nem003() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> StringUtil.recuperarTipoDeDados("çinfo"))
            .withMessage("O início da linha não pertence a um tipo de dados válido.");
    }

    @Test
    @DisplayName("Deve lançar exception quando linha informada for vazia")
    public void recuperarTipoDeDados_deveLancarException_quandoLinhaInformadaForVazia() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> StringUtil.recuperarTipoDeDados(null))
            .withMessage("A linha não pode ser vazia.");
    }

    @Test
    @DisplayName("Deve lançar exception quando linha informada for vazia")
    public void validarLinhasComApenasTresItens_deveExecutarNormalmente_quandoListaDeLinhasPossuir3Itens() {
        StringUtil.validarLinhasComApenasTresItens(List.of("1", "2", "3"));
    }

    @Test
    @DisplayName("Deve lançar exception quando lista de linhas possuir menos que 3 itens")
    public void validarLinhasComApenasTresItens_deveExecutarNormalmente_quandoListaDeLinhasPossuirMaisQue3Itens() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> StringUtil.validarLinhasComApenasTresItens(List.of("1", "2")))
            .withMessage("O tamanho da linha é inválido para gerar os dados dos arquivos.");
    }

    @Test
    @DisplayName("Deve lançar exception quando lista de linhas possuir mais que 3 itens")
    public void validarLinhasComApenasTresItens_deveExecutarNormalmente_quandoListaDeLinhasPossuirMenosQue3Itens() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> StringUtil.validarLinhasComApenasTresItens(List.of("1", "2", "3", "4")))
            .withMessage("O tamanho da linha é inválido para gerar os dados dos arquivos.");
    }

    @Test
    @DisplayName("Deve lançar exception quando lista de linhas for vazia")
    public void validarLinhasComApenasTresItens_deveExecutarNormalmente_quandoListaDeLinhasForVazia() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> StringUtil.validarLinhasComApenasTresItens(null))
            .withMessage("O tamanho da linha é inválido para gerar os dados dos arquivos.");
    }
}
