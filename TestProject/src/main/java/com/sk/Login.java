package com.sk;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();

	        String usrName = request.getParameter("username");
	        String usrPass = request.getParameter("password");

	        try {
	            // Load JDBC driver
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            System.out.println("Driver class is loaded");

	            // Establish a database connection
	            String url = "jdbc:mysql://localhost:3306/jaykisan";
	            String usr = "root";
	            String pass = "";
	            Connection con = DriverManager.getConnection(url, usr, pass);

	            if (con != null) {
	            	System.out.println("Connection Etstablished.....!");
	                // Prepare SQL query with parameters to prevent SQL injection
	                String sql = "SELECT * FROM `login` WHERE user = ? AND password = ?";
	                PreparedStatement ps = con.prepareStatement(sql);
	                ps.setString(1, usrName);
	                ps.setString(2, usrPass);

	                // Execute the query
	                ResultSet result = ps.executeQuery();

	                // Check if user exists
	                if (result.next()) {
	                    String user = result.getString("user");
	                    String password = result.getString("password");
	                    System.out.println("User name = " + user + "\tPass= " + password);
	                    response.sendRedirect("admin.html");
	                } else {
	                    out.println("<h2>Invalid username or password.</h2>");
	                }

	                // Close the ResultSet, PreparedStatement, and Connection
	                result.close();
	                ps.close();
	                con.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            out.println("<h2>Error: " + e.getMessage() + "</h2>");
	        }
	}

}
