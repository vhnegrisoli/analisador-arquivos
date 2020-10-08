package com.br.analisadorarquivos.config;

import com.br.analisadorarquivos.modulos.processamento.ArquivoProcessamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;

import static com.br.analisadorarquivos.modulos.comum.util.ArquivoUtil.formatarDiretorioArquivo;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Configuration
public class MonitoramentoConfig {

    @Autowired
    private DiretoriosProperties properties;
    @Autowired
    private ArquivoProcessamentoService arquivoProcessamentoService;

    @Bean
    public void monitorarArquivosDoDiretorio() throws Exception {
        var monitor = FileSystems.getDefault().newWatchService();
        var diretorio = Paths.get(properties.getDiretorioIn());
        diretorio.register(monitor, ENTRY_CREATE);
        log.info("Monitorando o diretório: ".concat(properties.getDiretorioIn()));
        monitorarDiretorio(monitor);
    }

    private void monitorarDiretorio(WatchService monitor) throws Exception {
        var key = monitor.take();
        while (!isEmpty(key)) {
            key.pollEvents()
                .stream()
                .map(WatchEvent::context)
                .map(Object::toString)
                .forEach(evento -> arquivoProcessamentoService
                    .processarArquivo(formatarDiretorioArquivo(properties.getDiretorioIn(), evento)));
            if (!key.reset()) {
                break;
            }
        }
    }
}