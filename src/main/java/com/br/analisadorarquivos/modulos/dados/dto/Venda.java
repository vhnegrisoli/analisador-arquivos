package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static com.br.analisadorarquivos.modulos.dados.dto.ItemVenda.gerarListaItensDaVenda;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    private Integer vendaId;
    private List<ItemVenda> itensDeVenda;
    private String vendedorNome;

    public static Venda gerarDadosVenda(String linha) {
        var dadosLinha = Stream
            .of(linha.split(SEPARADOR))
            .filter(linhaTratada -> !linhaTratada.equals(VENDA))
            .collect(Collectors.toList());
        return Venda
            .builder()
            .vendaId(Integer.parseInt(dadosLinha.get(INDICE_VENDA_ID)))
            .itensDeVenda(gerarListaItensDaVenda(dadosLinha.get(INDICE_VENDA_ITENS)))
            .vendedorNome(dadosLinha.get(INDICE_VENDA_VENDEDOR_NOME))
            .build();
    }
}
