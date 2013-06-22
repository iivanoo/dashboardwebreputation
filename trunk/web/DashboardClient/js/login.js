var p = document.getElementById("pwd-div");
var e = document.getElementById("mail-div");
var err = document.getElementById("errore-pwd");
var err_in = document.getElementById("errore-pwd-in");
$("#login").attr("disabled",true);

eraseCookie("idutente");
eraseCookie("fontecliccata");
eraseCookie("ad");


/*-----------------------------validazione------------------------------------------*/
var val_pwd = document.getElementById("password");


//TODO perchè da problemi nell'enter in #mail-to-users
$("#password").keypress(function(e) {
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
				if(utente.Admin == "true"){
					
					createCookie("ad","1");
					
			
				}
					else{
						createCookie("ad","0");
						
				};
			$.get("http://localhost:8080/DashboardServer/api/Utenti/id/id="+utente.ID,function(accesso){
				
				if(accesso.Attivo == 0){
					eraseCookie("fontecliccata");
					eraseCookie("idutente");
					eraseCookie("ad")
					
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
	eraseCookie("ad");

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