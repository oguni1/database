package ru.ac.uniyar.databasescourse;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseExample {
    private static final String URL = String.format("jdbc:mariadb://%s", System.getenv("MARIADB_HOST"));
    private static final String user = System.getenv("MARIADB_USER");
    private static final String password = System.getenv("MARIADB_PASSWORD");

    public static void main(String[] args) {

        System.out.println("The work has started");

        try (Connection conn = createConnection()) {
            try (Statement smt = conn.createStatement()) {
                smt.executeQuery("USE IvanOgarkovDB");
                try {
                    ArrayList<PreparedStatement> inserts = insertSolutions(conn);
                    for(PreparedStatement insert: inserts){
                        ResultSet rs = insert.executeQuery();
                        while (rs.next()) {
                            System.out.printf("Student added: %s %s\n", rs.getString(1),rs.getString(2));
                        }
                    }
                }
                catch (SQLException ex) {
                    System.out.printf("Statement execution error: %s\n", ex);
                }
            }
            catch (SQLException ex) {
                System.out.printf("Can't create statement: %s\n", ex);
            }
        }
        catch (SQLException ex) {
            System.out.printf("Can't create connection: %s\n", ex);
        }
    }

    private static ArrayList<PreparedStatement> insertSolutions(Connection conn){
        ArrayList<PreparedStatement> queries = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("students.csv"));
            String[] line = reader.readNext();
            line = reader.readNext();
            while(line!=null){
                Student student = new Student(
                        line[1],
                        line[2]
                );
                queries.add(student.insert(conn));
                line = reader.readNext();
            }
        }catch (IOException  e){
            System.err.println("File not found");
        }
        catch (CsvValidationException e){
            System.err.println("CSV file is not validating");
        }
        catch (Exception e){
            System.err.println(e.getStackTrace());
        }

        return queries;
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, password);
    }

}