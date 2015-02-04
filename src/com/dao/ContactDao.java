package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.context.Container;
import com.context.DataSource;
import com.model.Contact;

public class ContactDao {

	private DataSource dataSource;

	public ContactDao() {
		dataSource = Container.getDataSource();
	}

	public List<Contact> findAllContacts() throws SQLException,
			ClassNotFoundException {
		Connection connection = dataSource.getConnection();
		String sql = "select id,name,mobile,landline,email from contact";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		return populateContacts(resultSet);
	}

	private List<Contact> populateContacts(ResultSet resultSet)
			throws SQLException {
		List<Contact> contacts = new ArrayList<Contact>();
		while (resultSet.next()) {
			Contact contact = new Contact();
			contact.setId(resultSet.getInt("id"));
			contact.setName(resultSet.getString("name"));
			contact.setMobile(resultSet.getString("mobile"));
			contact.setLandLine(resultSet.getString("landline"));
			contact.setEmail(resultSet.getString("email"));
			contacts.add(contact);
		}
		return contacts;
	}

	public Contact findById(int id) throws SQLException, ClassNotFoundException {
		Connection connection = dataSource.getConnection();
		String sql = "select id,name,mobile,landline,email from contact where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		return populateContact(resultSet);
	}

	private Contact populateContact(ResultSet resultSet) throws SQLException {
		Contact contact = null;
		if (resultSet.next()) {
			contact = new Contact();
			contact.setId(resultSet.getInt("id"));
			contact.setName(resultSet.getString("name"));
			contact.setMobile(resultSet.getString("mobile"));
			contact.setLandLine(resultSet.getString("landline"));
			contact.setEmail(resultSet.getString("email"));
		}
		return contact;
	}

	public Contact create(String name, String mobile, String landline,
			String email) throws SQLException, ClassNotFoundException {
		Connection connection = dataSource.getConnection();
		String sql = "insert into contact(name,mobile,landline,email) values('?','?','?','?')";
		PreparedStatement statement = connection.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, name);
		statement.setString(2, mobile);
		statement.setString(3, landline);
		statement.setString(4, email);
		int insertedContactId = statement.executeUpdate();
		return findById(insertedContactId);
	}

}
