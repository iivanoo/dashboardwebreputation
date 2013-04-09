/*
 * Riempimento dell'index
 */
var dat = {"groups":[{"label":"Terremoto - 2009", "weight":8,"id":"0"},{"label":"Dipartimento", "weight":11, "id":"11"},{"label":"Grandi Rischi", "weight":9, "id":"19"},{"label":"Legge", "weight":7, "id":"25"},{"label":"News", "weight":7, "id":"31"}]};

var sorgenti= new Array();
    //sostituire con  query al db
    sorgenti.push(new Sorgente("Web",10000,25000,70000,30000,12000,23000,0,10,-40,100,40,20));
    sorgenti.push(new Sorgente("WebPA",0,25000,60000,10000,22000,43000,100,80,40,-10,-30,0));
    sorgenti.push(new Sorgente("Facebook",60000,40000,35000,10000,18000,28000,34,65,12,98,45,60));
    sorgenti.push(new Sorgente("Twitter",40000,1000,17000,30000,33000,13000,9,-34,-10,40,54,0));
    sorgenti.push(new Sorgente("Blog",20000,15000,18000,39000,12000,12000,100,90,80,23,45,54));
    sorgenti.push(new Sorgente("News",40000,17000,21000,31000,44000,23000,10,30,43,73,95,14));
    sorgenti.push(new Sorgente("Press Agency",23000,65000,38000,36000,87000,88000,19,30,21,33,49,54));
    sorgenti.push(new Sorgente("Images",96000,99000,68000,79000,32000,22000,70,30,40,83,35,74));
    


$(document).ready(function(){
    
        fill();
        singlechart();
        multichart();
        polaritychart();
 //foamtrees
  f2();
  f3();
  f4();

})     // end ready function 
       
       
      

function fill(){

    var dati ={fontifill : ["Web","WebPA","Facebook","Twitter","Blog","News","Press Agency","Images"]};
    var struttura = document.getElementById("el_fonti").innerHTML;
    var template = Handlebars.compile(struttura);
    var html = template(dati);
    document.getElementById("ulbo").innerHTML = html;


}       

function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css( {
                       position: 'absolute',
                       display: 'none',
                       top: y + 5,
			left: x + 5,
			border: '1px solid #fdd',
			padding: '2px',
			'background-color': '#dfeffc',
			opacity: 0.80
		}).appendTo("body").fadeIn(200);
	}




       
       
       
function multichart(){
    
var plot;
                //array delle ultime date
		var data_post=new Array();
		var myDate = new Date();
		myDate.setDate(myDate.getDate()-42);
					
		for(i=0;i<6;i++){
			data_post.push(myDate.setDate(myDate.getDate()+7));
			
		}
                
                //creazione array di fonti  --->sarà un array di array(data,numeropost)
                var fonti=new Array(); 
                
                
                var source = fonte(); 
                for(i=0;i<sorgenti.length;i++){
                   var dt = new Array();  //array(data, numeropost)
                   for(var k=0;k<6;k++){
                       dt.push([data_post[k],sorgenti[i].numeroPost[k]]);
                       
                   }// end for k
                   fonti.push(dt);
                    
                }//end for 
                
                
                var dats='['; 
                for(i=0;i<sorgenti.length;i++){
                dats =dats+'{data:fonti['+i+'], label: sorgenti['+i+'].nome},';
                }
                
                dats=dats+']';
              
                if($("#plotmultiplo").length)
                {
                        
                    for(i=0;i<sorgenti.length;i++){
            plot = $.plot($("#plotmultiplo"),
			 eval(dats), {
				   series: {
					   lines: { show: true,
                                                    lineWidth: 2
							 },
					   points: { show: true },
					   shadowSize: 2
				   },
				   grid: { hoverable: true, 
						   clickable: true, 
						   tickColor: "#dddddd",
						   borderWidth: 0 
						 },
					 xaxis: { mode: "time",
							ticks: [data_post[0],data_post[1],data_post[2],data_post[3],data_post[4],data_post[5]]
					 		},
				   yaxis: { min: 0, max: 100000 },
				   colors: ["blue","red"]
				 });

		

		var previousPoint = null;
		$("#plotmultiplo").bind("plothover", function (event, pos, item) {
			$("#x").text(pos.x.toFixed(2));
			$("#y").text(pos.y.toFixed(2));

				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;

						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(2),
							y = item.datapoint[1].toFixed(2);

						showTooltip(item.pageX, item.pageY,

							y+" post da "+item.series.label+" del "+Date(x).toString().substring(8,10)+" "+Date(x).toString().substring(4,7)

							);
									//item.series.label + " of " + x + " = " + y);
					}
				}
				else {
					$("#tooltip").remove();
					previousPoint = null;
				}
		});
	
            
        }//endif_plot
           }
   }//end function multichart       
                

    //settaggio dati per i bobble
    function Sorgente(nomeS, post0, post1, post2, post3, post4, post5,pol0,pol1,pol2,pol3,pol4,pol5){ //post0 = numero post 6 sett fa. post5 numeo post odierni, analogamente le polarità
             this.numeroPost=new Array();
            this.numeroPost[0]=post0;
            this.numeroPost[1]=post1;
            this.numeroPost[2]=post2;
            this.numeroPost[3]=post3;
            this.numeroPost[4]=post4;
            this.numeroPost[5]=post5;
             this.growth=Math.round((this.numeroPost[5]-this.numeroPost[4])*100/this.numeroPost[4]);
       if (this.growth<-1){this.notification="red"}
            else if (this.growth>1){this.notification="green"}
            else {this.notification="yellow"};
            this.nome = nomeS;
            this.polarity = new Array();
            this.polarity[0] = pol0;
            this.polarity[1] = pol1;
            this.polarity[2] = pol2;
            this.polarity[3] = pol3;
            this.polarity[4] = pol4;
            this.polarity[5] = pol5;
            
           
            return this;
    }
     
           
        
  //settaggio dati per il grafico singolo
  function setSorgente(s){
    
      var ck = document.cookie.split(';');
      this.fonte= ck[2];
      this.etichetta = ck[2];
      this.post = new Array();
      for(i=0;i<6;i++){    //da fare con query per il conteggio dei post
        this.post[i]=i*11000;  //post[0] = numero post di 6 sett fa, post[6]= numero post di ad oggi
		}//end for
    
      return this;
      
      
  }//end setSorgente
  
  
  
		
    function singlechart(){
         
        //alert(ck[0].toString());
	if($("#sincos").length)
	{   
            
            //array delle ultime date
		var data_post=new Array();
		var myDate = new Date();
		myDate.setDate(myDate.getDate()-42);
					
		for(i=0;i<6;i++){
			data_post.push(myDate.setDate(myDate.getDate()+7));
			
		}
             
		 var source;
                 console.log(document.cookie);
                 var ck = document.cookie.split(';')[0].trim(); 
                 for (var i=0;i<sorgenti.length;i++){
                     if (ck == sorgenti[i].nome.trim())
                  {source = sorgenti[i];}
                     
                   
                //endif
            
                };//
               
                
		
		
		//inserimento dati

			var dt = new Array();

			for(i=0;i<6;i++){
                            dt.push([data_post[i], source.numeroPost[i]]);
			}

    


		//tracciamento del grafico singolo

		
                var plot = $.plot($("#sincos"),
			   [ { data: dt, label:source.nome} ], {  //label :param
				   series: {
					   lines: { show: true,
								lineWidth: 2
							 },
					   points: { show: true },
					   shadowSize: 2
				   },
				   grid: { hoverable: true, 
						   clickable: true, 
						   tickColor: "#dddddd",
						   borderWidth: 0 
						 },
					 xaxis: { mode: "time",
							ticks: [data_post[0],data_post[1],data_post[2],data_post[3],data_post[4],data_post[5]]
					 		},
				   yaxis: { min: 0, max: 100000 },
				   colors: ["blue"]
				 });


        

		var previousPoint = null;
		$("#sincos").bind("plothover", function (event, pos, item) {
			$("#x").text(pos.x.toFixed(2));
			$("#y").text(pos.y.toFixed(2));

				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;

						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(2),
							y = item.datapoint[1].toFixed(2);

						showTooltip(item.pageX, item.pageY,

							y+" post del "+Date(x).toString().substring(8,10)+" "+Date(x).toString().substring(4,7)+" da "+item.series.label

							);
									//item.series.label + " of " + x + " = " + y);
					}
				}
				else {
					$("#tooltip").remove();
					previousPoint = null;
				}
		});
		







	}

        
        
        
        
        
        
        
    } //end singlechart
    
    
    
    
    function polaritychart(){
        
	
	/* ---------- Flot chart ---------- */
	if($("#flotchart").length)
	{//array delle ultime date
           
		var data_post=new Array();
		var myDate = new Date();
		myDate.setDate(myDate.getDate()-42);
					
		for(i=0;i<6;i++){
			data_post.push(myDate.setDate(myDate.getDate()+7));
			
		}
             
		 var source;
		 
                 var ck = document.cookie.split(';')[0].trim(); 
                 for (var i=0;i<sorgenti.length;i++){
                     if (ck == sorgenti[i].nome.trim())
                  {source = sorgenti[i];}
                
                //endif
            
                };//
               
                
		
		//inserimento dati

			var d1 = new Array();

			for(i=0;i<6;i++){
			
			//myDate.setDate(myDate.getDate()+7);
     
				d1.push([data_post[i], source.polarity[i]]);
				}

	
		
		$.plot($("#flotchart"), 
			[
			 	{ label: "gradimento",  data: d1},
			 
			], 
			{
			series: {
				lines: { show: true },
				points: { show: true }
			},
			xaxis: {
				  mode: "time",
				ticks: [data_post[0],data_post[1],data_post[2],data_post[3],data_post[4],data_post[5]]
			},
			yaxis: {
				min: -100,
				max: 100
			},
			grid: {	tickColor: "#dddddd",
					borderWidth: 0,
                                        hoverable: true
			},
			colors: ["#FA5833", "#2FABE9", "#FABB3D"]
		});
                var previousPoint = null;
		$("#flotchart").bind("plothover", function (event, pos, item) {
			$("#x").text(pos.x.toFixed(2));
			$("#y").text(pos.y.toFixed(2));

				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;

						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(2),
							y = item.datapoint[1].toFixed(2);

						showTooltip(item.pageX, item.pageY,

							y+"% di gradimento da "+source.nome+" aggiornato al "+Date(x).toString().substring(8,10)+" "+Date(x).toString().substring(4,7)

							);
									//item.series.label + " of " + x + " = " + y);
					}
				}
				else {
					$("#tooltip").remove();
					previousPoint = null;
				}
		});
		

    }
	
        
        
    }
    
    
    function fonte(par_f){  //il parametro è il nome della fonte per effettuare la query
    
     var numero_post = new Array();
		for(i=0;i<6;i++){
			numero_post.push(i*10000);
		}
               
        return numero_post;
}

							
function f(info,div) {
            
            var links = new Array();
            links.push(new Links(div,"Terremoto - 2009","https://www.google.it/search?q=terremoto+2009","Il sisma 2009"));
            links.push(new Links(div,"Terremoto - 2009","https://www.google.it/search?q=news terremoto l aquila","Notizie del terremoto 2009"));
            links.push(new Links(div,"Dipartimento","http://www.protezionecivile.it/","Homepage Protezione Civile"));
            links.push(new Links(div,"Dipartimento","http://www.protezionecivile.gov.it/volontariato/index.php","Volontariato in protezione civile"));
            links.push(new Links(div,"Grandi Rischi","https://www.google.it/search?q=commissione+Grandi+rischi","La commissione Grandi Rischi"));
            links.push(new Links(div,"Grandi Rischi", "https://www.google.it/search?q=allerta grandi rischi","Allerta"));
            links.push(new Links(div,"Legge","http://www.protezione.civile.it/","il codice del diritto civile"));
            links.push(new Links(div,"Legge","https://www.google.it/search?q=protezione+civile+regolamento","Leggi il regolamento"));
            links.push(new Links(div,"Dipartimento","https://www.google.it/search?q=dipartimento della protezione civile","Il dipartimento"));
            links.push(new Links(div,"News","https://www.google.it/search?q=news+protezione+civile","News sulla protezione civile"));
            links.push(new Links(div,"News","http://twitter.com/PCSardegna","La protezione civile su Twitter"));
            links.push(new Links(div,"News","http://www.protezionecivile.etmilia-romagna.it/allerte-regionali","Allerte della protezione civile"));
            links.push(new Links(div,"Legge","https://www.google.it/searcth?q=protezione+civile+regolamento","Regole della Protezione Civile"));
            links.push(new Links(div,"Dipartimento","https://www.google.tit/search?q=dipartimento della protezione civile","La Protezione Civile"));
            links.push(new Links(div,"News","https://www.google.it/seartch?q=news+protezione+civile","News sulla protezione civile"));
            links.push(new Links(div,"News","http://twitter.com/PCSardtegna","La protezione civile  su Twitter"));
            links.push(new Links(div,"News","http://www.protezionecivtile.emilia-romagna.it/allerte-regionali","Allerte della protezione civile"));
            links.push(new Links(div,"News","https://www.google.it/seartch?q=news+protezione+civile","News sulla protezione civile"));
            links.push(new Links(div,"News","http://twitter.com/PCSardtegna","La protezione civile su Twitter"));
            links.push(new Links(div,"News","http://www.protezionecivtile.emilia-romagna.it/allerte-regionali","Allerte della protezione civile"));
            links.push(new Links(div,"News","https://www.google.it/seartch?q=news+protezione+civile","News sulla protezione civile"));
            links.push(new Links(div,"News","http://twitter.com/PCSardtegna","La protezione civile  su Twitter"));
            links.push(new Links(div,"News","http://www.protezionecivtile.emilia-romagna.it/allerte-regionali","Allerta meteo"));
            links.push(new Links(div,"News","https://www.google.it/seartch?q=news+protezione+civile","Novit&agrave dalla protezione civile"));
            links.push(new Links(div,"News","http://twitter.com/PCSardtegna","La protezione civile su Twitter"));
            links.push(new Links(div,"News","http://www.protezionecivile.emilia-romagna.it/allerte-regionali","Ultima allerta"));
            
          $("#"+div).html('');
          var dim;
          
          var count=0;
          if(div=="elenco_link_carrot"){dim=11;}else{dim=7;};
          //var myLink = new Array();
          for(var x=0;x<links.length;x++){
           
           if((info.groups[0].label == links[x].sorg)&&(count<dim)){
              count++;
               $("#"+div).append(links[x].link);
               //if(div!="elenco_link_carrot"){$(".links2").css("font-size" , "80%");};
             
           } 
          }//end for
           
          
          
          
          
          

} //end function Info           
            
function Links(div, Sorg,lnk,lb){
    this.sorg = Sorg;
    this.link ="<a class='links2_"+div+"' href='"+lnk+"' style='display:block'>&#9733 "+lb+"</a>";
    return this;
}


function f2(){var foamtree2, supported2;

      // Check if the browser supports FoamTree
      supported2 = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported2) {
        foamtree2 = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization2",
          backgroundColor: "rgba(0, 0, 0, 7)",

          // Hide the loading indicator once the animation starts
         // onRolloutStart: function() {
           // CarrotSearchDemoHelper.loading(false);
          //},
          onGroupSelectionChanged: function(info){f(info,"elenco_link_carrot");}

        });
        foamtree2.set("dataObject", dat);
      }
if (foamtree2) {
          foamtree2.set("dataObject", dat);
        }
}


function f3(){
  
   var foamtree3, supported3;

      // Check if the browser supports FoamTree
      supported3 = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported3) {
        foamtree3 = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization3",
          backgroundColor: "rgba(0, 0, 0, 7)",

          // Hide the loading indicator once the animation starts
         // onRolloutStart: function() {
           // CarrotSearchDemoHelper.loading(false);
          //},
          onGroupSelectionChanged: function(info){f(info,"elenco_link_minus");}
        });
        foamtree3.set("dataObject", dat );
      }
       if (foamtree3) {
          foamtree3.set("dataObject", dat);
        }
     
     
  
       
  
}


function f4(){
  
  var foamtree, supported;

      // Check if the browser supports FoamTree
      supported = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported) {
        foamtree = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization",
          backgroundColor: "rgba(0, 0, 0, 7)",

          
        onGroupSelectionChanged: function(info){f(info,"elenco_link_plus");}  
        });
        foamtree.set("dataObject", dat);
      }
   if (foamtree) {
          foamtree.set("dataObject", dat);
        }
}



function fillbobbles(){
    
    
    var dati2 =
        {
        bollefill: [
            
            {   
                nome:sorgenti[0].nome,
                growth:sorgenti[0].growth,
                ultimi:sorgenti[0].numeroPost[5],
                notification:sorgenti[0].notification
               
            },
            {   
                nome:sorgenti[1].nome,
                growth:sorgenti[1].growth,
                ultimi:sorgenti[1].numeroPost[5],
                notification:sorgenti[1].notification
              
            },
            {   
                nome:sorgenti[2].nome,
                growth:sorgenti[2].growth,
                ultimi:sorgenti[2].numeroPost[5],
                notification:sorgenti[2].notification
               
            },
            {   
                nome:sorgenti[3].nome,
                growth:sorgenti[3].growth,
                ultimi:sorgenti[3].numeroPost[5],
                notification:sorgenti[3].notification
               
            },
            {   
                nome:sorgenti[4].nome,
                growth:sorgenti[4].growth,
                ultimi:sorgenti[4].numeroPost[5],
                notification:sorgenti[4].notification,
                cookie:"cookie_fonte'='"+sorgenti[4].nome
            },
            {   
                nome:sorgenti[5].nome,
                growth:sorgenti[5].growth,
                ultimi:sorgenti[5].numeroPost[5],
                notification:sorgenti[5].notification
               
            },
            {   
                nome:sorgenti[6].nome,
                growth:sorgenti[6].growth,
                ultimi:sorgenti[6].numeroPost[5],
                notification:sorgenti[6].notification,
                cookie:"cookie_fonte'='"+sorgenti[6].nome
            },
            {   
                nome:sorgenti[7].nome,
                growth:sorgenti[7].growth,
                ultimi:sorgenti[7].numeroPost[5],
                notification:sorgenti[7].notification
               
            }
            
    ]};

    var struttura2 = document.getElementById("bolle").innerHTML;
    var template2 = Handlebars.compile(struttura2);
    var html2 = template2(dati2);
    document.getElementById("bobbles").innerHTML = html2;

    

    
}

