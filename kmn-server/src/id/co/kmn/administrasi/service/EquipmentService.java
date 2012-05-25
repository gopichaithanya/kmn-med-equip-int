package id.co.kmn.administrasi.service;

import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedequipment;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/25/12
 * Time: 5:06 AM
 */
public interface EquipmentService {
    /**
     * EN: Get a new Mmedequipment object.<br>
     *
     * @return Mmedequipment
     */
    public Mmedequipment getNew();

    /**
     * EN: Get the count of all Mmedequipment.<br>
     *
     * @return int
     */
    public int getCountAll();

    /**
     * EN: Get an Mmedequipment by its ID.<br>
     *
     * @param objId / the persistence identifier
     * @return Mmedequipment
     */
    public Mmedequipment getById(int objId);

    /**
     * EN: Get a list of all Mmedequipment.<br>
     *
     * @return List of Mmedequipment
     */
    public List<Mmedequipment> getAll();

    /**
     * EN: Gets a list of Mmedequipment where the Mmedequipment name contains the %string% .<br>
     *
     * @param string Name of the Mmedequipment
     * @return List of Mmedequipment
     */
    public List<Mmedequipment> getListLikeName(String string);

    /**
     * EN: Gets a list of Mmedequipment where the Mmedequipment code contains the %string%
     * .<br>
     *
     * @param string Code of the Mmedequipment
     * @return List of Mmedequipment
     */
    public List<Mmedequipment> getListLikeCode(String string);

    /**
     * EN: Saves new or updates an Mmedequipment.<br>
     * @param entity
     */
    public void saveOrUpdate(Mmedequipment entity);

    /**
     * EN: Deletes an Mmedequipment.<br>
     */
    public void delete(Mmedequipment entity);

    public ResultObject getAllLikeText(String text, int start, int pageSize);
}
