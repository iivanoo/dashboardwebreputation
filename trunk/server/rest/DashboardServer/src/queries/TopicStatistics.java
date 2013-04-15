//restituisce il numero dei post tra due date per la fonte cliccata e il topic cercato 

package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/Sorgenti/grafico/topic/idfonte={ID_Fonte}/topic={topic}/data1={Data1}/data2={Data2}")  //Data1 < Data2 & formato: AAAA-MM-GG
public class TopicStatistics{

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTopicStatistics(@PathParam("ID_Fonte") int id, @PathParam("topic") String topic, @PathParam("Data1") String d1, @PathParam("Data2") String d2) {
		String result = "";
		try {
			
			List<Integer> sorgenti = this.getSorgenti(id,topic,d1,d2);
			System.out.print(sorgenti.size()-1);
			result += sorgenti.get(sorgenti.size()-1);
		
		}
		catch (Exception exc) {
			result = "0";
			//result += "0]";
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<Integer> getSorgenti(int id, String topic, String d1, String d2) throws Exception {
		List<Integer> sorgenti = new ArrayList<Integer>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT s.ID FROM Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND c.IDTopic = t.ID AND s.ID ="+id+" AND t.Topic = '"+topic+"' AND p.Data BETWEEN '"+d1+"' AND '"+d2+"';");
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
