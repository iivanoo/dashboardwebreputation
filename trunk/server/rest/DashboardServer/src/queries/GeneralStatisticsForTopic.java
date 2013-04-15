//restituisce il numero dei post tra due date per la fonte cliccata a prescindere dal topic
package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


//import entities.Sorgenti;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/Sorgenti/grafico/general/idfonte={ID_Fonte}/idtopic={ID_Topic}/data1={Data1}/data2={Data2}")  //Data1 < Data2 & formato: AAAA-MM-GG
public class GeneralStatisticsForTopic{

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGeneralStatistics(@PathParam("ID_Fonte") int id, @PathParam("ID_Topic") int idt, @PathParam("Data1") String d1, @PathParam("Data2") String d2) {
		//String result = "[";
		String result = "";
		try {
			
			List<Integer> sorgenti = this.getSorgenti(id,idt,d1,d2);
			
			result += sorgenti.get(sorgenti.size()-1);
			//result += "]";
		}
		catch (Exception exc) {
			result = "0";
			//result += "0]";
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<Integer> getSorgenti(int id, int idt, String d1, String d2) throws Exception {
		List<Integer> sorgenti = new ArrayList<Integer>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT p.ID AS label FROM Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE p.Data BETWEEN '"+d1+"' AND '"+d2+"' AND ID_Fonte = "+id+" AND p.ID = c.IDPost AND c.IDTopic = t.ID AND t.ID = "+idt+";");
		int count = 0;
		while (rset.next()) {
			count++;
			sorgenti.add(count);
			/*
			String st = rset.getObject("label").toString();
			sorgenti.add(Integer.parseInt(st));*/
		}
		
		rset.close();
		stmt.close();
		conn.close();
	
		return sorgenti;
	}
	
}