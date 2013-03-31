package id.co.kmn.administrasi.service.impl;

import id.co.kmn.administrasi.dao.RoomDAO;
import id.co.kmn.administrasi.service.RoomService;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedroom;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 3:21 AM
 */
public class RoomServiceImpl implements RoomService{
    private RoomDAO roomDAO;

    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Override
    public Mmedroom getNew() {
        return getRoomDAO().getNew();
    }

    @Override
    public int getCountAll() {
        return getRoomDAO().getCountAll();
    }

    @Override
    public Mmedroom getById(int objId) {
        return getRoomDAO().getById(objId);
    }

    @Override
    public List<Mmedroom> getAll() {
        return getRoomDAO().getAll();
    }

    @Override
    public List<Mmedroom> getListLikeName(String string) {
        return getRoomDAO().getListLikeName(string);
    }

    @Override
    public List<Mmedroom> getListLikeCode(String string) {
        return getRoomDAO().getListLikeCode(string);
    }

    @Override
    public void saveOrUpdate(Mmedroom entity) {
        getRoomDAO().saveOrUpdate(entity);
    }

    @Override
    public void delete(Mmedroom entity) {
        getRoomDAO().delete(entity);
    }

    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getRoomDAO().getAllLikeText(text, start, pageSize);
    }
}
