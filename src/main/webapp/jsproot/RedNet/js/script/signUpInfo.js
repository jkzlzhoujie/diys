;(function ($) {
	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	var toast = new auiToast({});
    var indexVue = new Vue({
        el: '#indexApp',
        data: {
            time: 60,
            codeBtnTxt: '获取验证码',
            isClick: true,
            user: {
            	id: 0,
                name: '',
                city: '',
                phone: '',
                selfIntroduction: '',
              //  weichatUserId:GetQueryString("voteUserId")  
                
            },
            oneLevelId: '510000',
            twoLevelId: '510100',
            threeLevelId: '510105',
            code: '',
            showTPSuc: false,//报名成功弹窗
            isTrue: false
        },
        methods: {
            getArea: function (t) {
                var vm = this;
                var iosSelect = new IosSelect(3, 
                    [iosProvinces, iosCitys, iosCountys],
                    {
                        title: '地址选择',
                        itemHeight: 35,
                        relation: [1, 1],
                        oneLevelId: vm.oneLevelId,
                        twoLevelId: vm.twoLevelId,
                        threeLevelId: vm.threeLevelId,
                        callback: function (selectOneObj, selectTwoObj, selectThreeObj) {
                            vm.oneLevelId = selectOneObj.id;
                            vm.twoLevelId = selectTwoObj.id;
                            vm.threeLevelId = selectThreeObj.id;
                            vm.user.city = selectOneObj.value + ',' + selectTwoObj.value + ',' + selectThreeObj.value;
                        }
                });
            },
            getCode: function () {
                if(!(/^1(3|4|5|7|8)\d{9}$/.test(this.user.phone))){ 
                    toast.fail({
                    	title: '手机号码格式有误，请确认',
                        duration: 2000
                    });
                    return false; 
                } 
            	//获取验证码
                $.ajax({
                    url: 'getCode',
                    data: {
                   	 phone:this.user.phone
                    },
                    success: function (result) {
                   	 var obj = JSON.parse(result);
                   	 if(obj.code == "00000"){
                   		 alert("验证码获取成功");
                   	 }else{
                   		alert("验证码获取失败");
                   	 }
                    }
                });
                if (this.isClick) {
                    this.isClick = false;
                    this.getTime();
                }
            },
            getTime: function () {
                this.codeBtnTxt = this.time + 's';
                this.time--;
                if (this.time < 0) {
                    this.isClick = true;
                    this.codeBtnTxt = '获取验证码';
                } else {
                    setTimeout(this.getTime, 1000);
                } 
            },
            sendInfo: function () {
            	 var that = this;
                this.checkData();
                if (this.code == '') {
                    toast.fail({
                        title: '请输入验证码',
                        duration: 2000
                    });
                    return;
                }
                if(!(/^1(3|4|5|7|8)\d{9}$/.test(this.user.phone))){ 
                    toast.fail({
                        title: '手机号码格式有误，请确认',
                        duration: 2000
                    });
                    return false; 
                } 
                if (this.isTrue) {
                	return;
                }
            	this.isTrue = true;
               //数据请求
                $.ajax({
                    url: 'saveNetRedUser',
                    data: {
                   	    userStr: JSON.stringify( this.user),
                        code: this.code
                    },
                    success: function (result) {
                    	that.isTrue = false;
                   	 var obj = JSON.parse(result);
                   	 if(obj.code == "00000"){
//                   		 alert("报名成功");
                   		that.showTPSuc = true;
                   		window.location.href = 'userShowPage?netRedUserId='+'';
                   	 }else{
                   		 alert("报名失败," +obj.desc);
                   	 }
                    }
                });
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
                        case 'city':
                            isTrue = false;
                            vm.checkVal(o, '请输选择城市');
                        break;
                        case 'phone':
                            isTrue = false;
                            vm.checkVal(o, '请输入手机号');
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
            gbtpcg: function () {
                this.showTPSuc = false;
            },
            ljqw: function () {
                window.location.href='';//跳转到报名成功页
            }
        }
    }); 
})(Zepto)
