;(function ($) {
    
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
	var ui = {
        uiVue: null,
        init: function () {
            this.initVue();
        },
        initVue: function () {
            var me = this;
            me.initVue = new Vue({
                el: '#uiApp',
                data: {
                    user: {
                    	firstImage: '../../jsproot/images/null.png',
                        name: '张三',
                        city: '厦门',
                        lables: []
                    }
                },
                methods: {
                	getData: function () {
                		var that = this;
                		$.ajax({
                			url:'../../clientNew/weixin/getUserInfo',
                			data: {
                				//id:GetQueryString('voteUserId')
                			},
                			success: function(result) {
                				var obj = JSON.parse(result);
	                           	 if(obj.code == "00000"){
	                           		 that.user = obj.response;
	                           	 }else{
	                           		
	                           	 }
                			}
                		})
                	},
	                meowGirl: function () {
	                	 window.location.href = 'userShowPage?netRedUserId='+'';
//	                	window.location.href = '../../jsproot/RedNet/userShow.html?netRedUserId=' + this.user.id;
	                },
	                supportMe: function () {
	                	window.location.href = 'supportMePage';
//	                	window.location.href = '../../jsproot/RedNet/supportMe.html?netRedUserId=' + this.user.id;
	                },
	                meSupport: function (){
	                	window.location.href = 'meSupportPage';
	                }
	                
	            },
             created: function () {
                 this.getData();
             }    
         });
      }
    }
    ui.init();
})(Zepto)