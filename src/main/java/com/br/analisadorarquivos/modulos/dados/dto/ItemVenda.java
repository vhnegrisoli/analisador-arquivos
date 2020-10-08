package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda {

    private Integer itemId;
    private Integer quantidade;
    private Double preco;

    public static List<ItemVenda> gerarListaItensDaVenda(String linha) {
        linha = validarExistenciaDeColchetes(linha);
        return Stream.of(linha.split(SEPARADOR_LISTA_ITEM_VENDA))
            .map(ItemVenda::gerarDadosItemVenda)
            .collect(Collectors.toList());
    }

    private static ItemVenda gerarDadosItemVenda(String itemDaVenda) {
        var itens = Stream
            .of(itemDaVenda.split(SEPARADOR_ITEM_VENDA))
            .collect(Collectors.toList());
        return ItemVenda
            .builder()
            .itemId(Integer.parseInt(itens.get(INDICE_ITEM_VENDA_ID)))
            .quantidade(Integer.parseInt(itens.get(INDICE_ITEM_VENDA_QUANTIDADE)))
            .preco(Double.parseDouble(itens.get(INDICE_ITEM_VENDA_PRECO)))
            .build();
    }

    private static String validarExistenciaDeColchetes(String linha) {
        if (linha.contains(ITEM_VENDA_COLCHETES_DIREITA)) {
            linha = linha.replace(ITEM_VENDA_COLCHETES_DIREITA, VAZIO);
        }
        if (linha.contains(ITEM_VENDA_COLCHETES_ESQUERDA)) {
            linha = linha.replace(ITEM_VENDA_COLCHETES_ESQUERDA, VAZIO);
        }
        return linha;
    }
}