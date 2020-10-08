package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArquivoSaida {

    private Integer totalClientesEntrada;
    private Integer totalClientesDistintosEntrada;
    private Integer totalVendedoresEntrada;
    private Integer totalVendedoresDistintosEntrada;
    private Integer idVendaMaisCara;
    private String piorVendedor;
}
