
var pat;



$("#username-reg").blur(function(){
	pat =/^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/ ;
	var match = document.getElementById("username-reg").value.match(pat);
	
	//var bool = validazione(document.getElementById("username-reg").value,pat);
	if(match == null){
		$("#errore-username").css("display","inline");
		$("#register").attr("disabled",true);
			}
	else{
		$("#errore-username").css("display","none");
		$("#errore-username-vuoto").css("display","none");
	};
});



$("#password-reg").blur(function(){
	pat = /^[a-zA-Z0-9\_\*\-\+\xE0\xE8\xE9\xF9\xF2\xEC\x27]{6,12}/;
	var pwd = document.getElementById("password-reg").value;
	var bool = document.getElementById("password-reg").value.match(pat);
	
	if(bool == null){
		$("#register").attr("disabled",true);
		}
	else{
		$("#errore-password").css("display","none");
	};
});

function valida_Pwd(){
	var bool = document.getElementById("password-reg").value == document.getElementById("confirm-password-reg").value;
	if(!bool){
		$("#errore-confirm").css("display","inline");
		$("#register").attr("disabled",true);
		return false;
		}
	else{
		$("#errore-confirm").css("display","none");
		return true;
	};
};
$("#confirm-password-reg").blur(function(){
	valida_Pwd();
});



$("#nome-reg").keyup(function(){
	pat = '^[a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27 ]*';
	var bool = document.getElementById("nome-reg").value.match(pat);
	
	if(bool[0] != bool.input){
		$("#errore-nome").css("display","inline");
		$("#register").attr("disabled",true);
		$("#nome-reg").focus();}
	else{
		$("#errore-nome").css("display","none");
	};
});

$("#cognome-reg").keyup(function(){
	pat = '^[a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27 ]*';
	var bool = document.getElementById("cognome-reg").value.match(pat);
	if(bool[0] != bool.input){
		$("#errore-cognome").css("display","inline");
		$("#register").attr("disabled",true);
		$("#cognome-reg").focus();}
	else{
		$("#errore-cognome").css("display","none");
	};
});




$("#anno").blur(function(){
	
	var bool = document.getElementById("giorno").value == '' || document.getElementById("mese").value == '' || document.getElementById("anno").value == '';
	
	if(bool){
		$("#errore-data").css("display","inline");
			}
	else{
		$("#errore-data").css("display","none");
	};
});

$("#citta").keyup(function(){
	pat = '^[a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27 ]*';
	var bool = document.getElementById("citta").value.match(pat);
	if(bool[0] == document.getElementById("citta").value){
		$("#errore-citta").css("display","none");
		$("#register").attr("disabled",false);
		}
	else{
		$("#errore-citta").css("display","inline");
		$("#register").attr("disabled",true);
	
		};
		
});


$("#back").click(function(){
	$("#reset").click();
	$("#fonti_tab").css("display","inline");
	$("#addutente").css("display","inline");
	$("#reg_tab").css("display","none");
	
});

$("#reset").click(function(){
	document.getElementById("nome-reg").value = '';
	document.getElementById("cognome-reg").value = '';
	document.getElementById("password-reg").value = '';
	document.getElementById("confirm-password-reg").value = '';
	document.getElementById("username-reg").value = '';
	document.getElementById("giorno").value = '';
	document.getElementById("mese").value = '';
	document.getElementById("anno").value = '';
	document.getElementById("citta").value = '';
	//TODO problema checkbox che non si refreshano
});

$("#register").click(function(){

	if(document.getElementById("username-reg").value == ''){$("#errore-username-vuoto").css("display","inline");$("username-reg").focus();$("#register").attr("disabled",true);}else{$("#errore-username-vuoto").css("display","none");$("#register").attr("disabled",false);};
	if(document.getElementById("password-reg").value == ''){$("#errore-password").css("display","inline");$("password-reg").focus();$("#register").attr("disabled",true);}else{$("#errore-password").css("display","none");$("#register").attr("disabled",false);};
	if(document.getElementById("confirm-password-reg").value == ''){$("#errore-confirm").css("display","inline");$("confirm-password-reg").focus();$("#register").attr("disabled",true);}else{$("#errore-confirm").css("display","none");$("#register").attr("disabled",false);};
	if(document.getElementById("nome-reg").value == ''){$("#errore-nome").css("display","inline");$("nome-reg").focus();$("#register").attr("disabled",true);}else{$("#errore-nome").css("display","none");$("#register").attr("disabled",false);};
	if(document.getElementById("cognome-reg").value == ''){$("#errore-cognome").css("display","inline");$("cognome-reg").focus();$("#register").attr("disabled",true);}else{$("#errore-cognome").css("display","none");$("#register").attr("disabled",false);};
	if(document.getElementById("password-reg").value == ''){$("#errore-password").css("display","inline");$("password-reg").focus();$("#register").attr("disabled",true);}else{$("#errore-password").css("display","none");$("#register").attr("disabled",false);};
	if((document.getElementById("giorno").value == '')||(document.getElementById("mese").value == '')||(document.getElementById("anno").value == '')){$("#errore-data").css("display","inline");$("birthday").focus();$("#register").attr("disabled",true);}else{$("#errore-data").css("display","none");$("#register").attr("disabled",false);};
	if(document.getElementById("password-reg").value == ''){$("#errore-password").css("display","inline");$("password-reg").focus();$("#register").attr("disabled",true);}else{$("#errore-password").css("display","none");$("#register").attr("disabled",false);};
	if(document.getElementById("citta").value == ''){$("#errore-citta").css("display","inline");$("citta").focus();$("#register").attr("disabled",true);}else{$("#errore-citta").css("display","none");$("#register").attr("disabled",false);};

	var ok = (document.getElementById("username-reg").value !='') && (document.getElementById("password-reg").value!='') && (document.getElementById("confirm-password-reg").value!='') && (document.getElementById("nome-reg").value!='') && (document.getElementById("cognome-reg").value!='') && (document.getElementById("citta").value!='') && (document.getElementById("giorno").value!='') && (document.getElementById("mese").value!='') && (document.getElementById("anno").value!='');

	if(ok && valida_Pwd()){
	
		$.get("http://localhost:8080/DashboardServer/api/Utenti/email/email="+document.getElementById("username-reg").value,function(data){
			
			if(data.Nome == "null"){ 
			
			var nome = document.getElementById("nome-reg").value;
			var cognome = document.getElementById("cognome-reg").value;
			var email = document.getElementById("username-reg").value;
			var pwd = document.getElementById("password-reg").value;
			
			var data_nascita = document.getElementById("anno").value+"-"+document.getElementById("mese").value+"-"+document.getElementById("giorno").value;
			var citta = document.getElementById("citta").value;
			var cda = readCookie("idutente");
			var oggi = Date();
			var data_i = oggi.substring(11,15)+"-"+switch_data(oggi.substring(4,7))+"-"+oggi.substring(8,10);
			var admin;
			var attivo;


			if(document.getElementById("admin-reg").checked){admin = true;}else{admin = false;};
			if(document.getElementById("attivo-reg").checked){attivo = true;}else{attivo = false;};
			
		$.get("http://localhost:8080/DashboardServer/api/Utenti/add/nome="+nome.replace(/\'/gi,"")+"/cognome="+cognome.replace(/\'/gi,"")+"/email="+email+"/pwd="+hex_md5(pwd)+"/data_nascita="+data_nascita+"/citta="+citta.replace(/\'/gi,"")+"/creato_da="+cda+"/data_ins="+data_i+"/admin="+admin+"/attivo="+attivo,function(dato){
			
			window.location = "gestione_utenti.html";
		});

		}else{$("#errore-general").css("display","inline");};	
		});
		
	}
	else{
		//eventuale errore generico
	};
});

function switch_data(month){
	switch(month){
	case "Jan": mese = 1;break;
	case "Feb": mese = 2;break;
	case "Mar": mese = 3;break;
	case "Apr": mese = 4;break;
	case "May": mese = 5;break;
	case "Jun": mese = 6;break;
	case "Jul": mese = 7;break;
	case "Aug": mese = 8;break;
	case "Sep": mese = 9;break;
	case "Oct": mese = 10;break;
	case "Nov": mese = 11;break;
	default: mese=12;
	};
	return mese;
		
}