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
		String sql = "select id,name,mobile,landline,email,profile_image_url from contact";
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
			contact.setProfileImageUrl(resultSet.getString("profile_image_url"));
			contacts.add(contact);
		}
		return contacts;
	}

	public Contact findById(int id) throws SQLException, ClassNotFoundException {
		Connection connection = dataSource.getConnection();
		String sql = "select id,name,mobile,landline,email,profile_image_url from contact where id = ?";
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
			contact.setProfileImageUrl(resultSet.getString("profile_image_url"));
		}
		return contact;
	}

	public Contact create(Contact contact) throws SQLException,
			ClassNotFoundException {
		Connection connection = dataSource.getConnection();
		String sql = "insert into contact(name,mobile,landline,email,profile_image_url) values('?','?','?','?','?')";
		PreparedStatement statement = connection.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, contact.getName());
		statement.setString(2, contact.getMobile());
		statement.setString(3, contact.getLandLine());
		statement.setString(4, contact.getEmail());
		statement.setString(5, contact.getProfileImageUrl());
		int insertedContactId = statement.executeUpdate();
		contact.setId(insertedContactId);
		return contact;
	}

	public Contact update(Contact contact) throws SQLException,
			ClassNotFoundException {
		Connection connection = dataSource.getConnection();
		String sql = "update contact set name='?',mobile='?',landline='?',email='?',profile_image_url='?' where id=?";
		PreparedStatement statement = connection.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, contact.getName());
		statement.setString(2, contact.getMobile());
		statement.setString(3, contact.getLandLine());
		statement.setString(4, contact.getEmail());
		statement.setString(5, contact.getProfileImageUrl());
		statement.setInt(6, contact.getId());
		statement.executeUpdate();
		return findById(contact.getId());
	}

	public boolean delete(int id) throws ClassNotFoundException, SQLException {
		Connection connection = dataSource.getConnection();
		String sql = "delete from contact where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.executeUpdate();
		return findById(id) == null;
	}

}
