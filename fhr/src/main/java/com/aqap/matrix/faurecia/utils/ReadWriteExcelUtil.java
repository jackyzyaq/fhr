package com.aqap.matrix.faurecia.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

import com.aqap.matrix.faurecia.model.ExcelInfo;



/**
 * excel读写
 * 
 * @author zhg
 * 
 */
public class ReadWriteExcelUtil {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "d:" + File.separator + "test.xls";
		System.out.println(ReadWriteExcelUtil.readExcel(fileName));
		String fileName1 = "d:" + File.separator + "abc.xls";
//		ReadWriteExcelUtil.writeExcel(fileName1, "sheet1");
	}

	/**
	 * 從excel文件中讀取所有的內容
	 * 
	 * @param file
	 *            excel文件
	 * @return excel文件的內容
	 */
	public static String readExcel(String fileName) {
		StringBuffer sb = new StringBuffer();
		Workbook wb = null;
		try {
			// 构造Workbook（工作薄）对象
			wb = Workbook.getWorkbook(new File(fileName));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wb == null)
			return null;

		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = wb.getSheets();

		if (sheet != null && sheet.length > 0) {
			// 对每个工作表进行循环
			for (int i = 0; i < sheet.length; i++) {
				// 得到当前工作表的行数
				int rowNum = sheet[i].getRows();
				for (int j = 0; j < rowNum; j++) {
					// 得到当前行的所有单元格
					Cell[] cells = sheet[i].getRow(j);
					if (cells != null && cells.length > 0) {
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							String cellValue = cells[k].getContents();
							sb.append(cellValue + "\t");
						}
					}
					sb.append("\r\n");
				}
				sb.append("\r\n");
			}
		}
		// 最后关闭资源，释放内存
		wb.close();
		return sb.toString();
	}

	/**
	 * 把內容寫入excel文件中
	 * 
	 * @param fileName
	 *            要寫入的文件的名稱
	 */
	public static void writeExcel(String fileName, String sheetName, List<ExcelInfo> infos) {
		WritableWorkbook wwb = null;
		String type = "0";
		String imgPath ="";
		try {
			// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			WritableSheet ws = wwb.createSheet(sheetName, 0);
			try {
				// 下面开始添加单元格
				for (int i = 0; i < infos.size(); i++) {
					type = infos.get(i).getType();
					//设置列宽
					ws.setColumnView(infos.get(i).getJ(),30); 
					
					if("0".equals(type) || StringUtils.isBlank(type)){
						Label labelC = new Label(infos.get(i).getJ(), infos.get(i).getI(), infos.get(i).getContent());
						// 将生成的单元格添加到工作表中
						ws.addCell(labelC);
					}else{
						
						imgPath = infos.get(i).getContent();
						if(imgPath != null && !"".equals(imgPath)){
							//设置行高
							ws.setRowView(infos.get(i).getI(),3150);
							
							File imgFile = new File(imgPath);
							WritableImage image = new WritableImage(infos.get(i).getJ(),infos.get(i).getI(),1,1,imgFile);
							ws.addImage(image);
						}
					}
				}
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			try {
				// 从内存中写入文件中
				wwb.write();
				// 关闭资源，释放内存
				wwb.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

}
