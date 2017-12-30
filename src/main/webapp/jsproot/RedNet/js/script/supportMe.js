;(function ($) {
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}

	var ms = {
        msVue: null,
        auiTab: null,
        init: function () {
            this.initVue();
        },
        initVue: function () {
            var me = this;
            me.msVue = new Vue({
                el: '#msApp',
                data: {
                    chackOne: true,
                    callNum: 0,
                    pNum: 0,
                    callNumPer: 0,
                    pNumPer: 0,
                    callArr: [],
                    pArr: []
                },
                methods: {
                    changeTab: function () {
                        this.chackOne = !this.chackOne;
                    },
                    getData: function () {
                    	var that = this;
                        //数据请求
                        $.ajax({
                             url: '../../clientNew/meowred/supportMeUserCount',
                             data: {
                            	 //参数
                            	 netRedUserId:GetQueryString("netRedUserId")
                             },
                             success: function (result) {
                            	 var obj = JSON.parse(result);
                            	 if(obj.code == "00000"){
                            		 that.callNum = obj.response.callCount,
                            		 that.pNum = obj.response.count,
                            		 that.callNumPer = obj.response.callCountPer,
                            		 that.pNumPer = obj.response.countPer
                            	 }else{
                            		 that.callNum = 0,
                            		 that.pNum = 0,
                            		 that.callNumPer = 0,
                            		 that.pNumPer = 0
                            	 }
                             }
                         });
                        
                        $.ajax({
                            url: '../../clientNew/meowred/supportMeUserList',
                            data: {
                           	 //参数
                           	 netRedUserId:GetQueryString("netRedUserId")
                            },
                            success: function (result) {
                           	 var obj = JSON.parse(result);
                           	 if(obj.code == "00000"){
                           		that.callArr = obj.response;
                           		that.pArr = obj.response
                           	 }else{
                           		that.callArr = [ 
                                    {
                                    	headImgUrl: './images/null.png',
                                        name: '张三',
                                        count: 20,
                                        callCount: 22
                                    }
                                ];
                           		
                           		this.pArr = [
	                                 {
	                                     img: './images/null.png',
	                                     name: '张三',
	                                     pNum: 20,
	                                     callNum: 22
	                                 }
	                             ];
                           	 }
                            }
                        });
                        
                        
                    }
                },
                created: function () {
                    this.getData();
                }
            });
        }
    };
    ms.init();
})(Zepto)