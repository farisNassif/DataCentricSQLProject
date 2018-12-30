package com.project;

import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Course {

	private String cid;
	private String cName;
	private int duration;
	// Overload for join
	private String studentName;
	private String studentAddress;

	// Default Constructor - Requirement for ManagedBean
	public Course() { 
		
	}
	
	//Constructor
	public Course(String cid, String cName, int duration) { 
		this.cid = cid;
		this.cName = cName;
		this.duration = duration;
	}
	
	//Overloaded constructor for join
	public Course(String cid, String cName, int duration, String studentName, String studentAddress)
	{
		this.cid = cid;
		this.cName = cName;
		this.duration = duration;
		this.studentName = studentName;
		this.studentAddress = studentAddress;
	}

	// Everything below this is just getters + setters generated for the class attributes
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentAddress() {
		return studentAddress;
	}

	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}
}
