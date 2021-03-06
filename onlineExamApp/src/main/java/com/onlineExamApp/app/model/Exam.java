package com.onlineExamApp.app.model;

import java.util.Date;
import javax.persistence.Entity;

import javax.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Exam {
	

	@Id
    private Integer id;
	
	private String name;
	
	private String addedBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastUpdatedDate;
	
	private Integer noQues;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDateTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDateTime;
	
	private Integer duration;
	
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
	
	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public Integer getNoQue() {
        return noQues;
    }
	
	public void setNoQue(Integer noQues) {
        this.noQues = noQues;
    }

	
	public Date getStartDateTime() {
        return startDateTime;
    }
	
	public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }
	
	public Date getEndDateTime() {
        return endDateTime;
    }
	
	public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
	
	public Integer getDuration() {
        return duration;
    }
	
	public void setDuration(Integer duration) {
        this.duration = duration;
    }

	
}
