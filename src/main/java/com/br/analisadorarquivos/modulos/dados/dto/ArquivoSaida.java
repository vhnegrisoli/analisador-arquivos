package com.br.analisadorarquivos.modulos.dados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.SEPARADOR_SAIDA;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArquivoSaida {

    private Integer totalVendedoresEntrada;
    private Integer totalVendedoresDistintosEntrada;
    private Integer totalClientesEntrada;
    private Integer totalClientesDistintosEntrada;
    private Integer idVendaMaisCara;
    private String piorVendedor;

    public static ArquivoSaida gerarArquivoSaida(List<Vendedor> vendedores,
                                                 List<Cliente> clientes,
                                                 List<Venda> vendas) {
        return ArquivoSaida
            .builder()
            .totalVendedoresEntrada(calcularTotalVendedoresEntrada(vendedores, false))
            .totalVendedoresDistintosEntrada(calcularTotalVendedoresEntrada(vendedores, true))
            .totalClientesEntrada(calcularTotalClientesEntrada(clientes, false))
            .totalClientesDistintosEntrada(calcularTotalClientesEntrada(clientes, true))
            .idVendaMaisCara(buscarVendaMaisCara(vendas))
            .piorVendedor(buscarPiorVendedorDaVenda(vendas))
            .build();
    }

    private static Integer calcularTotalVendedoresEntrada(List<Vendedor> vendedores,
                                                   boolean distinto) {
        return distinto
            ? (int) vendedores
            .stream()
            .distinct()
            .count()
            : vendedores.size();
    }

    private static Integer calcularTotalClientesEntrada(List<Cliente> clientes, boolean distinto) {
        return distinto
            ? (int) clientes
            .stream()
            .distinct()
            .count()
            : clientes.size();
    }

    private static Integer buscarVendaMaisCara(List<Venda> vendas) {
        return vendas
            .stream()
            .max(Comparator.comparing(Venda::getTotalVenda))
            .orElseThrow()
            .getVendaId();
    }

    private static String buscarPiorVendedorDaVenda(List<Venda> vendas) {
        return vendas
            .stream()
            .min(Comparator.comparing(Venda::getTotalVenda))
            .orElseThrow()
            .getVendedorNome();
    }

    private String gerarCabecalhoArquivoSaida() {
        return String.join(
            SEPARADOR_SAIDA,
            "ARQUIVO_REFERENCIA",
            "TOTAL_VENDEDORES_ENTRADA",
            "TOTAL_VENDEDORES_DISTINTOS",
            "TOTAL_CLIENTES_ENTRADA",
            "TOTAL_CLIENTES_DISTINTOS",
            "ID_VENDA_MAIS_CARA",
            "PIOR_VENDEDOR\n"
        );
    }

    private String gerarLinhasArquivoSaida(String arquivoReferencia) {
        return String.join(SEPARADOR_SAIDA,
            arquivoReferencia,
            String.valueOf(totalVendedoresEntrada),
            String.valueOf(totalVendedoresDistintosEntrada),
            String.valueOf(totalClientesEntrada),
            String.valueOf(totalClientesDistintosEntrada),
            String.valueOf(idVendaMaisCara),
            piorVendedor.concat("\n")
        );
    }

    public String gerarTextoArquivoSaida(String arquivoReferencia) {
        return gerarCabecalhoArquivoSaida()
            .concat(gerarLinhasArquivoSaida(arquivoReferencia));
    }
}
