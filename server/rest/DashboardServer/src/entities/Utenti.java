//TODO sistemare Date (deprecato!!!) con Calendar

package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.*;


public class Utenti{
	
	//TODO si pu˜ sostituire int ID con String ID dove ID = nickname??? 
	
	private int ID;
	private String Nome;
	private String Cognome;
	private String Email;
	private String Password;
	private String Data_nascita;
	private String Citta;
	private int Creato_da;
	private String Data_ins;
	private boolean Admin;
	private Boolean Attivo;
	
	public Utenti(int id, String nome, String cognome, String email, String password, String data, 
			String citta, int creatore, String datains, boolean ad, boolean at){
		this.ID = id;
		this.Nome = nome;
		this.Cognome = cognome;
		this.Email = email;
		this.Password = password;
		this.Data_nascita = data;
		this.Citta = citta;
		this.Creato_da = creatore;
		this.Data_ins = datains;
		this.Admin = ad;
		this.Attivo = at;
	}
	
	public Utenti(ResultSet resultSet){
		try{
		this.ID = resultSet.getInt("ID");
		this.Nome = resultSet.getString("Nome");
		this.Cognome = resultSet.getString("Cognome");
		this.Email = resultSet.getString("Email");
		this.Password = resultSet.getString("Password");
		this.Data_nascita = resultSet.getString("Data_nascita");
		this.Citta = resultSet.getString("Citta");
		this.Creato_da = resultSet.getInt("Creato_da");
		this.Data_ins = resultSet.getString("Data_ins");
		this.Admin = resultSet.getBoolean("Admin");
		this.Attivo = resultSet.getBoolean("Attivo");
		}
		catch(SQLException e){e.getStackTrace();}
	}
	
	public Utenti(String json){
		//TODO controllare il try/catch	
			JSONObject obj = new JSONObject(json);
			try{
				this.ID = obj.getInt("ID");
				this.Nome = obj.getString("Nome");
				this.Cognome = obj.getString("Cognome");
				this.Email = obj.getString("Email");
				this.Password = obj.getString("Password");
				this.Data_nascita = obj.getString("Data_nascita");
				this.Citta = obj.getString("Citta");
				this.Creato_da = obj.getInt("Creato_da");
				this.Data_ins = obj.getString("Data_ins");
				this.Admin = obj.getBoolean("Admin");
				this.Attivo = obj.getBoolean("Attivo");
			}
			catch(Exception e){e.getStackTrace();}
			
			
		}//end costruttore da stringa
		
	
	public String toJson(){
		return "{\"ID\":\""+this.ID+"\","+
				"\"Nome\":\""+this.Nome+"\","+
				"\"Cognome\":\""+this.Cognome+"\","+
				"\"Email\":\""+this.Email+"\","+
				"\"Password\":\""+this.Password+"\","+
				"\"Data_nascita\":\""+this.Data_nascita+"\","+
				"\"Citta\":\""+this.Citta+"\","+
				"\"Creato_da\":\""+this.Creato_da+"\","+
				"\"Data_ins\":\""+this.Data_ins+"\","+
				"\"Admin\":\""+this.Admin+"\","+
				"\"Attivo\":\""+this.Attivo+"\"}";
	}//end toJson
	
	public int getID(){return this.ID;}
	public void setID(int id){this.ID = id; }
	
	public String getNome(){return this.Nome;}
	public void setNome(String nome){this.Nome = nome;}
	
	public String getCognome(){return this.Cognome;}
	public void setCognome(String cognome){ this.Cognome = cognome;}
	
	public String getEmail(){return this.Email;}
	public void setEmail(String mail){ this.Email = mail;}
	
	public String getPassword(){return this.Password;}
	public void setPassword(String pwd){ this.Password = pwd;}
	
	public String getData_nascita(){return this.Data_nascita;}
	public void setData_nascita(String data){ this.Data_nascita = data;}
	
	public String getCitta(){return this.Citta;}
	public void setCitta(String citta){this.Citta = citta;}

	public int getCreato_da(){return this.Creato_da;}
	public void setCreato_da(int creatore){this.Creato_da = creatore;}
	
	public String getData_ins(){return this.Data_ins;}
	public void setData_ins(String inserimento){this.Data_ins = inserimento;}
	
	public boolean getAdmin(){return this.Admin;}
	public void setAdmin(boolean ad){this.Admin = ad;}
	
	public boolean getAttivo(){return this.Attivo;}
	public void setAttivo(boolean at){this.Attivo = at;}

}//end class