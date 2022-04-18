package br.com.serasa.restapi.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table( name = "afinidade")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Afinidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "regiao", nullable = false)
    private String regiao;

    @ElementCollection
    @Column(name = "estados", nullable = false)
    private Set<String> estados;
}
