package com.green.testtrixi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CastObce {
    @Id
    private Long id;
    private String name;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "obec_id")
    @JsonBackReference
    private Obec obec;
}
