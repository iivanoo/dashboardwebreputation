package queries;

import entities.Utenti;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


import db.DbManager;

import java.sql.*;

@Path("/Utenti/email/email={Email}")
public class UtentiByEmail {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchUtentiByEmailJSON(@PathParam("Email") String mail) {
		String result = "";
		try {
			result = this.getUtenti(mail).toJson();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	private Utenti getUtenti(String mail) throws Exception {
		Utenti utenti = null;
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * from Utenti WHERE Email =\""+mail+ "\";";
		ResultSet rset = stmt.executeQuery(query);
		if (rset.next()) {
			utenti = new Utenti(rset);
		}
		rset.close();
		stmt.close();
		conn.close();
		return utenti;
	}

//________________________________________________________________________________________________________________________


	@DELETE
	public void deleteUtenti(@PathParam("Email") String mail) {
		try {
			Connection conn = DbManager.getConnection();
			String query = "DELETE from Utenti where Email = \"" + mail + "\";";
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
	public void updateUtenti(@PathParam("Email") String mail, String payload) {
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
					" WHERE Email = \"" + mail + "\";";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	
}
