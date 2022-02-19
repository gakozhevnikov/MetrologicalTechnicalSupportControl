package com.kga.metrologicaltechnicalsupportcontrol.util;


import com.kga.metrologicaltechnicalsupportcontrol.Title;
import com.kga.metrologicaltechnicalsupportcontrol.exceptions.WorkPlanFileToDataBaseException;
import com.kga.metrologicaltechnicalsupportcontrol.model.*;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.*;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance.TypeServiceRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Setter
@Getter
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
    //В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля
    //@Value("${work-plan-file.column-title-equipment}")
    @Value("#{${work-plan-file.column-title-equipment}-1}")
    //В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля
    //@Value("#{T(java.lang.Math).decrementExact('${work-plan-file.column-title-equipment}')}")//Второй вариант уменьшения значения из настроек на одну единицу
    private Integer columnTitleEquipment;
    @Value("#{${work-plan-file.column-title-serialNumber}-1}")
    private Integer columnSerialNumber;
    @Value("#{${work-plan-file.column-text-object}-1}")
    private Integer columnTextObject;
    @Value("#{${work-plan-file.column-title-object}-1}")
    private Integer columnTitleObject;


    @Value("#{${work-plan-file.sheet}-1}")//В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля
    private Integer sheet;
    /**Номера столбцов в которых указаны месяца*/
    @Value("#{${work-plan-file.column-title-january}-1}")
    private Integer columnTitleJanuary;
    @Value("#{${work-plan-file.column-title-february}-1}")
    private Integer columnTitleFebruary;
    @Value("#{${work-plan-file.column-title-march}-1}")
    private Integer columnTitleMarch;
    @Value("#{${work-plan-file.column-title-april}-1}")
    private Integer columnTitleApril;
    @Value("#{${work-plan-file.column-title-may}-1}")
    private Integer columnTitleMay;
    @Value("#{${work-plan-file.column-title-june}-1}")
    private Integer columnTitleJune;
    @Value("#{${work-plan-file.column-title-july}-1}")
    private Integer columnTitleJuly;
    @Value("#{${work-plan-file.column-title-august}-1}")
    private Integer columnTitleAugust;
    @Value("#{${work-plan-file.column-title-september}-1}")
    private Integer columnTitleSeptember;
    @Value("#{${work-plan-file.column-title-october}-1}")
    private Integer columnTitleOctober;
    @Value("#{${work-plan-file.column-title-november}-1}")
    private Integer columnTitleNovember;
    @Value("#{${work-plan-file.column-title-december}-1}")
    private Integer columnTitleDecember;
    @Value("#{${work-plan-file.column-title-dateVMI}-1}")
    private Integer columnDateVMI;
    /**Переменная в которой хранится значение столбца в котором перечисляются позиции. В настройках и файле отчет колонки идет от 1, но при программной обработке файла отчет идет от нуля*/
    @Value("#{${work-plan-file.column-position}-1}")
    private Integer columnPosition;

    @Value("${work-plan-file.text-identify-object}")
    private String textIdentifyObject;


    @Value("${work-plan-file.message.error.count-tech-object}")
    private String errorCountTechObject;
    @Value("${work-plan-file.message.error.count-equipment}" )
    private String errorCountEquipment;
    @Value("${work-plan-file.message.error.sheet.null}" )
    private String errorSheetNull;
    @Value("${work-plan-file.message.error.setting.column}" )
    private String errorSettingColumn;
    @Value("${work-plan-file.message.error.equipment-with-attributes.repository.get}" )
    private String errorEwaRepositoryGet;


    private Set<TechObject> techObjectsSet = new TreeSet<>();//Сортированное без повторений множество, такое множество необходимо для чтобы не было повторений, т.е. только уникальные значения и сортировка для удобства поиска
    private Set<Equipment> equipmentSet = new TreeSet<>();

    /**Поле для перечня наименования позиций(мест установки) оборудования на объектах*/
    private Set<String> positionTitleStringSet = new TreeSet<>();//uniqueLocation
    /**Поле для хранения пар: id объекта {@link TechObject} и наименований позиции у данного объекта*/
    private Map<String, Set<String>> mapObjectPosition = new TreeMap<>();//mapObjectLocation

    /**Поле для хранения множества рабочего плана {@link com.kga.metrologicaltechnicalsupportcontrol.model.WorkPlan} в котором будет храниться данные файла*/
    private List<WorkPlan> workPlans = new ArrayList<>();

    List<Integer> integerListSetting = new ArrayList<>();
    Map<Month, Integer> monthColumnsMap = new HashMap<>();

    boolean hasError = false;

    List<String> errorList = new ArrayList<>();

    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    TechObjectRepository techObjectRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    TypeServiceRepository typeServiceRepository;
    @Autowired
    EquipmentWithAttributesRepository ewaRepository;


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
                    } else if (cell.getStringCellValue().toString().equals(textIdentifyObject)) {
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
            techObjectsSet=techObjectsTmp;
        }else{
            this.setHasErrorAndErrorList(makesStringOfErrorMessageAndModelTitle(techObjectsTmp, errorCountTechObject));//Do a test in the future
        }
    }

    public Set<TechObject> getTechObjects(){
        this.setTechObjectsFromFile();
        if (!hasError()){
            return this.techObjectsSet;
        } else {
            throw new WorkPlanFileToDataBaseException(errorList);
        }
    }

    /**Метод выбирающий из файла данные по оборудованию*/
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
                            | cell.getStringCellValue().equals(textIdentifyObject)){
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

    /**Метод заполняющий Map mapObjectPosition {@link WorkPlanFileToDataBase#mapObjectPosition} из файла
     * @see WorkPlanFileToDataBase#mapObjectPosition */
    public void setMapObjectPositionFromFile(){
        String locationObjectString = "";
        String locationString = "";
        try {
            Sheet sheet =getSheetFromFile();
            if(null!=sheet){
                for (int rowInt = startRow; rowInt <= endRow; rowInt++){
                    //читаем первое поле (отсчет полей идет с нуля) т.е. по сути читаем второе - cell с 0, а Row с 1
                    Row row = sheet.getRow(rowInt);
                    //читаем столбцы
                    Cell cell = row.getCell(columnPosition);
                    Cell cellObject = row.getCell(columnTextObject);
                    Cell cellObjectTitle = row.getCell(columnTitleObject);
                    if(cellObject.getStringCellValue().equals(textIdentifyObject)){
                        if (!locationObjectString.equals("")){
                            mapObjectPosition.put(locationObjectString, new TreeSet<>(positionTitleStringSet));
                            positionTitleStringSet.clear();
                        }
                        locationObjectString=cellObjectTitle.getStringCellValue();
                    }
                    //Проводим проверку на пустые ячейки, т.к. если ячейка будет пустая то в дальнейшем будет возникать ошибка.
                    if (cell!=null){
                        locationString=cell.getStringCellValue();
                    }
                    else {
                        locationString="";
                        continue;
                    }
                    if((rowInt != endRow)&&(locationString.equals("")| locationString.equals("-"))){//Убираем разные не нужные вспомогательные слова которые попадаются по ходу чтения из файла
                        locationString="";
                        continue;
                    }
                    else if(!locationObjectString.equals("")&&!locationString.equals(""))  {
                        positionTitleStringSet.add(locationString);
                    }
                    if (rowInt == endRow){
                        mapObjectPosition.put(locationObjectString, new TreeSet<>(positionTitleStringSet));
                        positionTitleStringSet.clear();
                    }

                }
            } else{
                this.setHasErrorAndErrorList(errorSheetNull);//Do a test in the future
            }

        } catch (IOException e) {
            log.info("Class {}, setMapObjectPositionFromFile Exception {}", getClass().getName(),e.toString());
            this.setHasErrorAndErrorList(String.format("Class %s, setMapObjectPositionFromFile Exception %s", getClass().getName(), e));//Do a test in the future
        }
    }

    public Map<String, Set<String>> getMapObjectPosition() {
        this.setMapObjectPositionFromFile();
        return mapObjectPosition;
    }

    /**Метод выбирающий из файла данные плана работ и сохраняющий их в базе данных. Даты и виды ТО в две строки*/
    private void setWorkPlanSetFromFileTwoRow(){
        monthColumnsMap.put(Month.JANUARY, columnTitleJanuary);
        monthColumnsMap.put(Month.FEBRUARY, columnTitleFebruary);
        monthColumnsMap.put(Month.MARCH, columnTitleMarch);
        monthColumnsMap.put(Month.APRIL, columnTitleApril);
        monthColumnsMap.put(Month.MAY, columnTitleMay);
        monthColumnsMap.put(Month.JUNE, columnTitleJune);
        monthColumnsMap.put(Month.JULY, columnTitleJuly);
        monthColumnsMap.put(Month.AUGUST, columnTitleAugust);
        monthColumnsMap.put(Month.SEPTEMBER, columnTitleSeptember);
        monthColumnsMap.put(Month.OCTOBER, columnTitleOctober);
        monthColumnsMap.put(Month.NOVEMBER, columnTitleNovember);
        monthColumnsMap.put(Month.DECEMBER, columnTitleDecember);
        try {
            Sheet sheet =getSheetFromFile();
            if(null!=sheet){
                TechObject techObject = new TechObject();
                for (int rowInt = startRow; rowInt <= endRow; rowInt++){
                    //WorkPlan workPlan = new WorkPlan();
                    EquipmentWithAttributes equipmentWithAttributes = new EquipmentWithAttributes();
                    Position position;
                    Row rowFirst = sheet.getRow(rowInt);
                    int rowIntSecond=rowInt+1;
                    Row rowSecond = sheet.getRow(rowIntSecond);
                    if (getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnTextObject)).equals(textIdentifyObject)) {
                        techObject = techObjectRepository.findTechObjectByTitle(getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnTitleObject)));
                        if (null==techObject){
                            TechObject techObjectIntermediate = new TechObject();
                            techObjectIntermediate.setTitle(getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnTitleObject)));
                            techObject = techObjectRepository.saveAndFlush(techObjectIntermediate);
                        }
                        continue;
                    } else {
                        Equipment equipment = equipmentRepository.findEquipmentByTitle(getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnTitleEquipment)));
                        if(null == equipment){
                            equipment = new Equipment();
                            equipment.setTitle(getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnTitleEquipment)));
                            equipment=equipmentRepository.saveAndFlush(equipment);
                        }
                        equipmentWithAttributes.setEquipment(equipment);
                        equipmentWithAttributes.setSerialNumber(getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnSerialNumber)));
                        String dateVMI = getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnDateVMI));
                        if (null != dateVMI && !dateVMI.equals("")) {
                            //log.info("Class {}, setWorkPlanSetFromFileTwoRow, dateVMI: {}", getClass().getName(), dateVMI);
                            equipmentWithAttributes.setDateVMI(FactoryFormatterLocalDateTime.parseStringFormatDDMMYYYY(dateVMI));
                        }
                        String positionTitleFromCell = getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(columnPosition));
                        position = positionRepository.findPositionByTitleAndTechObject(positionTitleFromCell, techObject);
                        if(null == position){
                            Position positionIntermediate = new Position();
                            positionIntermediate.setTitle(positionTitleFromCell);
                            positionIntermediate.setTechObject(techObject);
                            position = positionRepository.saveAndFlush(positionIntermediate);
                        }
                        equipmentWithAttributes.setPosition(position);
                        List<EquipmentWithAttributes> ewaListFromRepository = ewaRepository
                                .findEquipmentWithAttributesByEquipment_TitleAndSerialNumber(equipmentWithAttributes.getEquipment().getTitle()
                                        , equipmentWithAttributes.getSerialNumber());
                        EquipmentWithAttributes ewaFromRepository = new EquipmentWithAttributes();
                        if(null == ewaListFromRepository){
                            /*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(null == ewaListFromRepository), equipmentWithAttributes: {}"
                                    , getClass().getName(), equipmentWithAttributes);*/
                            ewaFromRepository = ewaRepository.saveAndFlush(equipmentWithAttributes);
                            /*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(null == ewaListFromRepository), ewaFromRepository: {}"
                                    , getClass().getName(), ewaFromRepository);*/
                        }else if (ewaListFromRepository.size() == 0){
                            /*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if (ewaListFromRepository.size() == 0), equipmentWithAttributes: {}"
                                    , getClass().getName(), equipmentWithAttributes);*/
                            ewaFromRepository = ewaRepository.saveAndFlush(equipmentWithAttributes);
                            /*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(null == ewaListFromRepository), ewaFromRepository: {}"
                                    , getClass().getName(), ewaFromRepository);*/
                        }else if (ewaListFromRepository.size() == 1){
                            ewaFromRepository = ewaListFromRepository.get(0);
                        }else if (ewaListFromRepository.size() > 1){
                            /*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if (ewaListFromRepository.size() > 1), ewaListFromRepository.size(): {}"
                                    , getClass().getName(), ewaListFromRepository.size());*/
                            List<EquipmentWithAttributes> ewaListFromRepositoryFindByPosition = ewaRepository
                                    .findEquipmentWithAttributesByEquipment_TitleAndSerialNumberAndPosition
                                    (equipmentWithAttributes.getEquipment().getTitle(), equipmentWithAttributes.getSerialNumber(), equipmentWithAttributes.getPosition());
                            if(ewaListFromRepositoryFindByPosition.size()==1){
                                ewaFromRepository = ewaListFromRepositoryFindByPosition.get(0);
                            }else {
                                this.setHasErrorAndErrorList(errorEwaRepositoryGet);//Do a test in the future
                            }
                        }
                        for(Map.Entry<Month, Integer> monthColumn : monthColumnsMap.entrySet()){
                            String typeServiceFromCell = getStringFromCellInMethodSetWorkPlan(rowFirst.getCell(monthColumn.getValue()));
                            String DateOfWorkFromCell = getStringFromCellInMethodSetWorkPlan(rowSecond.getCell(monthColumn.getValue()));
                            if (isNullEmpty (typeServiceFromCell)) {
                                TypeService typeService = typeServiceRepository.findTypeServiceByDesignation(typeServiceFromCell);
                                if(null == typeService){
                                    typeService= new TypeService();
                                    typeService.setDesignation(typeServiceFromCell);
                                    typeService = typeServiceRepository.saveAndFlush(typeService);
                                }
                                WorkPlan workPlan = new WorkPlan();
                                workPlan.setEquipmentWithAttributes(ewaFromRepository);
                                workPlan.setDateOfWork(DateOfWorkFromCell);
                                workPlan.setTypeService(typeService);
                                workPlan.setMonth(monthColumn.getKey());
                                this.workPlans.add(workPlan);
                            }
                        }
                    }
                    rowInt++;
                }
            } else{
                this.setHasErrorAndErrorList(errorSheetNull);//Do a test in the future
            }

        } catch (IOException e) {
            log.info("Class {}, setEquipmentsFromFile Exception {}", getClass().getName(),e.toString());
            this.setHasErrorAndErrorList(String.format("Class %s, setEquipmentsFromFile Exception %s", getClass().getName(), e));//Do a test in the future
        }
        //Set<WorkPlan> workPlanSet = new TreeSet<>();
        /*integerListSetting.add(columnTitleEquipment);
        integerListSetting.add(columnSerialNumber);
        integerListSetting.add(columnTitleJanuary);
        integerListSetting.add(columnTitleFebruary);
        integerListSetting.add(columnTitleMarch);
        integerListSetting.add(columnTitleApril);
        integerListSetting.add(columnTitleMay);
        integerListSetting.add(columnTitleJune);
        integerListSetting.add(columnTitleJuly);
        integerListSetting.add(columnTitleAugust);
        integerListSetting.add(columnTitleSeptember);
        integerListSetting.add(columnTitleOctober);
        integerListSetting.add(columnTitleNovember);
        integerListSetting.add(columnTitleDecember);
        integerListSetting.add(columnDateVMI);
        integerListSetting.add(columnPosition);
        //log.info("Class {}, setWorkPlanSetFromFileTwoRow, integerListSetting: {}" , getClass().getName(), integerListSetting);
        int max = integerListSetting.stream().max(Comparator.comparing(Integer::valueOf)).get();
        int min = integerListSetting.stream().min(Comparator.comparing(Integer::valueOf)).get();
        boolean selectObject=false;
        try {
            Sheet sheet =getSheetFromFile();
            if(null!=sheet){
                if (max!=min) {
                    TechObject techObject = new TechObject();
                    for (int rowInt = startRow; rowInt <= endRow; rowInt++){
                        EquipmentWithAttributes equipmentWithAttributes = new EquipmentWithAttributes();
                        Row rowFirst = sheet.getRow(rowInt);
                        int rowIntSecond=rowInt+1;
                        Row rowSecond = sheet.getRow(rowIntSecond);
                        for (int columnInt = min; columnInt<=max ;columnInt++){
                            WorkPlan workPlan = new WorkPlan();
                            Position position;
                            Cell firstCell = createCellIfNullInMethodSetWorkPlan(rowFirst, columnInt);
                            Cell secondCell = createCellIfNullInMethodSetWorkPlan(rowSecond, columnInt);
                            *//*log.info("Class {}, setWorkPlanSetFromFileTwoRow, rowInt: {}, columnInt: {}, firstCell.getCellType(): {}, secondCell.getCellType(): {}"
                                    , getClass().getName(), rowInt, columnInt, firstCell.getCellType(), secondCell.getCellType());*//*
                            String stringCellFirstValue ="";
                            String stringCellSecondValue ="";
                            *//*stringCellFirstValue = firstCell.toString();
                            stringCellSecondValue = getStringInMethodSetWorkPlan(secondCell, stringCellSecondValue);*//*
                            stringCellFirstValue = getStringInMethodSetWorkPlan(firstCell, stringCellFirstValue);
                            stringCellSecondValue = getStringInMethodSetWorkPlan(secondCell, stringCellSecondValue);
                            *//*log.info("Class {}, setWorkPlanSetFromFileTwoRow, rowInt: {}, columnInt: {}, stringCellFirstValue: {}, stringCellSecondValue: {}"
                                    , getClass().getName(), rowInt, columnInt,stringCellFirstValue,stringCellSecondValue);*//*
                            if(columnTitleEquipment.equals(columnInt)){
                                if(stringCellFirstValue.equals("")){
                                    break;
                                }else if(stringCellFirstValue.equals(textIdentifyObject)){
                                    selectObject= true;
                                    continue;
                                }
                                else {
                                    Equipment equipment = equipmentRepository.findEquipmentByTitle(stringCellFirstValue);
                                    if(null == equipment){
                                        equipment = new Equipment();
                                        equipment.setTitle(stringCellFirstValue);
                                        equipment=equipmentRepository.saveAndFlush(equipment);
                                    }
                                    equipmentWithAttributes.setEquipment(equipment);
                                }
                            }else if(columnSerialNumber.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) break;
                                if(selectObject){
                                    techObject = techObjectRepository.findTechObjectByTitle(stringCellFirstValue);
                                    selectObject= false;
                                    break;
                                }else{
                                    equipmentWithAttributes.setSerialNumber(stringCellFirstValue);
                                }
                            }else if(columnTitleJanuary.equals(columnInt)){
                                *//*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(columnTitleJanuary.equals(columnInt)), rowInt: {}, columnInt: {}, stringCellFirstValue: {}, stringCellSecondValue: {}"
                                        , getClass().getName(), rowInt, columnInt,stringCellFirstValue,stringCellSecondValue);*//*
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.JANUARY, stringCellFirstValue, stringCellSecondValue, workPlan);
                                *//*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(columnTitleJanuary.equals(columnInt)), workPlan: {}"
                                        , getClass().getName(), workPlan);*//*
                            }else if(columnTitleFebruary.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.FEBRUARY, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleMarch.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.MARCH, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleApril.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.APRIL, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleMay.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.MAY, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleJune.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.JUNE, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleJuly.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.JULY, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleAugust.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.AUGUST, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleSeptember.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.SEPTEMBER, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleOctober.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.OCTOBER, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleNovember.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.NOVEMBER, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnTitleDecember.equals(columnInt)){
                                if(stringCellFirstValue.equals("")) continue;
                                workPlan = factoryWorkPlan(Month.DECEMBER, stringCellFirstValue, stringCellSecondValue, workPlan);
                            }else if(columnDateVMI.equals(columnInt)){
                                log.info("Class {}, setWorkPlanSetFromFileTwoRow, stringCellFirstValue: {}, !stringCellFirstValue.equals(): {}, " +
                                                "firstCell.getCellType(): {}, firstCell.getLocalDateTimeCellValue() {}"
                                        , getClass().getName(), stringCellFirstValue, !stringCellFirstValue.equals(""), firstCell.getCellType(), firstCell.getLocalDateTimeCellValue());
                                if(!stringCellFirstValue.equals("") && firstCell.getCellType().equals(CellType.NUMERIC)){
                                    log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(!stringCellFirstValue.equals() && DateUtil.isCellDateFormatted(firstCell))" , getClass().getName());
                                    //equipmentWithAttributes.setDateVMI(FactoryFormatterLocalDateTime.parseStringFormatDDMMYYYY(stringCellFirstValue));
                                    equipmentWithAttributes.setDateVMI(firstCell.getLocalDateTimeCellValue());
                                }
                            }else if(columnPosition.equals(columnInt)){
                                if(!stringCellFirstValue.equals("")){
                                    position = positionRepository.findPositionByTitleAndTechObject(stringCellFirstValue, techObject);
                                    if(null == position){
                                        position = new Position();
                                        position.setTitle(stringCellFirstValue);
                                        position.setTechObject(techObject);
                                    }
                                    equipmentWithAttributes.setPosition(position);
                                }
                            }
                            if(null != equipmentWithAttributes.getEquipment() && null != workPlan.getTypeService()){
                                *//*log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(null != equipmentWithAttributes.getEquipment() && null != workPlan.getTypeService())" +
                                                ", workPlan: {}"
                                        , getClass().getName(), workPlan);*//*
                                List<EquipmentWithAttributes> ewaListFromRepository = ewaRepository
                                        .findEquipmentWithAttributesByEquipment_TitleAndSerialNumber(equipmentWithAttributes.getEquipment().getTitle()
                                                , equipmentWithAttributes.getSerialNumber());
                                EquipmentWithAttributes ewaFromRepository = new EquipmentWithAttributes();
                                if(null == ewaListFromRepository){
                                    log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(null == ewaListFromRepository), equipmentWithAttributes: {}"
                                            , getClass().getName(), equipmentWithAttributes);
                                    ewaFromRepository = ewaRepository.saveAndFlush(equipmentWithAttributes);
                                    log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(null == ewaListFromRepository)" +
                                                    ", ewaFromRepository: {}"
                                            , getClass().getName(), ewaFromRepository);
                                }else if (ewaListFromRepository.size() == 0){
                                    log.info("Class {}, setWorkPlanSetFromFileTwoRow, if (ewaListFromRepository.size() == 0)" +
                                                    ", equipmentWithAttributes: {}"
                                            , getClass().getName(), equipmentWithAttributes);
                                    ewaFromRepository = ewaRepository.saveAndFlush(equipmentWithAttributes);
                                    log.info("Class {}, setWorkPlanSetFromFileTwoRow, if(null == ewaListFromRepository)" +
                                                    ", ewaFromRepository: {}"
                                            , getClass().getName(), ewaFromRepository);
                                }else if (ewaListFromRepository.size() == 1){
                                    ewaFromRepository = ewaListFromRepository.get(0);
                                }else if (ewaListFromRepository.size() > 1){
                                    log.info("Class {}, setWorkPlanSetFromFileTwoRow, if (ewaListFromRepository.size() > 1)" +
                                                    ", ewaListFromRepository.size(): {}"
                                            , getClass().getName(), ewaListFromRepository.size());
                                    List<EquipmentWithAttributes> ewaListFromRepositoryFindByPosition
                                            = ewaRepository.findEquipmentWithAttributesByEquipment_TitleAndSerialNumberAndPosition
                                            (equipmentWithAttributes.getEquipment().getTitle(), equipmentWithAttributes.getSerialNumber(), equipmentWithAttributes.getPosition());
                                    if(ewaListFromRepositoryFindByPosition.size()==1){
                                        ewaFromRepository = ewaListFromRepositoryFindByPosition.get(0);
                                    }else {
                                        this.setHasErrorAndErrorList(errorEwaRepositoryGet);//Do a test in the future
                                    }
                                }
                                workPlan.setEquipmentWithAttributes(ewaFromRepository);
                                this.workPlan.add(workPlan);
                            }
                        }

                    }
                } else {
                    this.setHasErrorAndErrorList(errorSettingColumn);//Do a test in the future
                }
            } else{
                this.setHasErrorAndErrorList(errorSheetNull);//Do a test in the future
            }

        } catch (IOException e) {
            log.info("Class {}, setEquipmentsFromFile Exception {}", getClass().getName(),e.toString());
            this.setHasErrorAndErrorList(String.format("Class %s, setEquipmentsFromFile Exception %s", getClass().getName(), e));//Do a test in the future
        }*/
    }

    private String getStringFromCellInMethodSetWorkPlan(Cell cell, String stringCellValue) {
        if (null != cell) {
            if(cell.getCellType().equals(CellType.STRING)){
                stringCellValue = cell.getStringCellValue();
            } else if (cell.getCellType().equals(CellType.NUMERIC) && DateUtil.isCellDateFormatted(cell)){
                stringCellValue = cell.getLocalDateTimeCellValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }else if (cell.getCellType().equals(CellType.NUMERIC) && !DateUtil.isCellDateFormatted(cell)){
                cell.setCellType(CellType.STRING);
                stringCellValue = cell.toString();
            }
        }
        return stringCellValue;
    }

    private String getStringFromCellInMethodSetWorkPlan(Cell cell) {
        String stringCellValue = "";
        return getStringFromCellInMethodSetWorkPlan(cell, stringCellValue);
    }

    private Cell createCellIfNullInMethodSetWorkPlan(Row row, int columnInt){
        Cell cell = row.getCell(columnInt);
        if (null == cell){
            cell = row.createCell(columnInt);
            cell.setCellValue("");
        }
        return cell;
    }

    public List<WorkPlan> getWorkPlans(){
        this.setWorkPlanSetFromFileTwoRow();
        //log.info("Class {}, getWorkPlanSet, this.workPlanSet: {}", getClass().getName(),this.workPlanSet);
        if (!hasError()){
            return this.workPlans;
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

    public WorkPlan factoryWorkPlan(Month month, String stringCellFirstValue, String stringCellSecondValue, WorkPlan workPlan){
        workPlan.setMonth(month);
        TypeService typeService = typeServiceRepository.findTypeServiceByDesignation(stringCellFirstValue);
        if(null == typeService){
            typeService= new TypeService();
            typeService.setDesignation(stringCellFirstValue);
            typeService = typeServiceRepository.saveAndFlush(typeService);
        }
        workPlan.setTypeService(typeService);
        if (!stringCellFirstValue.equals("") && !stringCellSecondValue.equals("")){
            workPlan.setDateOfWork(stringCellSecondValue);
        }else if (!stringCellFirstValue.equals("") && stringCellSecondValue.equals("")){
            workPlan.setDateOfWork("");
        }
        return workPlan;
    }

    private boolean isNullEmpty(String value){
        return null !=value && !value.equals("");
    }

}


