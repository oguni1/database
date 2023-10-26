package ru.ac.uniyar.databasescourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student {
    public String name;
    public String surname;

    public Student( String  name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public PreparedStatement insert(Connection conn) throws SQLException {
        String state = """
        INSERT INTO Students (
            name,
            surname,
            )
        VALUES (?, ?)
        """;
        PreparedStatement prep = conn.prepareStatement(state);
        prep.setString(2,this.name);
        prep.setString(3,this.surname);
        return prep;
    }
}
