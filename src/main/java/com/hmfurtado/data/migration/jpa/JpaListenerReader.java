package com.hmfurtado.data.migration.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JpaListenerReader implements ItemReadListener {
    @Override
    public void beforeRead() {
        log.info("LENDO");
    }

    @Override
    public void afterRead(Object item) {

    }

    @Override
    public void onReadError(Exception ex) {

    }
}
