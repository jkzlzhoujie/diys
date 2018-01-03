;(function ($) {
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
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
                        selfIntroduction: '',
                        liveExperience: '1',
                        fansAmount: '1',
                        webSit: '',
                        welcomeWord: '',
                        thanksWord: '',
                        callTanksWord: '',
                        firstImage: '',//形象图片（1张）
                        lablesArr:[],
                        imagesArr: []//其他形象图片（1张）
                    },
                    checkList: {
                        cyVal: false,
                        yzVal: false,
                        ssVal: false,
                        mzVal: false,
                        gxVal: false,
                        msVal: false,
                        yxVal: false,
                        qtVal: false
                    }
                },
                methods: {
                	
                	getData: function () {
                    	var that = this;
                        //数据请求
                         $.ajax({
                             url: '../../clientNew/weixin/getNetRedUser',
                             data: {
                            	 //参数
                            	// netRedUserId:GetQueryString("netRedUserId")
                             },
                             success: function (result) {
                            	 var data = JSON.parse(result);
                            	 if(data != null){
                            		 that.user = data;
                            		 that.user.firstImage = data.firstImage;
                            		 $.each(that.user.lablesArr, function(k,o) {
                            			 that.checkList[o] = true;
                            		 });
//                            		 $.each(data.imagesArr,function (k,o) {
//                         				o = "http://" + o;
//                         			 });
                         			 that.user.imagesArr = data.imagesArr;
                            	 }
                             }
                         });
                    },
                	
                    checkVala: function (v) {
                    	this.checkList[v] = !this.checkList[v];
                    	if (this.checkList[v]) {
                    		this.user.lablesArr.push(v);
                    	} else {
                    		var ind = this.user.lablesArr.indexOf(v);
                    		this.user.lablesArr.splice(ind,1);
                    	}
                    },
                    unImg: function (v, t) {
                        var $dom = t.event.target;
                        this.ysImg(v, $dom);
                    },
                    delImg: function (v, n) {//删除图片
                        if (v == 'firstImage') {
                            this.user[v] = '';
                        } else {
                            this.user[v].splice(n, 1);
                        }
                    },
                    sendInfo: function () {//提交数据
                    	delete this.user.createTime;
                    	debugger
                    	//数据请求
                        $.ajax({
                        	type : 'post',
                            url: '../../clientNew/weixin/updateNetRedUser',
                            data: {
                           	 userStr: JSON.stringify( this.user)
                            },
                            success: function (result) {
                           	 var obj = JSON.parse(result);
                           	 if(obj.code == "00000"){
                           		 alert("修改成功");
                           		 window.location.href = 'userShowPage';
//                           		window.location.href = '../../jsproot/RedNet/userShow.html?netRedUserId=' + obj.response.id;
                           	 }else{
                           		 alert("修改失败," +obj.desc);
                           	 }
                            }
                        });
                    },
                    ysImg: function (v, file) {//上传图片
                        var vm = this;
                        if (file.files && file.files[0]) {
                            var reader = new FileReader();
                            reader.onload = function (evt) {
                            	if (v == 'firstImage') {
                            		vm.user.firstImage = evt.target.result;
                            	} else {
                                    vm.user[v].push(evt.target.result);
                            	}
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
                                case 'selfIntroduction':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入自荐');
                                break;
                                case 'webSit':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入展示网址');
                                break;
                                case 'welcomeWord':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入欢迎词');
                                break;
                                case 'tanksWord':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入投票感谢词');
                                break;
                                case 'callTanksWord':
                                    isTrue = false;
                                    vm.checkVal(o, '请输入打CALL感谢词');
                                break;
                                case 'firstImage':
                                    isTrue = false;
                                    vm.checkLen(o, '请上传形象图片');
                                break;
                                case 'imagesArr':
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
                },
                
                created: function () {
                    this.getData();
                } 
            });
        }
    };
    uf.init();
})(Zepto)