package com.misobuild.domain.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

// TODO 인덱싱 by surveyId
@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "ANSWER_TB")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_ID")
    private Long id;

    @Column(nullable = false, name = "DESCRIPTION", columnDefinition = "varchar(20)")
    private String description;

    @Column(nullable = false, name = "ANSWER" , columnDefinition = "varchar(10)")
    private String answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false, name = "SURVEY_ID")
    private Survey survey;
}
