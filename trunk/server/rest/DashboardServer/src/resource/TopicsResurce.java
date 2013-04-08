package resource;

import entities.Topics;


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

@Path("/Topics/{ID}")
public class TopicsResurce {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchTopicsByIdJSON(@PathParam("ID") String id) {
		String result = "";
		try {
			result = this.getTopics(id).toJson();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	/*
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String searchTopicsByIdXML(@PathParam("ID") String id) {
		String result = "";
		try {
			result = "<?xml version=\"1.0\"?>\n";
			result += this.getTopics(id).toXML();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}
*/


	@DELETE
	public void deleteTopics(@PathParam("ID") String id) {
		try {
			Connection conn = DbManager.getConnection();
			String query = "DELETE from Topics where ID = " + id + ";";
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
	public void updateTopics(@PathParam("ID") String id, String payload) {
		try {
			Topics topic = new Topics(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE INTO Topics SET " + 
					"Topic = '" + topic.getTopic() + "', " +
					
					" WHERE ID = " + id + ";";
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
	public String addTopic(String payload) {
		String result = "";
		try {
			Topics topic = new Topics(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Topics VALUES (0,'" +
				topic.getTopic() + "');";
			ResultSet rset = stmt.executeQuery(query);
			result = new Topics(rset).toJson();
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	private Topics getTopics(String id) throws Exception {
		Topics topic = null;
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * from Topics where ID = " + id + ";";
		ResultSet rset = stmt.executeQuery(query);
		if (rset.next()) {
			topic = new Topics(rset);
		}
		rset.close();
		stmt.close();
		conn.close();
		return topic;
	}
}
