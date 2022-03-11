package com.hmfurtado.data.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class WriterListenerCustom implements ItemWriteListener {
    @Override
    public void beforeWrite(List items) {

    }

    @Override
    public void afterWrite(List items) {

    }

    @Override
    public void onWriteError(Exception exception, List items) {

    }
}
