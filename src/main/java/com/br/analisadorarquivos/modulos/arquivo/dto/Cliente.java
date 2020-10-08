package com.br.analisadorarquivos.modulos.arquivo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static com.br.analisadorarquivos.modulos.comum.util.StringUtil.validarLinhasComApenasTresItens;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private Long cnpj;
    private String nome;
    private String areaNegocio;

    public static Cliente gerarDadosCliente(String linha) {
        var dadosLinha = Stream
            .of(linha.split(SEPARADOR))
            .filter(linhaTratada -> !linhaTratada.equals(CLIENTE))
            .collect(Collectors.toList());
        validarLinhasComApenasTresItens(dadosLinha);
        return Cliente
            .builder()
            .cnpj(Long.parseLong(dadosLinha.get(INDICE_CLIENTE_CNPJ)))
            .nome(dadosLinha.get(INDICE_CLIENTE_NOME))
            .areaNegocio(dadosLinha.get(INDICE_CLIENTE_AREA_NEGOCIO))
            .build();
    }
}
