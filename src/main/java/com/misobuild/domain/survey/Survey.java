package com.misobuild.domain.survey;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "SURVEY_TB")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SURVEY_ID")
    private Long id;

    @Column(nullable = false, name = "DESCRIPTION", columnDefinition = "varchar(10)")
    private String description;

    @Column(nullable = false, name = "TITLE" , columnDefinition = "varchar(20)")
    private String title;

    @Column(nullable = false, name = "CATEGORY" , columnDefinition = "varchar(20)")
    private String category;


    @Builder
    public Survey(String description, String title, String category) {
        this.description = description;
        this.title = title;
        this.category = category;
    }
}
