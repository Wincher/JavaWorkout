package solr;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * @author wincher
 * @date   22/11/2017.
 */
public class User implements Serializable {
	
	@Field("id")
	private String id;
	
	@Field("user_name")
	private String name;
	
	@Field("user_age")
	private int age;
	
	@Field("user_sex")
	private String sex;
	
	@Field("user_like")
	private String[] like;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String[] getLike() {
		return like;
	}
	
	public void setLike(String[] like) {
		this.like = like;
	}
}
