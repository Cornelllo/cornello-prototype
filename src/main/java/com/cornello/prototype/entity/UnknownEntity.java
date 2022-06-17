package com.cornello.prototype.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@NamedStoredProcedureQuery( 
    name = "namedFindByFullName",
    procedureName = "findByFullName", 
    resultClasses = { UnknownEntity.class }, 
    parameters = { @StoredProcedureParameter(name = "full_name", type = String.class) } 
)
public class UnknownEntity {
    @Id
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private LocalDateTime createdAt;
}
