package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import com.kga.metrologicaltechnicalsupportcontrol.model.EquipmentWithAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EquipmentWithAttributesRepository extends JpaRepository<EquipmentWithAttributes, Long> {
    List<EquipmentWithAttributes> findEquipmentWithAttributesBySerialNumber(String serialNumber);
    List<EquipmentWithAttributes> findEquipmentWithAttributesByEquipment_Title(String title);
    List<EquipmentWithAttributes> findEquipmentWithAttributesByDateVMI(LocalDateTime dateVMI);
    List<EquipmentWithAttributes> findEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title(String positionTitle, String techObjectTitle);
    List<EquipmentWithAttributes> findEquipmentWithAttributesByPosition_TechObject_Title(String techObjectTitle);
}
