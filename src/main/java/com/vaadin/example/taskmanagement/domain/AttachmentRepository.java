package com.vaadin.example.taskmanagement.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository for managing {@link Attachment} entities.
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment> {

    /**
     * Find an attachment by its ID.
     *
     * @param id the ID of the attachment
     * @return the attachment with the given ID
     */
    Attachment findById(long id);
}