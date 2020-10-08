package com.br.analisadorarquivos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app-config.diretorios")
public class DiretoriosProperties {

    private String diretorioIn;
    private String diretorioOut;
    private String diretorioProcessado;
    private String diretorioFalha;
}
