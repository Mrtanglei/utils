package com.utils.common.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;

/**
 * excel工具
 *
 * @author tanglei
 * @date 18/9/10 下午12:49
 */
public class BaseExportService {

    /**
     * 将文件写入响应流,实现同步下载
     *
     * @param workbook
     * @param title
     * @param responese
     * @throws IOException
     */
    public static void writeToResponse(Workbook workbook, String title, HttpServletResponse responese) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ServletOutputStream outputStream = null;
        try {
            workbook.write(os);
            byte[] bytes = os.toByteArray();
            is = new ByteArrayInputStream(bytes);
            responese.setCharacterEncoding("utf-8");
            responese.setContentType("multipart/form-data");
            responese.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(title, "UTF-8"));
            responese.setContentLength(bytes.length);
            outputStream = responese.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } finally {
            os.close();
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            workbook.close();
        }
    }

    public static Sheet cereateSheet(Workbook workbook, String title){
        if(workbook == null){
            return null;
        }
        return workbook.createSheet(title);
    }

    public static Workbook createWorkbook(){
        return new HSSFWorkbook();
    }

    /**
     * 创建文本型的cell
     * @param row 要创建的cell所在的行
     * @param value 要创建的cell的值
     * @param cellIndex 要创建的cell的列号(0-based)
     */
    public static void createCell(Row row, Object value, int cellIndex) {
        Cell cell = null;
        if(value == null){
            cell.setCellValue("");
            cell.setCellType(CellType.BLANK);
        }else if(value instanceof String) {
            cell.setCellValue((String)value);
            cell.setCellType(CellType.STRING);
        }else if(value instanceof Integer){
            cell.setCellValue((Integer)value);
            cell.setCellType(CellType.NUMERIC);
        }else if(value instanceof Double){
            cell.setCellValue((Double)value);
            cell.setCellType(CellType.NUMERIC);
        }else if(value instanceof Long){
            cell.setCellValue((Long)value);
            cell.setCellType(CellType.NUMERIC);
        }else if(value instanceof BigDecimal){
            cell.setCellValue(((BigDecimal)value).doubleValue());
            cell.setCellType(CellType.NUMERIC);
        }else if(value instanceof Boolean){
            cell.setCellValue((Boolean)value);
            cell.setCellType(CellType.BOOLEAN);
        }else if(value instanceof Date){
            cell.setCellValue((Date)value);
            cell.setCellType(CellType.STRING);
        }
    }

    /**
     * 创建文本型的cell
     * @param row 要创建的cell所在的行
     * @param values 要创建的cell的值
     *
     */
    public static void createCells(Row row, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            createCell(row, values[i], i);
        }
    }
}
