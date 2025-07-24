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

/**
 * Servlet implementation class DataServer
 */
@WebServlet("/DataServer")
public class DataServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        
        pw.println("<table class='table table-bordered table-striped'>");
        pw.println("<thead><tr><th>ID</th><th>Name</th><th>Number</th><th>Email</th><th>Message</th></tr></thead><tbody>");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jaykisan";
            String usr = "root";
            String pass = "";
            Connection con = DriverManager.getConnection(url, usr, pass);
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM contact_info");
            ResultSet rs = ps.executeQuery();
            
            int i =1;
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>"+ i + "</td>");
                pw.println("<td>" + rs.getString("name") + "</td>");
                pw.println("<td>" + rs.getString("number") + "</td>");
                pw.println("<td>" + rs.getString("email") + "</td>");
                pw.println("<td>" + rs.getString("message") + "</td>");
                pw.println("</tr>");
                i++;
            }
            
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<tr><td colspan='5'>Error retrieving data.</td></tr>");
        }
        
        pw.println("</tbody></table>");
    }

}
