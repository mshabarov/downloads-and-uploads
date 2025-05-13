package com.vaadin.example.taskmanagement.domain;

import com.vaadin.example.base.domain.AbstractEntity;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.sql.Blob;

/**
 * Entity representing an attachment with binary data.
 */
@Entity
@Table(name = "attachment")
public class Attachment extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "data", nullable = false)
    private Blob data;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mime", nullable = false)
    private String mime;

    @Override
    public @Nullable Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}