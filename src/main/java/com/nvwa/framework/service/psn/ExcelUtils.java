package com.nvwa.framework.service.psn;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class  ExcelUtils {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private final static Logger logger = LoggerFactory.getLogger(ExcelUtils.class); 

    public synchronized static void writeExcel(List<Item> dataList, String finalXlsxPath){
        OutputStream out = null;
        try { 
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbook(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0); 
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性行：" + rowNumber);  
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + rowNumber + 1);
                // 得到要插入的每一条记录
                Item data = dataList.get(j);   
                Cell first = row.createCell(0);
                first.setCellValue(data.getTitle());  
                row.createCell(1).setCellValue(data.getDisplay_price());  
                row.createCell(2).setCellValue(data.getStrikethrough_price());
                row.createCell(3).setCellValue(data.getDiscount());
                row.createCell(4).setCellValue(data.getPlus_price());
                row.createCell(5).setCellValue(data.getPlus_discount());
                row.createCell(6).setCellValue(data.getClassification());
                row.createCell(7).setCellValue(data.getPlatform());
                row.createCell(8).setCellValue(data.getHypelink());
              
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            System.out.println("数据导出成功" + dataList.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
    }
    
    public synchronized static void writeExcel(List<Item> dataList, Workbook workBook){ 
        try {  
        	Sheet sheet = workBook.getSheetAt(0);
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            logger.info("原始数据总行数，除标题行：{}", rowNumber);  
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + rowNumber + 1);
                // 得到要插入的每一条记录
                Item data = dataList.get(j);   
                Cell first = row.createCell(0);
                first.setCellValue(data.getTitle());  
                row.createCell(1).setCellValue(data.getDisplay_price());  
                row.createCell(2).setCellValue(data.getStrikethrough_price());
                row.createCell(3).setCellValue(data.getDiscount());
                row.createCell(4).setCellValue(data.getPlus_price());
                row.createCell(5).setCellValue(data.getPlus_discount());
                row.createCell(6).setCellValue(data.getClassification());
                row.createCell(7).setCellValue(data.getPlatform());
                row.createCell(8).setCellValue(data.getHypelink());
            } 
            logger.info("数据写入{} 条", dataList.size());
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static void writeAndClose(OutputStream out, Workbook workBook) { 
        try {
			workBook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			try {
				out.close();
			} catch (IOException e1) { 
				e1.printStackTrace();
			} 
		}
    }

    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
 
	/** 
     * 判断文件是否存在. 
     * @param fileDir  文件路径 
     * @return 
     */  
    public static boolean fileExist(String fileDir){  
         boolean flag = false;  
         File file = new File(fileDir);  
         flag = file.exists();  
         return flag;  
    }  
         
        /** 
         * 创建新excel. 
         * @param fileDir  excel的路径 
         * @param sheetName 要创建的表格索引 
         * @param titleRow excel的第一行即表格头 
         */  
        public static void createExcel(String fileDir,String sheetName) throws Exception{  
            //创建workbook  
        	Workbook workbook = new XSSFWorkbook();  
            //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
            Sheet sheet1 = workbook.createSheet(sheetName);    
            //新建文件  
            FileOutputStream out = null;  
            try {  
                //添加表头  
                Row row = workbook.getSheet(sheetName).createRow(0);    //创建第一行     
                row.createCell(0).setCellValue("名称");
                row.createCell(1).setCellValue("价格");  
                row.createCell(2).setCellValue("原价");
                row.createCell(3).setCellValue("折扣");
                row.createCell(4).setCellValue("plus价格");
                row.createCell(5).setCellValue("plus折扣");
                row.createCell(6).setCellValue("类别");
                row.createCell(7).setCellValue("平台");
                row.createCell(8).setCellValue("链接");  
                out = new FileOutputStream(fileDir);  
                workbook.write(out);  
            } catch (Exception e) {  
                throw e;
            } finally {    
                try {    
                    out.close();    
                } catch (IOException e) {    
                    e.printStackTrace();  
                }    
            }    
        }  
        /** 
         * 删除文件. 
         * @param fileDir  文件路径 
         */  
        public static boolean deleteExcel(String fileDir) {  
            boolean flag = false;  
            File file = new File(fileDir);  
            // 判断目录或文件是否存在    
            if (!file.exists()) {  // 不存在返回 false    
                return flag;    
            } else {    
                // 判断是否为文件    
                if (file.isFile()) {  // 为文件时调用删除文件方法    
                    file.delete();  
                    flag = true;  
                }   
            }  
            return flag;  
        }  
       
        public static void init(String fileDir, String sheetName) throws Exception {
        	ExcelUtils.deleteExcel(fileDir);
        	ExcelUtils.createExcel(fileDir, sheetName);
        }
}