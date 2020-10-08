package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    private Integer vendaId;
    private List<ItemVenda> itensDeVenda;
    private String vendedorNome;

    public static Venda gerarDadosVenda(String linha) {
        return new Venda();
    }
}
