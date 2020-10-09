package com.br.analisadorarquivos.modulos.arquivo.service;

import com.br.analisadorarquivos.modulos.arquivo.dto.ArquivoSaida;
import com.br.analisadorarquivos.modulos.arquivo.dto.Cliente;
import com.br.analisadorarquivos.modulos.arquivo.dto.Venda;
import com.br.analisadorarquivos.modulos.arquivo.dto.Vendedor;
import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.*;
import static com.br.analisadorarquivos.modulos.comum.util.StringUtil.recuperarTipoDeDados;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ArquivoTratamentoService {

    public ArquivoSaida tratarDadosDoArquivo(List<String> linhas) {
        validarLinhasVazias(linhas);
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

    private void validarLinhasVazias(List<String> linhas) {
        if (isEmpty(linhas)) {
            throw new ValidacaoException("As linhas não podem ser vazias.");
        }
    }
}
