package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendedor {

    private String cpf;
    private String nome;
    private Double salario;

    public static Vendedor gerarDadosVendedor(String linha) {
        return new Vendedor();
    }
}
