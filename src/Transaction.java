import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
public class Transaction {
public Transaction(){
	
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
private int transID=0;
private Date date=new Date();
private long dt;
private double amount=0;
private int accountID=0;
private String flag="";
private String checkNum="";
private java.sql.Date sqlDate;

public int getTransID() {
	return transID;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
	dt=date.getTime();
}
public java.sql.Date getSqlDate(){
	sqlDate=new java.sql.Date(dt);
	return sqlDate;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public int getAccountID() {
	return accountID;
}
public void setAccountID(int accountID) {
	this.accountID = accountID;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}
public String getCheckNum() {
	return checkNum;
}
public void setCheckNum(String checkNum) {
	this.checkNum = checkNum;
}
public void addTransaction(Transaction newTrans){
	Connect();
		String add = "insert into Transaction "+"(transaction_date,amount,account_id,transaction_flag,check_num) "+"values (?,?,?,?,?)";
		try{
			Connect();
			PreparedStatement ps=con.prepareStatement(add);
			ps.setDate(1, newTrans.getSqlDate());
			ps.setDouble(2, newTrans.getAmount());
			ps.setInt(3, newTrans.getAccountID());
			ps.setString(4, newTrans.getFlag());
			ps.setString(5, newTrans.getCheckNum());
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
public Transaction findTransaction(String transID){
	 Transaction newTrans=new Transaction();	
		String sql = "select * from account where transaction_id='"+transID+"'";
		try{
			Connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				newTrans.setDate(rs.getDate("transaction_date"));
				newTrans.setAmount(rs.getDouble("amount"));
				newTrans.setAccountID(rs.getInt("account_id"));
				newTrans.setFlag(rs.getString("transaction_flag"));
				newTrans.setCheckNum(rs.getString("check_num"));
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
	 return newTrans;
}
public double CalculateBalance(int accID){
	double balance=0;
	String sql = "select sum(amount) as balance from transaction where account_id='"+accID+"'";
	try{
		Connect();
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		rs.next();
		balance=rs.getDouble("balance");
			
			//System.out.println(rs.getString(2));
		
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
 return balance;
}
public ArrayList<Transaction> findTransactionHistory(int accID){
	ArrayList<Transaction> transactions=new ArrayList<Transaction>(); 
	Transaction newTrans=new Transaction();	
		String sql = "select * from transaction where account_id='"+accID+"'";
		try{
			Connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				newTrans.setDate(rs.getDate("transaction_date"));
				newTrans.setAmount(rs.getDouble("amount"));
				newTrans.setAccountID(rs.getInt("account_id"));
				newTrans.setFlag(rs.getString("transaction_flag"));
				newTrans.setCheckNum(rs.getString("check_num"));
				transactions.add(newTrans);
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
	 return transactions;
}
}
