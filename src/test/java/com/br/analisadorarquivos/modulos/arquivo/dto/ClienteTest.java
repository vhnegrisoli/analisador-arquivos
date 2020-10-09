package com.br.analisadorarquivos.modulos.arquivo.dto;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.br.analisadorarquivos.modulos.arquivo.mocks.ClienteMocks.umaLinhaCliente;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ClienteTest {

    @Test
    @DisplayName("Deve gerar dados do cliente quando a linha for válida")
    public void gerarDadosCliente_deveGerarDadosCliente_quandoLinhaForValida() {
        var cliente = Cliente.gerarDadosCliente(umaLinhaCliente());
        assertThat(cliente).isNotNull();
        assertThat(cliente.getCnpj()).isEqualTo(2345675434544345L);
        assertThat(cliente.getNome()).isEqualTo("Jose da Silva");
        assertThat(cliente.getAreaNegocio()).isEqualTo("Rural");
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar gerar dados do cliente quando a linha for inválida")
    public void gerarDadosCliente_deveLancarException_quandoLinhaForInvalida() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> Cliente.gerarDadosCliente(umaLinhaCliente().concat("çumAMais")))
            .withMessage("O tamanho da linha é inválido para gerar os dados dos arquivos.");
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar gerar dados do cliente quando a linha possuir campo inválido")
    public void gerarDadosCliente_deveLancarException_quandoLinhaPossuirCampoInvalido() {
        var linha = "002ç2345675a434544345çJose da SilvaçRural";
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> Cliente.gerarDadosCliente(linha))
            .withMessage("Erro ao tentar converter linhas para objeto: For input string: \"2345675a434544345\"");
    }
}
