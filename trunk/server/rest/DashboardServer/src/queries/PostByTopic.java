package queries;

import entities.Contenuto;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


import db.DbManager;

import java.sql.*;

@Path("/Contenuto/idtopic={IDTopic}")
public class PostByTopic {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchContenutoByIdJSON(@PathParam("IDTopic") int idt) {
		String result = "[";
		try {
			result += this.getContenuto(idt).toJson();
		}
		catch (Exception exc) {
			result = "[{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		result+="]";
		return result;
	}

	
	@DELETE
	public void deleteContenuto(@PathParam("IDTopic") int idt) {
		try {
			Connection conn = DbManager.getConnection();
			String query = "DELETE from Contenuto where IDTopic = "+idt+";";
			Statement stmt = conn.prepareStatement(query);
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebApplicationException(404);
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	public void updateContenuto(@PathParam("IDTopic") int idt, String payload) {
		try {
			Contenuto contenuto = new Contenuto(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE Contenuto SET "  + 
			"IDPost = '" + contenuto.getIDPost() + "', " +
			"IDTopic = " + contenuto.getIDTopic() + ", " +
			" WHERE IDTopic  ="+idt+";";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	public String addContenuto(String payload) {
		String result = "";
		try {
			Contenuto contenuto = new Contenuto(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Contenuto VALUES (" +
					contenuto.getIDPost() + "','" +
					contenuto.getIDTopic() + "');";
			ResultSet rset = stmt.executeQuery(query);
			result = new Contenuto(rset).toJson();
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	private Contenuto getContenuto(int idt) throws Exception {
		Contenuto contenuto = null;
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * from Contenuto where IDTopic = "+idt+";";
		ResultSet rset = stmt.executeQuery(query);
		if (rset.next()) {
			contenuto = new Contenuto(rset);
		}
		rset.close();
		stmt.close();
		conn.close();
		return contenuto;
	}
}
