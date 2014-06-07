$(document).ready(function() {


	function validateEmail(email) { 
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	} 

	
			
			$("#submit").click(function(){
				
				if (!validateEmail($("#email").val())){
					$(".emptyEmail").show();
					return;
				}
				
				var items = {};
				
				//var list = $("#listView").data('kendoListView');
				//var ds = list.getDataSource().items();
				for (var i = 0; i < products.length; i++){
					var currAmount = $("#" + products[i].ProductID).find("#amount" + products[i].ProductID).data("kendoNumericTextBox").value();
					if (currAmount > 0){
						items[products[i].ProductName] = currAmount;					
					}
				}
			
				var requestData = {
					email: $("#email").val(),
					order: items
				};
				
				$.ajax({
					url: "/order",
					data: JSON.stringify(requestData),
					type: 'POST',
					dataType: 'text',
					contentType: 'application/json',
					success: function(data, textStatus, jqXHR){
						alert('confirmed');
					},
					error: function(jqXHR, textStatus, errorThrown){
						alert('error: ' + jqXHR + '; status: ' + status + '; errorThrown: ' + errorThrown);
					}
				});
			});

});
