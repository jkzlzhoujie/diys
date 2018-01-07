var parentDom = $('#usTop'),
pW = parentDom.width(),
pH = parentDom.height(),
numId = 0;
function init(opt) {
    var msgDom = $('<div class="dm-mian" id="dm' + numId + '">' +
                    '<img src="' + opt.img + '" alt="" class="dm-head">'+
                    '<div class="dm-con">' + opt.msg + '</div>'+
                '</div>');
    var lcW = pW;
    msgDom.css('top', Math.floor(Math.random() * (pH - 30)) + 'px');
    if (parentDom.children().last().hasClass('dm-mian')) {
        var l = parentDom.children().last();
        // console.log(l.offset().left + '-' + lcW);
        if ((l.offset().left + l.width()) > lcW) {
            lcW += ((l.offset().left + l.width()) - lcW);
        }
    }
    msgDom.css('transform', 'translateX(' + lcW + 'px)');
    $('#usTop').append(msgDom);
    (function (n, ln) {
        var lnum = ln;
        var tim = setInterval(function() {
            lnum--;
            if (lnum <= (0 - $('#dm' + n).width())) {
                clearInterval(tim);
                $('#dm' + n).remove();
            }
            $('#dm' + n).css('transform', 'translateX(' + lnum + 'px)');
        }, 20);
    })(numId, lcW);
    numId++;
}

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
                    	id: 0,
                    	name: '喵红',
                    	lablesArr: [],
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
//                            	 netRedUserId:GetQueryString("netRedUserId")
                             },
                             success: function (result) {
                            	 if(result == null  || result == ''){
                            		 alert("登录超时，请退出重新进入！");
                            	 }else{
                            		 var data = JSON.parse(result);
                                	 if(data != null){
                                		 var labels = [];
                                		 $.each(data.lablesArr, function (k, o) {
                                			 switch (o) {
                                			 	case 'cyVal':
                                			 		labels.push({
                                			 			color: 'blue',
                                			 			value: '才艺'
                                			 		})
                                			 		break;
                                			 	case 'yzVal':
                                			 		labels.push({
                                			 			color: 'red',
                                			 			value: '颜值'
                                			 		})
                                			 		break;
                                			 	case 'ssVal':
                                			 		labels.push({
                                			 			color: 'green',
                                			 			value: '时尚达人'
                                			 		})
                                			 		break;
                                			 	case 'mzVal':
                                			 		labels.push({
                                			 			color: 'yellow',
                                			 			value: '美妆达人'
                                			 		})
                                			 		break;
                                			 	case 'gxVal':
                                			 		labels.push({
                                			 			color: 'blink',
                                			 			value: '搞笑达人'
                                			 		})
                                			 		break;
                                			 	case 'msVal':
                                			 		labels.push({
                                			 			color: 'ls',
                                			 			value: '美食达人'
                                			 		})
                                			 		break;
                                			 	case 'yxVal':
                                			 		labels.push({
                                			 			color: 'ygl',
                                			 			value: '游戏达人'
                                			 		})
                                			 		break;
                                			 	case 'qtVal':
                                			 		labels.push({
                                			 			color: 'gh',
                                			 			value: '其他'
                                			 		})
                                			 		break;
                                			 }
                                		 })
                                		 that.user.id = data.id;
                                		 that.user.name = data.name;
                                		 that.user.lablesArr = labels;
                                		 that.user.firstImage =  data.firstImage;
                            			 that.user.imagesArr = data.imagesArr;
                                	 }
                            	 }
                            	 
                             }
                         });
                         
//                       //数据请求 获取用户等级
                         $.ajax({
                             url: '../../clientNew/weixin/netRedRankAndCount',
                             data: {
                            	 //参数
//                            	 netRedUserId:GetQueryString("netRedUserId"),
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
                         
                    },
                    perfectInfo: function () {
                    	window.location.href = '../../clientNew/weixin/userFormPage';
//                    	window.location.href = '../../jsproot/RedNet/userForm.html?netRedUserId=' + this.user.id;
                    },
                    goCanvassing: function () {
                        console.log('goCanvassing');
                    }
                },
                
                created: function () {
                    //弹幕：执行一次弹出一个消息
                    init({
                        img: './images/null.png',
                        msg: '喵网红'
                    });
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
                        url: '../../clientNew/weixin/supportNetRedWeixinUserList?pageNo='+page+'&pageSize='+size,
                        success: function(data){
                        	 var obj = JSON.parse(data);
                             if(obj.response.length != 0){
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