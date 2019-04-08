/**
 * jquery form fill
 */
(function($){
	function Fill() {
		this.defaults = {
			styleElementName: 'none',	// object | none
			dateFormat: 'mm/dd/yy',
			debug: false,
			elementsExecuteEvents: ['checkbox', 'radio', 'select-one']
		};
	};
	$.extend(Fill.prototype, {
		clear : function (obj, _element) {
			_element.find("*").each(function(i, item){
				if ($(item).is("input") || $(item).is("select") || $(item).is("textarea")) {
					try {
						switch ($(item).attr("type")) {
							case "hidden":
							case "password":
							case "textarea":
							case "text":
								$(item).val('');
								break;
							case "select":
								$(item).get(0).selectedIndex=0;//选中第一个
								break;
							case "radio":
							case "checkbox":
								$(item).attr("checked", false);
								break;
							}
						} catch(e) {}
				}
			});
		},
		
		fill : function (obj, _element, settings) {
			this.clear(obj,_element);
			options = $.extend({}, this.defaults, settings);
			_element.find("*").each(function(i, item){
				if ($(item).is("input") || $(item).is("select") || $(item).is("textarea")) {
					try {
						var objName=null;
						var arrayAtribute=null;
						var value=null;
						try {
							if (options.styleElementName == "object") {
								if ($(item).attr("name").match(/\[[0-9]*\]/i)) {
									objName = $(item).attr("name").replace(/^[a-z]*[0-9]*[a-z]*\./i, 'obj.').replace(/\[[0-9]*\].*/i, "");
									arrayAtribute = $(item).attr("name").match(/\[[0-9]*\]\.[a-z0-9]*/i) + "";
									arrayAtribute = arrayAtribute.replace(/\[[0-9]*\]\./i, "");
								} else {
									objName = $(item).attr("name").replace(/^[a-z]*[0-9]*[a-z]*\./i, 'obj.');
								}
							} else if (options.styleElementName == "none") {
								objName = 'obj.' + $(item).attr("name");
							}
							value = eval(objName);
						} catch(e) {
							if (options.debug) {
								debug(e.message);
							}
						}					
						if (value != null) {
							var _type = $(item).attr("type");
							if(_type == "hidden" || _type == "password"){
								$(item).val(value);
							}else if(_type == "text"){
								if ($(item).hasClass("hasDatepicker")) {
									var re = /^[-+]*[0-9]*$/;
									var dateValue = null;
									if (re.test(value)) {
										dateValue = new Date(parseInt(value));
										var strDate = dateValue.getUTCFullYear() + '-' + (dateValue.getUTCMonth() + 1) + '-' + dateValue.getUTCDate();
										dateValue = $.datepicker.parseDate('yy-mm-dd', strDate);
									} else if (value) {										
										dateValue = $.datepicker.parseDate(options.dateFormat, value);
									}
									$(item).datepicker('setDate', dateValue);							
								} else if ($(item).attr("alt") == "double") {
									$(item).val(value.toFixed(2));
								} else {
									$(item).val(value);
								}
							}else if(_type == "select-one"){
								if (value) {
									$(item).val(value);
								}
							}else if(_type == "radio"){
								$(item).each(function (i, radio) {
									if ($(radio).val() == value) {
										$(radio).attr("checked", "checked");
									}
								});
							}else if(_type == "checkbox"){
								if ($.isArray(value)) {
									$.each(value, function(i, arrayItem) {
										if (typeof(arrayItem) == 'object') {											
											arrayItemValue = eval("arrayItem." + arrayAtribute);
										} else {
											arrayItemValue = arrayItem;
										}
										if ($(item).val() == arrayItemValue) {
											$(item).attr("checked", "checked");
										}
									}); 
								} else {
									if ($(item).val() == value) {
										$(item).attr("checked", "checked");
									}
								}		
							}else{
								$(item).val(value);
							}
						}
					} catch(e) {
						if (options.debug) {
							debug(e.message);
						}
					}
				}
			});
		}
	});
	$.fn.fill = function (obj, settings) {
		$.fill.fill(obj, $(this), settings);
		return this;
	};
	$.fn.clear = function (obj) {
		$.fill.clear(obj, $(this));
		return this;
	};
	$.fill = new Fill();
})(jQuery);

/**
 * 提示modal - start
 */
OcModal = function (){};
OcModal.prototype = {
	// 成功提示
	tipSuccess : function(msg, id) {
		this._tip('success', msg, id);
	},
	
	// 失败提示
	tipFailure : function(msg ,id) {
		this._tip('failure', msg, id);
	},
	
	// 提示
	_tip : function(type, msg, id){
		if(id == undefined){
			id = '_ocAlertTip';
		}
		var ocTip = $('#'+id);
		ocTip.html(msg);
		ocTip.show();
		if(type == 'failure'){
			ocTip.attr('class','alert alert-warning');
		}else{
			ocTip.attr('class','alert alert-success');
		}
	},
	
	//alert 弹出提示
	alert : function(msg,callback){
		this._dialog('alert',msg,callback);
	},
	
	//confirm 弹出提示
	confirm : function(msg,okCallback,cancelCallback){
		this._dialog('confirm',msg,okCallback,cancelCallback);
	},
	
	//公共弹出提示
	_dialog : function(type,msg,okCallback,cancelCallback){
		var _ocDialog = $('#_ocDialogModal');
		_ocDialog.find('.oc-content').html(msg);
		_ocDialog.modal('show');
		
		if(type == 'alert'){
			_ocDialog.find('.oc-cancel').hide();
			_ocDialog.find('.oc-ok').click(function() {
				if(okCallback){
					okCallback(true);
				}
				_ocDialog.modal('hide');
			});
		}else if(type == 'confirm'){
			_ocDialog.find('.oc-cancel').show();
			_ocDialog.find('.oc-ok').click(function() {
				if(okCallback){
					okCallback(true);
				}
				_ocDialog.modal('hide');
			});
			_ocDialog.find('.oc-cancel').click(function() {
				if(cancelCallback){
					cancelCallback(true);
				}
				_ocDialog.modal('hide');
			});
		}
	},
	
	//根据id show dialog
	show : function(id){
		if(id){
			$('#' + id).find('form').each(function(i,item){
				$(item).clear();
			});
			$('#' + id).modal('show');
			$('#_ocAlertTip').hide();
		}
	},
	
	hide : function(id){
		$('#' + id).modal('hide');
	},
	
};
Modal = new OcModal();

/**
 * 提示modal - end
 */

/**
 * 通用类
 */



