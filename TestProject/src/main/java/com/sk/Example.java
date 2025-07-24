package com.sk;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Example")
public class Example extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public Example() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException{
    	
    	response.setContentType("text/html");
//    	PrintWriter pr = response.getWriter();
    	String nm = request.getParameter("name");
    	String em = request.getParameter("email1");
    	String num =request.getParameter("number1");
    	String msg = request.getParameter("message");
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		System.out.println("Driver class is loaded");
    		String url = "jdbc:mysql://localhost:3306/jaykisan";
    		String usr = "root";
    		String pass = "";
    		Connection con = DriverManager.getConnection(url,usr,pass);
    		if(con!=null) {
    			System.out.println("Connection is established....!");
    			PreparedStatement ps = con.prepareStatement("INSERT INTO `contact_info`(`name`, `number`, `email`, `message`) VALUES (?,?,?,?)");
    			ps.setString(1, nm);
    			ps.setString(2, num);
    			ps.setString(3, em);
    			ps.setString(4, msg);
    			int i = ps.executeUpdate();
    			System.out.println("Affected row : " +i);
    			
    			response.sendRedirect("contact.html");
    		}
    		else {
    			System.out.println("Connection not established...!");
    		}
    	}
    	catch (Exception e) {
    		System.out.println(e);
		}
    	
    }
    
}
