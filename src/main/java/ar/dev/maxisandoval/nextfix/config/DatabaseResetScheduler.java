package ar.dev.maxisandoval.nextfix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.context.*;

@Component
@RequiredArgsConstructor
public class DatabaseResetScheduler {

    private final ApplicationContext applicationContext;

    @Scheduled(fixedRate = 3600000) // 3600000 ms = 1 hora
    public void resetDatabase() {
        ((ConfigurableApplicationContext) applicationContext).close();
        System.exit(0);
    }
}