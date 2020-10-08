package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda {

    private Integer itemId;
    private Integer quantidade;
    private Double preco;

    public static ItemVenda gerarDadosItemVenda(String linha) {
        return new ItemVenda();
    }
}