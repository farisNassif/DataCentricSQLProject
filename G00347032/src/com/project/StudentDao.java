package com.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Interface for methods to implement in StudentDaoImpl
public interface StudentDao {
	// Array list of products (Product Class)
	ArrayList<Student> getAllStudents() throws SQLException; 
	
	// Array list for student display
	ArrayList<Student> showAllDetailsForStudent(String sid) throws SQLException; 
	
	Student getStudent(String sid); 
	
	Student extractStudentFromResultSet(ResultSet res) throws SQLException; 

	boolean deleteStudent(String sid);

	boolean insertStudent(Student student);

}
