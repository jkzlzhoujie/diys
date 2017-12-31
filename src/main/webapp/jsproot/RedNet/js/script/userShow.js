;(function ($) { 
	
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
    // 页数
    var page = 0;
    // 每页展示10个
    var size = 10;
    var us = {
        usVue: null,
        init: function () {
            this.initVue();
            this.initDropDown();
        },
        initVue: function () {
            var me = this;
            this.usVue = new Vue({
                el: '#usApp',
                data: {
                    user: {
                    	name: '喵红',
                    	lablesArr: [
                            {
                                value: '颜值',
                                color: 'blue'
                            },
                            {
                                value: '喵女郎',
                                color: 'red'
                            },
                            {
                                value: '才艺',
                                color: 'green'
                            }
                        ],
                        number: '123',
                        ranking: '0',
                        count: '0',
                        callCount: '0',
                        firstImage: ['../../jsproot/RedNet/images/null.png'],
                        imagesArr: [],
                        endorsementImg: ['../../jsproot/RedNet/images/null.png']
                    },
                    supportUser: []
                },
                methods: {
                	getData: function () {
                    	var that = this;
                        //数据请求  获取网红用户信息
                         $.ajax({
                             url: '../../clientNew/weixin/getNetRedUser',
                             data: {
                            	 //参数
                            	 netRedUserId:GetQueryString("netRedUserId")
                             },
                             success: function (result) {
                            	 var data = JSON.parse(result);
                            	 if(data != null){
                            		 that.user.id = data.id;
                            		 that.user.name = data.name;
                            		 that.user.lablesArr = data.lablesArr;
                            		 that.user.imagesArr = data.imagesArr;
                            		 that.user.firstImage = data.firstImage;
                            	 }
                             }
                         });
                         
                       //数据请求 获取用户等级
                         $.ajax({
                             url: '../../clientNew/weixin/netRedRankAndCount',
                             data: {
                            	 //参数
                            	 netRedUserId:GetQueryString("netRedUserId"),
                            	 pageNo:1,
                            	 pageSize:10
                             },
                             success: function (result) {
                            	 var data = JSON.parse(result);
                            	 if(data != null){
                            		 that.user.ranking = data.response.rank;
                            		 that.user.count = data.response.count;
                            		 that.user.callCount = data.response.callCount;
                            	 }
                             }
                         });
                         
                       /*//数据请求 获取支持网红的用户列表
                         $.ajax({
                             url: '../../clientNew/weixin/supportNetRedWeixinUserList',
                             data: {
                            	 netRedUserId:GetQueryString("netRedUserId"),
                            	 pageNo:1,
                            	 pageSize:10
                             },
                             success: function (result) {
                            	 var data = JSON.parse(result);
                            	 if(data != null){
                            		 that.supportUser = data.response;
                            	 }
                             }
                         });*/
                         
                    },
                    perfectInfo: function () {
                    	window.location.href = '../../jsproot/RedNet/userForm.html?netRedUserId=' + this.user.id;
                    },
                    goCanvassing: function () {
                        console.log('goCanvassing');
                    }
                },
                
                created: function () {
                    this.getData();
                } 
            });
        },
        initDropDown: function () {
            var that = this;
            $('.us-p-list').dropload({
                scrollArea : $('.us-p-list'),
                loadDownFn : function(me){
                    page++;
                    // 拼接HTML
                    $.ajax({
                        type: 'GET',
                        url: '../../clientNew/weixin/supportNetRedWeixinUserList?pageNo='+page+'&pageSize='+size + '&netRedUserId=' + GetQueryString("netRedUserId"),
                        success: function(data){
                        	 var obj = JSON.parse(data);
                             if(obj.response.length != 0){
                            	 debugger
                                 that.usVue.supportUser = that.usVue.supportUser.concat(obj.response);
                             // 如果没有数据
                             }else{
                            	  // 锁定
                                 me.lock();
                                 // 无数据
                                 me.noData();
                             }
                            // 为了测试，延迟1秒加载
                            setTimeout(function(){
                                // 插入数据到页面，放到最后面

                                // 每次数据插入，必须重置
                                me.resetload();
                            },1000);
                        },
                        error: function(xhr, type){
                        	// 锁定
                            me.lock();
                            // 无数据
                            me.noData();
                            // 即使加载出错，也得重置
                            me.resetload();
                        }
                    });
                }
            });
        }
    }
    us.init();
})(Zepto)