import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Account {
public Account(){
	
}
private Connection con = null;
private Statement stmt = null;
private ResultSet rs = null;
private int customerID=0;
private int accID=0;
private String accNum="";
private String flag="";

public int getCustomerID() {
	return customerID;
}
public void setCustomerID(int customerID) {
	this.customerID = customerID;
}
public int getAccID() {
	return accID;
}
public void setAccID(int accID) {
	this.accID = accID;
}
public String getAccNum() {
	return accNum;
}
public void setAccNum(String accNum) {
	this.accNum = accNum;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}


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
public void addAccount(Account myAcc){
	Connect();
		String add = "insert into account "+"(customer_id,account_number,account_type) "+"values (?,?,?)";
		try{
			Connect();
			PreparedStatement ps=con.prepareStatement(add);
			ps.setLong(1, myAcc.getCustomerID());
			ps.setString(2, myAcc.getAccNum());
			ps.setString(3, myAcc.getFlag());
			int count=ps.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		finally {
			try {
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	
 }

public Account findAccount(String accNum){
	 Account newAcc=new Account();	
		String sql = "select * from account where account_number='"+accNum+"'";
		try{
			Connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				newAcc.setCustomerID(rs.getInt("customer_id"));
				newAcc.setAccID(rs.getInt("account_id"));
				newAcc.setAccNum(rs.getString("account_number"));
				newAcc.setFlag(rs.getString("account_type"));
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
	 return newAcc;
}

public ArrayList<Account> findCustomerAccount(int custID){
	 Account newAcc=new Account();
	 ArrayList<Account> accounts=new ArrayList<Account>();
		String sql = "select * from account where customer_id='"+custID+"'";
		try{
			Connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				newAcc.setCustomerID(rs.getInt("customer_id"));
				newAcc.setAccID(rs.getInt("account_id"));
				newAcc.setAccNum(rs.getString("account_number"));
				newAcc.setFlag(rs.getString("account_type"));
				accounts.add(newAcc);
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
	 return accounts;
}

}
