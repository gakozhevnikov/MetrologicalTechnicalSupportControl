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
import java.util.*;

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
    @Value("#{${work-plan-file.column-text-object}-1}")
    private Integer columnTextObject;
    @Value("#{${work-plan-file.column-title-object}-1}")
    private Integer columnTitleObject;

    @Value("#{${work-plan-file.sheet}-1}")//В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля
    private Integer sheet;

    @Value("${work-plan-file.message.error.count-tech-object}")
    private String errorCountTechObject;
    @Value("${work-plan-file.message.error.count-equipment}" )
    private String errorCountEquipment;
    @Value("${work-plan-file.message.error.sheet.null}" )
    private String errorSheetNull;

    private Set<TechObject> techObjects = new TreeSet<>();//Сортированное без повторений множество, такое множество необходимо для чтобы не было повторений, т.е. только уникальные значения и сортировка для удобства поиска
    private Set<Equipment> equipmentSet = new TreeSet<>();

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
                    Cell cell = row.getCell(columnTextObject);
                    //Для Cell getStringCellValue().toString() именно .toString() обязательно т.к. если его убрать то будут возникать ошибки, к примеру при отсутствии в ячейки значения и без приведения к стринг значение будет восприниматься как null  и вызывать ошибку
                    if (cell.getStringCellValue().toString().equals("")) {//Убираем разные не нужные вспомогательные слова которые попадаются по ходу чтения из файла
                        continue;
                    } else if (cell.getStringCellValue().toString().equals("Объект:")) {
                        Cell cellObjectTitle = row.getCell(columnTitleObject);
                        TechObject techObject = new TechObject();
                        techObject.setTitle(cellObjectTitle.getStringCellValue().toString());
                        techObjectsTmp.add(techObject);
                    }
                }
            }else {
                this.setHasErrorAndErrorList(errorSheetNull);//Do a test in the future
            }
        } catch (IOException e) {
            log.info("Class {}, getTechObjects Exception {}", getClass().getName(),e.toString());
            this.setHasErrorAndErrorList(String.format("Class %s, getTechObjects Exception %s", getClass().getName(), e));//Do a test in the future
        }
        if(techObjectsTmp.size()==countTechObject){
            techObjects=techObjectsTmp;
        }else{
            this.setHasErrorAndErrorList(makesStringOfErrorMessageAndModelTitle(techObjectsTmp, errorCountTechObject));//Do a test in the future
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
        Set<Equipment> equipmentSetTmp =  new TreeSet<>();
        try {
            Sheet sheet =getSheetFromFile();
            if(null!=sheet){
                for (int rowInt = startRow; rowInt <= endRow; rowInt++){
                    //читаем первое поле (отсчет полей идет с нуля) т.е. по сути читаем второе - cell с 0, а Row с 1
                    Row row = sheet.getRow(rowInt);
                    //читаем столбцы
                    Cell cell = row.getCell(columnTitleEquipment);
                    if(cell.getStringCellValue().equals("")//Убираем разные не нужные вспомогательные слова которые попадаются по ходу чтения из файла
                            | cell.getStringCellValue().equals("Объект:")){
                        continue;
                    }
                    else {
                        Equipment equipment = new Equipment();
                        equipment.setTitle(cell.getStringCellValue());
                        equipmentSetTmp.add(equipment);
                    }
                }
            } else{
                this.setHasErrorAndErrorList(errorSheetNull);//Do a test in the future
            }

        } catch (IOException e) {
            log.info("Class {}, setEquipmentsFromFile Exception {}", getClass().getName(),e.toString());
            this.setHasErrorAndErrorList(String.format("Class %s, setEquipmentsFromFile Exception %s", getClass().getName(), e));//Do a test in the future
        }
        if(equipmentSetTmp.size()==countTitleEquipment){
            this.equipmentSet = equipmentSetTmp;
        }else{
            this.setHasErrorAndErrorList(makesStringOfErrorMessageAndModelTitle(equipmentSetTmp, errorCountEquipment));//Do a test in the future
        }
    }

    public Set<Equipment> getEquipments(){
        this.setEquipmentsFromFile();
        if (!hasError()){
            return this.equipmentSet;
        } else {
            throw new WorkPlanFileToDataBaseException(errorList);
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
        //stringBuilder.append(Arrays.toString(title));
         return stringBuilder.toString();
    }

    private void setHasErrorAndErrorList(String message){
        hasError=true;
        errorList.add(message);
    }
}


