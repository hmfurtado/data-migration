package com.hmfurtado.data.migration.jpa;

import com.hmfurtado.data.migration.FooDto2;
import com.hmfurtado.data.migration.jpa.entityold.OldEntityWIthCompositePK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JpaProcessor implements ItemProcessor<OldEntityWIthCompositePK, FooDto2> {
    @Override
    public FooDto2 process(OldEntityWIthCompositePK item) throws Exception {
        try {
            return FooDto2.builder()
                    .titleId(item.getId().getTitleid())
                    .title(item.getTitle())
                    .language(item.getLanguage())
                    .build();
        } catch (Exception e) {
            log.error("ERROR ON {}", item.toString());
            return FooDto2.builder().title("ZZZZZZZZZZZZZZZZZZZZZZZZZZZ").build();
        }
    }
}
