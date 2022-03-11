package com.hmfurtado.data.migration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FooDto2 {

    private String titleId;
    private String title;
    private String language;
}
