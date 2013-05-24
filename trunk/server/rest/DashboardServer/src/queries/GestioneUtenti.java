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

@Path("/gestioneutenti")
public class GestioneUtenti {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLinksGeneral() {
		String result="";
		try {
			result = this.getData();
			
				
			
	
		}
		catch (Exception exc) {
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private String getData() throws Exception {
		
		String data ="{\"selezione\":[ ";
		List<String> utenti = new ArrayList<String>();
		Connection conn_b = DbManager.getConnection();
		Statement stmt_b = conn_b.createStatement();
		ResultSet rset_b = stmt_b.executeQuery("SELECT Email FROM Utenti;");
		while (rset_b.next()) {
			
			String st_b = rset_b.getObject("Email").toString();
			utenti.add(st_b);
			
		}
		System.out.println(utenti);
		rset_b.close();
		stmt_b.close();
		conn_b.close();
		
		List<String> sorgenti = new ArrayList<String>();
		Connection conn_s = DbManager.getConnection();
		Statement stmt_s = conn_s.createStatement();
		ResultSet rset_s = stmt_s.executeQuery("SELECT DISTINCT Nome FROM Sorgenti;");
		while (rset_s.next()) {
			
			String st_s = rset_s.getObject("Nome").toString();
			sorgenti.add(st_s);
			
		}
		
		rset_b.close();
		stmt_b.close();
		conn_b.close();

		for(int x=0; x<utenti.size();x++){
			data+="{\"utente\":\""+utenti.get(x)+"\",\"fonti\":[ ";

			for(int i=0; i<sorgenti.size();i++){
				data+="{\"nome\":\""+sorgenti.get(i)+"\", \"pagine\": [ ";

				Connection conn = DbManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery("SELECT DISTINCT u.Email AS Utente, s.Nome, s.Pagina, \"checked\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE (u.ID,s.ID) IN (SELECT * From Accesso) AND s.Pagina IS NOT NULL AND u.Email = '"+utenti.get(x)+"' AND s.Nome='"+sorgenti.get(i)+"' UNION SELECT DISTINCT   u.Email AS Utente, s.Nome, s.Pagina, \"\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE (u.ID,s.ID) NOT IN (SELECT * From Accesso) AND s.Pagina IS NOT NULL  AND u.Email = '"+utenti.get(x)+"' AND s.Nome='"+sorgenti.get(i)+"' UNION SELECT DISTINCT  u.Email AS Utente, s.Nome, \"null\" AS Pagina, \"checked\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE (u.ID,s.ID) IN (SELECT * From Accesso) AND s.Pagina IS NULL  AND u.Email ='"+utenti.get(x)+"' AND s.Nome='"+sorgenti.get(i)+"' UNION SELECT DISTINCT   u.Email AS Utente, s.Nome, \"null\" AS Pagina, \"\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE (u.ID,s.ID) NOT IN (SELECT * From Accesso) AND s.Pagina IS NULL  AND u.Email ='"+utenti.get(x)+"' AND s.Nome='"+sorgenti.get(i)+"' ORDER BY Utente");
				while(rset.next()){
				//	System.out.println(rset.getObject("Utente").toString()+" - "+rset.getObject("Nome").toString()+" - "+rset.getObject("Pagina").toString());
					if(rset.getObject("Pagina").toString().equals("null")){
						data += " {\"pagina\":\"Pagina pubblica\",\"checked\":\""+rset.getObject("checked").toString()+"\"},";
					}
					else{
						data += "{\"pagina\":\""+rset.getObject("Pagina")+"\", \"checked\":\""+rset.getObject("checked")+"\"},";
					};
				
				};//end while rset				
				data = data.substring(0,data.length()-1);
				data += "]},";
			
			
			};//end for i
			data = data.substring(0,data.length()-1);
			data += "]},";
		};//end for x
		data = data.substring(0,data.length()-1);
		data +="}";
		
		
		/*
		for(int x=0;x<utenti.size();x++){
	Connection conn = DbManager.getConnection();
	Statement stmt = conn.createStatement();
	ResultSet rset = stmt.executeQuery("select DISTINCT s.Nome, u.Email AS Utente, \"checked\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE u.Email = '"+utenti.get(x)+"' AND (u.ID,s.ID) IN (SELECT * From Accesso) UNION SELECT DISTINCT  s.Nome,  u.Email AS Utente, \"\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE u.Email = '"+utenti.get(x)+"' AND (u.ID,s.ID) NOT IN (SELECT * From Accesso)");
	data+="{\"utente\":\""+utenti.get(x)+"\",\"fonti\":[ ";
	while(rset.next()){

			for (int i = 0; i<sorgenti.size();i++){

				data+="{\"nome\":\""+rset.getObject("Nome").toString()+"\",\"checked\":";
				data+= "\""+rset.getObject("checked").toString()+"\",\"pagine\":[ ";
				Connection conn_pag = DbManager.getConnection();
				Statement stmt_pag = conn_pag.createStatement();
				ResultSet rset_pag = stmt_pag.executeQuery("select DISTINCT s.Pagina, \"checked\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE u.Email = '"+utenti.get(x)+"' AND (u.ID,s.ID) IN (SELECT * From Accesso) AND s.Nome = '"+rset.getObject("Nome").toString()+"' AND s.Pagina IS NOT NULL UNION SELECT DISTINCT s.Pagina, \"\" AS checked from Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s WHERE u.Email = '"+utenti.get(x)+"' AND (u.ID,s.ID) NOT IN (SELECT * From Accesso) AND s.Nome='"+rset.getObject("Nome").toString()+"' AND s.Pagina IS NOT NULL");
				
				while(rset_pag.next()){
					data+="{\"pagina\":\""+rset_pag.getObject("Pagina").toString()+"\",\"checked\":\""+rset_pag.getObject("checked").toString()+"\"},";
					
				};
				rset_pag.close();
				stmt_pag.close();
				conn_pag.close();
				data = data.substring(0,data.length()-1);
				data+="]},";
			};	
			};

	data = data.substring(0,data.length()-1);
	
	data+="]},";
	rset.close();
	stmt.close();
	conn.close();
		};//end for x
*/
		
		
data = data.substring(0,data.length()-1);
data += "]}";
return data;
	}
	
}
