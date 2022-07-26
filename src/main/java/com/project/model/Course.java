package com.project.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;



@Entity
public class Course {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	@Column(length=25, nullable=false, unique=true)
	private String name;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

    public Course() {
    	
    }
	
	
	
	@Override
	public String toString() {
		return  this.name;
	}


	


	public Course(Integer id) {
	
		this.id = id;
	}


	public Course(String courseName){
		this.name=courseName;
	}


	


	/*
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; Course other = (Course) obj; if (id == null) { if (other.id != null)
	 * return false; } else if (!id.equals(other.id)) return false; return true; }
	 */
	
	
	

}
