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

@Path("/Utenti/disattiva/email={Email}")
public class DisattivaUtente {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String disattiva(@PathParam("Email") String email, @PathParam("IDFonte") int fonte) {
		String result = "";
		try {
			
			this.disattivazione(email,fonte);
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
	public void disattivazione(String email, int fonte) {
		try {
			System.out.println("entrata");
			
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE Utenti SET Attivo = 0 WHERE Email = '"+email+"';";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
