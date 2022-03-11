package com.hmfurtado.data.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReaderListenerCustom implements ItemReadListener {
    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(Object item) {

    }

    @Override
    public void onReadError(Exception ex) {

    }
}
