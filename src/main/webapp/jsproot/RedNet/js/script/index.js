;(function ($) {
    

    //页数 
    var page = 0;
    // 每页展示10个
    var size =10;
    var popup = new auiPopup();
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
            this.initScroll();
        },
        initVue: function () {
            var me = this;
            me.indVue = new Vue({
                el: '#indApp',
                data: {
                    csNum: 123,
                    tpNum: 545,
                    fwNum: 789,
                    supporter: [],//网红列表
                    hImg: imgs.hImg[0],
                    xImg: imgs.xImg[0],
                    type: 1,//投票类型：1：鲜花，2：打CALL
                    number: 0,//打CALL数
                    searchArr: [],
                    isShow: false,
                    searchVal: ''
                },
                methods: {
                    getData: function () {
                    	var that = this;
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
                        
                        $.ajax({
                            url: 'findNetRedListPage',
                            data: {
                           	 //参数
                           	 pageSize:10,
                           	 pageNo:1,
                           	 content: this.searchVal,
                            },
                            success: function (result) {
	                           	 var obj = JSON.parse(result);
	                           	 if(obj != null){
	                           		 debugger
	                           		that.supporter = obj.list;
	                           	 }else{
	                           		this.supporter = [
	                                  {
                                  		  firstImage: '../../jsproot/RedNet/images/null.png',
	                                      name: '张三',
	                                      count: 20,
	                                      id: 22
	                                  },
	                                  {
	                                  	  firstImage: '../../jsproot/RedNet/images/null.png',
	                                      name: '李四',
	                                      count: 20,
	                                      id: 22
	                                  }
	                                ]
	                           	 }
                            }
                        });
                        
                        
                    },
                    sendFab: function () {
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
                    sendInfo: function () {
                        var mask = $('#mask');
                        var weuiActionsheet = $('#weui_actionsheet');
                        hideActionSheet(weuiActionsheet, mask);
                        //支持投票
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
                        this.isShow = false;
                    },
                    clearVal: function () {
                        this.searchVal = '';
                    },
                    showPop: function () {
                        this.isShow = true;
                    }
                },
                created: function () {
                    this.getData();
                }
            });
        },
        initScroll: function () {
            var me = this;
            me.scroll = new auiScroll({
                listen: true,
                distance: 20
            },function(ret){
                //数据请求
                // $.ajax({
                //     url: '',
                //     data: {
                //参数
                //     },
                //     success: function () {

                //     }
                // });
               console.log(ret)
            });
        }
    }
    ind.init();
    $(document).on('keydown',function (e) {
    	if (e.keyCode == 13) {
    		debugger
    		// $.ajax({
            //     url: '',
            //     data: {
            //参数
            //     },
            //     success: function () {

            //     }
            // });
    	}
    	
    })
    //获取搜索数据
    $('.i-search-info').dropload({
        scrollArea : window,
        autoLoad : true,//自动加载
        domDown : {//上拉
            domClass   : 'dropload-down',
            domRefresh : '<div class="dropload-refresh f15 "><i class="icon icon-20"></i>上拉加载更多</div>',
            domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>',
            domNoData  : '<div class="dropload-noData">没有更多数据了</div>'
        },
        loadDownFn : function(me){//加载更多    分页加载
            // page++;
            // window.history.pushState(null, document.title, window.location.href);
            // var result = '';
            // $.ajax({
            //     type: 'GET',
            //     url:'',
            //     dataType: 'json',
            //     success: function(data){

            //         var arrLen = data.length;
            //         if(arrLen > 0){
                        
            //         // 如果没有数据
            //         }else{
            //             // 锁定
            //             me.lock();
            //             // 无数据
            //             me.noData();
            //         }
            //         // 每次数据加载完，必须重置
            //         me.resetload();
            //     },
            //     error: function(xhr, type){
            //         alert('Ajax error!');
            //         // 即使加载出错，也得重置
            //         me.resetload();
            //     }
            // });
        }
    });

})(Zepto)