package com.br.analisadorarquivos.modulos.processamento;

import com.br.analisadorarquivos.config.DiretoriosProperties;
import com.br.analisadorarquivos.modulos.comum.enums.EResultadoProcessamento;
import com.br.analisadorarquivos.modulos.tratamento.ArquivoTratamentoService;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.br.analisadorarquivos.modulos.comum.enums.EResultadoProcessamento.FALHA;
import static com.br.analisadorarquivos.modulos.comum.enums.EResultadoProcessamento.PROCESSADO;
import static com.br.analisadorarquivos.modulos.comum.util.ArquivoUtil.formatarDiretorioArquivo;

@Slf4j
@Component
public class ArquivoProcessamentoService {

    @Autowired
    private DiretoriosProperties properties;
    @Autowired
    private ArquivoTratamentoService tratamentoService;

    public void processarArquivo(String nomeArquivo) {
        System.out.println(nomeArquivo);
        var arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            try {
                @Cleanup var inputStream = new FileInputStream(arquivo);
                var reader = converterInputStreamParaBufferedReader(inputStream);
                tratamentoService.tratarDadosDoArquivo(reader
                    .lines()
                    .collect(Collectors.toList()));
                moverParaDiretorio(nomeArquivo, PROCESSADO);
            } catch (Exception ex) {
                log.error("Houve um erro ao processar o arquivo ".concat(nomeArquivo), ex);
                moverParaDiretorio(nomeArquivo, FALHA);
            }
            removerArquivoDoDiretorioDeEntrada(nomeArquivo);
        }
    }

    private BufferedReader converterInputStreamParaBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    private void moverParaDiretorio(String nomeArquivo, EResultadoProcessamento resultado) {
        var arquivoEntrada = new File(nomeArquivo);
        if (arquivoEntrada.exists()) {
            var arquivoSaida = new File(formatarDiretorioArquivo(
                resultado.equals(PROCESSADO)
                    ? properties.getDiretorioProcessado()
                    : properties.getDiretorioFalha(), arquivoEntrada.getName()
            ));
            try {
                FileUtils.copyDirectory(arquivoEntrada, arquivoSaida);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removerArquivoDoDiretorioDeEntrada(String nomeArquivo) {
        var arquivoEntrada = new File(nomeArquivo);
        if (arquivoEntrada.exists()) {
            arquivoEntrada.delete();
        }
    }
}
