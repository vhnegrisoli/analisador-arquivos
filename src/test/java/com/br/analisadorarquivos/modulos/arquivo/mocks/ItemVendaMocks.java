package com.br.analisadorarquivos.modulos.arquivo.mocks;

import com.br.analisadorarquivos.modulos.arquivo.dto.ItemVenda;

import java.util.List;

public class ItemVendaMocks {

    public static String umaLinhaItemVenda() {
        return "[1-10-100,2-30-2.50,3-40-3.10]";
    }

    public static List<ItemVenda> umaListaItemVenda() {
        return List.of(
            ItemVenda
                .builder()
                .itemId(1)
                .quantidade(10)
                .preco(3.10)
                .build(),
            ItemVenda
                .builder()
                .itemId(2)
                .quantidade(30)
                .preco(2.50)
                .build(),
            ItemVenda
                .builder()
                .itemId(3)
                .quantidade(40)
                .preco(3.10)
                .build()
        );
    }

    public static ItemVenda umItemVenda() {
        return ItemVenda
            .builder()
            .itemId(1)
            .quantidade(10)
            .preco(100.0)
            .build();
    }
}
