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

@Path("/Accesso/login/email={email}/pwd={pwd}")
public class Login {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllAccessoJSON(@PathParam("email") String email, @PathParam("pwd") String pwd) {
		String result = "";
		try {
			String accesso = this.getAccesso(email,pwd);
			if(accesso.isEmpty()){result="false";}else{result="true";};
	
		}
		catch (Exception exc) {
			
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private String getAccesso(String email, String pwd) throws Exception {
		String st="";
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT ID AS label FROM Utenti WHERE Email='"+email+"' AND Password = '"+pwd+"' AND Attivo = 1;");
	
		while (rset.next()) {
			st = rset.getObject("label").toString();
			System.out.println(st);
			
		};
		
		rset.close();
		stmt.close();
		conn.close();
		return st;
	}

}
