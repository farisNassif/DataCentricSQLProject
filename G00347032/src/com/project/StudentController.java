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
public class StudentController {
	
	StudentDaoImpl dao;
	ArrayList<Student> students;
	Student student = new Student();
	private UIComponent addButton;
	
	public StudentController() throws Exception {
		super();
		// New instances of StudentDaoImpl class
		dao = new StudentDaoImpl(); 
		// Array list for Students
		students = new ArrayList<Student>(); 
	}

	public String loadStudents() throws SQLException {		
		try 
		{
			// Passing on call get all students from the database
			students = dao.getAllStudents();
		} catch (Exception e) {
			// Had issues with the faces message
			System.out.println("Cannot connect to database");
		}
		return null;		
	}
	
	// For join to display the full student details
	public String passSidForJoin(String sid) throws SQLException {	
		students = dao.showAllDetailsForStudent(sid);	
		return "fullStudentDetails.xhtml";	
	}
	
	// Returning ArrayList of students
	public ArrayList<Student> getStudents() {
		return students;
	}
	
	// Add / Insert a student to the database
	public void insert(Student student) {		
		dao.insertStudent(student); 
	}

	// Pass on the SID of the object student to be removed
	public void delete(Student student) {		
		dao.deleteStudent(student.getSid());
	}

	// This is making sure there is no duplicates in the database
	public String entryValidation(Student student) {
		int i = 0;
		// For looping i times (i being rows in the database)
		for (i = 0 ; i < getStudents().size() ; i++) {
			// If there's already a record with the same name, refuse to enter it
			if (student.getName().equalsIgnoreCase(students.get(i).getName()))
			{
				// Faces message for user
	            FacesMessage message = new FacesMessage("• Error: Duplicate entry '" + student.getName() + "' for key 'name'");
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(addButton.getClientId(context), message);
	            // Do nothing pretty much & return
				return "addStudent.xhtml";
			}
			// If there's already a record with the same Student ID
			if (student.getSid().equalsIgnoreCase(students.get(i).getSid()))
			{
				// Faces message for user
	            FacesMessage message = new FacesMessage("• Error: Duplicate entry '" + student.getSid() + "' for key 'PRIMARY'");
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(addButton.getClientId(context), message);
	            // Do nothing pretty much & return
				return "addStudent.xhtml";
			}
		}
		// If it's a unique name stick the student in the database & return to the previous page
		insert(student); 			
		return "studentDisplay.xhtml";
	}
	
	// These two methods are required for the faces validation popup text when something can't be entered in addStudent.xhtml
    public void setAddButton(UIComponent addButton) {
        this.addButton = addButton;
    }

    public UIComponent getAddButton() {
        return addButton;
    }

}
