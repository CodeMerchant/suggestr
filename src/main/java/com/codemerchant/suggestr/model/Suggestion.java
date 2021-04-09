package com.codemerchant.suggestr.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity //marking class as a JPA entity so it can be used in Jpa persistence environment
@Table(name = "suggestr_comment")

//@EntityListeners - used to dynamically populate the createdDate and createdBy properties annotated
//with @CreatedDate/By in the Suggestion model when persisting the comment entry into the table
@EntityListeners(AuditingEntityListener.class)

@Data
public class Suggestion {
    @Id
    @GeneratedValue
    private Long id;
    private String suggestion;

    @Enumerated(EnumType.STRING) //telling JPA that the value of the enum SuggestionType needs to be persisted as String
    //in the db
    private SuggestionType type;

    @CreatedDate
    private Timestamp createdDate;

    @CreatedBy
    private String createdBy;

}
