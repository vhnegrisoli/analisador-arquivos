package com.br.analisadorarquivos.modulos.comum.util;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.br.analisadorarquivos.modulos.util.ConstantesTestes.DIRETORIO_INICIAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ArquivoUtilTest {

    @Test
    @DisplayName("Deve retornar diretório concatenado com nome do arquivo quando informado")
    public void formatarDiretorioArquivo_deveFormatarCorrretamente_quandoInformarArquivoEDiretorio() {
        assertThat(ArquivoUtil.formatarDiretorioArquivo("/dados", "arquivo.txt"))
            .isEqualTo("/dados/arquivo.txt");
    }

    @Test
    @DisplayName("Deve lançar exception quando diretório informado for vazio")
    public void formatarDiretorioArquivo_deveLancarException_quandoDiretorioForVazio() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> ArquivoUtil.formatarDiretorioArquivo(null, "arquivo.txt"))
            .withMessage("Não é possível formatar o diretório do arquivo se o diretório ou o arquivo for vazio.");
    }

    @Test
    @DisplayName("Deve lançar exception quando arquivo informado for vazio")
    public void formatarDiretorioArquivo_deveLancarException_quandoArquivoForVazio() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> ArquivoUtil.formatarDiretorioArquivo("/dados", null))
            .withMessage("Não é possível formatar o diretório do arquivo se o diretório ou o arquivo for vazio.");
    }

    @Test
    @DisplayName("Deve criar diretório quando não existir")
    public void criarDiretorioCasoNaoExistir_deveCriarDiretorio_quandoNaoExistir() throws IOException {
        var diretorioTeste = DIRETORIO_INICIAL.concat("/diretorio-teste");
        ArquivoUtil.criarDiretorioCasoNaoExistir(diretorioTeste);
        var diretorioCriado = Path.of(diretorioTeste);
        assertThat(Files.exists(diretorioCriado)).isTrue();
        Files.delete(diretorioCriado);
    }

    @Test
    @DisplayName("Deve lançar exception quando informar diretório com erro")
    public void criarDiretorioCasoNaoExistir_LancarException_quandoInformarDiretorioComErro() {
        var diretorioTeste = DIRETORIO_INICIAL.concat("/asdas1888/aa**-*198191891*");
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> ArquivoUtil.criarDiretorioCasoNaoExistir(diretorioTeste))
            .withMessage("Erro ao criar diretório: Illegal char <*> at index 36: "
                + "src/test/resources/data/asdas1888/aa**-*198191891*");
    }
}
