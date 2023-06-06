package com.spring.andorinha.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_tweet")
public class TweetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "data_postagem")
    private Calendar data;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UserEntity usuario;

}
