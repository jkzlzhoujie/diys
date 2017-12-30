;(function ($) {
    var toast = new auiToast({});
    var indexVue = new Vue({
        el: '#indexApp',
        data: {
            time: 60,
            codeBtnTxt: '获取验证码',
            isClick: true,
            user: {
                name: '',
                city: '',
                number: '',
                info: ''
            },
            oneLevelId: '510000',
            twoLevelId: '510100',
            threeLevelId: '510105',
            code: ''
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
                            vm.user.city = selectOneObj.value + ' ' + selectTwoObj.value + ' ' + selectThreeObj.value;
                        }
                });
            },
            getCode: function () {
                //获取验证码
                 $.ajax({
                     url: 'getCode',
                     data: {
                    	 phone:this.user.phone
                     },
                     success: function (result) {
                    	 var obj = JSON.parse(result);
                    	 if(obj.code == "00000"){
                    		 that.supporter = obj.response;
                    	 }else{
                    		 
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
                if (this.code == '') {
                    toast.fail({
                        title: '请输入验证码',
                        duration: 2000
                    });
                    return;
                }
                this.checkData();
                //数据请求
                 $.ajax({
                     url: 'saveNetRedUser',
                     data: {
                    	 user: this.user,
                         code: this.code
                     },
                     success: function (result) {
                    	 var obj = JSON.parse(result);
                    	 if(obj.code == "00000"){
                    		 that.supporter = obj.response;
                    	 }else{
                    		 
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
                        case 'info':
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
            }
        }
    }); 
})(Zepto)
