class PortTopology {
	constructor(div, ids) {
		$('#' + div).append('<div id="porttopologycanvas"></div>')
		this.diagram = new DiagramCanvas('porttopologycanvas');
		var me = this;
	   			
	   	$('#' + div).append('<div id="portdetaildialog"></div>');
		this.diagram.onClick(function(id, text) {
			$('#portdetaildialog').append('<div id="portdetailcontent"></div>');
			var detail = new PortDetail("portdetailcontent", id, 'port');
			$("#portdetaildialog").dialog({
				modal:true,
				title: text,
				width: 900,
				height: 500,
				buttons: {
				"Close": function() {
						detail.remove();
						$('#portdetailcontent').remove();
						$(this).dialog("destroy");
					}
				}
			});
		});
	    
	    var me = this;
	    	
		$.ajax({
			type: "GET",
			url: "portDiagram?parent=" + ids,
			dataType : "json"
		})
		.done(function(data){
			me.diagram.setData(data);
		})
		.fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
		});
	}
	
	remove() {
		this.diagram.remove();
		$('#porttopologycanvas').remove();
		$('#portdetaildialog').remove();
	}
}