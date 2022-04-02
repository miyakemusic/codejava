class PortDetail {
	constructor(div, id, type) {
		this.portDiv = div + '_portdiv';
		
		$('#' + div).append('<div id="'+ this.portDiv + '"></div>')
		this.tableid = "mytable" + id;
		
		$('#' + this.portDiv).append(	
			'<table id="' + this.tableid + '">' + 
				'<thead><tr><td>ID</td><td>Port</td><td>Direction</td><td>Category</td><td>Test Item</td><td>Criteria</td><td>Result</td><td>Pass/Fail</td><td>Edit</td><td>Delete</td></tr></thead>' + 
				'<tbody></tbody>' + 
			'</table>');
				
		var url;
		if (type == 'port') {
			url = "/PortTestEntityS?parent=" + id;
		}
		else if (type == 'equipment') {
			url = "/PortTestEntityByEquipment?equipment=" + id;
		}
		
		var table = $('#' + this.tableid).DataTable({
			"sAjaxSource": url,
			"sAjaxDataProp": "",
			"columnDefs": [
			            {
			                "targets": [ 0 ],
			                "visible": false,
			                "searchable": false
			            }
			        ],
			"aoColumns": [
				{ "sTitle": "ID", "mData": "test_itemEntity.id" },
				{ "sTitle": "Port", "mData": "port_name" },
				{ "sTitle": "Direction", "mData": "directionEntity.name" },
				{ "sTitle": "Category", "mData": "test_itemEntity.categoryEntity.category"},
				{ "sTitle": "Test Item", "mData": "test_itemEntity.test_item"},
				{ "sTitle": "Criteria", "mData": "criteria"},
				{ "sTitle": "Result", "mData": "result"},
				{ "sTitle": "Pass/Fail", "mData": "passfail"},
				{ "sTitle": "Edit", "mData": null, "sDefaultContent": '<button class="btn-edit">Edit</button>' },
				{ "sTitle": "Delete", "mData": null, "sDefaultContent": '<button class="btn-delete">Delete</button>' },
			]
		});
		
		$('#' + this.portDiv).append('<div id="portDetailDialog"></div>');
		
		$('#' + this.tableid + ' tbody').on( 'click', 'button', function () {
			var cls = $(this).attr('class');
			var data = table.row( $(this).parents('tr') ).data();
			if (cls == 'btn-edit') {
				var editor = new TestItemEditor('portDetailDialog', data.id);
				$("#portDetailDialog").dialog({
					modal:true,
					title: '',
					width: 600,
					height: 400,
					buttons: {
						"OK": function() {
							editor.post();
							editor.remove();
							$(this).dialog("destroy");
							table.ajax.reload();
							
						},
						"Cancel": function() {
							editor.remove();
							$(this).dialog("destroy");
						}
					}
				});	
			}
			else if ('btn-delete') {
			
			}
	    } );

		$('#' + this.tableid).css('width', '100%');
		
		var me = this;
		new MyWebSocket(function(obj){
			if (obj.signalType == 'ResultUpdated') {
				table.ajax.reload();
			}	
		});
	}
		
	remove() {
		$('#' + this.portDiv).remove();
	}
}