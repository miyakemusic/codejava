class TestItemEditor {
	constructor(div, portid) {
		this.id = portid;
		$('#' + div).append('<div id="testItemEditorDiv"></div>');
		
		$('#testItemEditorDiv').append('<div>ID:' + portid + '</div>');
		$('#testItemEditorDiv').append('<div>Port Direction:<select id="portDirectionSelect"></select></div>');
		$('#testItemEditorDiv').append('<div>Test Category:<select id="testCategorySelect"></select></div>');
		$('#testItemEditorDiv').append('<div>Test Item:<select id="testItemSelect"></select></div>');
		$('#testItemEditorDiv').append('<div>Criteria:<input type="text" id="criteria"></div>');
		$('#testItemEditorDiv').append('<div>Result:<input type="text" id="result"></div>');
		$('#testItemEditorDiv').append('<div>Pass/Fail:<text id="passfail"></text></div>');
		
		retrieveTestItemCategory(function completed(){
			retrievePortTestValues(portid);
		});
		
		function retrievePortTestValues(id2) {
			$.ajax({
				type: "GET",
				url: "PortTestEntity?id=" + id2,
				dataType : "json"
			})
			.done(function(data){
				$('#criteria').val(data.criteria);
				$('#result').val(data.result);
				$('#testCategorySelect').val(data.test_itemEntity.category);
				$('#passfail').text(data.passfail);
				
				retrieveTestItemOptions(data.test_itemEntity.category, data.test_itemEntity.id);
				retrieveDirectionOptions(data.direction);
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});
		}
		
		function retrieveDirectionOptions(value) {
			$.ajax({
				type: "GET",
				url: "PortDirectionEntityS",
				dataType : "json"
			})
			.done(function(data){
				$('#portDirectionSelect').empty();
				for (var o of data) {
					$('#portDirectionSelect').append($('<option>').html(o.name).val(o.id));
				}
				$('#portDirectionSelect').val(value);
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});		
		}
		
		function retrieveTestItemCategory(completed) {
			$.ajax({
				type: "GET",
				url: "TestItemCategoryEntityS",
				dataType : "json"
			})
			.done(function(data){
				$('#testCategorySelect').empty();
				for (var o of data) {
					$('#testCategorySelect').append($('<option>').html(o.category).val(o.id));
				}
				completed();
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});			
		}
		
		function retrieveTestItemOptions(category, value) {
			$.ajax({
				type: "GET",
				url: "TestItemEntityS?category=" + category,
				dataType : "json"
			})
			.done(function(data){
				$('#testItemSelect').empty();
				for (var o of data) {
					$('#testItemSelect').append($('<option>').html(o.test_item).val(o.id));
				}
				$('#testItemSelect').val(value);
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			});	
			
		}
		$('#testCategorySelect').change(function() {
			var categoryID = $(this).val();
			retrieveTestItemOptions(categoryID);
		});
	}
	
	remove() {
		$('#testItemEditorDiv').remove();
	}
	
	post() {
		var direction = $('#portDirectionSelect').val();
		var testItem = $('#testItemSelect').val();
		var criteria = $('#criteria').val();
		var result = $('#result').val();
		var obj = new Object();
		obj.id = this.id;
		obj.direction = direction;
		obj.test_item = testItem;
		obj.criteria = criteria;
		obj.result = result;
		
		var json = JSON.stringify(obj);
		$.ajax({
			type: "POST",
			url: "/PortTestJson",
			data: json,
			contentType: "application/json",
			dataType : "json"
		}).done(function(data){
			
		}).fail(function(XMLHttpRequest, status, e){
			alert(e);
		});		
	}
}