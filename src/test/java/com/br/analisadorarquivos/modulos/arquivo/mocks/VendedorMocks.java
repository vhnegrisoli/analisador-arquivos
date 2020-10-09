package com.br.analisadorarquivos.modulos.arquivo.mocks;

import com.br.analisadorarquivos.modulos.arquivo.dto.Vendedor;

public class VendedorMocks {

    public static String umaLinhaVendedor() {
        return "001ç1234567891234çPedroç50000";
    }

    public static Vendedor umVendedor() {
        return Vendedor
            .builder()
            .cpf(1234567891234L)
            .nome("Pedro")
            .salario(50000.00)
            .build();
    }
}
