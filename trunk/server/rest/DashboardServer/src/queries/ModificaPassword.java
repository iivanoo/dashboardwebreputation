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

@Path("/Utenti/modificaPwd/idutente={ID}/new-pwd={NEWPwd}")
public class ModificaPassword {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String modificaPassword(@PathParam("ID") int id,@PathParam("NEWPwd") String newpwd) {
		String result = "";
		try {
			
			int res = this.modifica(id,newpwd);
			result+="true";
			
			}
		
		
		catch (Exception exc) {
			result += "false";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)

	public int modifica(int id, String newpwd) {
		int res = 0;
		try {
		
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
	
			String query = "UPDATE Utenti SET Password = '"+newpwd+"' WHERE ID = "+id+";";
			res = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			res = 0;
		}
		return res;
	}
	
}
