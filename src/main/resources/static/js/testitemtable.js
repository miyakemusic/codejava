class TestItemTable {
	constructor(div, resource, hideport) {
//		var equipment = "[[${equipment}]]";

		var tableid = div + '_testitemtable';
		$('#' + div).append('<table id="' + tableid + '" class="table table-striped table-bordered"></table>');
		
		this.table = $('#' + tableid).DataTable({
			"iDisplayLength": 50,
			"sAjaxSource": resource, //'testSummaryEquipmentJson?id=' + equipment,
			"sAjaxDataProp": "",
			"columnDefs": [
			            {
			                "targets": [ 0 ],
			                "visible": false,
			                "searchable": false
			            },	
			            {
							"targets": 5,
							"render": function ( data, type, full, meta ) {
									return '<button class="btn btn-link" name="Criteria" id="criteria_' + full.id + '">' + full.criteria + '</button>';
							}
						},	     
			            {
							"targets": 6,
							"render": function ( data, type, full, meta ) {
									return '<button class="btn btn-link" name="Result" id="result_' + full.id + '">' + full.result + '</button>';
							}
						},	
			            {
							"targets": 8,
							"render": function ( data, type, full, meta ) {
									return '<button class="btn-edit" name="Edit" id="edit_' + full.id + '">' + 'Edit' + '</button>';
							}
						},	      
			        ],
			"aoColumns": [
				{ "sTitle": "ID", "mData": "id" },
				{ "sTitle": "Port", "mData": "port" },
				{ "sTitle": "Test Point", "mData": "testPoint" },
				{ "sTitle": "Test Category", "mData": "testCategory" },
				{ "sTitle": "Test Item", "mData": "testItem" },
				{ "sTitle": "Criteria", "mData": "criteria" },
				{ "sTitle": "Result", "mData": "result" },
				{ "sTitle": "Pass/Fail", "mData": "passFail" },
				{ "sTitle": "Edit", "mData": null },
			]
		});			
		$('#' + tableid).css('width', '100%');
		if (hideport != null) {
			this.table.column( 1 ).visible( false );
		}
				
		var me = this;
		var criteriaDialogId = div + '_criteriadialog';
		$('#' + div).append('<div id="' + criteriaDialogId + '"></div>');
		var criteriaDialog = new MyTextDialog(criteriaDialogId, function(v) {
			postItem(me.selectedId, 'criteria', v);		
		});

		var resultDialogId = div + '_resultdialog';
		$('#' + div).append('<div id="' + resultDialogId + '"></div>');
		var resultDialog = new MyTextDialog(resultDialogId, function(v) {
			postItem(me.selectedId, 'result', v);		
		});
			
		function postItem(id, field, value) {
			var obj = new Object();
			obj.id = id;
			obj.field = field;
			obj.value = value;
			var json = JSON.stringify(obj);
			$.ajax({
				type: "POST",
				url: "updatePortTest",
				data: json,
				contentType: "application/json",
				dataType : "text"
			}).done(function(data){
				me.table.ajax.reload();
			}).fail(function(XMLHttpRequest, status, e){
				alert(e);
			});				
		}
		
		me.table.on( 'draw', function () {
			$('.btn').click(function(){
				//$("#textdialog").dialog({ title: $(this).attr('name')});
				me.selectedId = $(this).attr('id').split('_')[1];
				//selectedField = $(this).attr('id').split('_')[0];
				if ($(this).attr('name') == 'Criteria') {
					criteriaDialog.show($(this).text());
				}
				else if ($(this).attr('name') == 'Result') {
					resultDialog.show($(this).text());
				}
				else if ($(this).attr('name') == 'Edit') {

				}
				else {
					window.open("testSummaryPort?id=" + me.selectedId, "_blank");
				}
			});
			
			$('.btn-edit').click(function(){
				me.selectedId = $(this).attr('id').split('_')[1];
				//selectedField = $(this).attr('id').split('_')[0];
				$("#" + me.editdialogid).dialog('open');
			});
		});
		
		var ws = new MyWebSocket(function(obj){
			//console.log(obj);
			if (obj.signalType == 'ResultUpdated') {
				me.table.ajax.reload();
			}
		});
	}
	
	reload() {
		this.table.ajax.reload();
	}
}