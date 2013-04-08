//TODO sistemare il tipo Date col tipo Calendar
//TODO Nomefonte--->ID_Fonte
package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.*;

public class Accesso{
	private int IDUtente;
	private int IDSorgente;
	
	
	public Accesso(int idutente, int idsorgente){
		this.IDUtente = idutente;
		this.IDSorgente = idsorgente;
	}//end costruttore base
	
	public Accesso(ResultSet resultSet){
		try{
		this.IDUtente = resultSet.getInt("IDUtente");
		this.IDSorgente = resultSet.getInt("IDSorgente");
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}//end costruttore da resultSet
	
	
	public Accesso(String json){
		//TODO controllare il try/catch	
			JSONObject obj = new JSONObject(json);
			try{
				this.IDUtente = obj.getInt("IDUtente");
				this.IDSorgente = obj.getInt("IDSorgente");

			}
			catch(Exception e){e.getStackTrace();}
			
			
		}//end costruttore da stringa
		
	
	
	public String toJson(){
		return "{\"IDUtente\":\""+this.IDUtente+"\","+
				"\"IDSorgente\":\""+this.IDSorgente+"\",}";
	}//end toJson
	/*
	public String toXML(){
		return "<Accesso>\n"+
		"<IDUtente>"+this.IDUtente+"</IDUtente>\n"+
		"<IDSorgente>"+ this.IDSorgente + "</IDSorgente>\n"+
		
		"</Accesso>\n";
	}//end toXML
	*/
	public int getIDUtente(){return this.IDUtente;}
	public void setIDUtente(int id){this.IDUtente = id;}

	public int getIDSorgente(){return this.IDSorgente;}
	public void setIDSorgente(int id){this.IDSorgente = id;}
	
	
	
}//end class