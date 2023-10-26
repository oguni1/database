package ru.ac.uniyar.databasescourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Teacher {
    public Integer teacherID;
    public String name;
    public String surname;
    public String department;

    public Teacher(
            Integer teacherID,
            String  name,
            String surname,
            String department){
        this.name = name;
        this.teacherID = teacherID;
        this.surname = surname;
        this.department = department;
    }

    public PreparedStatement insert(Connection conn) throws SQLException {
        String state = """
        INSERT INTO Teachers (
           name,
           surname,
           department
        )
        VALUES (?, ?, ?)
        """;
        PreparedStatement prep = conn.prepareStatement(state);
        prep.setString(2,this.name);
        prep.setString(3,this.surname);
        prep.setString(4,this.department);
        return prep;
    }
}
