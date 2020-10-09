package com.br.analisadorarquivos.modulos.arquivo.service;

import com.br.analisadorarquivos.config.DiretoriosProperties;
import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.WatchService;
import java.util.List;
import java.util.stream.Collectors;

import static com.br.analisadorarquivos.modulos.util.ConstantesTestes.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Import({
    ArquivoProcessamentoService.class,
    ArquivoTratamentoService.class,
    DiretoriosProperties.class
})
@SpringBootTest
@ActiveProfiles("test")
public class ArquivoProcessamentoServiceIntegrationTest {

    @Autowired
    private ArquivoProcessamentoService processamentoService;
    @Autowired
    private ArquivoTratamentoService tratamentoService;
    @Autowired
    private DiretoriosProperties properties;
    @MockBean
    private WatchService watchService;

    @BeforeEach
    public void inserirArquivosNoDiretorioIn() {
        var arquivoDiretorioExemplo = new File(DIRETORIO_INICIAL.concat(DIRETORIO_EXEMPLOS).concat(ARQUIVO_EXEMPLO));
        var arquivoErroDiretorioExemplo = new File(DIRETORIO_INICIAL.concat(DIRETORIO_EXEMPLOS).concat(ARQUIVO_EXEMPLO_ERRO));
        var arquivoDiretorioIn = new File(properties.getDiretorioIn().concat(ARQUIVO_EXEMPLO));
        var arquivoErroDiretorioIn = new File(properties.getDiretorioIn().concat(ARQUIVO_EXEMPLO_ERRO));
        try {
            if (arquivoDiretorioExemplo.exists()) {
                FileUtils.copyFile(arquivoDiretorioExemplo, arquivoDiretorioIn);
            }
            if (arquivoErroDiretorioExemplo.exists()) {
                FileUtils.copyFile(arquivoErroDiretorioExemplo, arquivoErroDiretorioIn);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AfterEach
    public void removerArquivosDeTodosOsDiretorios() {
        var arquivoDiretorioIn = new File(properties.getDiretorioIn().concat(ARQUIVO_EXEMPLO));
        var arquivoErroDiretorioIn = new File(properties.getDiretorioIn().concat(ARQUIVO_EXEMPLO_ERRO));
        var arquivoDiretorioOut = new File(properties.getDiretorioOut().concat(ARQUIVO_EXEMPLO));
        var arquivoExemploDiretorioProcessado = new File(properties.getDiretorioProcessado().concat(ARQUIVO_EXEMPLO));
        var arquivoErroDiretorioFalha = new File(properties.getDiretorioFalha().concat(ARQUIVO_EXEMPLO_ERRO));
        List.of(
            arquivoDiretorioIn,
            arquivoErroDiretorioIn,
            arquivoDiretorioOut,
            arquivoExemploDiretorioProcessado,
            arquivoErroDiretorioFalha
        )
            .stream()
            .filter(File::exists)
            .forEach(File::delete);
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve processar e salvar arquivo quando o arquivo estiver correto")
    public void processarArquivo_deveProcessarESalvarArquivo_quandoArquivoEstiverCorreto() {
        processamentoService.processarArquivo(properties.getDiretorioIn().concat(ARQUIVO_EXEMPLO));
        var arquivoSaida = new File(properties.getDiretorioOut().concat(ARQUIVO_EXEMPLO));
        assertThat(arquivoSaida.exists()).isTrue();
        @Cleanup var inputStream = new FileInputStream(arquivoSaida);
        var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        var linhasArquivo = reader
            .lines()
            .collect(Collectors.toList());
        assertThat(linhasArquivo.get(0)).isEqualTo("Arquivo de referência:                 exemplo 1.txt");
        assertThat(linhasArquivo.get(1)).isEqualTo("Total de vendedores na entrada:        2");
        assertThat(linhasArquivo.get(2)).isEqualTo("Total de vendedores distintos:         2");
        assertThat(linhasArquivo.get(3)).isEqualTo("Total de clientes na entrada:          2");
        assertThat(linhasArquivo.get(4)).isEqualTo("Total de clientes distintos:           2");
        assertThat(linhasArquivo.get(5)).isEqualTo("ID da venda mais cara:                 10");
        assertThat(linhasArquivo.get(6)).isEqualTo("Pior vendedor:                         Paulo");
        var arquivoDiretorioProcessado = new File(properties.getDiretorioProcessado().concat(ARQUIVO_EXEMPLO));
        assertThat(arquivoDiretorioProcessado.exists()).isTrue();
    }

    @Test
    @DisplayName("Deve processar e salvar no diretório de falha quando houver qualquer falha no arquivo")
    public void processarArquivo_deveProcessarESalvarNoDiretorioDeFalha_quandoArquivoEstiverComQualquerErro() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> processamentoService
                .processarArquivo(properties.getDiretorioIn().concat(ARQUIVO_EXEMPLO_ERRO)))
            .withMessage("Houve um erro ao processar o arquivo: src/test/resources/data/in/exemplo 1 erro.txt"
                + " - Erro: Erro ao tentar converter linhas para objeto: For input string: \"12345a67891234\"");
        var arquivoSaida = new File(properties.getDiretorioOut().concat(ARQUIVO_EXEMPLO_ERRO));
        assertThat(arquivoSaida.exists()).isFalse();
        var arquivoDiretorioProcessado = new File(properties.getDiretorioProcessado().concat(ARQUIVO_EXEMPLO_ERRO));
        assertThat(arquivoDiretorioProcessado.exists()).isFalse();
        var arquivoDiretorioFalha = new File(properties.getDiretorioFalha().concat(ARQUIVO_EXEMPLO_ERRO));
        assertThat(arquivoDiretorioFalha.exists()).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar processar um arquivo que não existe")
    public void processarArquivo_deveLancarException_quandoTentarProcessarArquivoQueNaoExiste() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> processamentoService.processarArquivo(ARQUIVO_EXEMPLO_ERRO))
            .withMessage("O arquivo /exemplo 1 erro.txt não existe no diretório.");
    }
}
