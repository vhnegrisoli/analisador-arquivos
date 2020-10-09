package com.br.analisadorarquivos.modulos.arquivo.dto;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.br.analisadorarquivos.modulos.arquivo.mocks.VendedorMocks.umaLinhaVendedor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class VendedorTest {

    @Test
    @DisplayName("Deve gerar dados do vendedor quando a linha for válida")
    public void gerarDadosVendedor_deveGerarDadosVendedor_quandoLinhaForValida() {
        var vendedor = Vendedor.gerarDadosVendedor(umaLinhaVendedor());
        assertThat(vendedor).isNotNull();
        assertThat(vendedor.getCpf()).isEqualTo(1234567891234L);
        assertThat(vendedor.getNome()).isEqualTo("Pedro");
        assertThat(vendedor.getSalario()).isEqualTo(50000.00);
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar gerar dados do vendedor quando a linha for inválida")
    public void gerarDadosVendedor_deveLancarException_quandoLinhaForInvalida() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> Vendedor.gerarDadosVendedor(umaLinhaVendedor().concat("çumAMais")))
            .withMessage("O tamanho da linha é inválido para gerar os dados dos arquivos.");
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar gerar dados do vendedor quando a linha possuir campo inválido")
    public void gerarDadosVendedor_deveLancarException_quandoLinhaPossuirCampoInvalido() {
        var linha = "001ç123a4567891234çPedroç50000";
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> Vendedor.gerarDadosVendedor(linha))
            .withMessage("Erro ao tentar converter linhas para objeto: For input string: \"123a4567891234\"");
    }
}
