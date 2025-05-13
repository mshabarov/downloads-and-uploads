package com.vaadin.example.taskmanagement.service;

import com.vaadin.example.taskmanagement.domain.Attachment;
import com.vaadin.example.taskmanagement.domain.AttachmentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service for managing attachments.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final DataSource dataSource;

    public AttachmentService(AttachmentRepository attachmentRepository, DataSource dataSource) {
        this.attachmentRepository = attachmentRepository;
        this.dataSource = dataSource;
    }

    /**
     * Retrieves an attachment by its ID.
     *
     * @param id the ID of the attachment to retrieve
     * @return the attachment with the given ID, or null if not found
     */
    public Attachment getAttachmentById(long id) {
        return attachmentRepository.findById(id);
    }

    /**
     * Fetches attachment data from the database as a ResultSet.
     * This method is used by the download handler to stream attachment data.
     *
     * @param id the ID of the attachment to fetch
     * @return a ResultSet containing the attachment data
     * @throws SQLException if a database error occurs
     */
    public ResultSet fetchAttachmentFromDatabase(long id) throws SQLException {
        Connection connection = dataSource.getConnection();

        // We need to keep the connection open for the ResultSet to be valid
        // The caller is responsible for closing the ResultSet, which will also close the connection
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id, data, size, name, mime FROM attachment WHERE id = ?");
        statement.setLong(1, id);

        return statement.executeQuery();
    }
}
