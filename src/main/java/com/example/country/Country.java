package com.example.country;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "COUNTRY")
@Data
@NoArgsConstructor
public class Country {

    @Id
    @Column(name = "COUNTRY_ID")
    private UUID id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "CODE")
    @NotNull
    private String code;
}
