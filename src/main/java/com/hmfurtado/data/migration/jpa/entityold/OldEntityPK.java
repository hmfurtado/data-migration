package com.hmfurtado.data.migration.jpa.entityold;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"titleid", "ordering"})
public class OldEntityPK implements Serializable {

    @Column(name = "titleid")
    private String titleid;

    @Column(name = "ordering")
    private String ordering;

}
