package id.co.kmn.administrasi.dao.impl;

import id.co.kmn.administrasi.dao.TransactionDAO;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.dao.impl.BaseDAOImpl;
import id.co.kmn.backend.model.Tmedequipment;
import id.co.kmn.util.ConstantUtil;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 8/1/12
 * Time: 12:58 AM
 */
public class TransactionDAOImpl extends BaseDAOImpl<Tmedequipment> implements TransactionDAO {
    /**
     * constructor
     */
    protected TransactionDAOImpl() {
        super(Tmedequipment.class);
    }

    @Override
    public Tmedequipment getNew() {
        return new Tmedequipment();
    }

    @Override
    public Tmedequipment getByCode(String code) {
        return getByCode(ConstantUtil.BRANCH_CODE, code);
    }

    @Override
    public List<Tmedequipment> getListLikeName(String string) {
        return getListLikeName(ConstantUtil.LAST_NAME, string);
    }

    @Override
    public List<Tmedequipment> getListLikeCode(String string) {
        return getListLikeCode(ConstantUtil.BRANCH_CODE, string);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getAllLikeText(ConstantUtil.LAST_NAME, text, start, pageSize);
    }
}
