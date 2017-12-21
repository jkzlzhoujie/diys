//flashBg	   
//var currentindex=1;
//$("#flashBg").css("background-color",$("#flash1").attr("name"));
//function changeflash(i) {	
//currentindex=i;
//for (j=1;j<=3;j++){//此处的3代表你想要添加的幻灯片的数量与下面的3相呼应
//	if (j==i) 
//	{$("#flash"+j).fadeIn("normal");
//	$("#flash"+j).css("display","block");
//	$("#f"+j).removeClass();
//	$("#f"+j).addClass("current");
//	$("#flashBg").css("background-color",$("#flash"+j).attr("name"));
//	}
//	else
//	{$("#flash"+j).css("display","none");
//	$("#f"+j).removeClass();
//	$("#f"+j).addClass("no");}
//}}
//function startAm(){
//timerID = setInterval("timer_tick()",4000);//4000代表间隔时间设置
//}
//function stopAm(){
//clearInterval(timerID);
//}
//function timer_tick() {
//    currentindex=currentindex>=3?1:currentindex+1;//此处的3代表幻灯片循环遍历的次数
//	changeflash(currentindex);}
//$(document).ready(function(){
//$(".flash_bar div").mouseover(function(){stopAm();}).mouseout(function(){startAm();});
//startAm();
//});

//attention-block
$(function () {
 $('.friend').mouseover(function(){
		$('.attention-block').show();
	});
	$('.friend').mouseout(function(){
		$('.attention-block').hide();
	});
});

























