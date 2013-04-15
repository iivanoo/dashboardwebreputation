package queries;

import entities.Utenti;


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
	public void deleteUtenti(@PathParam("ID") String id) {
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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	public void updateUtenti(@PathParam("ID") String id, String payload) {
		try {
			Utenti utenti = new Utenti(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE INTO Utenti SET " + 
					"Nome = '" + utenti.getNome() + "', " +
					"Cognome = " + utenti.getCognome() + ", " +
					"Email = " + utenti.getEmail() + ", " +
					"Password = " + utenti.getPassword() + ", " +
					"Data_nascita = '" + utenti.getData_nascita().toString() + "', " +
					"Citta = '" + utenti.getCitta() + "', " +
					"Creato_da = " + utenti.getCreato_da() + ", " +
					"Data_ins = " + utenti.getData_ins() + ", " +
					"Admin = " + utenti.getAdmin() + ", " +
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
	public String addUtenti(String payload) {
		String result = "";
		try {
			Utenti utenti = new Utenti(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Utenti VALUES (0,'" +
					utenti.getNome() + "','" +
					utenti.getCognome() + "','" +
					utenti.getData_nascita() + "','" +
					utenti.getCitta() + "','" +
					utenti.getCreato_da() + "','" +
					utenti.getData_ins() + "','" +
					utenti.getAdmin() + "');";
			ResultSet rset = stmt.executeQuery(query);
			result = new Utenti(rset).toJson();
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	

}
