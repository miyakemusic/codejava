class TestItemAdd {
	constructor(div) {
		var testitemCategoryListDiv = div + '_testitemCategoryList';
		this.testitemListDiv = div + '_testitemList';
		this.testPointListDiv = div + '_testPointList';
		this.testitemtable = div + '_testitemtable';
		this.testname = div + '_name';
		
		$('#' + div).append('<div>Test Point: <select id="' + this.testPointListDiv + '"></select></div>');
		$('#' + div).append('<div>Category: <select id="' + testitemCategoryListDiv + '"></select></div>');
//		$('#' + div).append('<div>Test Item: <select id="' + this.testitemListDiv + '"></select></div>');
		$('#' + div).append('Test Name:<input type="text" id="' + this.testname + '">');
		$('#' + div).append('<table id="' + this.testitemtable + '"></table>');
		
		$('#' + this.testname).css('width', '100%');
		$('#' + this.testitemtable).css('width', '100%');
		
		var me = this;
		function retreiveCategory() {
			$.ajax({
				type: "GET",
				url: "testItemCategoryJson",
				dataType : "json"
			})
			.done(function(data){
				for (var o of data) {
					$('#' + testitemCategoryListDiv).append($('<option>').html(o.name).val(o.id));
				}
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});	
		}
		retreiveCategory();
	
		function retreiveTestItems(category) {
			$('#' + me.testitemListDiv).empty();
			
			$.ajax({
				type: "GET",
				url: "testitemdefjson?category=" + category,
				dataType : "json"
			})
			.done(function(data){
				for (var o of data) {
					$('#' + me.testitemListDiv).append($('<option>').html(o.testitem).val(o.id));
				}
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});	
		}
			
		$('#' + testitemCategoryListDiv).change(function(){
			retreiveTestItems($('#' + testitemCategoryListDiv).val());
			$('#' + me.testname).val($(this).children('option:selected').text() + "@");
			updateTable($('#' + testitemCategoryListDiv).val());
		});
		
		function retreiveTestPoint() {
			$.ajax({
				type: "GET",
				url: "PortDirectionEntityS",
				dataType : "json"
			})
			.done(function(data){
				for (var o of data) {
					$('#' + me.testPointListDiv).append($('<option>').html(o.name).val(o.id));
				}
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});	
		}
		
		retreiveTestPoint();
		
		this.checks = new Set();
		function updateTable(category) {
			me.checks.clear();
			if (me.table == null) {
				me.table = $('#' + me.testitemtable).DataTable({
					"iDisplayLength": 50,
					"sAjaxSource": 'testitemdefjson?category=' + category,
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
											var id = 'check_' + full.id;
											me.checks.add(id);
											return '<input type="checkbox" name="Check" id="' + id + '" checked>';
								}
							},         
					        ],
					"aoColumns": [
						{ "sTitle": "ID", "mData": "id" },
						{ "sTitle": "Select", "mData": null },
						{ "sTitle": "Test Item", "mData": "testitem" },
						{ "sTitle": "Description", "mData": "description" },
					]
				});	
			}
			else {
				me.table.ajax.url('testitemdefjson?category=' + category).load();
			}			
		}

	}
	
	testPoint() {
		return $('#' + this.testPointListDiv).val();
	}
	
	testItem() {
		var ret = [];
//		var data = this.table.rows().data();
		for (var o of this.checks) {
//			var o = this.checks[i];
			var checked = $('#' + o).prop('checked');
			if (checked) {
				var id = o.replace('check_', '');
				ret.push(id);
			}
		}		
		return ret;
	}
	
	testName() {
		return $('#' + this.testname).val();
	}
}
class TestItemAddDialog{
	constructor(div, port, onOK) {
		this.dialogid = div + '_testitemadddialog';
		$('#' + div).append('<div id="' + this.dialogid + '"></div>');
		
		var chooser = new TestItemAdd(this.dialogid);
		
		$("#" + this.dialogid).dialog({
			modal:true,
			autoOpen: false,
			title: "Add Test",
			width: 800,
			height: 700,
			buttons: {
				"OK": function() {
					$(this).dialog("close");
					
					var obj = new Object();
					obj.port = port;
					obj.testItem = chooser.testItem();
					obj.testPoint = chooser.testPoint();
					obj.testGroupName = chooser.testName();
					
					var json = JSON.stringify(obj);
					$.ajax({
						type: "POST",
						url: "/addTestItem",
						data: json,
						contentType: "application/json",
						dataType : "text"
					}).done(function(data){
						onOK(obj);	
					}).fail(function(XMLHttpRequest, status, e){
						alert(e);
					});						
										
				},
				"Cancel": function() {
					$(this).dialog("close");
				}
			}
		});		
	}
	
	show() {
		$('#' + this.dialogid).dialog('open');
	}
}