package resource;

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

@Path("/Utenti/{ID}/{Nome}")
public class UtentiNomeResource {




		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String searchUtentiByIdJSON(@PathParam("ID") String id, @PathParam("Nome") String nome) {
			String result = "";
			try {
				result = this.getUtenti(id,nome).toJson();
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
		public String searchUtentiByIdXML(@PathParam("ID") String id) {
			String result = "";
			try {
				result = "<?xml version=\"1.0\"?>\n";
				result += this.getUtenti(id).toXML();
			}
			catch (Exception exc) {
				result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
				exc.printStackTrace();
			}
			return result;
		}
	*/
		@DELETE
		public void deleteUtenti(@PathParam("ID") String id, @PathParam("Nome") String nome) {
			try {
				Connection conn = DbManager.getConnection();
				String query = "DELETE from Utenti where ID = " + id + "AND Nome = "+nome+";";
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
		public void updateUtenti(@PathParam("ID") String id, @PathParam("Nome") String nome, String payload) {
			try {
				Utenti utenti = new Utenti(payload);
				Connection conn = DbManager.getConnection();
				Statement stmt = conn.createStatement();
				String query = "UPDATE INTO Utenti SET " + 
						
						"Cognome = " + utenti.getCognome() + ", " +
						"Email = " + utenti.getEmail() + ", " +
						"Password = " + utenti.getPassword() + ", " +
						"Data_nascita = '" + utenti.getData_nascita().toString() + "', " +
						"Citta = '" + utenti.getCitta() + "', " +
						"Creato_da = " + utenti.getCreato_da() + ", " +
						"Data_ins = " + utenti.getData_ins() + ", " +
						"Admin = " + utenti.getAdmin() + ", " +
						" WHERE ID = " + id + " AND Nome = "+nome+";";
				stmt.executeUpdate(query);
				stmt.close();
				conn.close();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		
		
		private Utenti getUtenti(String id, String nome) throws Exception {
			Utenti utenti = null;
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * from Utenti where ID = " + id + " AND Nome = "+nome+";";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next()) {
				utenti = new Utenti(rset);
			}
			rset.close();
			stmt.close();
			conn.close();
			return utenti;
		}



}
