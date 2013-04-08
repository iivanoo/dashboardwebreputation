package resource;

import entities.Accesso;


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

@Path("/Accesso/{ID}")
public class AccessoResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchAccessoByIdJSON(@PathParam("IDUtente") String idut, @PathParam("IDSorgente") String idsor) {
		String result = "";
		try {
			result = this.getAccesso(idut,idsor).toJson();
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
	public String searchAccessoByIdXML(@PathParam("IDUtente") String idut, @PathParam("IDSorgente") String idsor ) {
		String result = "";
		try {
			result = "<?xml version=\"1.0\"?>\n";
			result += this.getAccesso(idut, idsor).toXML();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}
	*/
	

	@DELETE
	public void deleteAccesso(@PathParam("IDUtente") String idut, @PathParam("IDSorgente") String idsor) {
		try {
			Connection conn = DbManager.getConnection();
			String query = "DELETE from Accesso where IDUtente = " + idut + "AND IDSorgente = "+idsor+";";
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
	public void updateAccesso(@PathParam("IDUtente") String idut, @PathParam("IDSorgente") String idsor, String payload) {
		try {
			Accesso accesso = new Accesso(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE INTO Accesso SET "  + 
					"IDUtente = '" + accesso.getIDUtente() + "', " +
					"IDSorgente = " + accesso.getIDSorgente() + ", " +
					" WHERE IDUtente = " + idut + "AND IDSorgente  ="+idsor+";";
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
	public String addAccesso(String payload) {
		String result = "";
		try {
			Accesso accesso = new Accesso(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Accesso VALUES (" +
					accesso.getIDUtente() + "','" +
					accesso.getIDSorgente() + "');";
			ResultSet rset = stmt.executeQuery(query);
			result = new Accesso(rset).toJson();
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	private Accesso getAccesso(String idut, String idsor) throws Exception {
		Accesso accesso = null;
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * from Accesso where IDUtente = " + idut + "AND IDSorgente = "+idsor+";";
		ResultSet rset = stmt.executeQuery(query);
		if (rset.next()) {
			accesso = new Accesso(rset);
		}
		rset.close();
		stmt.close();
		conn.close();
		return accesso;
	}
}
