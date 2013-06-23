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

@Path("/links/general/all/idutente={IDUtente}/nomefonte={NomeFonte}/limit={inf}-{sup}")
public class LinksGeneralNegative {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLinksGeneral(@PathParam("IDUtente") int idutente,@PathParam("NomeFonte") String nome, @PathParam("inf") int inf, @PathParam("sup") int sup) {
		String result="";
		try {
			result = this.getLinks(idutente,nome,inf,sup);
			
				
			
	
		}
		catch (Exception exc) {
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private String getLinks(int idutente, String nome,int inf, int sup) throws Exception {
		int max = 0;
		String links ="[ ";
		List<String> Topics = new ArrayList<String>();
		
		
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet topics_set = stmt.executeQuery("SELECT * FROM Topics;");
		while(topics_set.next()){
		
		
		Connection conn_b = DbManager.getConnection();
		Statement stmt_b = conn_b.createStatement();
		ResultSet rset_b = stmt_b.executeQuery("SELECT COUNT(t.Topic) as frequenza, t.Topic FROM Topics AS t INNER JOIN Contenuto AS c INNER JOIN Post AS p INNER JOIN Sorgenti AS s INNER JOIN Accesso AS acc INNER JOIN Utenti AS u WHERE t.Topic = '"+topics_set.getObject("Topic")+"' AND u.ID = "+idutente+" AND s.Nome='"+nome+"' AND t.ID = c.IDTopic AND c.IDPost = p.ID AND p.ID_Fonte = s.ID AND s.ID = acc.IDSorgente AND acc.IDUtente = u.ID;");
		
	
		while (rset_b.next()) {
			if(Integer.parseInt(rset_b.getObject("frequenza").toString()) != 0){
				int index = Integer.parseInt(rset_b.getObject("frequenza").toString());
				if(index>=max){
					max = index;
					System.out.println(max);
					Topics.add(0,rset_b.getObject("Topic").toString());
					}
				else{
					Topics.add(rset_b.getObject("Topic").toString());
					
					}
			};

			
			}
		
			rset_b.close();
			stmt_b.close();
			conn_b.close();
		};
	topics_set.close();
	stmt.close();
	conn.close();
		
	
//Ricavo i link cliccabili	
	for(int i=0; i<Topics.size();i++){
	Connection conn_l = DbManager.getConnection();
	Statement stmt_l = conn_l.createStatement();
	String query = "SELECT DISTINCT p.Link, p.Text FROM Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE u.ID = acc.IDUtente AND acc.IDSorgente = s.ID AND s.ID = p.ID_Fonte AND p.ID = c.IDPost AND c.IDTopic = t.ID AND t.Topic ='"+Topics.get(i)+"' LIMIT "+inf+","+sup+";";
	ResultSet rset_l = stmt_l.executeQuery(query);
	links += "{\"nome\":\""+Topics.get(i)+"\",\"links\":[ ";
	while(rset_l.next()){
		String st = rset_l.getObject("Link").toString();
		String tes = rset_l.getObject("Text").toString();
		if (tes.length() != 0 && tes.length()>25){tes = tes.substring(0,25)+"...";}
		else if (tes.length() == 0){tes = st;};
		
		links += "{\"link\":\""+st+"\",\"testo\":\""+tes+"\"},";
		
	};
	links = links.substring(0,links.length()-1);
	links += "]},";
	rset_l.close();
	stmt_l.close();
	conn_l.close();
	};
	
	
	

	links = links.substring(0,links.length()-1)+"]";
		return links;
	}

	
	
}


