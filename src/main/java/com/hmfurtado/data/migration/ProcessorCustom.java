package com.hmfurtado.data.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessorCustom implements ItemProcessor<FooDto, FooDto2> {

    @Override
    public FooDto2 process(FooDto item) throws Exception {
        try {
            return FooDto2.builder()
                    .titleId(item.getTitleId())
                    .title(item.getTitle())
                    .language(item.getLanguage())
                    .build();
        } catch (Exception e) {
            log.error("ERROR ON {}", item.toString());
            return FooDto2.builder().title("ZZZZZZZZZZZZZZZZZZZZZZZZZZZ").build();
        }
    }
}
