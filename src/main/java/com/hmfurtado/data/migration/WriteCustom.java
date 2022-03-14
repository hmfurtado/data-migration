package com.hmfurtado.data.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class WriteCustom implements ItemWriter {
    @Override
    public void write(List items) throws Exception {
        log.info("Writing: {}", items.size());
        for (Object a : items) {
            log.info(a.toString());
        }

    }
}
