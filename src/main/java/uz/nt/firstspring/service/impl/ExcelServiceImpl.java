package uz.nt.firstspring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.StudentDto;
import uz.nt.firstspring.service.ExcelService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    public void write(){
//        HSSFWorkbook excel = new HSSFWorkbook;
    }

    public static List<StudentDto> read() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(""));

        SXSSFWorkbook workbook = new SXSSFWorkbook(wb,1000);

        Sheet sheet = workbook.getSheetAt(2);
        List<StudentDto> students = new ArrayList<>();
        boolean r = true;
        for(Row row : sheet){ //for(int i=6; i<sheet.getLastRowNum(); i++)
            if (r) {
                r = false;
                continue;
            } //row = sheet.getRow(i);
            if (row.getCell(0).getNumericCellValue() == 0) break;

            StudentDto student = new StudentDto();
            student.setName(row.getCell(1).getStringCellValue());
            student.setAge((int)row.getCell(3).getNumericCellValue());
            student.setBirthDate(row.getCell(4).getDateCellValue());
            student.setCourse(row.getCell(5).getStringCellValue());

            students.add(student);
        }
        return students;
    }

    public void writeExcelFile(Stream<ProductDto> productDtos, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(getClass().getClassLoader().getResource("templates/Products.xlsx").openStream());

        Sheet sheet = workbook.getSheetAt(0);

        AtomicInteger r = new AtomicInteger(sheet.getLastRowNum());
        AtomicInteger i = new AtomicInteger(1);

        productDtos.forEach(productDto -> {
            Row row = sheet.createRow(r.getAndIncrement());
            row.createCell(0).setCellValue(i.getAndIncrement());
            row.createCell(1).setCellValue(productDto.getName());
            row.createCell(2).setCellValue(productDto.getPrice());
            row.createCell(3).setCellValue(productDto.getAmount());
            row.createCell(4).setCellValue(productDto.getType().getName());
        });


        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition","attachment; filename =\"products.xlsx\"");

        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();
    }

//    public static void main(String[] args) throws IOException {
//         List<StudentDto> student = read();
//        System.out.println(student);
//    }

    public void writeToExcel(List<StudentDto> students) throws IOException {

        Class cls = getClass();
//        Class cls = ExcelServiceImpl.class;
        ClassLoader classLoader = cls.getClassLoader();
        URL url = classLoader.getResource("templates/Excel.xlsx");
        InputStream inputStream = url.openStream();

        String path = url.getPath();
        File f = new File(path);
        f.createNewFile();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(f));

        Sheet sheet = workbook.getSheetAt(2);

        Row row = sheet.createRow(4);
        row.createCell(0).setCellValue(4);
        row.createCell(1).setCellValue("Ulug'bek");
        row.createCell(2).setCellValue("Kozimov");
        row.createCell(3).setCellValue("22");
        row.createCell(4).setCellValue("01.01.2000");
        row.createCell(5).setCellValue("Java");

        AtomicReference<Sheet> atomicSheet = new AtomicReference<>();
        atomicSheet.set(workbook.createSheet("new"));

        String pack = classLoader.getResource("templates").getPath();
        File file = new File(pack + "/" + "Excel2.xlsx");
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
    }

    public void exportProducts(Stream<ProductDto> productDtos, HttpServletRequest request, HttpServletResponse response) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(getClass().getClassLoader().getResource("templates/Products.xlsx").getPath());

        SXSSFWorkbook workbook = new SXSSFWorkbook(wb, 1000);

        Sheet sheet = workbook.getSheetAt(0);

        AtomicInteger r = new AtomicInteger(3);
        AtomicInteger i = new AtomicInteger(1);

        productDtos.forEach(productDto -> {
            Row row = sheet.createRow(r.getAndIncrement());
            row.createCell(0).setCellValue(i.getAndIncrement());
            row.createCell(1).setCellValue(productDto.getName());
            row.createCell(2).setCellValue(productDto.getPrice());
            row.createCell(3).setCellValue(productDto.getAmount());
            row.createCell(4).setCellValue(productDto.getType().getName());
        });

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"products.xlsx\"");

        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();
    }

    @Override
    public void exportLessProducts(List<ProductDto> products) {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(new XSSFWorkbook(getClass().getClassLoader().getResource("templates/control_products.xlsx").getPath()), 1000);
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }

        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = 3;

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());

        for (ProductDto product : products){
            Row row = sheet.createRow(++rowNum);
            row.createCell(0).setCellValue(rowNum - 3);
            row.createCell(1).setCellValue(product.getId());
            row.createCell(2).setCellValue(product.getName());
            row.createCell(3).setCellValue(product.getType().getName());
            row.createCell(4).setCellValue(product.getPrice());

            Cell amount = row.createCell(5);
            amount.setCellStyle(cellStyle);
            amount.setCellValue(product.getAmount());

            row.createCell(6).setCellValue((RichTextString) product.getType());
            row.createCell(7).setCellValue(String.format("%s(%s)", product.getType().getUnitId(), product.getType().getUnitId().getShortName()));
        }

        Calendar calendar = Calendar.getInstance();

        File upload = new File(getClass().getClassLoader().getResource("templates/Excel.xlsx").getPath()
                + String.format("/uploads/%d/%d/%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
        if (!upload.isDirectory()) upload.mkdirs();
        File newExcel = new File(upload.getPath() + "/Products_" + System.currentTimeMillis() + ".xlsx");
        try {
            newExcel.createNewFile();
            FileOutputStream fos = new FileOutputStream(newExcel);
            workbook.write(fos);

            fos.close();
            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

/**
 * Excel file - Workbook, XSSFWorkbook - .xlsx, HSSFWorkbook - .xls
 * List - Sheet
 * Row - qator
 * Cell - ustun
 */
