package com.hmfurtado.data.migration.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JpaListenerWriter implements ItemWriteListener {
    @Override
    public void beforeWrite(List items) {
        log.info("ESCREVENDO {}", items.size());
    }

    @Override
    public void afterWrite(List items) {

    }

    @Override
    public void onWriteError(Exception exception, List items) {

    }
}
