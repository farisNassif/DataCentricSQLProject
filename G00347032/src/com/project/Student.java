package com.project;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Student {

	private String sid;
	private String cid;
	private String name;
	private String address;
	
	// For Overload for join
	private String cName;
	private String cDuration;
	
	// Default Constructor - Requirement for ManagedBean
	public Student() { 
		
	}
	
	//Constructor for student object
	public Student(String sid, String cid, String name, String address) { 
		this.sid = sid;
		this.cid = cid;
		this.name = name;
		this.address = address;
	}
	
	// Overloaded Constructor for join 
	public Student(String sid, String name, String cid, String cName, String cDuration) { //Constructor
		this.sid = sid;
		this.name = name;
		this.cid = cid;
		this.cName = cName;
		this.cDuration = cDuration;
	}

	// Everything below is just getters + setters generated for the class attributes
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getCid() {
		return cid;
	}

	// Used for join
	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcDuration() {
		return cDuration;
	}

	public void setcDuration(String cDuration) {
		this.cDuration = cDuration;
	}
	
	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
