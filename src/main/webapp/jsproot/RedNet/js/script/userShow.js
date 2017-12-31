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
                        votesNum: '0',
                        callNum: '0',
                        firstImage: ['../../jsproot/RedNet/images/null.png'],
                        imagesArr: [],
                        endorsementImg: ['../../jsproot/RedNet/images/null.png']
                    },
                    supportUser: [
                        {
                        	headImgUrl: '../../jsproot/RedNet/images/null.png',
                        	voteUserUserNickName: 'ffff',
                        	typeName: 123,
                        	createTimeStr: 234
                        },
                        {
                        	headImgUrl: '../../jsproot/RedNet/images/null.png',
                        	voteUserUserNickName: 'ffff',
                        	typeName: 123,
                        	createTimeStr: 234
                        }
                    ]
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
                            		 that.user.name = data.name;
                            		 that.user.lablesArr = data.lablesArr;
                            		 that.user.imagesArr = data.imagesArr;
                            		 that.user.firstImage = data.firstImage;
                            	 }
                             }
                         });
                         
                       //数据请求 获取支持网红的用户列表
                         $.ajax({
                             url: '../../clientNew/weixin/supportNetRedWeixinUserList',
                             data: {
                            	 //参数
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
                         });
                    },
                    perfectInfo: function () {
                        console.log('perfectInfo');
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
                        url: 'http://ons.me/tools/dropload/json.php?page='+page+'&size='+size,
                        dataType: 'json',
                        success: function(data){
                            if(true){
                                that.usVue.supportUser.push({
                                    img: '../../jsproot/RedNet/images/null.png',
                                    name: 'ffff',
                                    callNum: 123,
                                    pNum: 234
                                });
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