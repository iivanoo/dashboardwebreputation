package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import entities.Sorgenti;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/Sorgenti/distinct")
public class NomiSorgentiDistinct {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllSorgentiJSON() {
		String sorgenti = "";
		try {
			sorgenti = this.getSorgenti();
	
		}
		catch (Exception exc) {
			sorgenti = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return sorgenti;
	}

	
	
	private String getSorgenti() throws Exception {
		String sorgenti ="[";
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT DISTINCT Nome from Sorgenti;");
		while (rset.next()) {
			sorgenti+="{\"Nome\":\""+rset.getString("Nome")+"\"},";
		};
		sorgenti = sorgenti.substring(0,sorgenti.length()-1);
		sorgenti+="]";
		rset.close();
		stmt.close();
		conn.close();
		return sorgenti;
	}
	
}
