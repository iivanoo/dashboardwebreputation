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

@Path("/Utenti/attivazione/action={Action}/email={Email}")
public class DisattivaUtente {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String disattiva(@PathParam("Action") String action, @PathParam("Email") String email) {
		String result = "";
		try {
			
			this.disattivazione(action, email);
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
	public void disattivazione(String action, String email) {
		try {
			System.out.println("entrata");
			
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			
			String query ="";
			if(action.equals("attiva")){query = "UPDATE Utenti SET Attivo = 1 WHERE Email = '"+email+"';";}
			else if(action.equals("disattiva")){query = "UPDATE Utenti SET Attivo = 0 WHERE Email = '"+email+"';";}
			else{query = "SELECT * FROM Utenti";};

			
			
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
