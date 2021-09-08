package com.hipravin.samples.jsonb;


import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE_JSONB")
public class EmployeeJsonb {
    @Id
    @Column(name = "ID")
    private Long id;
//    @Lob
//    @Column(name = "CONTENT")
    @Transient
    private byte[] content;

    public EmployeeJsonb() {
    }

    public EmployeeJsonb(Long id, byte[] content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
