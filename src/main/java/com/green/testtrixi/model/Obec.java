package com.green.testtrixi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Obec {
    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "obec", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CastObce> castObceList;
}
