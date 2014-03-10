package id.co.kmn.administrasi.dao.impl;

import id.co.kmn.administrasi.dao.SystemDAO;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.dao.impl.BaseDAOImpl;
import id.co.kmn.backend.model.Mmedsystem;
import id.co.kmn.util.ConstantUtil;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 7:06 AM
 */
public class SystemDAOImpl extends BaseDAOImpl<Mmedsystem> implements SystemDAO {
    /**
     * constructor
     */
    protected SystemDAOImpl() {
        super(Mmedsystem.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mmedsystem getNew() {
        return new Mmedsystem();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mmedsystem getByCode(String code) {
        return getByCode(ConstantUtil.SYSTEM_KEY, code);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedsystem> getListLikeName(String string) {
        return getListLikeName(ConstantUtil.SYSTEM_VALUE, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedsystem> getListLikeCode(String string) {
        return getListLikeCode(ConstantUtil.SYSTEM_KEY, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getAllLikeText(ConstantUtil.SYSTEM_VALUE, text, start, pageSize);
    }
}
