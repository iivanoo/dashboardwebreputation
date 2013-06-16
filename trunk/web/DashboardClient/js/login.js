var p = document.getElementById("pwd-div");
var e = document.getElementById("mail-div");
var err = document.getElementById("errore-pwd");
var err_in = document.getElementById("errore-pwd-in");
$("#login").attr("disabled",true);

eraseCookie("idutente");
eraseCookie("fontecliccata");


/*-----------------------------validazione------------------------------------------*/
var val_pwd = document.getElementById("password");

$(val_pwd).keypress(function(e) {
    if(e.which == 13) {
       $("#login").click();
    }
});

$(val_pwd).attr("onkeyup","validazione();");
function validazione(){
	
	var pattern = "['=]";
	if(val_pwd.value.match(pattern))
	{$("#login").attr("disabled",true);}
	else{
		$("#login").attr("disabled",false);
	};

};//end validazione
	    
/*-------------------------validazione superata-------------------------------------*/	    
$("#login").click(function(){
	 var remember = document.getElementById("remember").checked;
	eraseCookie("idutente");
	var username = document.getElementById("username").value;
	var pwd = hex_md5(document.getElementById("password").value);
	var url = "http://localhost:8080/DashboardServer/api/Accesso/login/email="+username+"/pwd="+pwd;
	$.get(url,function(log){
		if(log == true){
			 $(err).css("display","none");
			 $(err_in).css("display","none");
			 $(e).css("border-color","white");
			 $(p).css("border-color","white");
			$.get("http://localhost:8080/DashboardServer/api/Utenti/email/email="+username,function(utente){
				if(remember){createCookie("idutente",utente.ID,100);}else{createCookie("idutente",utente.ID,0.5);};
				if(utente.Admin){$("#gestione").css("display","inline");}else{$("#gestione").css("display","none");};
			$.get("http://localhost:8080/DashboardServer/api/Accesso/search/utente="+utente.ID,function(accesso){
				if(accesso[0].length == 0){
					eraseCookie("fontecliccata");
					eraseCookie("idutente");
					$(err).val("Non disponi dei permessi necessari all'accesso!");
					 $(err).css("display","inline");
					}
				else{window.location = "index.html";};
				})

			});//end recupero id utente
			
		}
		else{
	    
		$(err).val("Email e/o Password errata!");
		 $(err_in).css("display","none");
		 $(err).css("display","inline");
		 $(p).css("border-color","red");
		 $(e).css("border-color","red");
	
	   
		};
		
	});//end get accesso
	
});


$("#logout").click(function(){
	eraseCookie("idutente");
	eraseCookie("fontecliccata");
	});



$("#forgot").click(function(){


$("#mail-div").css("display","none");
$("#pwd-div").css("display","none");
$("#login").css("display","none");
$("#remember-id").css("display","none");
$("#btn-g").css("display","none");
$("#p").css("display","none");
var form = document.getElementById("form");
$(form).css("display","inline");

});//end forgot