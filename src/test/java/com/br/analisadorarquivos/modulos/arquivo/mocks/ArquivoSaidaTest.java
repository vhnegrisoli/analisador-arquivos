package com.br.analisadorarquivos.modulos.arquivo.mocks;

import com.br.analisadorarquivos.modulos.arquivo.dto.ArquivoSaida;

public class ArquivoSaidaTest {

    public static ArquivoSaida umArquivoSaida() {
        return ArquivoSaida
            .builder()
            .totalVendedoresEntrada(5)
            .totalVendedoresDistintosEntrada(2)
            .totalClientesEntrada(6)
            .totalClientesDistintosEntrada(3)
            .idVendaMaisCara(15)
            .piorVendedor("Vendedor")
            .build();
    }
}
