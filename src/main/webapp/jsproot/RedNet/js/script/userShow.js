;(function ($) {    
    // 页数
    var page = 0;
    // 每页展示5个
    var size = 5;
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
                        nickName: 'asdf',
                        labelList: [
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
                        picOne: ['../../jsproot/RedNet/images/null.png'],
                        picOther: [],
                        endorsementImg: ['../../jsproot/RedNet/images/null.png']
                    },
                    supportUser: [
                        {
                            img: '../../jsproot/RedNet/images/null.png',
                            name: 'ffff',
                            callNum: 123,
                            pNum: 234
                        },
                        {
                            img: '../../jsproot/RedNet/images/null.png',
                            name: 'ffff',
                            callNum: 123,
                            pNum: 234
                        }
                    ]
                },
                methods: {
                    perfectInfo: function () {
                        console.log('perfectInfo');
                    },
                    goCanvassing: function () {
                        console.log('goCanvassing');
                    }
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
                            alert('Ajax error!');
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