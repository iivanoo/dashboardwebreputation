//numero post positivi relativi ad una fonte in un intervallo di date

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


@Path("/polarity/positive/general/nomefonte={nome}/data1={Data1}/data2={Data2}") 
public class PositivePolarityGeneral{

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTopicStatistics(@PathParam("nome") String nome, @PathParam("Data1") String d1, @PathParam("Data2") String d2) {
		String result = "";
		try {
			
			List<Integer> count = this.getPolarity(nome, d1, d2);
			
			result += count.get(count.size()-1);
		
		}
		catch (Exception exc) {
			result = "0";
			//result += "0]";
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<Integer> getPolarity(String nome, String d1, String d2) throws Exception {
		List<Integer> res = new ArrayList<Integer>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND c.IDTopic = t.ID AND p.Polarity = '+1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d1+"' AND '"+d2+"';");
		int count = 0;
		while (rset.next()) {
			count++;
			res.add(count);
			/*
			String st = rset.getObject("label").toString();
			sorgenti.add(Integer.parseInt(st));*/
		}
		
		rset.close();
		stmt.close();
		conn.close();
	
		return res;
	}
	
}

//TODO %pos = (numero post con pol = -1)/GeneralStatistics con cli stessi parametri
// -----> indice di gradimento % = %pos -% neg
