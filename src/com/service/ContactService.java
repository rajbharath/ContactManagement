package com.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.context.Container;
import com.dao.ContactDao;
import com.model.Contact;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("contacts")
public class ContactService {

	private ContactDao contactDao;

	public ContactService() {
		this.contactDao = Container.getContactDao();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Contact> findAllContacts() throws ClassNotFoundException,
			SQLException {
		return contactDao.findAllContacts();
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Contact findAllContacts(@PathParam("id") int id)
			throws ClassNotFoundException, SQLException {
		return contactDao.findById(id);
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Contact uploadFile(@FormParam("name") String name,
			@FormParam("mobile") String mobile,
			@FormParam("landline") String landline,
			@FormParam("email") String email,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail)
			throws IOException, ClassNotFoundException, SQLException {
		String uploadedFileLocation = "c://uploaded/" + fileDetail.getFileName();
		writeToFile(uploadedInputStream, uploadedFileLocation);
		return contactDao.create(name, mobile, landline, email);
	}

	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) throws IOException {
		OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(uploadedFileLocation));
		while ((read = uploadedInputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
}
