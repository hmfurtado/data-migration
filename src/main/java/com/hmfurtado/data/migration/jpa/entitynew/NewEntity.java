package com.hmfurtado.data.migration.jpa.entitynew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "newtable3")
public class NewEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "id_sequence3", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_sequence3", sequenceName = "id_sequence3", allocationSize = 1)
    private Long id;

    @Column(name = "titleid")
    private String titleid;

    @Column(name = "title")
    private String title;

    @Column(name = "language")
    private String language;

}
