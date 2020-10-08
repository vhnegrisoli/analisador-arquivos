package com.br.analisadorarquivos.modulos.comum.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.BARRA;

@Slf4j
public class ArquivoUtil {

    public static String formatarDiretorioArquivo(String diretorio, String arquivo) {
        return diretorio.concat(BARRA).concat(arquivo);
    }

    public static void criarDiretorioCasoNaoExistir(String diretorioNome) {
        var diretorio = Path.of(diretorioNome);
        if (Files.notExists(diretorio)) {
            try {
                Files.createDirectory(diretorio);
            } catch (Exception ex) {
                log.error("Erro ao criar diretório.", ex);
            }
        }
    }
}
