package id.co.kmn.administrasi.dao.impl;

import id.co.kmn.administrasi.dao.EquipTypeDAO;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.dao.impl.BaseDAOImpl;
import id.co.kmn.backend.model.Mmedequiptype;
import id.co.kmn.util.ConstantUtil;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 5:08 AM
 */
public class EquipTypeDAOImpl extends BaseDAOImpl<Mmedequiptype> implements EquipTypeDAO {
    /**
     * constructor
     */
    protected EquipTypeDAOImpl() {
        super(Mmedequiptype.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mmedequiptype getNew() {
        return new Mmedequiptype();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mmedequiptype getByCode(String code) {
        return getByCode(ConstantUtil.TYPE_CODE, code);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedequiptype> getListLikeName(String string) {
        return getListLikeName(ConstantUtil.TYPE_NAME, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedequiptype> getListLikeCode(String string) {
        return getListLikeCode(ConstantUtil.TYPE_CODE, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getAllLikeText(ConstantUtil.TYPE_NAME, text, start, pageSize);
    }
}
