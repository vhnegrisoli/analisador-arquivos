package com.br.analisadorarquivos.modulos.arquivo.mocks;

import com.br.analisadorarquivos.modulos.arquivo.dto.Venda;

import static com.br.analisadorarquivos.modulos.arquivo.mocks.ItemVendaMocks.umaListaItemVenda;

public class VendaMocks {

    public static String umaLinhaVenda() {
        return "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
    }

    public static Venda umaVenda() {
        return Venda
            .builder()
            .vendaId(10)
            .itensDeVenda(umaListaItemVenda())
            .vendedorNome("Pedro")
            .build();
    }
}
