package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Utenti;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/Utenti")
public class UtentiResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllUtentiJSON() {
		String result = "[";
		try {
			List<Utenti> utenti = this.getUtenti();
			for(int i=0; i<utenti.size(); i++) {
				result += utenti.get(i).toJson();
				if(i != utenti.size() - 1) {
					result += ",";
				}
			}
			result += "]";
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	/*
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getAllUtentiXML() {
		String result = "<?xml version=\"1.0\"?>\n<Utenti>\n";
		try {
			List<Utenti> utenti = this.getUtenti();
			for(int i=0; i<utenti.size(); i++) {
				result += utenti.get(i).toXML();
			}
			result += "</Utenti>";
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}
*/
	private List<Utenti> getUtenti() throws Exception {
		List<Utenti> utenti = new ArrayList<Utenti>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Utenti;");
		while (rset.next()) {
			utenti.add(new Utenti(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return utenti;
	}
}
