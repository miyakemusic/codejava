class PortSummary {
	constructor(div) {
		this.tableid = div + '_portsummary';
		
		$('#' + div).append('<table id="'+ this.tableid + '" class="table table-striped table-bordered"></table>');
		$('#' + this.tableid).css('width', '100%');
		
		this.editdialogid = div + '_editdialog';
		this.editoptionid = div + '_editoptions';
		this.copycountid = div + '_copycount';
		this.portchooserid = div + '_portchooser';
		$('#' + div).append('<div id="' + this.portchooserid + '"></div>');
		
		var me = this;
		
		$('#' + div).append('<div id="'+ this.editdialogid + '"><select id="'+ this.editoptionid + '"></select><input type="text" id="' + this.copycountid + '" value="1"></div>');
		$("#" + me.editdialogid).dialog({
			modal:true,
			autoOpen: false,
			title: "",
			width: 300,
			height: 200,
			buttons: {
				"OK": function() {
					$(this).dialog("close");
					
					var obj = new Object();
					obj.id = me.selectedId;
					obj.count = $('#' + me.copycountid).val();
					obj.editType = $('#' + me.editoptionid).val();
					
					var json = JSON.stringify(obj);
					$.ajax({
						type: "POST",
						url: "/editPort",
						data: json,
						contentType: "application/json",
						dataType : "text"
					}).done(function(data){
						me.table.ajax.reload();
					}).fail(function(XMLHttpRequest, status, e){
						alert(e);
					});								
				},
				"Cancel": function() {
					$(this).dialog("close");
				}
			}
		});
		$('#' + me.editoptionid).append($('<option>').html('Copy').val('copy'));
		$('#' + me.editoptionid).append($('<option>').html('Delete').val('delete'));
		$('#' + me.editoptionid).change(function(){
			if ($(this).val() == 'copy') {
				$('#' + me.copycountid).show();
			}
			else if ($(this).val() == 'delete') {
				$('#' + me.copycountid).hide();
			}				
		});	
		
		var dialogid = div + '_dialog';
		$('#' + div).append('<div id="' + dialogid + '"></div>')
		
		this.dialog = new MyTextDialog(dialogid, function(val){
			var obj = new Object();
			obj.id = me.selectedId;
			obj.value = val;
			obj.field = 'port_name';
			
			var json = JSON.stringify(obj);
			$.ajax({
				type: "POST",
				url: "/updatePort",
				data: json,
				contentType: "application/json",
				dataType : "text"
			}).done(function(data){
				me.table.ajax.reload();
			}).fail(function(XMLHttpRequest, status, e){
				alert(e);
			});					
		});		
	}
	
	update(equipment) {
		var url = 'portsummaryjson?id=' + equipment;
		var me = this;
		if (this.table == null) {
			this.table = $('#' + this.tableid).DataTable({
				"iDisplayLength": 50,
				"sAjaxSource": url,
				"sAjaxDataProp": "",
				"columnDefs": [
							{
								"targets": [ 0 ],
				                "visible": false,
				                "searchable": false
				            },
				            {
								"targets": 1,
								"render": function ( data, type, full, meta ) {
										return '<button class="btn btn-link" name="Name" id="name_' + full.id + '">' + full.name + '</button>';
								}
							},
				            {
								"targets": 2,
								"render": function ( data, type, full, meta ) {
										return '<button class="btn btn-link" name="LinkEquipment" id="linkequipment_' + full.id + '">' + full.linkEquipment + '</button>';
								}
							},
				            {
								"targets": 3,
								"render": function ( data, type, full, meta ) {
										return '<button class="btn btn-link" name="LinkPort" id="linkport_' + full.id + '">' + full.linkPort + '</button>';
								}
							},
				            {
								"targets": 4,
								"render": function ( data, type, full, meta ) {
										return '<button class="btn btn-link" name="Cable" id="cable_' + full.id + '">' + full.cable + '</button>';
								}
							},
				            {
								"targets": 5,
								"render": function ( data, type, full, meta ) {
										return '<button class="btn btn-link" name="TestPoints" id="testPoints_' + full.id + '">' + full.testPoints + '</button>';
								}
							},
				            {
								"targets": 6,
								"render": function ( data, type, full, meta ) {
									return '<button class="btn btn-link" name="Tests" id="tests_' + full.id + '">' + full.tests + '</button>';
								}
							},
							{
								"targets": 7,
								"render": function ( data, type, full, meta ) {
									return '<button class="btn btn-link" name="Test Status" id="status_' + full.id + '">' + full.status + '</button>';
								}
							},
				            {
								"targets": 8,
								"render": function ( data, type, full, meta ) {
									//return '<a href="portsummary?id=' + full.id + '">' + full.ports + '</a>';
									return '<button class="btn-edit" name="Edit" id="edit_' + full.id + '">Edit</button>';
								}
							},								
				        ],
				"aoColumns": [
					{ "sTitle": "ID", "mData": "id" },
					{ "sTitle": "Port", "mData": "name" },
					{ "sTitle": "Destination Equipment", "mData": "linkEquipment" },
					{ "sTitle": "Destination Port", "mData": "linkPort" },
					{ "sTitle": "Cable", "mData": "cable" },
					{ "sTitle": "Test Point", "mData": "testPoint" },
					{ "sTitle": "Tests", "mData": "tests" },
					{ "sTitle": "Test Progress", "mData": "status" },
					{ "sTitle": "Edit", "mData": null },
				]
			});		
			$('#' + me.tableid).css('width', '100%');
			
			var cableChooser = new CommonChooserDialog(this.portchooserid, 'CableEntityS', 'cabletype', function(obj){
				var json = JSON.stringify(obj);
				$.ajax({
					type: "POST",
					url: "/updatePort",
					data: json,
					contentType: "application/json",
					dataType : "text"
				}).done(function(data){
					me.table.ajax.reload();
				}).fail(function(XMLHttpRequest, status, e){
					alert(e);
				});					
			});
			
			this.table.on( 'draw', function () {
				$('.btn').click(function(){
//					$("#textdialog").dialog({ title: $(this).attr('name')});
					me.selectedId = $(this).attr('id').split('_')[1];
//					selectedField = $(this).attr('id').split('_')[0];
					if ($(this).attr('name') == 'Name') {
						me.dialog.show($(this).text());
					}
					else if ($(this).attr('name') == 'Cable') {
						cableChooser.show(me.selectedId);
					}
					else {
						window.open("testSummaryPort?id=" + me.selectedId, "_blank");
					}
				});
				
				$('.btn-edit').click(function(){
					me.selectedId = $(this).attr('id').split('_')[1];
//					selectedField = $(this).attr('id').split('_')[0];
					$("#" + me.editdialogid).dialog('open');
				});
			});

		}
		else {
			this.table.ajax.url(url).load();
		}
	}
}