package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendedor {

    private Integer cpf;
    private String nome;
    private Double salario;

    public static Vendedor gerarDadosVendedor(String linha) {
        var dadosLinha = Stream
            .of(linha.split(SEPARADOR))
            .filter(linhaTratada -> !linhaTratada.equals(VENDEDOR))
            .collect(Collectors.toList());
        return Vendedor
            .builder()
            .cpf(Integer.parseInt(dadosLinha.get(INDICE_VENDEDOR_CPF)))
            .nome(dadosLinha.get(INDICE_VENDEDOR_NOME))
            .salario(Double.parseDouble(dadosLinha.get(INDICE_VENDEDOR_SALARIO)))
            .build();
    }
}
