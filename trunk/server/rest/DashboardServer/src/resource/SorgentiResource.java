package resource;

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

@Path("/Sorgenti/{ID}")
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
/*
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String searchSorgenteByIdXML(@PathParam("ID") String id) {
		String result = "";
		try {
			result = "<?xml version=\"1.0\"?>\n";
			result += this.getSorgente(id).toXML();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}
	*/
	

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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	public void updateSorgenti(@PathParam("id") String id, String payload) {
		try {
			Sorgenti sorgente = new Sorgenti(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE INTO Sorgenti SET " + 
					"Nome = '" + sorgente.getNome() + "', " +
					"Pagina = '" + sorgente.getPagina() + "', " +
					"Link = '" + sorgente.getLink() + "', " +
					"Tipo = " + sorgente.getTipo() + ", " +
					"Autore = '" + sorgente.getAutore() + "', " +
					"Icona = '" + sorgente.getIcona() + "', " +
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
	public String addSorgente(String payload) {
		String result = "";
		try {
			Sorgenti sorgente = new Sorgenti(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Sorgenti VALUES (0,'" + sorgente.getNome() + ",'" +
					sorgente.getPagina() + "','" +
					sorgente.getLink() + "','" +
					sorgente.getTipo() + "','" +
					sorgente.getAutore() + "','" +
					sorgente.getIcona() + "','" +
					sorgente.getIcona() + "');";
			ResultSet rset = stmt.executeQuery(query);
			result = new Sorgenti(rset).toJson();
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
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
