package com.cestarcollege.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean inserted =false;
		 try {
			insertData(request);
			inserted = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inserted = false;
		}
		 
		if(inserted)
			response.sendRedirect("login.html");
		else
			response.sendRedirect("/exception");
	}

	private void insertData(HttpServletRequest request) throws SQLException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String emailAddress = request.getParameter("email_address");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String category= request.getParameter("category");
		
		Connection conn = getConnection();
		String insertQuery = new StringBuilder()
				.append("INSERT INTO CESTAR_USER(")
				.append(" first_name, last_name, gender")
				.append(", email, department, password)")
				.append("VALUES(?,?,?,?,?,?)")
				.toString();
		try {
			PreparedStatement ps = conn.prepareStatement(insertQuery);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, gender);
			ps.setString(4, emailAddress);
			ps.setString(5, category);
			ps.setString(6, password);
			 ps.execute();
		} catch (SQLException e) {
			throw e;
		}
		
	}

	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 con=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:xe","system","1234");  
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return con;
	}

}
