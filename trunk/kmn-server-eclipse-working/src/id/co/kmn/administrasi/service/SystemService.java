package id.co.kmn.administrasi.service;

import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedsystem;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 7:14 AM
 */
public interface SystemService {
    /**
     * EN: Get a new Mmedsystem object.<br>
     *
     * @return Mmedsystem
     */
    public Mmedsystem getNew();

    /**
     * EN: Get the count of all Mmedsystem.<br>
     *
     * @return int
     */
    public int getCountAll();

    /**
     * EN: Get an Mmedsystem by its ID.<br>
     *
     * @param objId / the persistence identifier
     * @return Mmedsystem
     */
    public Mmedsystem getById(int objId);

    /**
     * EN: Get a list of all Mmedsystem.<br>
     *
     * @return List of Mmedsystem
     */
    public List<Mmedsystem> getAll();

    /**
     * EN: Gets a list of Mmedsystem where the Mmedsystem name contains the %string% .<br>
     *
     * @param string Name of the Mmedsystem
     * @return List of Mmedsystem
     */
    public List<Mmedsystem> getListLikeName(String string);

    /**
     * EN: Gets a list of Mmedsystem where the Mmedsystem code contains the %string%
     * .<br>
     *
     * @param string Code of the Mmedsystem
     * @return List of Mmedsystem
     */
    public List<Mmedsystem> getListLikeCode(String string);

    /**
     * EN: Saves new or updates an Mmedsystem.<br>
     * @param entity
     */
    public void saveOrUpdate(Mmedsystem entity);

    /**
     * EN: Deletes an Mmedsystem.<br>
     * @param entity
     */
    public void delete(Mmedsystem entity);

    public ResultObject getAllLikeText(String text, int start, int pageSize);
}
