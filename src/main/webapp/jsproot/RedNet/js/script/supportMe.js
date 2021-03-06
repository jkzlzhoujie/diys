;(function ($) {
	
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
    // 页数
    var page = 0;
    var pageTwo = 0;
    // 每页展示5个
    var size = 5;
    var ms = {
        msVue: null,
        auiTab: null,
        isOne: false,
        init: function () {
            this.initVue();
            this.loadCallList();
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
                    changeTab: function (n) {
                        this.chackOne = !this.chackOne;
                        if (n == 1) {
                            $('.call-list').show();
                            $('.piao-list').hide();
                        } else {
                            $('.call-list').hide();
                            $('.piao-list').show();
                        }
                        if (!me.isOne) {
//                            setTimeout(function () {
                                me.loadPiaoList();
//                            }, 500);
                        }
                        me.isOne = true;
                    },
                    getData: function () {
                    	var that = this;
                        //数据请求
                        $.ajax({
                             url: '../../clientNew/weixin/supportMeUserCount',
                             data: {
                            	 //参数
//                            	 netRedUserId:GetQueryString("netRedUserId")
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
                    }
                },
                created: function () {
                    this.getData();
                }
            });
        },
        loadCallList: function () { 
            var that = this;
            $('.call-list').dropload({
                scrollArea : $('.call-list'),
                loadDownFn : function(me){
                    page++;
                    // 拼接HTML
                    $.ajax({
                        type: 'GET',
                        url: '../../clientNew/weixin/supportMeUserList?type=2' + '&pageNo='+page+'&pageSize='+size,
                        success: function(result){
                        	var obj = JSON.parse(result);
                          	 if(obj.response.length >0){
                          		that.msVue.callArr = that.msVue.callArr.concat(obj.response);
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
        },
        loadPiaoList: function () { 
        	var that = this;
            $('.piao-list').dropload({
                scrollArea : $('.piao-list'),
                loadDownFn : function(me){
                	debugger
                	pageTwo++;
//                     拼接HTML
                    $.ajax({
                        type: 'GET',
                        url: '../../clientNew/weixin/supportMeUserList?type=1' + '&pageNo='+pageTwo+'&pageSize='+size,
                        success: function(data){
                        	var obj = JSON.parse(data);
                         	 if(obj.response.length >0){
                           		that.msVue.pArr = that.msVue.pArr.concat(obj.response);
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
    };
    ms.init();
})(Zepto)