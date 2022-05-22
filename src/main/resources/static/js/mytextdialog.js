class MyTextDialog {
	constructor(div, okClicked, title) {
		this.dialogDiv = div + '_dialog';
		this.textDiv = div + '_text';
		$('#' + div).append('<div id="' + this.dialogDiv + '"></div>');
		$('#' + this.dialogDiv).append('<input type="text" id="' + this.textDiv + '">');
		$('#' + this.textDiv).css("width", "100%");
		
		var me = this;
		
		$("#" + this.dialogDiv).dialog({
			modal:true,
			autoOpen: false,
			title: title,
			width: 300,
			height: 200,
			buttons: {
				"OK": function() {
					$(this).dialog("close");
					var v = $('#' + me.textDiv).val();
					okClicked(v);							
				},
				"Cancel": function() {
					$(this).dialog("close");
				}
			}
		});				
	}
	
	show(defaultVal) {
		$('#' + this.textDiv).val(defaultVal);
		$("#" + this.dialogDiv).dialog('open');
	}
}