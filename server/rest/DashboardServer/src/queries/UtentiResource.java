package queries;

import entities.Utenti;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


import db.DbManager;

import java.sql.*;

@Path("/Utenti/id/id={ID}")
public class UtentiResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchUtentiByIdJSON(@PathParam("ID") String id) {
		String result = "";
		try {
			result = this.getUtenti(id).toJson();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	private Utenti getUtenti(String id) throws Exception {
		Utenti utenti = null;
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * from Utenti WHERE ID = "+id+ ";";
		ResultSet rset = stmt.executeQuery(query);
		if (rset.next()) {
			utenti = new Utenti(rset);
		}
		rset.close();
		stmt.close();
		conn.close();
		return utenti;
	}


	@DELETE
	public void deleteUtenti(@PathParam("ID") int id) {
		try {
			Connection conn = DbManager.getConnection();
			String query = "DELETE from Utenti where ID = " + id + ";";
			Statement stmt = conn.prepareStatement(query);
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebApplicationException(404);
		}
	}
}
