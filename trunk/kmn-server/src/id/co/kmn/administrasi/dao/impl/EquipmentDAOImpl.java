package id.co.kmn.administrasi.dao.impl;

import id.co.kmn.administrasi.dao.EquipmentDAO;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.dao.impl.BaseDAOImpl;
import id.co.kmn.backend.model.Mmedequipment;
import id.co.kmn.util.ConstantUtil;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/25/12
 * Time: 5:05 AM
 */
public class EquipmentDAOImpl extends BaseDAOImpl<Mmedequipment> implements EquipmentDAO {
    /**
     * constructor
     */
    protected EquipmentDAOImpl() {
        super(Mmedequipment.class);
    }

    @Override
    public Mmedequipment getNew() {
        return new Mmedequipment();
    }

    @Override
    public Mmedequipment getByCode(String code) {
        return getByCode(ConstantUtil.EQUIPMENT_CODE, code);
    }

    @Override
    public List<Mmedequipment> getListLikeName(String string) {
        return getListLikeName(ConstantUtil.EQUIPMENT_NAME, string);
    }

    @Override
    public List<Mmedequipment> getListLikeCode(String string) {
        return getListLikeCode(ConstantUtil.EQUIPMENT_CODE, string);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getAllLikeText(ConstantUtil.EQUIPMENT_NAME, text, start, pageSize);
    }
}
