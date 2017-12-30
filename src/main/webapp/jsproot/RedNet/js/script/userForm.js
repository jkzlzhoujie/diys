;(function ($) {
    var toast = new auiToast({});
    var uf = {
        ufVue: null,
        init: function () {
            this.initVue();
        },
        initVue: function () {
            var me = this;
            me.ufVue = new Vue({
                el: '#ufApp',
                data: {
                    user: {//用户 数据
                        name: '',
                        info: '',
                        experience: '1',
                        fans: '1',
                        net: '',
                        welval: '',
                        thankval: '',
                        thanksval: '',
                        xxImg: [],//形象图片（1张）
                        qtxxImg: []//其他形象图片（1张）
                    },
                    checkList: {
                        cyVal: {
                            class: '',
                            value: '1'
                        },
                        yzVal: {
                            class: '',
                            value: '2'
                        },
                        ssVal: {
                            class: '',
                            value: '3'
                        },
                        mzVal: {
                            class: '',
                            value: '4'
                        },
                        gxVal: {
                            class: '',
                            value: '5'
                        },
                        msVal: {
                            class: '',
                            value: '6'
                        },
                        yxVal: {
                            class: '',
                            value: '7'
                        },
                        qtVal: {
                            class: '',
                            value: '8'
                        }
                    }
                },
                methods: {
                    checkVal: function (v) {
                        var str = this.checkList[v].class;
                        if (str != '') {
                            this.checkList[v].class = '';
                        } else {
                            this.checkList[v].class = 'active';
                        }
                    },
                    unImg: function (v, t) {
                        var $dom = t.event.target;
                        this.ysImg(v, $dom);
                    },
                    delImg: function (v, n) {//删除图片
                        if (v == 'xxImg') {
                            this.user[v] = [];
                        } else {
                            this.user[v].splice(n, 1);
                        }
                    },
                    sendInfo: function () {//提交数据
                        this.checkData();
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
                    ysImg: function (v, file) {//上传图片
                        var vm = this;
                        if (file.files && file.files[0]) {
                            var reader = new FileReader();
                            reader.onload = function (evt) {
                                vm.user[v].push(evt.target.result);
                            };
                            reader.readAsDataURL(file.files[0]);
                        }
                    },
                    checkData: function () {
                        var vm = this;
                        _.each(vm.user, function (o, k) {
                            var isTrue = true;
                            switch (k) {
                                case 'name':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入姓名');
                                break;
                                case 'info':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入自荐');
                                break;
                                case 'net':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入展示网址');
                                break;
                                case 'welval':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入欢迎词');
                                break;
                                case 'thankval':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入投票感谢词');
                                break;
                                case 'thanksval':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入打CALL感谢词');
                                break;
                                case 'xxImg':
                                    isTrue = false;
                                    vm.checkLen(o, '请上传形象图片');
                                break;
                                case 'qtxxImg':
                                    isTrue = false;
                                    vm.checkLen(o, '请上传形象图片');
                                break;
                            }
                            if (!isTrue) {
                                return false;
                            }
                        });
                    },
                    checkVal: function (v, msg) {
                        if (v == '') {
                            toast.fail({
                                title: msg,
                                duration: 2000
                            });
                        }
                    },
                    checkLen: function (arr, msg) {
                        if (arr.length == 0) {
                            toast.fail({
                                title: msg,
                                duration: 2000
                            });
                        }
                    }
                }
            });
        }
    };
    uf.init();
})(Zepto)