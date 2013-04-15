package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Contenuto;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/Contenuto")
public class ContenutoResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllContenutoJSON() {
		String result = "[";
		try {
			List<Contenuto> contenuto = this.getContenuto();
			for(int i=0; i<contenuto.size(); i++) {
				result += contenuto.get(i).toJson();
				if(i != contenuto.size() - 1) {
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

	

	private List<Contenuto> getContenuto() throws Exception {
		List<Contenuto> contenuto = new ArrayList<Contenuto>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Contenuto;");
		while (rset.next()) {
			contenuto.add(new Contenuto(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return contenuto;
	}

}
