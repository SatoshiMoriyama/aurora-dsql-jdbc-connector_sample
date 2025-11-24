package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        String clusterEndpoint = System.getenv("DSQL_CLUSTER_ENDPOINT");
        String url = "jdbc:aws-dsql:postgresql://" + clusterEndpoint + "/postgres?user=admin";
        
        try (Connection conn = DriverManager.getConnection(url)) {
            
            // Insert data
            System.out.println("Inserting data...");
            UUID insertedId;
            
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO owner (id, name, city, telephone) VALUES (?, ?, ?, ?) RETURNING *")) {
                pstmt.setObject(1, UUID.randomUUID());
                pstmt.setString(2, "mori");
                pstmt.setString(3, "sapporo");
                pstmt.setString(4, "090-9999-9999");
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        insertedId = (UUID) rs.getObject("id");
                        System.out.println("Inserted: id=" + insertedId + ", name=" + rs.getString("name") + 
                                         ", city=" + rs.getString("city") + ", telephone=" + rs.getString("telephone"));
                    }
                }
            }
            
            // Select data
            String selectSql = "SELECT * FROM owner WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                pstmt.setString(1, "mori");
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("Selected:");
                    while (rs.next()) {
                        System.out.println("  id=" + rs.getObject("id") + ", name=" + rs.getString("name") + 
                                         ", city=" + rs.getString("city") + ", telephone=" + rs.getString("telephone"));
                    }
                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
