package com.br.analisadorarquivos.modulos.tratamento;

import com.br.analisadorarquivos.modulos.dados.dto.ArquivoSaida;
import com.br.analisadorarquivos.modulos.dados.dto.Cliente;
import com.br.analisadorarquivos.modulos.dados.dto.Venda;
import com.br.analisadorarquivos.modulos.dados.dto.Vendedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static com.br.analisadorarquivos.modulos.comum.util.StringUtil.recuperarTipoDeDados;

@Service
public class ArquivoTratamentoService {

    public ArquivoSaida tratarDadosDoArquivo(List<String> linhas) {
        var vendedores = new ArrayList<Vendedor>();
        var clientes = new ArrayList<Cliente>();
        var vendas = new ArrayList<Venda>();
        linhas
            .forEach(linha -> {
                if (recuperarTipoDeDados(linha).equals(VENDEDOR)) {
                    vendedores.add(Vendedor.gerarDadosVendedor(linha));
                }
                if (recuperarTipoDeDados(linha).equals(CLIENTE)) {
                    clientes.add(Cliente.gerarDadosCliente(linha));
                }
                if (recuperarTipoDeDados(linha).equals(VENDA)) {
                    vendas.add(Venda.gerarDadosVenda(linha));
                }
            });
        return ArquivoSaida.gerarArquivoSaida(vendedores, clientes, vendas);
    }
}
