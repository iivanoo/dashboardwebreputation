package queries;

import entities.Sorgenti;


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

@Path("/Sorgenti/search/id/id={ID}")
public class SorgentiResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchSorgenteByIdJSON(@PathParam("ID") String id) {
		String result = "";
		try {
			result = this.getSorgente(id).toJson();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	@DELETE
	public void deleteSorgente(@PathParam("ID") String id) {
		try {
			Connection conn = DbManager.getConnection();
			String query = "DELETE from Sorgenti where ID = " + id + ";";
			Statement stmt = conn.prepareStatement(query);
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebApplicationException(404);
		}
	}
	private Sorgenti getSorgente(String id) throws Exception {
		Sorgenti sorgente = null;
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * from Sorgenti where ID = " + id + ";";
		ResultSet rset = stmt.executeQuery(query);
		if (rset.next()) {
			sorgente = new Sorgenti(rset);
		}
		rset.close();
		stmt.close();
		conn.close();
		return sorgente;
	}
}
