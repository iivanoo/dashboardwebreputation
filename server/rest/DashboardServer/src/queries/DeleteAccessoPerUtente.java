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

@Path("/Accesso/mod/del/utente={EmailUtente}/fonte={IDFonte}")
public class DeleteAccessoPerUtente {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String AddAccessoJSON(@PathParam("EmailUtente") int email, @PathParam("IDFonte") int fonte) {
		String result = "";
		try {
			
			this.setAccesso(email,fonte);
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
	public void setAccesso(int email, int fonte) {
		try {
			
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Accesso WHERE Email="+email+" AND IDSorgente="+fonte+";";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
