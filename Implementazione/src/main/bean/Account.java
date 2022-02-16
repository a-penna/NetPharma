package main.bean;

public class Account {
	private int id;
	private String username;
	private String password;
	private int orderCount;
	
	public Account() { }

	public Account(int id, String username, String password, int orderCount) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.orderCount = orderCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int count) {
		this.orderCount = count;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", orderCount=" + orderCount + "]";
	}

	public boolean equals(Object otherObject){
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		Account other = (Account)otherObject; 
		return id == (other.id)
				&& username.equals(other.username)
				&& password.equals(other.password)
				&& orderCount == other.orderCount; 
	}
	
}
