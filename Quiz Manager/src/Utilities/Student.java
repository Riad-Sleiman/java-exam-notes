package Utilities;

public class Student {
	
	private String name;
	private String id;
	
	private Integer db_id;
	//Getters and Setters.
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getDb_id() {
		return db_id;
	}
	public void setDb_id(Integer db_id) {
		this.db_id = db_id;
	}
	//Getters and Setters.
	
	//Constructors
	public Student() {
		this.name = null;
		this.id = null;
		this.db_id = null;
	}
	
	public Student(String name, String id) {
		this.name = name;
		this.id = id;
		this.db_id = null;
	}
	
	public Student(String name, String id, Integer db_id) {
		this.name = name;
		this.id = id;
		this.db_id = db_id;
	}
	
	public Student(Integer db_id) {
		this.db_id = db_id;
		this.name = null;
		this.id = null;
	}
	
}
