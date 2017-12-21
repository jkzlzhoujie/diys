// jqm弹出框框架限制鼠标动作
$.jqm.params.modal = true;
var domainName = "http://localhost:8091";//上传域名 

function submitStop(e){
    if (!e) var e = window.event;
    if(e.keyCode == 13)
     return false;
}

// 翻页方法
function updatePageNo(pageNo){
	document.getElementById("pageNo").value=pageNo;
}

function updatePageNoByYw(pageNo){
	document.getElementById("pageNo").value=pageNo;
	var formName = document.getElementById("formName").value;
	$("#"+formName).submit();
}

function updatePageNo2(){
	document.getElementById("pageNo").value=document.getElementById("pageNumber").value;
}

// 弹出编辑框
function edit(urlString, index) {
	$('#editor').jqm({
				ajax : "/diys/Bo/"+urlString+"?id="+ index,
				overlay : 30,
				overlayClass : 'whiteOverlay'
			}).draggable();
	$('#editor').jqmShow();
}

function editAll(urlString) {
	$('#editor').jqm({
				ajax : urlString,
				overlay : 30,
				overlayClass : 'whiteOverlay'
			}).draggable();
	$('#editor').jqmShow();
}


//弹出新增
function editNew(urlString) {
	$('#editor').jqm({
				ajax : "/diys/Bo/"+urlString,
				overlay : 30,
				overlayClass : 'whiteOverlay'
			}).draggable();
	$('#editor').jqmShow();
}


//批量编辑
function editBatch(urlString) {
	var m = document.getElementsByName('subBox');
	var val = 0;
	for (var i = 0; i < m.length; i++) {
		if (m[i].checked) {
			value = m[i].value;
			val += "," + value;
		}
	}
	if (val == 0) {
		$('#alertDiv').jqm({
					overlay : 30,
					modal : true,
					trigger : false
				});
		myAlert('请选择要操作的数据！');
	} else {
		$('#editor').jqm({
					ajax : "/diys/Bo/"+urlString+"?ids="+ val,
					overlay : 30,
					overlayClass : 'whiteOverlay'
				}).draggable();
		$('#editor').jqmShow();
	}
}

function confirm(msg, url) {
	$('#confirm').jqmShow().find('span.jqmConfirmMsg').html(msg).end()
			.find(':submit:visible').click(function() {
						if (this.value == '确定')
							window.location.href = url;
						$('#confirm').jqmHide();
					});
}

// 弹出提示框
function myAlert(msg) {
	$('#alertDiv').jqmShow().find('span.jqmConfirmMsg').html(msg).end()
			.find(':submit:visible').click(function() {
						if (this.value == '确定')
							$('#alertDiv').jqmHide();
					});
}

// 勾选复选框批量删除
function deleteBySelectIds(urlString) {
	var m = document.getElementsByName('subBox');
	var val = 0;
	for (var i = 0; i < m.length; i++) {
		if (m[i].checked) {
			value = m[i].value;
			val += "," + value;
		}
	}
	if (val == 0) {
		$('#alertDiv').jqm({
					overlay : 30,
					modal : true,
					trigger : false
				});
		myAlert('请选择要删除的数据！');
	} else {
		$('#confirm').jqm({
					overlay : 30,
					modal : true,
					trigger : false
				});
		confirm('您确定要删除所选数据吗！', urlString + val);
	}
}

//勾选复选框批量删除
function deleteBySelectIdsTo(urlString) {
	var m = document.getElementsByName('subBox');
	var val = 0;
	for (var i = 0; i < m.length; i++) {
		if (m[i].checked) {
			value = m[i].value;
			val += "," + value;
		}
	}
	if (val == 0) {
		$('#alertDiv').jqm({
					overlay : 30,
					modal : true,
					trigger : false
				});
		myAlert('请选择要删除的数据！');
	} else {
		$('#confirm').jqm({
					overlay : 30,
					modal : true,
					trigger : false
				});
		$('#confirm').jqmShow().find('span.jqmConfirmMsg').html("您确定要删除所选数据吗!").end()
		.find(':submit:visible').click(function() {
					if (this.value == '确定') {
						$.post(urlString+"?ids="+val, function() {
						document.forms[0].submit();
						})
					}
					$('#confirm').jqmHide();
			});
	}
}

// 全选框操作
function checkBox() {
	var m = document.getElementsByName('subBox');
	for (var i = 0; i < m.length; i++) {
		m[i].checked == true ? m[i].checked = false : m[i].checked = true;
	}
}

$(document).ready(function() {
	// 初始化弹出确认框
			$('#alertDiv').jqm({
						overlay : 30,
						modal : true,
						trigger : false
					});
			
			$('#loadingimg').jqm({
				trigger: '.jqModalLoading',	
				overlay: 30, 
				overlayClass: 'whiteOverlay',
				toTop:true
			}).draggable();
		});
	

// 确认框

function deleteFile(oldImgUrl){
   	if (typeof(oldImgUrl)!="undefined" && oldImgUrl!=''){
		jQuery(function(){
			$.ajax({
			url:domainName+"/delete/file",
			data:{typeUrl:oldImgUrl},
			cache:false,
			success:function(response){
				if(response=="true"){
				}
			}
			});
		});
	}
};

function del_(urlStr,id) {
	$('#confirm').jqm({
				overlay : 30,
				modal : true,
				trigger : false
			});
	$('#confirm').jqmShow().find('span.jqmConfirmMsg').html("您确定要删除所选数据吗!").end()
	.find(':submit:visible').click(function() {
				if (this.value == '确定') {
					$.post("/diys/Bo/"+urlStr+"?id="+id, function() {
					document.forms[0].submit();
					})
				}
				$('#confirm').jqmHide();
});
}
function del_new(urlStr,Str,id) {
	$('#confirm').jqm({
		overlay : 30,
		modal : true,
		trigger : false
	});
	$('#confirm').jqmShow().find('span.jqmConfirmMsg').html(Str).end()
			.find(':submit:visible').click(function() {
						if (this.value == '确定') {
							 $.post(urlStr, function(data) {
								 	$('#confirm').jqmHide();
								 	$("#"+id).submit();
								});	
						}else{
							$('#confirm').jqmHide();
						}
	});
}

function del_To(urlStr,Str,id) {
	$('#confirm').jqm({
				overlay : 30,
				modal : true,
				trigger : false
			});
	$('#confirm').jqmShow().find('span.jqmConfirmMsg').html(Str).end()
			.find(':submit:visible').click(function() {
						if (this.value == '确定') {
							$.post("/diys/Bo/"+urlStr+"?id="+id, function() {
								document.forms[0].submit();
								})
						}
						$('#confirm').jqmHide();
	});
}

 function caseSMS(urlString){
	$('#confirm').jqm({overlay: 30, modal: true, trigger: false});
	confirm('您确定要发送提醒吗？',urlString); 
}


 $(document).ready(function() { 
	 $('#loadingimg').jqm({
			trigger: '.jqModalLoading',	
			overlay: 30, 
			overlayClass: 'whiteOverlay',
			toTop:true
		}).draggable();
});
 
 function refreshCache() {
	 $('#loadingimg').jqmShow();
		$.post('/diys/Bo/product/refreshCache',function(data){
					myAlert("成功");
					$('#loadingimg').jqmHide();
				});
}
 function refreshDrCache() {
	 $('#loadingimg').jqmShow();
	 $.post('/diys/Bo/product/refreshDrCache',function(data){
		 myAlert("成功");
		 $('#loadingimg').jqmHide();
	 });
 }
 