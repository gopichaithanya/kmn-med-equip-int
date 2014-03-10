package id.co.kmn.administrasi.dao.impl;

import id.co.kmn.administrasi.dao.RoomDAO;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.dao.impl.BaseDAOImpl;
import id.co.kmn.backend.model.Mmedroom;
import id.co.kmn.util.ConstantUtil;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 3:14 AM
 */
public class RoomDAOImpl extends BaseDAOImpl<Mmedroom> implements RoomDAO {
    /**
     * constructor
     */
    protected RoomDAOImpl() {
        super(Mmedroom.class);
    }

    @Override
    public Mmedroom getNew() {
        return new Mmedroom();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mmedroom getByCode(String code) {
        return getByCode(ConstantUtil.ROOM_CODE, code);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedroom> getListLikeName(String string) {
        return getListLikeName(ConstantUtil.ROOM_NAME, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mmedroom> getListLikeCode(String string) {
        return getListLikeCode(ConstantUtil.ROOM_CODE, string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultObject getAllLikeText(String text, int start, int pageSize) {
        return getAllLikeText(ConstantUtil.ROOM_NAME, text, start, pageSize);
    }
}
