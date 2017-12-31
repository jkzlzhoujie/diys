;(function ($) {
	
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
    // 页数
    var page = 0;
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
                    callNum: 123,
                    pNum: 454,
                    callArr: [],
                    pArr: []
                },
                methods: {
                    changeTab: function () {
                        this.chackOne = !this.chackOne;
                        if (!me.isOne) {
                            setTimeout(function () {
                                me.loadPiaoList();
                            }, 300);
                        }
                        me.isOne = true;
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
                        url: 'http://ons.me/tools/dropload/json.php?page='+page+'&size='+size,
                        dataType: 'json',
                        success: function(data){
                            debugger
                            if(true){
                                that.msVue.callArr.push({
                                    img: '../../jsproot/RedNet/images/null.png',
                                    name: '张三',
                                    pNum: 20,
                                    callNum: 22
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
                            alert('Ajax error!');
                            // 即使加载出错，也得重置
                            me.resetload();
                        }
                    });
                }
            });
        },
        loadPiaoList: function () { var that = this;
            $('.piao-list').dropload({
                scrollArea : $('.piao-list'),
                loadDownFn : function(me){
                    page++;
                    // 拼接HTML
                    $.ajax({
                        type: 'GET',
                        url: 'http://ons.me/tools/dropload/json.php?page='+page+'&size='+size,
                        dataType: 'json',
                        success: function(data){
                            debugger
                            if(true){
                                that.msVue.pArr.push(
                                    {
                                        img: '../../jsproot/RedNet/images/null.png',
                                        name: '张三',
                                        pNum: 20,
                                        callNum: 22
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
                            alert('Ajax error!');
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