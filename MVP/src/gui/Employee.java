package gui;

public class Employee {
	String name;
	int id;
	int age;
	public Employee() {
		name="";
		id=0;
		age=0;
	}
	@Override
	public String toString() {
		return ("" + name+ " " + id+ " " + age);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
