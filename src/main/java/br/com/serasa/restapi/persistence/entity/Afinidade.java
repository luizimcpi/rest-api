package br.com.serasa.restapi.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "afinidade")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Afinidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "regiao", nullable = false)
    private String regiao;

    @ElementCollection
    @Column(name = "estados", nullable = false)
    private List<String> estados;
}
