package com.adnanbk.ecommerce.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public interface ExcelHelperService<T> {
    boolean hasExcelFormat(MultipartFile file);

    List<T> excelToList(InputStream is);

    ByteArrayInputStream listToExcel(List<T> list);

}
