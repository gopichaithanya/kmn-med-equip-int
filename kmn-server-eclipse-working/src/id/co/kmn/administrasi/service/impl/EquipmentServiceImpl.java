package id.co.kmn.administrasi.service.impl;

import id.co.kmn.administrasi.dao.EquipmentDAO;
import id.co.kmn.administrasi.service.EquipmentService;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedequipment;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/25/12
 * Time: 5:06 AM
 */
public class EquipmentServiceImpl implements EquipmentService{
    private EquipmentDAO equipmentDAO;

    public EquipmentDAO getEquipmentDAO() {
        return equipmentDAO;
    }

    public void setEquipmentDAO(EquipmentDAO equipmentDAO) {
        this.equipmentDAO = equipmentDAO;
    }

    @Override
    public Mmedequipment getNew() {
        return getEquipmentDAO().getNew();
    }

    @Override
    public int getCountAll() {
        return getEquipmentDAO().getCountAll();
    }

    @Override
    public Mmedequipment getById(int objId) {
        return getEquipmentDAO().getById(objId);
    }

    @Override
    public List<Mmedequipment> getAll() {
        return getEquipmentDAO().getAll();
    }

    @Override
    public List<Mmedequipment> getListLikeName(String string) {
        return getEquipmentDAO().getListLikeName(string);
    }

    @Override
    public List<Mmedequipment> getListLikeCode(String string) {
        return getEquipmentDAO().getListLikeCode(string);
    }

    @Override
    public void saveOrUpdate(Mmedequipment entity) {
        getEquipmentDAO().saveOrUpdate(entity);
    }

    @Override
    public void delete(Mmedequipment entity) {
        getEquipmentDAO().delete(entity);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getEquipmentDAO().getAllLikeText(text, start, pageSize);
    }
}
