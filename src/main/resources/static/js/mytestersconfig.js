class MyTestersConfig {
	constructor(div) {
		this.thisdiv = div + "_mytetser";
		var tableid = this.thisdiv + "_table";
		$('#' + div).append('<div id="'+ this.thisdiv + '"><table id="'+ tableid + '" class="table table-striped table-bordered"></table></div>');
		
		$('#' + this.thisdiv).css('width', '100%');
		var me = this;
		this.table = $('#' + tableid).DataTable({
			"iDisplayLength": 50,
			"sAjaxSource": 'mytestersforjs',
			"sAjaxDataProp": "",
			"columnDefs": [
			            {
			                "targets": [ 0 ],
			                "visible": false,
			                "searchable": false
			            },
//			            {
//						    "targets": 7,
//						    "data": "download_link",
//						    "render": function ( data, type, full, meta ) {
//						      return '<a href="/testerScreen?id=' + full.myTesterName+'" target="_blank">' + full.status + '</a>';     }
//						 }			            
			        ],
			"aoColumns": [
				{ "sTitle": "ID", "mData": "id" },
				{ "sTitle": "Category", "mData": "category" },
				{ "sTitle": "Vendor", "mData": "vendor" },
				{ "sTitle": "Product", "mData": "testerName" },
				{ "sTitle": "Description", "mData": "description" },
				{ "sTitle": "Tester ID", "mData": "myTesterName" },
				{ "sTitle": "Parent", "mData": "parentid" },
//				{ "sTitle": "Status", "mData": "status" },
//				{ "sTitle": "Candidates", "mData": "parentCandidates" }
			],
			fnCreatedRow : function(nRow, aData, iDataIndex) {
				if (aData.standalone) {
					return;
				}
				var element = $("td:eq(5)", nRow);
				element.text("");
				var choice = $('<select id="sel' + aData.id + '"></select>')
				.attr("id", "sel" + aData.id)
				.attr("class", "parentList")
				.change(function() {
					var obj = new Object();
					obj.id = aData.id;
					obj.parentid = $(this).val();
					var json = JSON.stringify(obj);
					$.ajax({
						type: "POST",
						url: "/setParent",
						data: json,
						contentType: "application/json",
						dataType : "text"
					}).done(function(data){
						me.table.ajax.reload();
					}).fail(function(XMLHttpRequest, status, e){
						alert(e);
					});						
				});

				element.append(choice);
			}
		});
		
		var me = this;
		this.table.on( 'draw', function () {
			var data = me.table.rows().data();
			var map = new Map();
			for (var i in data) {
				var o = data[i];
				map.set(o.id, o.testerName + ' (' + o.myTesterName + ')');
			}
			for (var i in data) {
				var o = data[i];
				$('#sel' + o.id).append($('<option>').html('-').val(-1));
				if (o.parentid != null && o.parentid > 0) {
					$('#sel' + o.id).append($('<option>').html(map.get(o.parentid)).val(o.parentid));
				}
				for (var j in o.parentCandidates) {
					var option = o.parentCandidates[j];
					$('#sel' + o.id).append($('<option>').html(map.get(option)).val(option));
				}
				$('#sel' + o.id).val(o.parentid);
			}
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