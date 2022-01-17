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
@Entity(name = "EMOJI_TB")
public class Emoji {
    @Id
    @Column(name = "EMOJI_ID")
    private Long id;

    @Column(name = "EMOJI_NAME", nullable = false)
    private String word;

    @Column(name = "EMOJI", nullable = false)
    private String emoji;
}