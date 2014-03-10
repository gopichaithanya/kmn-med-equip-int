package id.co.kmn.administrasi.service.impl;

import id.co.kmn.administrasi.dao.ProducerDAO;
import id.co.kmn.administrasi.service.ProducerService;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedproducer;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/1/12
 * Time: 12:09 PM
 */
public class ProducerServiceImpl implements ProducerService {
    private ProducerDAO producerDAO;

    public ProducerDAO getProducerDAO() {
        return producerDAO;
    }

    public void setProducerDAO(ProducerDAO producerDAO) {
        this.producerDAO = producerDAO;
    }

    @Override
    public Mmedproducer getNew() {
        return getProducerDAO().getNew();
    }

    @Override
    public int getCountAll() {
        return getProducerDAO().getCountAll();
    }

    @Override
    public Mmedproducer getById(int prodId) {
        return getProducerDAO().getById(prodId);
    }

    @Override
    public List<Mmedproducer> getAll() {
        //return getProducerDAO().getAll(Mmedproducer.class);
        return getProducerDAO().getAll();
    }

    @Override
    public List<Mmedproducer> getListLikeName(String string) {
        return getProducerDAO().getListLikeName(string);
    }

    @Override
    public List<Mmedproducer> getListLikeCode(String string) {
        return getProducerDAO().getListLikeCode(string);
    }

    @Override
    public void saveOrUpdate(Mmedproducer entity) {
        getProducerDAO().saveOrUpdate(entity);
    }

    @Override
    public void delete(Mmedproducer entity) {
        getProducerDAO().delete(entity);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getProducerDAO().getAllLikeText(text, start, pageSize);
    }
}
