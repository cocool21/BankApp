import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
	private int customerID=0;
	private String customerName="";
	public void setCustomerID(int value){
		this.customerID=value;
	}
	public int getCustomerID() {
		return customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String newName) {
		this.customerName = newName;
	}
	public Customer(){

	}
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	public void Connect(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		}
	}	
	public void addCustomer(Customer newCus){
		Connect();
		String add = "insert into customer "+"(customer_name) "+"values (?)";
		try{
			Connect();
			PreparedStatement ps=con.prepareStatement(add);
			ps.setString(1, newCus.getCustomerName());
			int count=ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

	}
	public Customer findCustomer(String name){
		Customer newCus=new Customer();	
		String sql = "select * from customer where customer_name='"+name+"'";
		try{
			Connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				newCus.setCustomerID(rs.getInt("customer_id"));
				newCus.setCustomerName(rs.getString("customer_name"));

				//System.out.println(rs.getString(2));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return newCus;
	}

}
