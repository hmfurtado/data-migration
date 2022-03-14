package com.hmfurtado.data.migration.jpa.entityold;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "title")
public class OldEntityWIthCompositePK {

    @EmbeddedId
    private OldEntityPK id;

    @Column(name = "title")
    private String title;

    @Column(name = "region")
    private String region;

    @Column(name = "language")
    private String language;

    @Column(name = "types")
    private String types;

    @Column(name = "attributes")
    private String attributes;

    @Column(name = "isoriginaltitle")
    private String isoriginaltitle;


}
