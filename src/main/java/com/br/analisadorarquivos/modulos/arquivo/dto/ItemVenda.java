package com.br.analisadorarquivos.modulos.arquivo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static com.br.analisadorarquivos.modulos.comum.util.ExceptionUtil.gerarExceptionParaErroDeConversaoLinhasEmObjeto;
import static com.br.analisadorarquivos.modulos.comum.util.StringUtil.validarLinhasComApenasTresItens;

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
        validarLinhasComApenasTresItens(itens);
        try {
            return ItemVenda
                .builder()
                .itemId(Integer.parseInt(itens.get(INDICE_ITEM_VENDA_ID)))
                .quantidade(Integer.parseInt(itens.get(INDICE_ITEM_VENDA_QUANTIDADE)))
                .preco(Double.parseDouble(itens.get(INDICE_ITEM_VENDA_PRECO)))
                .build();
        } catch (Exception ex) {
            gerarExceptionParaErroDeConversaoLinhasEmObjeto(ex);
            return null;
        }
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