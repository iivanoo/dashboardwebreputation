package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Accesso;
import entities.Sorgenti;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/Sorgenti")
public class SorgentiResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllSorgentiJSON() {
		String result = "[";
		try {
			List<Sorgenti> sorgenti = this.getSorgenti();
			for(int i=0; i<sorgenti.size(); i++) {
				result += sorgenti.get(i).toJson();
				if(i != sorgenti.size() - 1) {
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
	public String getAllSorgentiXML() {
		String result = "<?xml version=\"1.0\"?>\n<sorgenti>\n";
		try {
			List<Sorgenti> sorgenti = this.getSorgenti();
			for(int i=0; i<sorgenti.size(); i++) {
				result += sorgenti.get(i).toXML();
			}
			result += "</sorgenti>";
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}
*/
	
	
	private List<Sorgenti> getSorgenti() throws Exception {
		List<Sorgenti> sorgenti = new ArrayList<Sorgenti>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Sorgenti;");
		while (rset.next()) {
			sorgenti.add(new Sorgenti(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return sorgenti;
	}
	
}
