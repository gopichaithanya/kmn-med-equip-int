package id.co.kmn.administrasi.dao.impl;

import id.co.kmn.administrasi.dao.ProducerDAO;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.dao.impl.BaseDAOImpl;
import id.co.kmn.backend.model.Mmedproducer;
import id.co.kmn.util.ConstantUtil;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/1/12
 * Time: 11:49 AM
 */
public class ProducerDAOImpl extends BaseDAOImpl<Mmedproducer> implements ProducerDAO {
    /**
     * constructor
     */
    protected ProducerDAOImpl() {
        super(Mmedproducer.class);
    }

    @Override
    public Mmedproducer getNew() {
        return new Mmedproducer();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mmedproducer getByCode(String prodCode) {
        return getByCode(ConstantUtil.PRODUCER_CODE, prodCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedproducer> getListLikeName(String string) {
        return getListLikeName(ConstantUtil.PRODUCER_NAME, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedproducer> getListLikeCode(String string) {
        return getListLikeCode(ConstantUtil.PRODUCER_CODE, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getAllLikeText(ConstantUtil.PRODUCER_NAME, text, start, pageSize);
    }
}
