package queries;


import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import db.DbManager;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;

@Path("/admin")
public class IsAdmin {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String GestisciAccessoJSON() {
		String result = "";
		try {
			
			
			result += this.setAccesso();
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
	public String setAccesso() {
		
		
		String res = "[ ";
		try {
			List<String> utenti = new ArrayList<String>();
		Connection conn_ut = DbManager.getConnection();
		Statement st_conn = conn_ut.createStatement();
		ResultSet resSet = st_conn.executeQuery("SELECT DISTINCT Email FROM Utenti");
		while(resSet.next()){
			utenti.add(resSet.getObject("Email").toString());
		};
		System.out.println(utenti);
		resSet.close();
		st_conn.close();
		conn_ut.close();
			for(int i=0; i<utenti.size();i++){
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT Admin FROM Utenti WHERE Email = '"+utenti.get(i)+"'");
			
			while(rset.next()){
				if (rset.getObject("Admin").toString().equals("true")){res += "{\"utente\":\""+utenti.get(i)+"\", \"admin\":\"true\"},";}
				else{res += "{\"utente\":\""+utenti.get(i)+"\", \"admin\":\"false\"},";};
			};
		}
			res=res.substring(0,res.length()-1)+"]";
		}
		catch (Exception exc) {
			
			exc.printStackTrace();
		}
		return res;
	}
	
}
