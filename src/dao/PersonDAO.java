package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Person;

public class PersonDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

	//共通接続処理
	public void connect() throws NamingException, SQLException {
		Context context = new InitialContext();
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/jsp");
		this.db = ds.getConnection();
	}

	//共通切断処理
	public void disconnect() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
			if(db != null) {
				db.close();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//全件取得
	public List<Person> findAll(){
		List<Person> list = new ArrayList<>();
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM persons");
			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				Person person = new Person(id,name,age);
				list.add(person);
			}
			System.out.println(list.size());
		} catch (NamingException | SQLException e) {
			System.out.println("catch");
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
		return list;
	}

	//一件取得
	public Person findOne(int id) {
		Person person = null;
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM persons WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			person = new Person(rs.getInt("id"),rs.getString("name"),rs.getInt("age"));
		} catch (NamingException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
		return person;
	}

	//一件新規作成
	public void insertOne(Person person) {
		try {
			this.connect();
			ps = db.prepareStatement("INSERT INTO persons(name,age) VALUES(?,?)");
			ps.setString(1, person.getName());
			ps.setInt(2,person.getAge());
			ps.execute();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
	}
}
