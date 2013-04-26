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

@Path("/Utenti/modificaPwd/email={Email}/pwd={Pwd}")
public class ModificaPassword {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String modificaPassword(@PathParam("Email") String email, @PathParam("Pwd") String password) {
		String result = "";
		try {
			
			this.modifica(email,password);
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
	public void modifica(String email, String password) {
		try {
		
			
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
	
			String query = "UPDATE Utenti SET Password = '"+password+"' WHERE Email = '"+email+"';";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
