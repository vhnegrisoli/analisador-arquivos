package com.br.analisadorarquivos.modulos.arquivo.service;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.WatchService;
import java.util.Collections;

import static com.br.analisadorarquivos.modulos.arquivo.mocks.ArquivoSaidaMocks.umaListaDeLinhasDeProcessamento;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class ArquivoTrartamentoServiceTest {

    @InjectMocks
    private ArquivoTratamentoService tratamentoService;
    @Mock
    private WatchService watchService;

    @Test
    @DisplayName("Deve tratar linhas e retornar dados do arquivo quando linhas estiverem válidas")
    public void tratarDadosDoArquivo_deveTratarLinhasEGerarDadosDeSaida_quandoLinhasEstiveremValidas() {
        var dadosSaida = tratamentoService.tratarDadosDoArquivo(umaListaDeLinhasDeProcessamento());
        assertThat(dadosSaida).isNotNull();
        assertThat(dadosSaida.getTotalVendedoresEntrada()).isEqualTo(2);
        assertThat(dadosSaida.getTotalVendedoresDistintosEntrada()).isEqualTo(2);
        assertThat(dadosSaida.getTotalClientesEntrada()).isEqualTo(2);
        assertThat(dadosSaida.getTotalClientesDistintosEntrada()).isEqualTo(2);
        assertThat(dadosSaida.getIdVendaMaisCara()).isEqualTo(10);
        assertThat(dadosSaida.getPiorVendedor()).isEqualTo("Paulo");
    }

    @Test
    @DisplayName("Deve lançar exception quando linhas forem nulas")
    public void tratarDadosDoArquivo_deveLancarException_quandoLinhasForemNulas() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> tratamentoService.tratarDadosDoArquivo(null))
            .withMessage("As linhas não podem ser vazias.");
    }

    @Test
    @DisplayName("Deve lançar exception quando linhas forem lista vazia")
    public void tratarDadosDoArquivo_deveLancarException_quandoLinhasForemListaVazia() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> tratamentoService.tratarDadosDoArquivo(Collections.emptyList()))
            .withMessage("As linhas não podem ser vazias.");
    }
}
