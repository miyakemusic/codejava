class EquipmentChooser {
	constructor (div, resource) {
		this.selectorid = div + '_selector';
		this.countid = div + '_count';
		this.nameid = div + '_name';
		
		$('#' + div).append('<div><select id="'+ this.selectorid + '"></select></div>');
		$('#' + div).append('<div>Name:<input type="text" id="'+ this.nameid + '" value="New Equipment#00"></div>');
		$('#' + div).append('<div>Count:<input type="text" id="'+ this.countid + '" value="1"></div>');
		
		var me = this;
		$.ajax({
			type: "GET",
			url: resource,
			dataType : "json"
		})
		.done(function(data){ 
			for (var o of data) {
				$('#' + me.selectorid).append($('<option>').html(o.category).val(o.id));
			}
			
		})
		.fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
		});			
	}
	
	category() {
		return $('#' + this.selectorid).val();
	}
	
	name() {
		return $('#' + this.nameid).val();
	}
	
	count() {
		return $('#' + this.countid).val();
	}
}
class EquipmentChooserDialog {
	constructor(div, project, resource, onOK) {
		this.dialogid = div + '_dialog';
		$('#' + div).append('<div id="' + this.dialogid + '"></div>');
		
		var chooser = new EquipmentChooser(this.dialogid, resource);
		
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
					obj.project = project;
					obj.category = chooser.category();
					obj.count = chooser.count();
					obj.name = chooser.name();
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