/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author phily
 */
public class ConectorDB extends HttpServlet{
     private static final long serialVersionUID = 1L;
 
    @Resource(name = "jdbc/Hospital")
    private DataSource fuente;
 
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String sql = "insert into cursos  values (2,'java') ";
 
        try (Connection conn = fuente.getConnection(); Statement stmt = conn.createStatement();) {
 
            stmt.executeUpdate(sql);
             
        } catch (Exception se) {
 
            throw new RuntimeException("error SQL", se);
        }
    }
    
    
}
