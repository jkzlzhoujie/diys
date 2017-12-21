    
$(function() {
	
	$("#sendIm").hide();
	$("#sendText").hide();
	$("#sendPic").hide();
	
	var conn = new WebIM.connection({
	  https: WebIM.config.https,
	  url: WebIM.config.xmppURL,
	  isAutoLogin: WebIM.config.isAutoLogin,
	  isMultiLoginSessions: WebIM.config.isMultiLoginSessions
	});

	//登录 IM
	var login = function() {
		var userName = $("#userName").val();
		var password = $("#password").val();
		if(userName == "" || password == ""){
			alert("账号或者密码不能为空");
			return ;
		}
        var username = userName;
        var pass = password;
        var options = { 
		   apiUrl: WebIM.config.apiURL,
		   user: username,
		   pwd: pass,
		   appKey: WebIM.config.appkey
		};  
		conn.open(options);
		conn.listen({
			   onOpened: function ( message ) {          //连接成功回调
			    //如果isAutoLogin设置为false，那么必须手动设置上线，否则无法收消息
				alert("登录成功");
				$("#sendIm").show();
				$("#sendText").show();
				$("#sendPic").show();
				
				
				$("#loginUserName").hide();
				$("#loginPassword").hide();
				$("#loginIm").hide();
				
			    conn.setPresence();  
			    return;
			  }
		});
   };
	
	//发送文本消息
	 var sendText = function(){
		    var msgContent = $("#textMess").val();
		    var to =  $("#userId").val();
			var id = conn.getUniqueId();//生成本地消息id
			var msg = new WebIM.message('txt', id);//创建文本消息
			msg.set({
			  msg: msgContent,
			  to: "heh565737",
			  success: function ( ) {
				//消息发送成功回调   
			//	$("#textMess").val("");
				alert("消息发送成功！");
			   }
			}); 
			conn.send(msg.body);	
			
	 };
	 
	//发送图片消息
	var sendPic = function(){
		 var sendTo =  $("#userId").val();
		 var input = document.getElementById('pictureInputMess');//选择的图片
		 var id = conn.getUniqueId();
		 var msg = new WebIM.message('img', id);
		 var file = WebIM.utils.getFileUrl(input);
		 var allowType = {
		   "jpg": true,
		   "gif": true,
		   "png": true,
		   "bmp": true
		 };
		  
		 if ( file.filetype.toLowerCase() in allowType ) {
		   msg.set({
		     apiUrl: WebIM.config.apiURL,
		     file: file,
		     to: sendTo,
		     onFileUploadError: function ( error ) {},//图片上传失败
		     onFileUploadComplete: function ( data ) {
		       //图片地址：data.uri + '/' + data.entities[0].uuid;
		     },
		     success: function ( ) {
					alert("图片消息发送成功！");
			  },
		     flashUpload: WebIM.flashUpload
		  });
		}
		 conn.send(msg.body);
	 } ;
		 
	 
	 
	 //发送消息
	 $("#send").on('click', function() {
		 var msgContent = $("#textMess").val();
		 var picInput = document.getElementById('pictureInputMess');//选择的图片
		 var value = $("#pictureInputMess").attr("src");
		 if(msgContent.length == 0 && value == null){
			 alert("消息内容和图片不能同时为空");
			 return ;
		 }
		 if(msgContent != null ){
			 sendText();
		 }
		 if( picInput != null){
			 sendPic();
		 }
	 });
	 
	 //登录环信
	 $("#login").on('click', function() {
		 login();
	 });
	 
//	 conn.close();

	
		
});