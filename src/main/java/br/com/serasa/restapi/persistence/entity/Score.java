package br.com.serasa.restapi.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "score")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score_descricao", nullable = false)
    private String scoreDescricao;

    @Column(name = "inicial", nullable = false)
    private Integer scoreInicial;

    @Column(name = "final", nullable = false)
    private Integer scoreFinal;

}
