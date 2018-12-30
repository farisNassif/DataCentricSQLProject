package com.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean
@SessionScoped
public class StudentDaoImpl implements StudentDao{
	// Arraylists for sutdent data, second one is for specific student data
	ArrayList<Student> studentsList;
	ArrayList<Student> specificStudentDisplay;
	
	private DataSource mysqlDS;
	Statement myStmt = null;
	ResultSet res = null;
	PreparedStatement ps = null;
	
	// Constructor
	public StudentDaoImpl() throws NamingException { 
		Context context = new InitialContext();
		String jndiName = "java:comp/env/emp";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}
	
	// Retrieve list of students from the database
	// Array list of students (Student Class)
	@Override
	public ArrayList<Student> getAllStudents() throws SQLException { 
		// Establishing a connection 
		Connection conn = mysqlDS.getConnection();
		// Create a Statement
		myStmt = conn.createStatement();
		// Query
		String query = "select * from student";
		// Execute the Query
		res = myStmt.executeQuery(query);
		
		// Array list for students
		studentsList = new ArrayList<Student>(); 
		
		// Loops through all records in the database
		while (res.next())  
		{
			// Make an instance of class student
			Student stud = new Student(); 
			// Add to array list
			stud.setSid(res.getString("sid")); 
			stud.setCid(res.getString("cid"));
			stud.setName(res.getString("name")); 
			stud.setAddress(res.getString("address"));
			// Add to list records
			studentsList.add(stud); 
		} 

		// Close connection, statement and result set
		conn.close();
		myStmt.close();
		res.close();
		
		// Return students
		return studentsList; 
	}

	@Override
	public ArrayList<Student> showAllDetailsForStudent(String sid) throws SQLException {
		// Establish a Connection
		Connection conn = mysqlDS.getConnection();
		// Query
		String query = "SELECT s.sid, s.name, s.cID, c.cName, c.duration FROM course c inner join student s on s.cID = c.cID where s.sid like ?";
		// Create a prepare Statement
		myStmt = conn.createStatement();
		PreparedStatement ps = null;
		ResultSet res = null;
		ps = conn.prepareStatement(query);
		// Execute the Query
		ps.setString(1, sid);
		res = ps.executeQuery();

		// Array list for specific student detail display
		specificStudentDisplay = new ArrayList<Student>();

		while (res.next()) { 
			// Make an instance of class student
			Student student = new Student();
			// Add to array list
			student.setSid(res.getString("sid"));
			student.setName(res.getString("name"));
			student.setCid(res.getString("cid"));
			student.setcName(res.getString("cname"));
			student.setcDuration(res.getString("duration"));
			
			// Adding student object to specific student array list
			specificStudentDisplay.add(student);
		}
		// Close connection, statement and result set
		conn.close();
		ps.close();
		res.close();

		// Return the specific student details
		return specificStudentDisplay;
	}
	
	@Override
	public Student getStudent(String sid) {
		try {
			// Establish a Connection
			Connection conn = mysqlDS.getConnection();
			// Create a Statement
			myStmt = conn.createStatement();
			// Query
			String query = "SELECT * from student WHERE sid =" + sid;
			// Execute the Query
			res = myStmt.executeQuery(query);

			if (res.next()) 
			{
				// Get a student from the array list by SID
				return extractStudentFromResultSet(res); 
			} 

			// Close connection, statement and result set
			conn.close();
			myStmt.close();
			res.close();
			} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public boolean deleteStudent(String sid) {
		try {
			// Establish a Connection
			Connection conn = mysqlDS.getConnection();
			// Query
			String query = "DELETE FROM student WHERE sid='" +  sid +"'";
			// Create a prepare Statement
			myStmt = conn.createStatement();
			// Execute the Query
			int i = myStmt.executeUpdate(query);
			if (i == 1) {
				return true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	@Override
	public boolean insertStudent(Student student) {
		try {
			// Establish a Connection
			Connection conn = mysqlDS.getConnection();
			// Query
			String query = "INSERT INTO student VALUES(?, ?, ?, ?)";
			// Create a prepare Statement
			ps = conn.prepareStatement(query);
			// Execute the Query
			ps.setString(1, student.getSid());
			ps.setString(3, student.getName());
			ps.setString(4, student.getAddress());
			ps.setString(2, student.getCid());
			// Execute the Query 
			int i = ps.executeUpdate();

			if (i == 1) 
			{
				return true;
			}
			// Close connection, statement and result set
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	// Used to get student, returns the specific student
	public Student extractStudentFromResultSet(ResultSet res) throws SQLException {
		Student student = new Student();
		
		student.setSid(res.getString("sid"));
		student.setName(res.getString("name"));
		return student;
	}

}
