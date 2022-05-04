class PortSummary {
	constructor(div) {
		this.tableid = div + '_portsummary';
		
		$('#' + div).append('<table id="'+ this.tableid + '"></table>');
		$('#' + this.tableid).css('width', '100%');
	}
	
	update(equipment) {
		var url = 'portsummaryjson?id=' + equipment;
		
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
										return '<button class="btn btn-link" name="TestPoints" id="testPoints_' + full.id + '">' + full.testPoints + '</button>';
								}
							},
				            {
								"targets": 3,
								"render": function ( data, type, full, meta ) {
									return '<button class="btn btn-link" name="Tests" id="tests_' + full.id + '">' + full.tests + '</button>';
								}
							},
				        ],
				"aoColumns": [
					{ "sTitle": "ID", "mData": "id" },
					{ "sTitle": "Port", "mData": "name" },
					{ "sTitle": "Test Point", "mData": "testPoint" },
					{ "sTitle": "Tests", "mData": "tests" },
				]
			});		
			$('#' + this.tableid).css('width', '100%');
		}
		else {
			this.table.ajax.url(url).load();
		}
	}
}