package com.cestarcollege.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
			int id=-1;
			try {
				id = getUserId(request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if(id>0){
			response.sendRedirect("profile.html");
		}else{
			response.sendRedirect("exception");
		}
	}
	
	private int getUserId(HttpServletRequest request) throws SQLException {
		String emailAddress = request.getParameter("email_address");
		String password = request.getParameter("password");
		
		Connection conn = getConnection();
		
		String selectQuery =new StringBuilder()
					.append("SELECT id from CESTAR_USER ")
					.append("WHERE email=? and password=?").toString();
		PreparedStatement ps =conn.prepareStatement(selectQuery);
		ps.setString(1, emailAddress);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		int id=-1;
		while(rs.next()){
			id=rs.getInt(1);
			break;
		}
		return id;
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
