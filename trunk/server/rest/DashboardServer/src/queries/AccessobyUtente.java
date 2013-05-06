package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Accesso;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/Accesso/search/utente={IDUtente}")
public class AccessobyUtente {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllAccessoJSON(@PathParam("IDUtente") int id) {
		String result = "";
		try {
			List<String> accesso = this.getAccesso(id);
			for(int i=0; i<accesso.size(); i++) {
				result += accesso.get(i);
				if(i != accesso.size() - 1) {
					result += ",";
				}
			}
		
		}
		catch (Exception exc) {
			
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<String> getAccesso(int id) throws Exception {
		List<String> accesso = new ArrayList<String>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT IDSorgente AS label from Accesso WHERE IDUtente = "+id+";");
		while (rset.next()) {
			String st = rset.getObject("label").toString();
			accesso.add(st);
		}
		rset.close();
		stmt.close();
		conn.close();
		return accesso;
	}

}