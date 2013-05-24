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

@Path("/admin/action={Action}/email={Email}")
public class GestisciAdmin {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String GestisciAccessoJSON(@PathParam("Action") String action,@PathParam("Email") String email) {
		String result = "";
		try {
			
			this.setAccesso(action,email);
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
	public void setAccesso(String action, String email) {
		try {
			String query ="";
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
	
			if(action.equals("add")){
				
				query = "UPDATE Utenti SET Admin = 1 WHERE Email = '"+email+"'";
				}
			else if (action.equals("del")){
				query = "UPDATE Utenti SET Admin = 0 WHERE Email = '"+email+"'";

			}else{				
				query = "UPDATE Utenti SET Admin = Admin WHERE Email = '"+email+"'"; //gestione dell'errore: no action
			};
			
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
