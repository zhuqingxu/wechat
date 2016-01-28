// DateGrid扩展
$.extend($.fn.datagrid.defaults.editors, {
	// 增加对my97日期控件的支持
	my97date : {
		init : function(container, options) {
			var input = $('<input class="Wdate" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',qsEnabled:false,readOnly:true});" />').appendTo(container);
			return input;
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width);
			}
		}
	},
	// 增加对选取数据字典的支持
	dictionary : {
		init : function(container, options) {
			var wid = options.wid;
			var url = options.url;
			var input = $('<input type="text" class="datagrid-editable-input" onclick="selectDictionary(\'' + wid + '\',\'' + url + '\');" readonly="true" />').appendTo(container);
			return input;
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width);
			}
		}
	},
	// 增加对下拉列表的支持
	selectBox : {
		init : function(container, options) {
			var ol = options.list;
			var o='';
			for(var i=0;i<ol.length;i++){
				o=o+'<option value='+ol[i]+'>'+ol[i]+'</option>';
			}
			var input = $('<select>'+o+'</select>').appendTo(container);			
			return input;
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width);
			}
		}
	}
});

// 选取数据字典
function selectDictionary(id, url){
	var height=document.documentElement.clientHeight;
	var width=document.documentElement.clientWidth;
	
    $(id).window({
    	width:width*0.9,
    	height:height*0.9,
		draggable:true,
		resizable:false,
		shadow:false,
		collapsible:false,
		minimizable:false,
		maximizable:false,
		content:'<iframe src="' + url + '" width="100%" height="100%" scrolling="auto" frameborder="no"></iframe>'
	});
	$(id).window('open');
}

// 创建Tab页
function cteateTab(title, url){	
    if ($('#tabs').tabs('exists', title)){
        $('#tabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:98%;height:98%;padding: 5px;"></iframe>';
        $('#tabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}