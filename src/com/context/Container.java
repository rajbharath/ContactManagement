package com.context;

import com.dao.ContactDao;

public class Container {
	private static DataSource dataSource;
	private static ContactDao contactDao;
	static {
		dataSource = new DataSource();
		contactDao = new ContactDao();
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static ContactDao getContactDao() {
		return contactDao;
	}
}
