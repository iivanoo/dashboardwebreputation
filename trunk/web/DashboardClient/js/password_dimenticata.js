$("#sending").click(function(){
	
$.get("http://localhost:8080/DashboardServer/api/password_dimenticata/email="+$("#mail-to-users").val());
});


$("#mail-to-users").keypress(function(e) {
    if(e.which == 13) {
       $("#sending").click();
    }
});
