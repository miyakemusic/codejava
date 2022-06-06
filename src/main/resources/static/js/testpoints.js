class TestPoints {
	constructor(div) {
		this.tableid = div + '_testPointTable';
		
		var url = "PortDirectionEntityS";
		
		var addid = div + '_testpointadd';
		
		$('#' + div).append('<button id="' + addid + '">Add</button>');
		
		$('#' + div).append('<table id="'+ this.tableid + '"></table>');
		
		var table = $('#' + this.tableid).DataTable({
			"sAjaxSource": url,
			"sAjaxDataProp": "",
			"columnDefs": [
			            {
			                "targets": [ 0 ],
			                "visible": false,
			                "searchable": false
			            },
			            {
							"targets": 2,
							"render": function ( data, type, full, meta ) {
									var html = '<button class="btn-edit" name="Copy" id="copy_' + full.id + '">' + 'Copy' + '</button>';
									html += '<button class="btn-edit" name="Delete" id="delete_' + full.id + '">' + 'Delete' + '</button>';
									return html;
							}
						},	
			        ],
			"aoColumns": [
				{ "sTitle": "ID", "mData": "id" },
				{ "sTitle": "Name", "mData": "name" },
				{ "sTitle": "Edit", "mData": null, "sDefaultContent": '<button class="btn-edit">Edit</button>' },
			]
		});		
		
		var dialogid = div + '_testpoint_dialog';
		$('#' + div).append('<div id="' + dialogid + '"></div>');
		
		var dialog = new MyTextDialog(dialogid, function(val){
			var obj = new Object();
			obj.name = val;
			
			var json = JSON.stringify(obj);
			var url = 'PortDirectionEntity';
			$.ajax({
				type: "POST",
				url: url,
				data: json,
				contentType: "application/json",
				dataType : "text"
			}).done(function(data){
				table.ajax.reload();
			}).fail(function(XMLHttpRequest, status, e){
				alert(e);
			});					
		});	
		
		$('#' + addid).click(function(){
			dialog.show('');
		});
	}
}