package id.co.kmn.administrasi.service.impl;

import id.co.kmn.administrasi.dao.SystemDAO;
import id.co.kmn.administrasi.service.SystemService;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedsystem;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 7:14 AM
 */
public class SystemServiceImpl implements SystemService {
        private SystemDAO systemDAO;

    public SystemDAO getSystemDAO() {
        return systemDAO;
    }

    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }

    @Override
    public Mmedsystem getNew() {
        return getSystemDAO().getNew();
    }

    @Override
    public int getCountAll() {
        return getSystemDAO().getCountAll();
    }

    @Override
    public Mmedsystem getById(int objId) {
        return getSystemDAO().getById(objId);
    }

    @Override
    public List<Mmedsystem> getAll() {
        return getSystemDAO().getAll();
    }

    @Override
    public List<Mmedsystem> getListLikeName(String string) {
        return getSystemDAO().getListLikeName(string);
    }

    @Override
    public List<Mmedsystem> getListLikeCode(String string) {
        return getSystemDAO().getListLikeCode(string);
    }

    @Override
    public void saveOrUpdate(Mmedsystem entity) {
        getSystemDAO().saveOrUpdate(entity);
    }

    @Override
    public void delete(Mmedsystem entity) {
        getSystemDAO().delete(entity);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getSystemDAO().getAllLikeText(text, start, pageSize);
    }
}
