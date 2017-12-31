;(function () {
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
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
    var sm = {
        scroll: null,
        smVue: null,
        init: function () {
            this.initScroll();
            this.initVue();
        },
        initVue: function () {
            var me = this;
            me.smVue = new Vue({
                el: '#smApp',
                data: {
                    supporter: [],
                    hImg: imgs.hImg[0],
                    xImg: imgs.xImg[0],
                    type: 1,//投票类型：1：鲜花，2：打CALL
                    number: 0//打CALL数
                },
                methods: {
                    getData: function () {
                    	var that = this;
                        //数据请求
                         $.ajax({
                             url: 'iSupportNetRedUserList',
                             data: {
                            	 //参数
                            	 voteUserId:GetQueryString("voteUserId")
                             },
                             success: function (result) {
                            	 var obj = JSON.parse(result);
                            	 if(obj.code == "00000"){
                            		 that.supporter = obj.response;
                            	 }else{
                            		 that.supporter = [];
                            	 }
                             }
                         });
                    },
                    sendFab: function () {
                        //数据请求
                        // $.ajax({
                        //     url: '',
                        //     data: {
                        //参数
                        //     },
                        //     success: function () {
        
                        //     }
                        // });
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
                        //数据请求
                        // $.ajax({
                        //     url: '',
                        //     data: {
                        //参数
                        //     },
                        //     success: function () {
        
                        //     }
                        // });
                    },
                    add: function () {
                        this.number += 1;
                    },
                    reduce: function () {
                        this.number -= 1;
                        if (this.number < 0) {
                            this.number = 0;
                        }
                    }
                },
                created: function () {
                    this.getData();
                }
            });
        },
        initScroll: function () {//上拉加载分页
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
    };
    sm.init();
})(Zepto)