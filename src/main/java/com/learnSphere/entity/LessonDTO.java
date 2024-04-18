package com.learnSphere.entity;

public class LessonDTO {
	int courseId;
	String lessonName;
	String topics;
	String link;
	public LessonDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LessonDTO(int courseId, String lessonName, String topics, String link) {
		super();
		this.courseId = courseId;
		this.lessonName = lessonName;
		this.topics = topics;
		this.link = link;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public String getTopics() {
		return topics;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "LessonDTO [courseId=" + courseId + ", lessonName=" + lessonName + ", topics=" + topics + ", link="
				+ link + "]";
	}
	

}
