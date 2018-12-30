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
public class CourseDaoImpl implements CourseDao {
	ArrayList<Course> coursesList;
	ArrayList<Course> specificCourseList;

	private DataSource mysqlDS;
	Statement myStmt = null;
	ResultSet res = null;
	PreparedStatement ps = null;

	// Constructor
	public CourseDaoImpl() throws NamingException {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/emp";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}

	@Override
	public ArrayList<Course> getAllCourses() throws SQLException {
		// Establishing a connection
		Connection conn = mysqlDS.getConnection();
		// Create a Statement
		myStmt = conn.createStatement();
		// Query
		String query = "select * from course";
		// Execute the Query
		res = myStmt.executeQuery(query);

		// Array list for courses
		coursesList = new ArrayList<Course>();

		while (res.next()) // Loops through all records in the database
		{ // Make an instance of class course
			Course course = new Course();
			// Add to array list
			course.setCid(res.getString("cid"));
			course.setcName(res.getString("cName"));
			course.setDuration(res.getInt("duration"));
			coursesList.add(course); // Add to list records
		}

		// Close connection, statement and result set
		conn.close();
		myStmt.close();
		res.close();

		return coursesList; // Return courses
	}

	@Override
	public ArrayList<Course> getAllSpecificCourses(String cid) throws SQLException {
		// Establish a Connection
		Connection conn = mysqlDS.getConnection();
		// Query
		String query = "select c.cID, c.cName, c.duration, s.name, s.address from course c inner join student s on c.cID = s.cID WHERE c.cid like ?";
		// Create a prepare Statement
		myStmt = conn.createStatement();
		PreparedStatement ps = null;
		ResultSet res = null;
		ps = conn.prepareStatement(query);
		// Execute the Query
		ps.setString(1, cid);
		res = ps.executeQuery();

		// Array list for specific courses
		specificCourseList = new ArrayList<Course>();

		while (res.next()) { // Make an instance of class course
			Course course = new Course();
			// Add to array list
			course.setCid(res.getString("cid"));
			course.setcName(res.getString("cname"));
			course.setDuration(res.getInt("duration"));
			course.setStudentName(res.getString("name"));
			course.setStudentAddress(res.getString("address"));
			specificCourseList.add(course);
		}
		// Close connection, statement and result set
		conn.close();
		ps.close();
		res.close();

		return specificCourseList;
	}

	@Override
	public boolean deleteCourse(String cid) {
		try {
			// Establish a Connection
			Connection conn = mysqlDS.getConnection();
			// Query
			String query = "DELETE FROM course WHERE cid='" + cid + "'";
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
	public boolean insertCourse(Course course) {
		try {
			// Establish a Connection
			Connection conn = mysqlDS.getConnection();
			// Query
			String query = "INSERT INTO course VALUES(?, ?, ?)";
			// Create a prepare Statement
			ps = conn.prepareStatement(query);
			// Execute the Query
			ps.setString(1, course.getCid());
			ps.setString(2, course.getcName());
			ps.setInt(3, course.getDuration());

			int i = ps.executeUpdate();

			if (i == 1) {
				// If the attributes have been added finish up in this method and exit
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

}
