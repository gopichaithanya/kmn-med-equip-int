package id.co.kmn.administrasi.service.impl;

import id.co.kmn.administrasi.dao.EquipTypeDAO;
import id.co.kmn.administrasi.service.EquipTypeService;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedequiptype;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 5:13 AM
 */
public class EquipTypeServiceImpl implements EquipTypeService {
    private EquipTypeDAO equipTypeDAO;

    public EquipTypeDAO getEquipTypeDAO() {
        return equipTypeDAO;
    }

    public void setEquipTypeDAO(EquipTypeDAO equipTypeDAO) {
        this.equipTypeDAO = equipTypeDAO;
    }

    @Override
    public Mmedequiptype getNew() {
        return getEquipTypeDAO().getNew();
    }

    @Override
    public int getCountAll() {
        return getEquipTypeDAO().getCountAll();
    }

    @Override
    public Mmedequiptype getById(int objId) {
        return getEquipTypeDAO().getById(objId);
    }

    @Override
    public List<Mmedequiptype> getAll() {
        return getEquipTypeDAO().getAll();
    }

    @Override
    public List<Mmedequiptype> getListLikeName(String string) {
        return getEquipTypeDAO().getListLikeName(string);
    }

    @Override
    public List<Mmedequiptype> getListLikeCode(String string) {
        return getEquipTypeDAO().getListLikeCode(string);
    }

    @Override
    public void saveOrUpdate(Mmedequiptype entity) {
        getEquipTypeDAO().saveOrUpdate(entity);
    }

    @Override
    public void delete(Mmedequiptype entity) {
        getEquipTypeDAO().delete(entity);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getEquipTypeDAO().getAllLikeText(text, start, pageSize);
    }
}
