package id.co.kmn.administrasi.service.impl;

import id.co.kmn.administrasi.dao.TransactionDAO;
import id.co.kmn.administrasi.service.TransactionService;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Tmedequipment;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 8/1/12
 * Time: 1:10 AM
 */
public class TransactionServiceImpl implements TransactionService{
    private TransactionDAO transactionDAO;

    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }

    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public Tmedequipment getNew() {
        return getTransactionDAO().getNew();
    }

    @Override
    public int getCountAll() {
        return getTransactionDAO().getCountAll();
    }

    @Override
    public Tmedequipment getById(int objId) {
        return getTransactionDAO().getById(objId);
    }

    @Override
    public List<Tmedequipment> getAll() {
        return getTransactionDAO().getAll();
    }

    @Override
    public List<Tmedequipment> getListLikeName(String string) {
        return getTransactionDAO().getListLikeName(string);
    }

    @Override
    public List<Tmedequipment> getListLikeCode(String string) {
        return getTransactionDAO().getListLikeCode(string);
    }

    @Override
    public void saveOrUpdate(Tmedequipment entity) {
        getTransactionDAO().saveOrUpdate(entity);
    }

    @Override
    public void delete(Tmedequipment entity) {
        getTransactionDAO().delete(entity);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getTransactionDAO().getAllLikeText(text, start, pageSize);
    }
}
