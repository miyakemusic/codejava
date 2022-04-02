class User {
	constructor(div, callback) {
		var userDiv = div + '_user';

		$('#' + div).append('<div id="'+ userDiv + '"></div>');
		$('#' + userDiv).append('<div>Group Name: <input type="text" id="groupName" class="form-control" required minlength="2" maxlength="20" /></div>');
		
		$('#' + userDiv).append('<div>Email: <input type="email" id="email" class="form-control" required/></div>');
		$('#' + userDiv).append('<div>Password: <input type="password" id="password" class="form-control" required minlength="6" maxlength="10"/></div>');
		$('#' + userDiv).append('<div>First Name: <input type="text" id="firstName" class="form-control" required minlength="2" maxlength="20"/></div>');
		$('#' + userDiv).append('<div>Last Name: <input type="text" id="lastName" class="form-control" required minlength="2" maxlength="20" /></div>');
		
		$('#' + userDiv).append('<button id="submit">Submit</button>');    
		
		$('#submit').click(function(){
			var obj = new Object();
			obj.groupName = $('#groupName').val();
			obj.email = $('#email').val();
			obj.password = $('#password').val();
			obj.firstName = $('#firstName').val();
			obj.lastName = $('#lastName').val();
			
			var json = JSON.stringify(obj);
			
			$.ajax({
				type: "POST",
				url: "/registerAdministrator",
				contentType: 'application/json',
				dataType : "json",
				data : json
			})
			.done(function(data){
				callback('completed');
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});			
		});
	}
}