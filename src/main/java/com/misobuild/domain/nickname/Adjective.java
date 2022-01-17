package com.misobuild.domain.nickname;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "ADJECTIVE_TB")
public class Adjective {
    @Id
    @Column(name = "ADJECTIVE_ID")
    private Long id;

    @Column(name = "WORD", nullable = false)
    private String word;
}