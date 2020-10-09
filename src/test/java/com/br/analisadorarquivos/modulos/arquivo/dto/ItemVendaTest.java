package com.br.analisadorarquivos.modulos.arquivo.dto;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.br.analisadorarquivos.modulos.arquivo.mocks.ItemVendaMocks.umItemVenda;
import static com.br.analisadorarquivos.modulos.arquivo.mocks.ItemVendaMocks.umaLinhaItemVenda;
import static com.br.analisadorarquivos.modulos.arquivo.mocks.VendaMocks.umaLinhaVenda;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ItemVendaTest {

    @Test
    @DisplayName("Deve gerar dados de item da venda quando a linha for válida")
    public void gerarListaItensDaVenda_deveGerarDadosItemDaVenda_quandoLinhaForValida() {
        var itemVenda = ItemVenda.gerarListaItensDaVenda(umaLinhaItemVenda());
        assertThat(itemVenda).isNotNull();
        assertThat(itemVenda.size()).isEqualTo(3);
        assertThat(itemVenda.get(0).getItemId()).isEqualTo(1);
        assertThat(itemVenda.get(0).getQuantidade()).isEqualTo(10);
        assertThat(itemVenda.get(0).getPreco()).isEqualTo(100);
        assertThat(itemVenda.get(1).getItemId()).isEqualTo(2);
        assertThat(itemVenda.get(1).getQuantidade()).isEqualTo(30);
        assertThat(itemVenda.get(1).getPreco()).isEqualTo(2.50);
        assertThat(itemVenda.get(2).getItemId()).isEqualTo(3);
        assertThat(itemVenda.get(2).getQuantidade()).isEqualTo(40);
        assertThat(itemVenda.get(2).getPreco()).isEqualTo(3.10);
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar gerar dados de item venda quando a linha possuir campo inválido")
    public void gerarListaItensDaVenda_deveLancarException_quandoLinhaForPossuirCampoInvalido() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> ItemVenda.gerarListaItensDaVenda(umaLinhaVenda().concat("çumAMais")))
            .withMessage("Erro ao tentar converter linhas para objeto: For input string: \"003ç10ç1\"");
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
