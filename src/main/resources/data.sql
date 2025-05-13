-- Initialize attachment table with test data
INSERT INTO attachment (id, data, size, name, mime)
VALUES (1, FILE_READ('classpath:com/vaadin/example/taskmanagement/ui/view/notes.txt'),
        (SELECT LENGTH(FILE_READ('classpath:com/vaadin/example/taskmanagement/ui/view/notes.txt'))),
        'notes.txt', 'text/plain');