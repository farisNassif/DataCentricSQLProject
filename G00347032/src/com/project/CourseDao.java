package com.project;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CourseDao {
	ArrayList<Course> getAllCourses() throws SQLException; // Array list of courses (Courses class)
	
	ArrayList<Course> getAllSpecificCourses(String cid) throws SQLException; // Array list of courses (Courses class)
	
	boolean deleteCourse(String cid);

	boolean insertCourse(Course course);	
}
