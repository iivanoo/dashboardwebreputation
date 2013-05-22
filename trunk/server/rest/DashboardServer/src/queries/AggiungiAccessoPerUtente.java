package queries;


import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import db.DbManager;


import java.sql.*;

import javax.ws.rs.PathParam;

@Path("/Accesso/mod/add/utente={IDUtente}/fonte={IDFonte}")
public class AggiungiAccessoPerUtente {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String AddAccessoJSON(@PathParam("IDUtente") int id, @PathParam("IDFonte") int fonte) {
		String result = "";
		try {
			
			this.setAccesso(id,fonte);
			result += "true";
			}
		
		
		catch (Exception exc) {
			result += "false";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	public void setAccesso(int id, int fonte) {
		try {
			
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Accesso(IDUtente,IdSorgente) SELECT * FROM (SELECT "+id+","+fonte+") AS tmp WHERE NOT EXISTS ( SELECT * FROM Accesso WHERE IDUtente = "+id+" AND IDSorgente = "+fonte+")";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
