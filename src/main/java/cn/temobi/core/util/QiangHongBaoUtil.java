package cn.temobi.core.util;
import java.util.*;

import java.math.*;

import java.text.*;



public class QiangHongBaoUtil
{


	
	public static List<Double> createRed(double price){
		List<Double> list = new ArrayList<Double>();
		if(!DateUtils.compareDate())
		{
			int totalDay = 0;
			int cycleDay = 5;
			if(price - 30 <= 0)
			{
				totalDay = 50;
			}else{
				totalDay = 100;
			}
			int totalCycle = totalDay/cycleDay;
			double cyclePrice = price/totalCycle;
			for(int i=0;i<totalCycle;i++)
			{
				qiang(cycleDay, cyclePrice,list);
			}
		}
		return list;
	}
	
	
	public static Map<String,Object> qiang(int num,double cash,List<Double> list)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		int MaxNo = 0;//记录最大值的位置
		int MinNo = 0;//记录最小值的位置
		double[] a = new double[num];//定义一个double型的有num个元素的数组
		pinshouqiArray(a,cash);
		double sum2 = Math.round(sum(a)*100);
		int cash1 = (int)(cash * 100);
		int sum1 = (int)(sum2);
		int resi = cash1 - sum1;
		for(int i = 0; i < resi; i++)
		{
			a[(int)(Math.floor(a.length * Math.random()))] += 0.01;
			sishewuru(a);
		}
		double sum3 = Math.round(sum(a)*100);
		MaxNo = Max(a);
		MinNo = Min(a,cash);
		map.put("total", sum3/100);
		map.put("MaxNo", MaxNo);
		map.put("MinNo", MinNo);
		for(int i = 0; i <= a.length-1; i++)
		{
			list.add(a[i]);
		}
		map.put("arr", a);
		//printArray(a);
		return map;
	}

	public static void printArray(double[] a)//方法：打印数组到屏幕
	{
		double total = 0;
		for(int i = 0; i <= a.length-1; i++)
		{
			System.out.println("第" + (i+1) + "个红包里面有" + a[i] + "元");
			total+=a[i];
		}
		System.out.println("总金额"+total);
	}

	public static double[] baoliu(double[] a)//保留两位小数，不要四舍五入
	{
		double b = 0;
		double c = 0;
		for(int i = 0; i < a.length; i++)
		{
			b = a[i];
			c = (Math.floor(100*b));//向下取整
			c /= 100;
			a[i] = c;
		}
		return a;
	}

	public static double[] arraygenerate(double[] a)//产生随机数组
	{
		for(int i = 0; i <= a.length-1; i++)
		{
			a[i] = Math.random();
			a = baoliu(a);
		}
		return a;
	}

	public static double[] pinshouqiArray(double[] a, double cash)
	{
		arraygenerate(a);//随机给数组中的每个元素分配一个0～1之间的小数
		double sum = 0;//定义变量
		sum = sum(a);
		for(int i = 0; i <= a.length-1; i++)
		{
			a[i] *= ((cash-a.length*0.01)/sum);
			a[i] += 0.01;
		}
		baoliu(a);
		return a;//返回数组

	}

	public static double[] sishewuru(double[] a)//保留两位小数，四舍五入
	{
		double b = 0;
		double c = 0;
		for(int i = 0; i < a.length; i++)
		{
			b = a[i];
			c = Math.round(100*b);//向下取整
			c /= 100;
			a[i] = c;
		}
		return a;
	}

	public static double sum(double[] a)
	{
		double sum = 0;
		for(int i = 0; i <= a.length-1; i++)
			sum += a[i];//算出所有元素的总和
		return sum;
	}

	public static int Max(double[] a)//求最大值的序号
	{
		double max = 0;
		int recmax = 0;
		for(int i = 0; i <= a.length-1; i++)
		{
			if(a[i] > max)
			{
				max = a[i];
				recmax = i + 1;
			}

		}
		return recmax;//返回最大值的序号
	}
	
	public static int Min(double[] a, double cash)//求最小值的序号
	{
		double min = cash + 1;
		int recmin = 0;
		for(int i = 0; i <= a.length-1; i++)
		{
			if(a[i] < min)
			{
				min = a[i];
				recmin = i + 1;
			}
		}
		return recmin;//返回最小值的序号
	}
	
}