class MyTesterStatus {
	constructor(div) {
		this.thisdiv = div + "_mytetser";
		var tableid = this.thisdiv + "_table";
		$('#' + div).append('<div id="'+ this.thisdiv + '"><table id="'+ tableid + '"></table></div>');
		
		this.table = $('#' + tableid).DataTable({
			"sAjaxSource": 'mytestersforjs',
			"sAjaxDataProp": "",
			"columnDefs": [
			            {
			                "targets": [ 0 ],
			                "visible": false,
			                "searchable": false
			            },
			            {
						    "targets": 4,
						    "data": "download_link",
						    "render": function ( data, type, full, meta ) {
						      return '<a href="/testerScreen?id=' + data+'" target="_blank">' + data + '</a>';     }
						 }			            
			        ],
			"aoColumns": [
				{ "sTitle": "ID", "mData": "id" },
				{ "sTitle": "Category", "mData": "category" },
				{ "sTitle": "Vendor", "mData": "vendor" },
				{ "sTitle": "Product", "mData": "testerName" },
				{ "sTitle": "Tester ID", "mData": "myTesterName" },
				{ "sTitle": "Status", "mData": "status" }
			]
		});
		var me = this;
		
		var ws = new MyWebSocket(function(obj){
			if (obj.signalType == 'Signin' || obj.signalType == 'Signout') {
				me.update();
			}			
		});
	}
	
	update() {
		this.table.ajax.reload();
	}
	
	remove() {
		$('#' + this.thisdiv).remove();
	}
}