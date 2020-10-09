package com.br.analisadorarquivos.modulos.arquivo.service;

import com.br.analisadorarquivos.config.DiretoriosProperties;
import com.br.analisadorarquivos.modulos.arquivo.dto.ArquivoSaida;
import com.br.analisadorarquivos.modulos.comum.enums.EResultadoProcessamento;
import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.br.analisadorarquivos.modulos.comum.enums.EResultadoProcessamento.FALHA;
import static com.br.analisadorarquivos.modulos.comum.enums.EResultadoProcessamento.PROCESSADO;
import static com.br.analisadorarquivos.modulos.comum.util.ArquivoUtil.criarDiretorioCasoNaoExistir;
import static com.br.analisadorarquivos.modulos.comum.util.ArquivoUtil.formatarDiretorioArquivo;

@Slf4j
@Component
public class ArquivoProcessamentoService {

    @Autowired
    private DiretoriosProperties properties;
    @Autowired
    private ArquivoTratamentoService tratamentoService;

    @Async
    public void processarArquivo(String nomeArquivo) {
        System.out.println(nomeArquivo);
        var arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            var dadosSaida = lerDadosDoArquivoDeSaida(nomeArquivo, arquivo);
            salvarArquivoSaida(dadosSaida, arquivo.getName());
            moverParaDiretorio(nomeArquivo, PROCESSADO);
            removerArquivoDoDiretorioDeEntrada(nomeArquivo);
        } else {
            throw new ValidacaoException("O arquivo ".concat(nomeArquivo).concat(" não existe no diretório."));
        }
    }

    private ArquivoSaida lerDadosDoArquivoDeSaida(String nomeArquivo, File arquivo) {
        try {
            @Cleanup var inputStream = new FileInputStream(arquivo);
            var reader = converterInputStreamParaBufferedReader(inputStream);
            return tratamentoService.tratarDadosDoArquivo(reader.lines().collect(Collectors.toList()));
        } catch (Exception ex) {
            moverParaDiretorio(nomeArquivo, FALHA);
            throw new ValidacaoException("Houve um erro ao processar o arquivo: "
                .concat(nomeArquivo).concat(" - Erro: ").concat(ex.getMessage()));
        }
    }

    private BufferedReader converterInputStreamParaBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    private void moverParaDiretorio(String nomeArquivo, EResultadoProcessamento resultado) {
        var arquivoEntrada = new File(nomeArquivo);
        var diretorioSaida = recuperarDiretorioResultado(resultado);
        criarDiretorioCasoNaoExistir(diretorioSaida);
        if (arquivoEntrada.exists()) {
            var arquivoSaida = new File(formatarDiretorioArquivo(diretorioSaida, arquivoEntrada.getName()));
            try {
                FileUtils.copyFile(arquivoEntrada, arquivoSaida);
            } catch (IOException ex) {
                log.error("Erro ao  tentar copiar arquivo.", ex);
            }
        }
    }

    private String recuperarDiretorioResultado(EResultadoProcessamento resultado) {
        return resultado.equals(PROCESSADO)
            ? properties.getDiretorioProcessado()
            : properties.getDiretorioFalha();
    }

    private void removerArquivoDoDiretorioDeEntrada(String nomeArquivo) {
        var arquivoEntrada = new File(nomeArquivo);
        if (arquivoEntrada.exists()) {
            arquivoEntrada.delete();
        }
    }

    private void salvarArquivoSaida(ArquivoSaida arquivoSaida, String arquivoReferencia) {
        var arquivoTextoSaida = arquivoSaida.gerarTextoArquivoSaida(arquivoReferencia);
        escreverInformacoesNoArquivo(arquivoTextoSaida, arquivoReferencia);
    }

    private void escreverInformacoesNoArquivo(String dadosArquivo, String arquivoReferencia) {
        try {
            var diretorioOut = properties.getDiretorioOut();
            criarDiretorioCasoNaoExistir(diretorioOut);
            var writter = new FileWriter(formatarDiretorioArquivo(diretorioOut, arquivoReferencia));
            writter.write(dadosArquivo);
            writter.close();
        } catch (Exception ex) {
            throw new ValidacaoException("Erro ao escrever informações no arquivo "
                .concat(arquivoReferencia)
                .concat(" - Erro: ")
                .concat(ex.getMessage()));
        }
    }
}