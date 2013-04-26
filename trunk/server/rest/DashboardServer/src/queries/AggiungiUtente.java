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

@Path("/Utenti/add/utenteJSON={json}")
public class AggiungiUtente {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchUtentiByIdJSON(@PathParam("json") String ut) {
		String result = "";
		try {
			result = this.addUtente(ut);
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	
	public String addUtente(String payload) {
		String result = "";
		try {
			Utenti utenti = new Utenti(payload);
			System.out.println(utenti.getNome());
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Utenti (ID, Nome, Cognome, Email, Password, Data_nascita, Citta, Creato_da, Data_ins, Admin, Attivo) VALUES (0, 'mara', 'disi', 'mara@mara.it', 'qwejyfgsjvl', '2013-04-10', 'frosinone', 1, '2013-04-18', 0, 1);";
			stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	

}
