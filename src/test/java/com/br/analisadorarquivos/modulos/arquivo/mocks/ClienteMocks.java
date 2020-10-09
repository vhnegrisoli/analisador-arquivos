package com.br.analisadorarquivos.modulos.arquivo.mocks;

import com.br.analisadorarquivos.modulos.arquivo.dto.Cliente;

public class ClienteMocks {

    public static String umaLinhaCliente() {
        return "002ç2345675434544345çJose da SilvaçRural";
    }

    public static Cliente umCliente() {
        return Cliente
            .builder()
            .cnpj(2345675434544345L)
            .nome("Jose da Silva")
            .areaNegocio("Rural")
            .build();
    }
}
