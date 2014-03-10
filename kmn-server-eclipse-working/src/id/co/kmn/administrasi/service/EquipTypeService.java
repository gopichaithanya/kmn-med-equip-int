package id.co.kmn.administrasi.service;

import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedequiptype;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 5:13 AM
 */
public interface EquipTypeService {
    /**
     * EN: Get a new Mmedequiptype object.<br>
     *
     * @return Mmedequiptype
     */
    public Mmedequiptype getNew();

    /**
     * EN: Get the count of all Mmedequiptype.<br>
     *
     * @return int
     */
    public int getCountAll();

    /**
     * EN: Get an Mmedequiptype by its ID.<br>
     *
     * @param objId / the persistence identifier
     * @return Mmedequiptype
     */
    public Mmedequiptype getById(int objId);

    /**
     * EN: Get a list of all Mmedequiptype.<br>
     *
     * @return List of Mmedequiptype
     */
    public List<Mmedequiptype> getAll();

    /**
     * EN: Gets a list of Mmedequiptype where the Mmedequiptype name contains the %string% .<br>
     *
     * @param string Name of the Mmedequiptype
     * @return List of Mmedequiptype
     */
    public List<Mmedequiptype> getListLikeName(String string);

    /**
     * EN: Gets a list of Mmedequiptype where the Mmedequiptype code contains the %string%
     * .<br>
     *
     * @param string Code of the Mmedequiptype
     * @return List of Mmedequiptype
     */
    public List<Mmedequiptype> getListLikeCode(String string);

    /**
     * EN: Saves new or updates an Mmedequiptype.<br>
     * @param entity
     */
    public void saveOrUpdate(Mmedequiptype entity);

    /**
     * EN: Deletes an Mmedequiptype.<br>
     * @param entity
     */
    public void delete(Mmedequiptype entity);

    public ResultObject getAllLikeText(String text, int start, int pageSize);
}
