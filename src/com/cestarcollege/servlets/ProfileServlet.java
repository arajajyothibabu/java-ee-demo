package com.cestarcollege.servelets;

/**
 * Created by jyothi on 7/11/17.
 */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 * Servlet implementation class ExceptionServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        // TODO Auto-generated method stub
		System.out.println("Im in do Profile Sevelet Get..!!!");
		response.setContentType("application/json");
		try {
			System.out.println();
			response.getWriter().write(getData(request.getSession().getAttribute("id").toString()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //response.getWriter().append("Error in your process!!! ").append(request.getContextPath());
		
		//response.sendRedirect("profile.html");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
		boolean updated =false; 
		try 
		{
			updateData(request);
			updated =true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		if(updated)
			response.sendRedirect("profile.html");
		else
			response.sendRedirect("/exception");
    }
	
	private void updateData(HttpServletRequest request) throws SQLException {
		
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String emailAddress = request.getParameter("email_address");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String category= request.getParameter("category");
		int id = (int) request.getSession().getAttribute("id");
		Connection conn = getConnection();
		String updateQuery = new StringBuilder()
				.append("Update  CESTAR_USER set ")
				.append(" first_name = ?,")
				.append(" last_name = ?,")
                .append(" gender = ?, ")
				.append(" email = ?, department = ?, password = ? ")
				.append("where id =" + id)
				.toString();
		try {
			PreparedStatement ps = conn.prepareStatement(updateQuery);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, gender);
			ps.setString(4, emailAddress);
			ps.setString(5, category);
			ps.setString(6, password);
			ps.execute();
			System.out.println("Successfully updated the data for user id:" + id);
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	
	public String getData(String id) throws SQLException
	{
		Connection conn = getConnection();
		String selectQuery =new StringBuilder()
					.append("SELECT * from CESTAR_USER ")
					.append("WHERE id=?").toString();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(selectQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		Gson  jsonobj = new Gson();
		HashMap<String,String> json = new HashMap<String,String>();
		while(rs.next()){
			
			json.put("firstName",rs.getString(2));
			json.put("lastName",rs.getString(3));
			json.put("gender",rs.getString(4));
			json.put("email",rs.getString(5));
			json.put("department",rs.getString(6));
			json.put("password",rs.getString(7));
		}
		return jsonobj.toJson(json);
	}
	
	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 con=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:xe","system","123");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return con;
	}
}