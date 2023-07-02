package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {
    public static void main(String[] args) {


        String filename="D:\\write.xlsx";
        //write方法中 第一个是你要写入的文件名称 第二个是你的实体类
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(writeData());
//        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();


    }

    private static List<DemoData> writeData(){

        List<DemoData> list=new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            DemoData data=new DemoData();
            System.out.println(i);
            data.setSno(i);
            data.setSname("haoqiang"+i);
            list.add(data);
        }
        return list;


//        List<DemoData> list = new ArrayList<DemoData>();
//        for (int i = 0; i < 10; i++) {
//            DemoData data = new DemoData();
//            data.setSno(i);
//            data.setSname("张三"+i);
//            list.add(data);
//        }
//        return list;
    }
}
