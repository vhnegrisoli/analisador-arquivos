package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private String cnpj;
    private String nome;
    private String areaNegocio;

    public static Cliente gerarDadosCliente(String linha) {
        return new Cliente();
    }
}
