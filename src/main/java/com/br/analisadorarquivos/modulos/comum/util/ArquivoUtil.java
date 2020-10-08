package com.br.analisadorarquivos.modulos.comum.util;

import static com.br.analisadorarquivos.modulos.comum.constantes.Constantes.BARRA;

public class ArquivoUtil {

    public static String formatarDiretorioArquivo(String diretorio, String arquivo) {
        return diretorio.concat(BARRA).concat(arquivo);
    }
}
