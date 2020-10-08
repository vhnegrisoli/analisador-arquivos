package com.br.analisadorarquivos.modulos.arquivo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static java.lang.String.valueOf;

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
            .map(Vendedor::getNome)
            .distinct()
            .count()
            : vendedores.size();
    }

    private static Integer calcularTotalClientesEntrada(List<Cliente> clientes, boolean distinto) {
        return distinto
            ? (int) clientes
            .stream()
            .map(Cliente::getNome)
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

    public String gerarTextoArquivoSaida(String arquivoReferencia) {
        return
            gerarLinhaArquivo("Arquivo de referência:", DEZESSETE_ESPACOS, arquivoReferencia)
                .concat(
                    gerarLinhaArquivo("Total de vendedores na entrada:", OITO_ESPACOS, valueOf(totalVendedoresEntrada)))
                .concat(
                    gerarLinhaArquivo("Total de vendedores distintos:", NOVE_ESPACOS, valueOf(totalVendedoresDistintosEntrada)))
                .concat(
                    gerarLinhaArquivo("Total de clientes na entrada:", DEZ_ESPACOS, valueOf(totalClientesEntrada)))
                .concat(
                    gerarLinhaArquivo("Total de clientes distintos:", ONZE_ESPACOS, valueOf(totalClientesDistintosEntrada)))
                .concat(
                    gerarLinhaArquivo("ID da venda mais cara:", DEZESSETE_ESPACOS, valueOf(idVendaMaisCara)))
                .concat(
                    gerarLinhaArquivo("Pior vendedor:", VINTE_E_CINCO_ESPACOS, piorVendedor));
    }

    private String gerarLinhaArquivo(String cabecalho, Integer quantidadeEspacos, String valor) {
        return cabecalho.concat(gerarEspacosDeFormatacao(quantidadeEspacos).concat(valor).concat(QUEBRA_DE_LINHA));
    }

    private String gerarEspacosDeFormatacao(Integer quantidadeDeEspacos) {
        return ESPACO_EM_BRANCO.repeat(quantidadeDeEspacos);
    }
}
