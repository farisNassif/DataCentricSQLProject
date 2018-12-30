package com.project;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;


@ManagedBean
@SessionScoped
public class CourseController {

	CourseDaoImpl dao;
	ArrayList<Course> courses;
	Course course = new Course();
	private UIComponent addButton;
	public static String testJoin = "";
	
	public CourseController() throws Exception {
		super();
		// New instances of CourseDaoImpl class
		dao = new CourseDaoImpl(); 
		// Array list for Courses
		courses = new ArrayList<Course>(); 
	}
	
	public void loadCourses() throws SQLException {
		try 
		{
			// Passing on call get all courses from the database
			courses = dao.getAllCourses(); 
		} catch (Exception e) {
			// Had issues with the faces message
			System.out.println("Cannot connect to database");
		}	
	}
	
	// Used for joining when user clicks show students in this course
	public String passCidForJoin(String cid) throws SQLException {		
		courses =  dao.getAllSpecificCourses(cid);		
		return "courseStudentDetails.xhtml";	
	}
	
	public void insert(Course course) {
		// Add / Insert a course to the database
		dao.insertCourse(course); 
	}
	public void delete(Course course) {
		// Pass on the cid of the object course
		dao.deleteCourse(course.getCid());
	}
	
	// Very similar to the entry validation in studentController, ensures there are no duplicate cid's
	public String entryValidation(Course course) {
		int i = 0;
		// For looping i times (i being rows in the database)
		for (i = 0 ; i < getCourses().size() ; i++) {
			// If there's already a record with the same name
			if (course.getCid().equalsIgnoreCase(courses.get(i).getCid()))
			{
				// Faces message for user
	            FacesMessage message = new FacesMessage("• Error: Course ID '" + course.getCid() + "' already exists!");
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(addButton.getClientId(context), message);
	            // Do nothing pretty much
				return "addCourse.xhtml";
			}
		}
		// If it's a unique cid stick the course in the database & return to the previous page
		insert(course); // Add course		
		return "manageCourses.xhtml";
	}
	
	// Getters/setters for courses
	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	// For faces display message
    public void setAddButton(UIComponent addButton) {
        this.addButton = addButton;
    }

    public UIComponent getAddButton() {
        return addButton;
    }       
}
