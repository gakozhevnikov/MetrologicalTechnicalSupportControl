package com.kga.metrologicaltechnicalsupportcontrol.util;


import com.kga.metrologicaltechnicalsupportcontrol.Title;
import com.kga.metrologicaltechnicalsupportcontrol.exceptions.ErrorInfo;
import com.kga.metrologicaltechnicalsupportcontrol.exceptions.ErrorType;
import com.kga.metrologicaltechnicalsupportcontrol.exceptions.WorkPlanFileToDataBaseException;
import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    //@Value("${work-plan-file.column-title-equipment}")
    @Value("#{${work-plan-file.column-title-equipment}-1}")
    //В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля
    //@Value("#{T(java.lang.Math).decrementExact('${work-plan-file.column-title-equipment}')}")//Второй вариант уменьшения значения из настроек на одну единицу
    private Integer columnTitleEquipment;

    @Value("#{${work-plan-file.sheet}-1}")//В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля
    private Integer sheet;

    @Value("${work-plan-file.message.error.count-tech-object}" )
    private String errorCountTechObject;

    Set<TechObject> techObjects = new TreeSet<>();//Сортированное без повторений множество, такое множество необходимо для чтобы не было повторений, т.е. только уникальные значения и сортировка для удобства поиска
    Set<Equipment> equipmentSet = new TreeSet<>();

    boolean hasError = false;

    List<String> errorList = new ArrayList<>();

    public WorkPlanFileToDataBase() {
        //this.setTechObjectsFromFile();//вызов этого метода в конструкторе не работает
        //log.info("Class {} in constructor after setTechObjectsFromFile content of Set<TechObject> techObjects {}", getClass().getName(), techObjects);
    }

    public void setTechObjectsFromFile() {
        Set<TechObject> techObjectsTmp = new TreeSet<>();
        try {
            Sheet sheet =getSheetFromFile();
            if (null != sheet){
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
                        techObjectsTmp.add(techObject);
                    }
                }
            }
        } catch (IOException e) {
            log.info("Class {}, getTechObjects Exception {}", getClass().getName(),e.toString());
        }
        if(techObjectsTmp.size()==countTechObject){
            techObjects=techObjectsTmp;
        }else{
            errorList.add(makesStringOfErrorMessageAndModelTitle(techObjectsTmp, errorCountTechObject));
            hasError=true;
        }
    }

    public Set<TechObject> getTechObjects(){
        this.setTechObjectsFromFile();
        if (!hasError()){
            return this.techObjects;
        } else {
            throw new WorkPlanFileToDataBaseException(errorList);
        }
    }

    public void setEquipmentsFromFile(){
        try {
            Sheet sheet =getSheetFromFile();

        } catch (IOException e) {
            log.info("Class {}, setEquipmentsFromFile Exception {}", getClass().getName(),e.toString());
        }
    }

    private Sheet getSheetFromFile() throws IOException {
        Sheet sheet = null;
        if (null != FileManager.getWorkPlanFile()) {
            InputStream inputStream = new FileInputStream(FileManager.getWorkPlanFile());
                Workbook wb = WorkbookFactory.create(inputStream);
                //берем первый лист
                return sheet = wb.getSheetAt(0);
        }else {
            log.info(FileManager.getWorkPlanFile()+" file not exist");
        }
        return sheet;
    }

    public boolean hasError(){
        return hasError && errorList.size() != 0;
    }

    public void setCountTechObject(Integer countTechObject){
        this.countTechObject = countTechObject;
    }

    private String makesStringOfErrorMessageAndModelTitle(Set<? extends Title> model, String errorMessage){
        String[] title = new String[model.size()];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(errorMessage);
        int a = 0;
        for (Title titleModel : model){
            title[a++]=titleModel.getTitle();
        }
        for(int b =0; b<title.length; b++){
            stringBuilder.append(title[b]);
            if (b<title.length-1){
                stringBuilder.append(", ");
            }
        }
         return stringBuilder.toString();
    }
}


