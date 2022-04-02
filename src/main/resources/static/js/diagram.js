class DiagramCanvas {
	constructor(baseDiv) {
		this.offsetx = 20;
		this.offsety = 20;
		
		this.canvas_width = 1600;
		this.canvas_height = 1600;
		
		this.mode = 'link';
		
		this.div = baseDiv + '_diagram';
		$('#' + baseDiv).append('<div id="'+ this.div + '"></div>');
			
		this.grid_id = this.div + '_grid';
		this.link_id = this.div + '_link';
		this.tentative_id = this.div + '_tentative';
		
		var me = this;
		
		$('#' + this.div).css({'position':'relative', 'width': this.canvas_width + 'px', 'height': this.canvas_height + 'px'});
		$('#' + this.div).append('<canvas id="' + me.grid_id + '"></canvas>');
		$('#' + me.grid_id).css({'position':'absolute', 'top':'0px', 'left':'0px', 'width': this.canvas_width + 'px', 'height':this.canvas_height + 'px'});
		var gridCanvas = document.getElementById(me.grid_id);
		if (gridCanvas != null) {
			gridCanvas.width = this.canvas_width;
			gridCanvas.height = this.canvas_height;
		}
				
		$('#' + this.div).append('<canvas id="' + me.link_id + '"></canvas>');
		$('#' + me.link_id).css({'position':'absolute', 'top':'0px', 'left':'0px', 'width': this.canvas_width + 'px', 'height':this.canvas_height + 'px'});
		var linkCanvas = document.getElementById(me.link_id);
		if (linkCanvas != null) {
			linkCanvas.width = this.canvas_width;
			linkCanvas.height = this.canvas_height;
		}
		
		$('#' + this.div).append('<canvas id="' + me.tentative_id + '"></canvas>');
		$('#' + me.tentative_id).css({'position':'absolute', 'top':'0px', 'left':'0px', 'width':this.canvas_width + 'px', 'height':this.canvas_height + 'px'});
		
		var tentativeCanvas = document.getElementById(me.tentative_id);
		if (tentativeCanvas != null) {
			tentativeCanvas.width = this.canvas_width;
			tentativeCanvas.height = this.canvas_height;				
		}
		
		this.linkCtx = $("#" + me.link_id)[0].getContext("2d");
		this.linkCtx.strokeRect(0, 0, this.canvas_width, this.canvas_height);
		
		this.tentativeCtx = $("#" + me.tentative_id)[0].getContext("2d");
		
		this.selectedButtons = new Set();
		
		/// draw grid
		this.gridCtx = $("#" + me.grid_id)[0].getContext("2d");
		
		this.gridCtx.strokeStyle = 'gray';	
		
		this.buttons = [];
	}
	
	setData(containers) {
		var fontsize = 14;
		var selectedButton = null;
		var currentButton = null;
		var buttonDown = false;

		var moveStartX;
		var moveStartY;
		
		var me = this;
		
		this.gridCtx.clearRect(0, 0, me.canvas_width, me.canvas_height);
		this.gridCtx.strokeStyle = 'white';	
		this.gridCtx.strokeRect(0, 0, me.canvas_width, me.canvas_height);
		this.gridCtx.strokeStyle = 'gray';	
		this.gridCtx.setLineDash([1, containers.gridx - 1]);
//		this.gridCtx.clearRect(0, 0, me.canvas_width, me.canvas_height);	
		for (var x = 0; x < this.canvas_width; x += containers.gridx) {
			this.gridCtx.moveTo(x, 0);
			this.gridCtx.lineTo(x, this.canvas_height);
		}
		for (var y = 0; y < this.canvas_height; y += containers.gridy) {
			this.gridCtx.moveTo(0, y);
			this.gridCtx.lineTo(this.canvas_width, y);
		}
		this.gridCtx.stroke();	
		
		for (var button of this.buttons) {
			$('#' + button).remove();
		}

		this.linkCtx.clearRect(0, 0, me.canvas_width, me.canvas_height);
		
		function tentativeMove(e) {
			me.tentativeCtx.clearRect(0, 0, me.canvas_width, me.canvas_height);
			var buttonPos = selectedButton.position();
			me.tentativeCtx.strokeRect(e.clientX + buttonPos.left - moveStartX, e.clientY + buttonPos.top - moveStartY, 
				selectedButton.outerWidth(), selectedButton.outerHeight());
		}
		
		for (let data of containers.diagramContainers) {
			
			for (let id in data.items) {
				var obj = data.items[id];
				me.buttons.push(obj.id);
				
				$('#' + this.div).append('<input class="itemButton" type="submit" id="' + obj.id + '"' + ' value="' + obj.text + '">');
				
				$('#' + obj.id).mousedown(function(e) {
					buttonDown = true;
					selectedButton = $(this);
					
					moveStartX = e.clientX;
					moveStartY = e.clientY;
				});
				$('#' + obj.id).mousemove(function(e) {
					if (me.mode == 'move' && selectedButton != null) {
						tentativeMove(e);
					}
					else if (me.mode == 'link') {
						e.offsetX = $(this).position().left + e.offsetX;
						e.offsetY = $(this).position().top + e.offsetY;
						mouseMoveHandler(e);
					}
				});
				$('#' + obj.id).mouseup(function(e) {
					var id1 = selectedButton.attr('id');
				
					if (me.mode == 'link') {
						if (currentButton != null) {
							var id2 = currentButton.attr('id');
							if (id1 != id2) {
								me.onLink(id1, id2);
							}
							else {
								me.onClick(id1, $(this).attr('value'));
							}
						}
						else {
							me.onClick(id1, $(this).attr('value'));
						}
					}
					else if (me.mode == 'move') {
						positionChanged(e);
					}
					
					selectedButton = null;
				});
				$('#' + obj.id).mouseenter(function() {
					if (selectedButton != null && me.mode == 'link') {
						$(this).css("color", "red");
						currentButton = $(this);
					}
				});
				$('#' + obj.id).mouseleave(function() {
					if (selectedButton != null) {
						$(this).css("color", "black");
						currentButton = null;
					}
				});
				
				$('#' + obj.id).css("position", "absolute");
				$('#' + obj.id).css("display", "inline-block");
				$('#' + obj.id).css("left", obj.x + this.offsetx + 'px');
				$('#' + obj.id).css("top", obj.y + this.offsety + 'px');
				$('#' + obj.id).css("width", obj.width + 'px');
				$('#' + obj.id).css("height", obj.height + 'px');
				$('#' + obj.id).css("line-height", obj.height + 'px');
				$('#' + obj.id).css("font-size", "10px");
			}
			this.linkCtx.strokeRect(this.offsetx + data.x1, this.offsety + data.y1, data.x2 - data.x1, data.y2 - data.y1);
			this.linkCtx.fillText(data.name, this.offsetx + data.x1, this.offsety + data.y1 - 5);
		}

		for(let link of containers.links) {
			this.linkCtx.beginPath();

			this.linkCtx.moveTo(link.x1 + this.offsetx, link.y1 + this.offsety);
			this.linkCtx.lineTo(link.x2 + this.offsetx, link.y2 + this.offsety);
			
			this.linkCtx.closePath();
			this.linkCtx.stroke();			
		}
				
		var draggin = false;
		var rectStartx;
		var rectStarty;
		
		function mouseMoveHandler(e) {
			if (selectedButton != null) { // some button is selected
				if (me.mode == 'link') {
					me.tentativeCtx.clearRect(0, 0, me.canvas_width, me.canvas_height);
					me.tentativeCtx.beginPath();
					var buttonPos = selectedButton.position();
					var buttonMaxX = buttonPos.left + selectedButton.width();
					var buttonMaxY = buttonPos.top;
					
					var startX;
					var startY;
					var endX;
					var endY;
					if (e.offsetX > buttonMaxX) {
						startX = buttonMaxX;
					}
					else {
						startX = buttonPos.left;
					}
					endX = e.offsetX;
					me.tentativeCtx.setLineDash([5, 15]);
					me.tentativeCtx.moveTo(startX,	buttonPos.top + selectedButton.height()/2);
					me.tentativeCtx.lineTo(endX, e.offsetY);
					me.tentativeCtx.closePath();
					me.tentativeCtx.stroke();			
				}	
				else if (me.mode == 'move') {
					tentativeMove(e);
				}
			}
			else if (draggin == true) { // dragging canvas, selecting multiple buttons
				me.tentativeCtx.clearRect(0, 0, me.canvas_width, me.canvas_height);
				me.tentativeCtx.beginPath();
				me.tentativeCtx.strokeRect(rectStartx, rectStarty, e.offsetX - rectStartx, e.offsetY - rectStarty);
				
				for (let button of me.buttons) {				
					var offset = $('#' + button).position();
					var y = offset.top;
					var x = offset.left;
					var w = $('#' + button).width();
					var h = $('#' + button).height();
					
					if (rectStartx < x && rectStarty < y && e.offsetX > (x+w) && e.offsetY > (y+h)) {
						$('#' + button).css("color", "red");
						me.selectedButtons.add($('#' + button).attr('id'));
					}
					else {
						$('#' + button).css("color", "black");
					}
				}
			}
		}
		
		$('#' + this.tentative_id).mousemove(function(e) {
			mouseMoveHandler(e);
		});
				
		$('#' + this.tentative_id).mousedown(function(e) {
			draggin = true;
			me.selectedButtons.clear();
			$('.itemButton').css("color", "black");
			rectStartx = e.offsetX;
			rectStarty = e.offsetY;
		});
		
		function positionChanged(e) {
			draggin = false;
			me.tentativeCtx.clearRect(0, 0, me.canvas_width, me.canvas_height);
			
			if (me.mode == 'move' && selectedButton != null) {
				var id = selectedButton.attr('id');
				var x = e.clientX - $('#' + this.tentative_id).offset().left;
				var y = e.clientY - $('#' + this.tentative_id).offset().top;

				var buttonPos = selectedButton.position();
				x = e.clientX + buttonPos.left - moveStartX - me.offsetx;
				y = e.clientY + buttonPos.top - moveStartY - me.offsety;
				
				x = Math.round(x / containers.gridx) * containers.gridx;
				y = Math.round(y / containers.gridy) * containers.gridy;
				
				me.onMove(id, x, y, selectedButton.outerWidth(), selectedButton.outerHeight());
			}
			
			selectedButton = null;
		}
		
		$('#' + this.tentative_id).mouseup(function(e) {
			positionChanged(e);
		});
	}
		
	onClick(f) {
		this.onClick = f;
	}
	onLink(f) {
		this.onLink = f;
	}
	onMove(f) {
		this.onMove = f;
	}
	modeLink() {
		this.mode = 'link';
	}
	
	modeMove() {
		this.mode = 'move';
	}
	
	remove() {
		var me = this;
/*
		var gridCanvas = document.getElementById(me.grid_id);
		var linkCanvas = document.getElementById(me.link_id);
		var tentativeCanvas = document.getElementById(me.tentative_id);

		gridCanvas.remove();
		linkCanvas.remove();
		tentativeCanvas.remove();
*/		
//		document.getElementById(this.grid_id).remove();
//		document.getElementById(this.link_id).remove();
//		document.getElementById(this.tentative_id).remove();
		
//		$('#' + this.grid_id).remove();
//		$('#' + this.link_id).remove();
//		$('#' + this.tentative_id).remove();
		
		$('#' + this.div).remove();
//		gridCanvas = document.getElementById(me.grid_id);

//		
	}
}