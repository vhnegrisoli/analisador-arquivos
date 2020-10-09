package com.br.analisadorarquivos.modulos.arquivo.mocks;

import com.br.analisadorarquivos.modulos.arquivo.dto.ArquivoSaida;

import java.util.List;

public class ArquivoSaidaMocks {

    public static List<String> umaListaDeLinhasDeProcessamento() {
        return List.of(
            "001ç1234567891234çPedroç50000",
            "001ç3245678865434çPauloç40000.99",
            "002ç2345675434544345çJose da SilvaçRural",
            "002ç2345675433444345çEduardo PereiraçRural",
            "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
            "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo"
        );
    }

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
