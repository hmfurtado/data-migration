package com.hmfurtado.data.migration;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writerzin implements ItemWriter {
    @Override
    public void write(List items) throws Exception {
        items.stream().forEach(System.out::println);
    }
}
