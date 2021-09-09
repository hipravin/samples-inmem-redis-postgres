package com.hipravin.samples.jsonb;


import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_JSONB")
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public class EmployeeJsonb {
    @Id
    @Column(name = "ID")
    private Long id;

    @Type(type = "json")
    @Column(name = "CONTENT", columnDefinition = "jsonb")
    private String content;

    public EmployeeJsonb() {
    }

    public EmployeeJsonb(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
