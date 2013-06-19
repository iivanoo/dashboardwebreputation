package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.*;
import java.util.Date;


public class Sorgenti {
	
	private int ID;
	private String Nome;
	private String Pagina;
	private String Link;
	private String Tipo;
	private int Autore;
	private String Icona;
	private int updateInterval;
	private Date lastTimestamp;
	
	public Sorgenti(int id, String nome, String pagina, String link, String tipo, int autore, String icona, int updateinterval, Date lasttimestamp ){
		this.ID = id;
		this.Nome = nome;
		this.Pagina = pagina;
		this.Link = link;
		this.Tipo = tipo;
		this.Autore = autore;
		this.Icona = icona;
		this.updateInterval = updateinterval;
		this.lastTimestamp = lasttimestamp;
	}// end costruttore base
	
	public Sorgenti(ResultSet resultSet){
		try{
			this.ID = resultSet.getInt("ID");
			this.Nome = resultSet.getString("Nome");
			this.Pagina = resultSet.getString("Pagina");
			this.Link = resultSet.getString("Link");
			this.Tipo = resultSet.getString("Tipo");
			this.Autore = resultSet.getInt("Autore");
			this.Icona = resultSet.getString("Icona");
			this.updateInterval = resultSet.getInt("updateInterval");
			this.lastTimestamp = resultSet.getDate("lastTimestamp");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}//end costruttore da ResultSet
	
	public Sorgenti(String json){
	//TODO controllare il try/catch	
		JSONObject obj = new JSONObject(json);
		try{
			this.ID = obj.getInt("ID");
			this.Nome = obj.getString("Nome");
			this.Pagina = obj.getString("Pagina");
			this.Link = obj.getString("Link");
			this.Tipo = obj.getString("Tipo");
			this.Autore = obj.getInt("Autore");
			this.Icona = obj.getString("Icona");
			this.updateInterval = obj.getInt("updateInterval");
			this.lastTimestamp = new Date(obj.getString("lastTimestamp"));
		}
		catch(Exception e){e.getStackTrace();}
		
		
	}//end costruttore da stringa
	
	
	public String toJson(){
		return "{\"ID\":\"" + this.ID + "\"," + 
		"\"Nome\":\"" + this.Nome + "\"," +
		"\"Pagina\":\"" + this.Pagina + "\"," +
		"\"Link\":\"" + this.Link + "\"," +
		"\"Tipo\":\"" + this.Tipo + "\"," +
		"\"Autore\":\"" + this.Autore + "\"," +
		"\"updateInterval\":\"" + this.updateInterval + "\"," +
		"\"lastTimestamp\":\"" + this.lastTimestamp + "\"," +
		"\"Icona\":\"" + this.Icona + "\"}" ;
	}//end toJson

	
	public int getID(){ return this.ID; }
	public void setID(int id){ this.ID = id;}

	public String getNome(){ return this.Nome; }
	public void setNome(String nome){ this.Nome = nome;}
	
	public String getPagina(){ return this.Pagina; }
	public void setPagina(String pagina){ this.Pagina = pagina;}
	
	public String getLink(){return this.Link;}
	public void setLink(String link) {this.Link = link;}
	
	public String getTipo(){return this.Tipo;}
	public void setTipo(String tipo){this.Tipo = tipo;}
	
	public int getAutore(){return this.Autore;}
	public void setAutore(int autore){this.Autore = autore;}
	
	public String getIcona(){return this.Icona;}
	public void setIcona(String icona){ this.Icona = icona;}
	
	public int getupdateInterval(){ return this.updateInterval; }
	public void setupdateInterval(int updateinterval){ this.updateInterval = updateinterval;}
	
	public Date getlastTimestamp(){return this.lastTimestamp;}
	public void setlastTimestamp(Date lasttimestamp){this.lastTimestamp = lasttimestamp;}
	
}