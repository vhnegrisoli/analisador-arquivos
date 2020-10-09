package com.br.analisadorarquivos.modulos.comum.util;

import com.br.analisadorarquivos.modulos.comum.exception.ValidacaoException;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.BARRA;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class ArquivoUtil {

    public static String formatarDiretorioArquivo(String diretorio, String arquivo) {
        if (isEmpty(diretorio) || isEmpty(arquivo)) {
            throw new ValidacaoException("Não é possível formatar o diretório do arquivo"
                + " se o diretório ou o arquivo for vazio.");
        }
        return diretorio.concat(BARRA).concat(arquivo);
    }

    public static void criarDiretorioCasoNaoExistir(String diretorioNome) {
        try {
            var diretorio = Path.of(diretorioNome);
            if (Files.notExists(diretorio)) {
                Files.createDirectory(diretorio);
            }
        } catch (Exception ex) {
            throw new ValidacaoException("Erro ao criar diretório: ".concat(ex.getMessage()));
        }
    }
}

