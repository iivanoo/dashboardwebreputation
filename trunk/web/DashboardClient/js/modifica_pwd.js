$("#confirm-password").keypress(function(e){
	if(e.which == 13) {
	       $("#change-pwd").click();
	    };
})


$("#change-pwd").click(function(){
	var old_pwd = document.getElementById("old-password").value;
	var new_pwd = hex_md5(document.getElementById("new-password").value);
	var confirm = document.getElementById("confirm-password").value;
	if((old_pwd != "") && (new_pwd != "") && (confirm != "")){
		$("#vuoto-old").css("display","none");
		$("#vuoto-new").css("display","none");
		$("#vuoto-confirm").css("display","none");
		$.get("http://localhost:8080/DashboardServer/api/Utenti/id/id="+readCookie("idutente"),function(utente){
			console.log(hex_md5(old_pwd));
			console.log(utente.Password.toString());
			var ok = (hex_md5(old_pwd).toString().trim() == utente.Password.toString().trim());
			console.log(ok);
			if(hex_md5(old_pwd).toString().trim() == utente.Password.toString().trim()){
			
			$.get("http://localhost:8080/DashboardServer/api/Utenti/modificaPwd/idutente="+readCookie("idutente")+"/new-pwd="+new_pwd,function(esito){
				if(esito == "true"){
				$("#errore-old").css("display","none");
				$("#old-pwd").css("display","none");
				$("#new-pwd").css("display","none");
				$("#confirm-pwd").css("display","none");
				$("#change-pwd").css("display","none");
				document.getElementById("mod").innerHTML = "Password modificata con successo";
				$("#mod").attr("align","center");
				$("#btn_h").css("display","inline");
				$("#btn_g").css("display","none");
				}
				else
					{
					//alert{"si è verificato un errore, riprova"};
					};
			});	
				
				
				
			}
			else{
				$("#errore-old").css("display","inline");
			};
		});
	}
	else{
		if(old_pwd == ""){$("#vuoto-old").css("display","inline");};
		if(new_pwd == ""){$("#vuoto-new").css("display","inline");};
		if(confirm == ""){$("#vuoto-confirm").css("display","inline");};
	};
	
});





$("#new-password").blur(function(){
	pat = /^[a-zA-Z0-9\_\*\-\+\xE0\xE8\xE9\xF9\xF2\xEC\x27]{6,12}/;
	var pwd = document.getElementById("new-password").value;
	var bool = document.getElementById("new-password").value.match(pat);
	
	if(bool == null){
		$("#errore-new").css("display","inline");
		$("#change-pwd").attr("disabled",true);
		}
	else{
		$("#errore-new").css("display","none");
	};
});

function valida_Pwd(){
	var bool = document.getElementById("new-password").value == document.getElementById("confirm-password").value;
	if(!bool){
		$("#errore-confirm-new").css("display","inline");
		$("#change-pwd").attr("disabled",true);
		return false;
		}
	else{
		$("#errore-confirm-new").css("display","none");
		$("#change-pwd").attr("disabled",false);
		return true;
	};
};
$("#confirm-password").blur(function(){
	valida_Pwd();
});




