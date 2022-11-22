package com.costrategix.gbp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email_template")
public class EmailTemplate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="templateName")
	private String templateName;
	
	@Column(name="templateHTML")
	private String templateHTML;
	
	@Column(name="subject")
	private String subject;

	public EmailTemplate() {
		
	}
	
	public EmailTemplate(String templateName, String templateHTML, String subject) {
		super();
		this.templateName = templateName;
		this.templateHTML = templateHTML;
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateHTML() {
		return templateHTML;
	}

	public void setTemplateHTML(String templateHTML) {
		this.templateHTML = templateHTML;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
}
