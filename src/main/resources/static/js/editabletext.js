class EditableText {
	constructor(div, original, changed, fontsize) {
		var textid = div + "_edit";
		var labelid = div + "_label";
		
		$('#' + div).append('<u><label id="'+ labelid + '" class="mark"></label></u>');
		$('#' + labelid).text(original);
		$('#' + labelid).css('font-size', fontsize)
		
		$('#' + div).append('<input type="text" id="'+ textid + '">');
		$('#' + textid).css('width', '100%');
		$('#' + textid).val(original);
		$('#' + textid).hide();
		
		$('#' + labelid).click(function() {
			$(this).hide();
			$('#' + textid).show();
		});
		
		$('#' + textid).keypress(function(e) {
			if (e.keyCode == 13) {
				var newText = $(this).val();
				$('#' + labelid).text(newText);
				
				$('#' + labelid).show();
				$('#' + textid).hide();
				changed(newText);
			}
		});
	}
}