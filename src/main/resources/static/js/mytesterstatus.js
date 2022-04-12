class MyTesterStatus {
	constructor(div) {
		this.thisdiv = div + "_mytetser";
		var tableid = this.thisdiv + "_table";
		$('#' + div).append('<div id="'+ this.thisdiv + '"><table id="'+ tableid + '"></table></div>');
		
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
				{ "sTitle": "Parent", "mData": "parentid" },
				{ "sTitle": "Status", "mData": "status" }
			],
			fnCreatedRow : function(nRow, aData, iDataIndex) {
				var element = $("td:eq(4)", nRow);
				element.text("");
				var choice = $("<select></select>")
//				.attr("type", "checkbox")
				.attr("id", "sel" + aData.id)
				.attr("class", "parentList")
//				.attr("checked", aData.selected)
				.change(function() {
//					aData.check = $(this).is(":checked").toString();
				});

				element.append(choice);
			}
		});
		
		var me = this;
		this.table.on( 'draw', function () {
			var data = me.table.rows().data();
			for (var i in data) {
				var o = data[i];
				$('.parentList').append($('<option>').html(o.testerName + '(' + o.myTesterName + ')').val(o.id));
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