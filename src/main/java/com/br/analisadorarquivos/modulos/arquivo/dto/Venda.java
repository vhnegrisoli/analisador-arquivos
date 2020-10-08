package com.br.analisadorarquivos.modulos.arquivo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static com.br.analisadorarquivos.modulos.comum.util.StringUtil.validarLinhasComApenasTresItens;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    private Integer vendaId;
    private List<ItemVenda> itensDeVenda;
    private String vendedorNome;
    private Double totalVenda;

    public static Venda gerarDadosVenda(String linha) {
        var dadosLinha = Stream
            .of(linha.split(SEPARADOR))
            .filter(linhaTratada -> !linhaTratada.equals(VENDA))
            .collect(Collectors.toList());
        validarLinhasComApenasTresItens(dadosLinha);
        var itensVenda = ItemVenda.gerarListaItensDaVenda(dadosLinha.get(INDICE_VENDA_ITENS));
        return Venda
            .builder()
            .vendaId(Integer.parseInt(dadosLinha.get(INDICE_VENDA_ID)))
            .itensDeVenda(itensVenda)
            .vendedorNome(dadosLinha.get(INDICE_VENDA_VENDEDOR_NOME))
            .totalVenda(calcularTotalVenda(itensVenda))
            .build();
    }

    private static Double calcularTotalVenda(List<ItemVenda> itensVenda) {
        return itensVenda
            .stream()
            .mapToDouble(item -> item.getPreco() * item.getQuantidade())
            .sum();
    }
}
