package com.br.analisadorarquivos.modulos.tratamento;

import com.br.analisadorarquivos.modulos.dados.dto.Cliente;
import com.br.analisadorarquivos.modulos.dados.dto.Venda;
import com.br.analisadorarquivos.modulos.dados.dto.Vendedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.VENDEDOR;
import static com.br.analisadorarquivos.modulos.comum.util.StringUtil.recuperarTipoDeDados;

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
                    clientes.add(Cliente.gerarDadosCliente(linha));
                }
                if (recuperarTipoDeDados(linha).equals(VENDEDOR)) {
                    vendas.add(Venda.gerarDadosVenda(linha));
                }
            });
    }
}
