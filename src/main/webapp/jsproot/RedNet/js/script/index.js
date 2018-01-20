;(function ($) {
	$.ajax({
		url: 'getPayparam',
		data: {},
		success: function (data) {
			var result = JSON.parse(data);
	        wx.config({//测试工具  测试微信参数是否正确
	            debug: false,
	            appId: result.appId,
	            timestamp:  result.configTimestamp,	// 必填，生成签名的时间戳
	            nonceStr:  result.configNonceStr,	// 必填，生成签名的随机串
	            signature:  result.signature,// 必填，签名，见附录1
	            jsApiList: ['chooseWXPay']
	        });
		}
	});
	
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
    // 页数
    var page = 0;
    // 每页展示5个
    var size = 10;
    var serPage = 0;
    var popup = new auiPopup();
    var serLoad = null;
    var imgs = {
        hImg: ['../../jsproot/RedNet/images/h-click.png','../../jsproot/RedNet/images/h.png'],
        xImg: ['../../jsproot/RedNet/images/x.png','../../jsproot/RedNet/images/x-click.png']
    }
    function hideActionSheet(weuiActionsheet, mask) {
        weuiActionsheet.removeClass('weui_actionsheet_toggle');
        mask.removeClass('weui_fade_toggle');
        weuiActionsheet.on('transitionend', function () {
            mask.hide();
        }).on('webkitTransitionEnd', function () {
            mask.hide();
        })
    }
    var ind = {
        indVue: null,
        scroll:null,
        init: function () {
            this.initVue();
            this.initMainDropDowm();
        },
        initVue: function () {
            var me = this;
            me.indVue = new Vue({
                el: '#indApp',
                data: {
                    csNum: 0,
                    tpNum: 0,
                    fwNum: 0,
                    supporter: [],//网红列表
                    hImg: imgs.hImg[0],
                    xImg: imgs.xImg[0],
                    type: 1,//投票类型：1：鲜花，2：打CALL
                    number: 1,//打CALL数
                    searchArr: [],
                    isShow: false,
                    searchVal: '',
                    seleclUser: {
                        name: '',
                        id: '',
                        img: '',
                        thanksWord:''
                    },
                    showTPSuc: false,//投票成功弹窗
                    showTPOverSuc: false,//票数用完弹窗
                    showTPCallSuc: false,//打call成功
                    ggImg: [],
                    ggDiv: '',
                    showtext: '我要报名',
                    sta: '',
                    isShowMe: false
                    
                },
                methods: {
                	getData: function () {
                    	var that = this;

                    	$.ajax({
                            url: 'isRegister',
                            data: {
                           	 //参数
                            },
                            success: function (result) {
                            	if(result == 1){
                            		that.showtext = '我的信息';
                            		that.sta = '1';
                            		that.isShowMe = true;
                            	}
                            }
                        });
                    	

                    	$.ajax({
                            url: 'getGameBanner',
                            data: {
                           	 //参数
                            },
                            success: function (result) {
                            	that.ggImg = JSON.parse(result);
                        		setTimeout(function () {
                                    var swiper = new Swiper('.swiper-container', {
                                        paginationClickable: true,
                                        speed: 300,
                                        loop: true,
                                        observer:true,
                                        observeParents:true,
                                        pagination: '.swiper-pagination',
                                        autoplayDisableOnInteraction : false,
                                        autoplay:1000
                                    });
                        		},300)
                            }
                        });
                    	
                    	$.ajax({
                            url: 'getGameIntroduce',
                            data: {
                           	 //参数
                            },
                            success: function (result) {
                            	that.ggDiv = result.substring(1,result.length - 1);
                            }
                        });
                    	
                    	
                    	
                    	$.ajax({
                            url: 'supperCount',
                            data: {
                           	 //参数
                            },
                            success: function (result) {
                           	 var obj = JSON.parse(result);
                           	 if(obj != null){
                           		 that.csNum =  obj.personCount;
                               	 that.tpNum =  obj.supperCount;
                               	 that.fwNum =  obj.visitCount
                           	 }else{
                           		 that.csNum =  0;
                               	 that.tpNum =  0;
                               	 that.fwNum =  0
                           	 }
                            }
                        });
                        
                    },
                    sendFab: function (id,name, img, thanksWord) {
                    	this.type = 1;
                        this.hImg = imgs.hImg[0];
                        this.xImg = imgs.xImg[0];
                        this.seleclUser.id = id;
                        this.seleclUser.name = name;
                        this.seleclUser.img = img;
                        this.seleclUser.thanksWord = (function () {
                        	var str = '亲，谢谢您为我打CALL哦！';
                        	if (thanksWord != null && thanksWord != '') {
                        		return thanksWord;
                        	} else {
                        		return str;
                        	}
                        })();
                        this.showTP();
                    },
                    selLw: function (n) {
                        if (n == 1) {
                            this.hImg = imgs.hImg[0];
                            this.xImg = imgs.xImg[0];
                        } else {
                            this.hImg = imgs.hImg[1];
                            this.xImg = imgs.xImg[1];
                        }
                        this.type = n;
                    },
                    
                    showInfo: function (id) {
                    	 //查看个人网红信息
                    	 window.location.href = 'netRedUserShowPage?netRedUserId='+id;
                    },
                    
                    sendInfo: function (id) {//支持投票
                    	var that = this;
                        var mask = $('#mask');
                        var price = 0;
                        var weuiActionsheet = $('#weui_actionsheet');
                        hideActionSheet(weuiActionsheet, mask);
                        var params = {};
                        if (this.type == 2) {
                        	price = this.number; 
                        	if(price == 0){
                        		alert('打call数不能少于1个');
                        		return ;
                        	} 
                        	params = {
                        		netRedUserId: id,
                        		count: this.number,
                        		type: this.type
                            }
                        	onBridgeReady(price, function () {
//                              支持投票
                              $.ajax({
                              	url: 'userVote',
                              	data: params,
                              	success: function (result) {
                              		var obj = JSON.parse(result);
      	                           	 if(obj.code == "success"){
      	                           		 $.each(that.supporter, function (k, o) {
      	                           			 if (o.id == id) {
      	                           				 var count = parseInt(o.count);
      	                           				 if (that.type == 2) {
      	                           					that.supporter[k]['count'] = count + (price * 10);
      	                           				 } else {
      	                           					that.supporter[k]['count'] = count + 1;
      	                           				 }
      	                           			 }
      	                           		 });
      	                           		 $.each(that.searchArr, function (k, o) {
      	                           			 if (o.id == id) {
      	                           				 var count = parseInt(o.count);
      	                           				 if (that.type == 2) {
      	                           					that.searchArr[k]['count'] = count + (price * 10);
      	                           				 } else {
      	                           					that.searchArr[k]['count'] = count + 1;
      	                           				 }
      	                           			 }
      	                           		 });
      	                           		if (that.type == 2) {
      	                                    $('#showTPCallSuc').show();
      	                           		}else{
      	                                    $('#showTPSuc').show();
      	                           		}
      	                           	 }else if(obj.code == "moreFive"){
      	                                 $('#showTPOverSuc').show();
      	                           	 }else{
      	                           		 alert(obj.desc);
      	                           	 }
                              	}
                              })
                        	});
                        	return;
                        }
                    	params = {
                    		netRedUserId: id,
                    		count: 1,
                    		type: this.type
                        }
//                        支持投票
                        $.ajax({
                        	url: 'userVote',
                        	data: params,
                        	success: function (result) {
                        		var obj = JSON.parse(result);
	                           	 if(obj.code == "success"){
	                           		 $.each(that.supporter, function (k, o) {
	                           			 if (o.id == id) {
	                           				 var count = parseInt(o.count);
	                           				 if (that.type == 2) {
	                           					that.supporter[k]['count'] = count + (price * 10);
	                           				 } else {
	                           					that.supporter[k]['count'] = count + 1;
	                           				 }
	                           			 }
	                           		 });
	                           		 $.each(that.searchArr, function (k, o) {
	                           			 if (o.id == id) {
	                           				 var count = parseInt(o.count);
	                           				 if (that.type == 2) {
	                           					that.searchArr[k]['count'] = count + (price * 10);
	                           				 } else {
	                           					that.searchArr[k]['count'] = count + 1;
	                           				 }
	                           			 }
	                           		 });
	                           		if (that.type == 2) {
	                                    $('#showTPCallSuc').show();
	                           		}else{
	                                    $('#showTPSuc').show();
	                           		}
	                           	 }else if(obj.code == "moreFive"){
	                                 $('#showTPOverSuc').show();
	                           	 }else{
	                           		 alert(obj.desc);
	                           	 }
                        	}
                        })
                    },
                    add: function () {
                        this.number += 1;
                    },
                    reduce: function () {
                        this.number -= 1;
                        if (this.number < 0) {
                            this.number = 0;
                        }
                    },
                    cancelSer: function () {
                        this.searchVal = '';
                        this.isShow = false;
                    },
                    clearVal: function () {
                        this.searchVal = '';
                    },
                    showPop: function () {
                        this.isShow = true;
                        this.searchArr = [];
                    },
                    showTP: function() {
                        var mask = $('#mask');
                        var weuiActionsheet = $('#weui_actionsheet');
                        weuiActionsheet.addClass('weui_actionsheet_toggle');
                        mask.show().addClass('weui_fade_toggle').one('click', function () {
                            hideActionSheet(weuiActionsheet, mask);
                        });
                        $('#actionsheet_cancel').one('click', function () {
                            hideActionSheet(weuiActionsheet, mask);
                        });
                        weuiActionsheet.unbind('transitionend').unbind('webkitTransitionEnd');
                    },
                    wtdc: function () {
                        this.showTP();
                        $('#showTPOverSuc').hide();
                        $('#showTPSuc').hide();
                        $('#showTPCallSuc').hide();
                    },
                    gbtpcg: function () {
                        $('#showTPOverSuc').hide();
                        $('#showTPSuc').hide();
                        $('#showTPCallSuc').hide();
                    },
                    wybm: function (sta) {//我要报名
                    	if (sta == true){
	       	            	 window.location.href = '../../clientNew/weixin/userShowPage?netRedUserId='+'';
	       	            } else {
	       	            	window.location.href = 'signUpinfoPage';
	       	            }
                    	                    }
                },
                created: function () {
                    this.getData();
                }
            });
        },
        initMainDropDowm: function () {
            var that = this;
            $('#indApp').dropload({
                scrollArea : $('#indApp'),
                loadDownFn : function(me){
                    page++;
                    // 拼接HTML
                    $.ajax({
                        type: 'GET',
                        url: 'findNetRedListPage?pageNo='+page+'&pageSize='+size,
                        success: function(data){
                        	var obj = JSON.parse(data);
                            if(obj){
                            	if (obj.list.length > 0){
                                	that.indVue.supporter = that.indVue.supporter.concat(obj.list);
                            	}else {
                                    // 锁定
                                    me.lock();
                                    // 无数据
                                    me.noData();
                            		
                            	}
                            // 如果没有数据
                            }else{
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
        initPopDropDown: function () {
        	serPage++;
            var that = this;
            var searcheContent = '';
            if(that.indVue.searchVal ){
          	  searcheContent = that.indVue.searchVal;
            }
            $.ajax({
                type: 'GET',
                url: 'findNetRedListPage?pageNo='+serPage+'&pageSize='+size+'&content=' + searcheContent,
                success: function(data){
                	var obj = JSON.parse(data);
                    if(obj){
                    	if (obj.list.length > 0){
                    		that.indVue.searchArr = [];
                        	that.indVue.searchArr = that.indVue.searchArr.concat(obj.list);
                    	}else {
	//                    		
                    	}
                    // 如果没有数据
                    }else{
                    }
                    // 为了测试，延迟1秒加载
                    setTimeout(function(){
                        // 插入数据到页面，放到最后面

                        // 每次数据插入，必须重置
//                        me.resetload();
                    },1000);
                },
                error: function(xhr, type){
//                	 // 锁定
//                    me.lock();
//                    // 无数据
//                    me.noData();
//                    // 即使加载出错，也得重置
//                    me.resetload();
                }
            });
        }
    }
    ind.init();
    $(document).on('keydown',function (e) {
    	if (e.keyCode == 13) {
    		serPage = 0;
	          setTimeout(function () {
	        	  ind.initPopDropDown();
		      }, 300);
    	}
    })
})(Zepto)