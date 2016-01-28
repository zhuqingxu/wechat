// 格式化日期
function formatDate(val,row) {
	if(val != null && val.year != null && val.month != null && val.date != null) {
		var year = parseInt(val.year) + 1900;
		
		var month = (parseInt(val.month) + 1);
		month = month > 9 ? month : ('0' + month);
		
		var date = parseInt(val.date);
		date = date > 9 ? date:('0' + date);
		
		var time = year + '-' + month + '-' + date;
		
		return time;
	} else {
		return "";
	}
}
 
// 格式化时间
function formatTime(val,row) {
	if(val != null && val.year != null && val.month != null && val.date != null) {
		var year = parseInt(val.year) + 1900;
		
		var month = (parseInt(val.month) + 1);
		month = month > 9 ? month : ('0' + month);
		
		var date = parseInt(val.date);
		date = date > 9 ? date:('0' + date);
		
		var hours=parseInt(val.hours);
		hours=hours>9?hours:('0'+hours);
		
		var minutes=parseInt(val.minutes);
		minutes=minutes>9?minutes:('0'+minutes);
		
		var seconds=parseInt(val.seconds);
		seconds=seconds>9?seconds:('0'+seconds);
		
		var time = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes + ':' + seconds;
		
		return time;
	} else {
		return "";
	}
}

// 格式化状态
function formatStatus(val, row){
	if(val == 'enabled') {
		return "有效";
	} else if(val == 'disabled') {
		return "无效";
	}
}

// 格式化悬浮提示
function formatTip(val, row){
	if(val){
		return '<a title="'+val+'" class="easyui-tooltip">'+ val +'</a>';
	}
}
