cah.Ajax={};cah.Ajax.instance={};cah.ajax={};cah.ajax.ErrorHandlers={};cah.ajax.SuccessHandlers={};cah.Ajax=function(){this.pendingRequests_={};};$(document).ready(function(){cah.Ajax.instance=new cah.Ajax();$.ajaxSetup({cache:false,context:cah.Ajax.instance,error:cah.Ajax.instance.error,success:cah.Ajax.instance.done,timeout:cah.DEBUG?undefined:10*1000,type:'POST',url:cah.AJAX_URI,});});cah.Ajax.prototype.requestWithBuilder=function(builder){var jqXHR=$.ajax({data:builder.data});this.pendingRequests_[builder.getSerial()]=builder;cah.log.debug("ajax req",builder.data);if(builder.errback){jqXHR.fail(builder.errback);}};cah.Ajax.prototype.error=function(jqXHR,textStatus,errorThrown){debugger;cah.log.error(textStatus+" "+ errorThrown);};cah.Ajax.prototype.done=function(data){cah.log.debug("ajax done",data);if(data[cah.$.AjaxResponse.ERROR]){var req=this.pendingRequests_[data[cah.$.AjaxResponse.SERIAL]];if(req&&cah.ajax.ErrorHandlers[req.getOp()]){cah.ajax.ErrorHandlers[req.getOp()](data,req.data);}else{cah.log.error(cah.$.ErrorCode_msg[data[cah.$.AjaxResponse.ERROR_CODE]]);}}else{var req=this.pendingRequests_[data[cah.$.AjaxResponse.SERIAL]];if(req&&cah.ajax.SuccessHandlers[req.getOp()]){cah.ajax.SuccessHandlers[req.getOp()](data,req.data);}else if(req){cah.log.error("Unhandled response for op "+ req.getOp());}else{cah.log.error("Response for unknown serial "+ data[cah.$.AjaxResponse.SERIAL]);}}
var serial=data[cah.$.AjaxResponse.SERIAL];if(serial>=0&&this.pendingRequests_[serial]){delete this.pendingRequests_[serial];}};cah.Ajax.build=function(op){return new cah.ajax.Builder(op);};