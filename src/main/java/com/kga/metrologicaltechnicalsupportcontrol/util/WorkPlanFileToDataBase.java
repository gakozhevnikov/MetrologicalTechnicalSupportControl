package com.kga.metrologicaltechnicalsupportcontrol.util;


import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;

@Component
@Setter
@Getter
@Data
public class WorkPlanFileToDataBase {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${work-plan-file.count-tech-object}")
    private Integer countTechObject;

    @Value("${work-plan-file.count-title-equipment}")
    private Integer countTitleEquipment;

    @Value("#{${work-plan-file.start-row}-1}")
    private Integer startRow;

    @Value("${work-plan-file.end-row}")
    private Integer endRow;

    Set<TechObject> techObjects = new TreeSet<>();//сортированное без повторений множество, такое множество необходимо для чтобы не было повторений, т.е. только уникальные значения и сортировка для удобства поиска

    //@Value("${work-plan-file.column-title-equipment}")
    @Value("#{${work-plan-file.column-title-equipment}-1}")
//В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля
    //@Value("#{T(java.lang.Math).decrementExact('${work-plan-file.column-title-equipment}')}")//Второй вариант уменьшения значения из настроек на одну единицу
    private Integer columnTitleEquipment;

    public WorkPlanFileToDataBase() {
        this.getTechObjects();

    }

    public void setTechObjectsFromFile() {
        if (null != FileManager.getWorkPlanFile()) {
            try (InputStream inputStream = new FileInputStream(FileManager.getWorkPlanFile());) {
                Workbook wb = WorkbookFactory.create(inputStream);
                //берем первый лист
                Sheet sheet = wb.getSheetAt(0);
                for (int rowInt = startRow; rowInt <= endRow; rowInt++) {
                    //читаем первое поле (отсчет полей идет с нуля) т.е. по сути читаем второе - cell с 0, а Row с 1
                    Row row = sheet.getRow(rowInt);
                    //читаем столбцы, отчет с нуля
                    Cell cell = row.getCell(0);
                    //Для Cell getStringCellValue().toString() именно .toString() обязательно т.к. если его убрать то будут возникать ошибки, к примеру при отсутствии в ячейки значения и без приведения к стринг значение будет восприниматься как null  и вызывать ошибку
                    if (cell.getStringCellValue().toString().equals("")) {//Убираем разные не нужные вспомогательные слова которые попадаются по ходу чтения из файла
                        continue;
                    } else if (cell.getStringCellValue().toString().equals("Объект:")) {
                        Cell cellObjectTitle = row.getCell(1);
                        TechObject techObject = new TechObject();
                        techObject.setTitle(cellObjectTitle.getStringCellValue().toString());
                        techObjects.add(techObject);
                    }
                }
            } catch (Exception e) {
                log.info("getTechObjects Exception {}", e.toString());
                e.printStackTrace();
            }
        }else {
            log.info(FileManager.getWorkPlanFile()+" file not exist");
        }

    }
}


