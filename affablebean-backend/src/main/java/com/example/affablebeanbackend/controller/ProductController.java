package com.example.affablebeanbackend.controller;

import com.example.affablebeanbackend.dao.CategoryDao;
import com.example.affablebeanbackend.dto.Products;
import com.example.affablebeanbackend.entity.Category;
import com.example.affablebeanbackend.entity.Product;
import com.example.affablebeanbackend.service.ProductService;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/backend")
public class ProductController {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ProductService productService;

    private Logger logger= LoggerFactory
            .getLogger(this.getClass().getName());

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @ResponseBody
    @GetMapping("/products")
    public Products listAllProduct(){
        return productService.listAllProducts();
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/read-excel-product")
    public String readExcelV2(@RequestParam("file1")MultipartFile file){
        XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet=workbook.getSheetAt(0);
        Iterator<Row> rowIterator=sheet.rowIterator();
        while(rowIterator.hasNext()){
            Row row=rowIterator.next();
            Iterator<Cell> cellIterator=null;
            Product product=new Product();
            if(row.getRowNum() !=0){
                cellIterator=row.cellIterator();

                while(cellIterator.hasNext()){
                    Cell cell=cellIterator.next();
                    if(cell.getColumnIndex() == 0){
                        product.setId((int)cell.getNumericCellValue());
                        System.out.print((int) cell.getNumericCellValue()+"\t\t");
                    }
                    else if(cell.getColumnIndex()==1){
                        product.setName(cell.getStringCellValue());
                        System.out.print(cell.getStringCellValue() + "\t\t");
                    }

                    else if(cell.getColumnIndex()==2){
                        product.setPrice(cell.getNumericCellValue());
                        System.out.print(cell.getNumericCellValue()+ "\t\t");
                    }

                    else if(cell.getColumnIndex()==3){
                        product.setDescription(cell.getStringCellValue());
                        System.out.print(cell.getStringCellValue()+"\t\t");
                    }

                    else if(cell.getColumnIndex()==4){
                        product.setDate(cell.getStringCellValue());
                        System.out.print(cell.getStringCellValue()+"\t\t");
                    }
                    else if(cell.getColumnIndex()==5){
                        product.setCategoryId((int)cell.getNumericCellValue());
                        System.out.println((int)cell.getNumericCellValue());
                    }

                }
                productService
                        .saveProduct(product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getDescription(),
                                product.getDate(),
                                product.getCategoryId());

            }
        }
        return "success";
    }
    @Transactional
    @SneakyThrows
    @ResponseBody
    @PostMapping("/read-excel")
    public String  readExcel(@RequestParam("file")MultipartFile file){

        XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet=workbook.getSheetAt(0);
        List<Category> categories=new ArrayList<>();
        Iterator<Row> rowIterator=sheet.iterator();
        while(rowIterator.hasNext()){
            Row row=rowIterator.next();
            Category category=new Category();
            Iterator<Cell> cellIterator=null;
            if(row.getRowNum() != 0) {
                cellIterator = row.cellIterator();


                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 0) {
                        category.setId((int)cell.getNumericCellValue());
                        System.out.print((int)cell.getNumericCellValue() + "\t\t");
                    } else if (cell.getColumnIndex() == 1) {
                        category.setName(cell.getStringCellValue());
                        System.out.println(cell.getStringCellValue());
                    }

                }
            }
            if(category.getName() !=null) {
                categories.add(category);
            }

        }
        categories.forEach(System.out::println);
        categories
                .forEach(categoryDao::save);


        return "success";
    }
}
