package id.co.kmn.administrasi.service;

import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedroom;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 3:20 AM
 */
public interface RoomService {
    /**
     * EN: Get a new Mmedroom object.<br>
     *
     * @return Mmedroom
     */
    public Mmedroom getNew();

    /**
     * EN: Get the count of all Mmedroom.<br>
     *
     * @return int
     */
    public int getCountAll();

    /**
     * EN: Get an Mmedroom by its ID.<br>
     *
     * @param objId / the persistence identifier
     * @return Mmedroom
     */
    public Mmedroom getById(int objId);

    /**
     * EN: Get a list of all Mmedroom.<br>
     *
     * @return List of Mmedroom
     */
    public List<Mmedroom> getAll();

    /**
     * EN: Gets a list of Mmedroom where the Mmedroom name contains the %string% .<br>
     *
     * @param string Name of the Mmedroom
     * @return List of Mmedroom
     */
    public List<Mmedroom> getListLikeName(String string);

    /**
     * EN: Gets a list of Mmedroom where the Mmedroom code contains the %string%
     * .<br>
     *
     * @param string Code of the Mmedroom
     * @return List of Mmedroom
     */
    public List<Mmedroom> getListLikeCode(String string);

    /**
     * EN: Saves new or updates an Mmedroom.<br>
     * @param entity
     */
    public void saveOrUpdate(Mmedroom entity);

    /**
     * EN: Deletes an Mmedproducer.<br>
     * @param entity
     */
    public void delete(Mmedroom entity);

    public ResultObject getAllLikeText(String text, int start, int pageSize);
}
