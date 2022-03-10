package com.hmfurtado.data.migration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DTOzin {

    private String titleId;
    private Integer ordering;
    private String title;
    private String region;
    private String language;
    private String types;
    private String attributes;
    private String isOriginalTitle;
}
