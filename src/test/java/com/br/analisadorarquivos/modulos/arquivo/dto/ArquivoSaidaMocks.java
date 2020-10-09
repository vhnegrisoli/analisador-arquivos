package com.br.analisadorarquivos.modulos.arquivo.dto;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.br.analisadorarquivos.modulos.arquivo.mocks.ArquivoSaidaMocks.umArquivoSaida;
import static com.br.analisadorarquivos.modulos.arquivo.mocks.ClienteMocks.umCliente;
import static com.br.analisadorarquivos.modulos.arquivo.mocks.VendaMocks.umaVenda;
import static com.br.analisadorarquivos.modulos.arquivo.mocks.VendedorMocks.umVendedor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ArquivoSaidaMocks {

    @Test
    @DisplayName("Deve gerar dados do arquivo de saída quando dados estiverem válidos")
    public void gerarArquivoSaida_deveGerarDadosArquivoSaida_quandoDadosEstiveremValidos() {
        var arquivoSaida = ArquivoSaida.gerarArquivoSaida(
            List.of(umVendedor()),
            List.of(umCliente()),
            List.of(umaVenda())
        );
        assertThat(arquivoSaida).isNotNull();
        assertThat(arquivoSaida.getTotalVendedoresEntrada()).isEqualTo(1);
        assertThat(arquivoSaida.getTotalVendedoresDistintosEntrada()).isEqualTo(1);
        assertThat(arquivoSaida.getTotalClientesEntrada()).isEqualTo(1);
        assertThat(arquivoSaida.getTotalClientesDistintosEntrada()).isEqualTo(1);
        assertThat(arquivoSaida.getIdVendaMaisCara()).isEqualTo(10);
        assertThat(arquivoSaida.getPiorVendedor()).isEqualTo("Pedro");
    }

    @Test
    @DisplayName("Deve gerar texto de saída quando dados estiverem corretos")
    public void gerarTextoArquivoSaida_deveGerarTextoSaida_quandoDadosEstiveremCorretos() {
        var arquivoSaida = umArquivoSaida();
        assertThat(arquivoSaida.gerarTextoArquivoSaida("arquivo_teste.txt"))
            .isEqualTo(
                "Arquivo de referência:                 arquivo_teste.txt\n"
                    + "Total de vendedores na entrada:        5\n"
                    + "Total de vendedores distintos:         2\n"
                    + "Total de clientes na entrada:          6\n"
                    + "Total de clientes distintos:           3\n"
                    + "ID da venda mais cara:                 15\n"
                    + "Pior vendedor:                         Vendedor\n"
            );
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar gerar texto de arquivo de saída sem informar arquivo de referência")
    public void gerarTextoArquivoSaida_deveLancarException_quandoNaoInformarArquivoReferencia() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> umArquivoSaida().gerarTextoArquivoSaida(null))
            .withMessage("É necessário informar o arquivo de referência.");
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar gerar texto de arquivo de saída com qualquer campo nulo")
    public void gerarTextoArquivoSaida_deveLancarException_quandoQualquerCampoForNulo() {
        var arquivoSaida = umArquivoSaida();
        arquivoSaida.setIdVendaMaisCara(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> arquivoSaida.gerarTextoArquivoSaida("teste"))
            .withMessage("É necessário que todos os campos existam para gerar o arquivo de resposta.");
    }
}
