class CableChooser {
	constructor (div, resource) {
		this.selectorid = div + '_selector';
		this.countid = div + '_count';
		this.nameid = div + '_name';
		
		$('#' + div).append('<div><select id="'+ this.selectorid + '"></select></div>');
//		$('#' + div).append('<div>Name:<input type="text" id="'+ this.nameid + '" value="New Equipment#00"></div>');
//		$('#' + div).append('<div>Count:<input type="text" id="'+ this.countid + '" value="1"></div>');
		
		var me = this;
		$.ajax({
			type: "GET",
			url: resource,
			dataType : "json"
		})
		.done(function(data){ 
			for (var o of data) {
				$('#' + me.selectorid).append($('<option>').html(o.name).val(o.id));
			}
			
		})
		.fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
		});			
	}
	
	id() {
		return $('#' + this.selectorid).attr('id');
	}
}
class CableChooserDialog {
	constructor(div, port, resource, onOK) {
		this.dialogid = div + '_dialog';
		$('#' + div).append('<div id="' + this.dialogid + '"></div>');
		
		var chooser = new CableChooser(this.dialogid, resource);
		
		$("#" + this.dialogid).dialog({
			modal:true,
			autoOpen: false,
			title: "",
			width: 500,
			height: 300,
			buttons: {
				"OK": function() {
					$(this).dialog("close");
					
					var obj = new Object();
					obj.port = port;
					obj.chooser.id();
					onOK(obj);						
				},
				"Cancel": function() {
					$(this).dialog("close");
				}
			}
		});		
	}
	
	show() {
		$('#' + this.dialogid).dialog('open');
	}	
}