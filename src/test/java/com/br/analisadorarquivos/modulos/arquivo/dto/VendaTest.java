package com.br.analisadorarquivos.modulos.arquivo.dto;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.br.analisadorarquivos.modulos.arquivo.mocks.ItemVendaMocks.umItemVenda;
import static com.br.analisadorarquivos.modulos.arquivo.mocks.VendaMocks.umaLinhaVenda;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class VendaTest {

    @Test
    @DisplayName("Deve gerar dados da venda quando a linha for válida")
    public void gerarDadosVenda_deveGerarDadosVenda_quandoLinhaForValida() {
        var venda = Venda.gerarDadosVenda(umaLinhaVenda());
        assertThat(venda).isNotNull();
        assertThat(venda.getVendaId()).isEqualTo(10);
        assertThat(venda.getItensDeVenda().size()).isEqualTo(3);
        assertThat(venda.getItensDeVenda().get(0).getItemId()).isEqualTo(1);
        assertThat(venda.getItensDeVenda().get(0).getQuantidade()).isEqualTo(10);
        assertThat(venda.getItensDeVenda().get(0).getPreco()).isEqualTo(100);
        assertThat(venda.getItensDeVenda().get(1).getItemId()).isEqualTo(2);
        assertThat(venda.getItensDeVenda().get(1).getQuantidade()).isEqualTo(30);
        assertThat(venda.getItensDeVenda().get(1).getPreco()).isEqualTo(2.50);
        assertThat(venda.getItensDeVenda().get(2).getItemId()).isEqualTo(3);
        assertThat(venda.getItensDeVenda().get(2).getQuantidade()).isEqualTo(40);
        assertThat(venda.getItensDeVenda().get(2).getPreco()).isEqualTo(3.10);
        assertThat(venda.getVendedorNome()).isEqualTo("Pedro");
        assertThat(venda.getTotalVenda()).isEqualTo(1199.0);
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar gerar dados da venda quando a linha for inválida")
    public void gerarDadosVenda_deveLancarException_quandoLinhaForInvalida() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> Venda.gerarDadosVenda(umaLinhaVenda().concat("çumAMais")))
            .withMessage("O tamanho da linha é inválido para gerar os dados dos arquivos.");
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar gerar dados da venda quando a linha possuir campo inválido")
    public void gerarDadosVenda_deveLancarException_quandoLinhaPossuirCampoInvalido() {
        var linha = "003ç1a0ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> Venda.gerarDadosVenda(linha))
            .withMessage("Erro ao tentar converter linhas para objeto: For input string: \"1a0\"");
    }

    @Test
    @DisplayName("Deve calcular o total quando informar lista de itens da venda")
    public void calcularTotalVenda_deveCalcularTotal_quandoInformarListaDeItensDaVenda() {
        var item1 = umItemVenda();
        item1.setQuantidade(13);
        item1.setPreco(45.80);
        var item2 = umItemVenda();
        item2.setQuantidade(3);
        item2.setPreco(156.90);
        assertThat(Venda.calcularTotalVenda(List.of(item1, item2))).isEqualTo(1066.1);
    }
}
