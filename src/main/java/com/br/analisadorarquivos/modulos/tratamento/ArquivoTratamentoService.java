package com.br.analisadorarquivos.modulos.tratamento;

import com.br.analisadorarquivos.modulos.comum.constantes.Constantes;
import com.br.analisadorarquivos.modulos.dados.dto.Cliente;
import com.br.analisadorarquivos.modulos.dados.dto.Venda;
import com.br.analisadorarquivos.modulos.dados.dto.Vendedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.VENDA;
import static com.br.analisadorarquivos.modulos.comum.util.StringUtil.recuperarTipoDeDados;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ArquivoTratamentoService {

    public void tratarDadosDoArquivo(List<String> linhas) {
        var vendedores = new ArrayList<Vendedor>();
        var clientes = new ArrayList<Cliente>();
        var vendas = new ArrayList<Venda>();
        linhas
            .forEach(linha -> {
                if (recuperarTipoDeDados(linha).equals(VENDEDOR)) {
                    vendedores.add(Vendedor.gerarDadosVendedor(linha));
                }
                if (recuperarTipoDeDados(linha).equals(VENDEDOR)) {
                    clientes.add(Cliente.gerarDadosVendedor(linha));
                }
                if (recuperarTipoDeDados(linha).equals(VENDEDOR)) {
                    vendedores.add(Vendedor.gerarDadosVendedor(linha));
                }
            });
    }

    private String definirTipoDeDadosDaLinha(String linhaArquivo) {
        var dadosLinha = linhaArquivo.split(Constantes.SEPARADOR.toString());
        if (!isEmpty(dadosLinha) && !isEmpty(dadosLinha[0])) {
            var tipoArquivo = dadosLinha[0];
            switch (tipoArquivo) {
                case VENDEDOR:
                    return VENDEDOR;
                case CLIENTE:
                    return CLIENTE;
                case VENDA:
                    return VENDA;
                default:
                    return null;
            }
        }
        return null;
    }
}
