package id.co.kmn.administrasi.service;

import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Tmedequipment;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 8/1/12
 * Time: 1:09 AM
 */
public interface TransactionService {
    /**
     * EN: Get a new Tmedequipment object.<br>
     *
     * @return Tmedequipment
     */
    public Tmedequipment getNew();

    /**
     * EN: Get the count of all Tmedequipment.<br>
     *
     * @return int
     */
    public int getCountAll();

    /**
     * EN: Get an Tmedequipment by its ID.<br>
     *
     * @param objId / the persistence identifier
     * @return Tmedequipment
     */
    public Tmedequipment getById(int objId);

    /**
     * EN: Get a list of all Tmedequipment.<br>
     *
     * @return List of Tmedequipment
     */
    public List<Tmedequipment> getAll();

    /**
     * EN: Gets a list of Tmedequipment where the Tmedequipment name contains the %string% .<br>
     *
     * @param string Name of the Tmedequipment
     * @return List of Tmedequipment
     */
    public List<Tmedequipment> getListLikeName(String string);

    /**
     * EN: Gets a list of Tmedequipment where the Tmedequipment code contains the %string%
     * .<br>
     *
     * @param string Code of the Tmedequipment
     * @return List of Tmedequipment
     */
    public List<Tmedequipment> getListLikeCode(String string);

    /**
     * EN: Saves new or updates an Tmedequipment.<br>
     * @param entity
     */
    public void saveOrUpdate(Tmedequipment entity);

    /**
     * EN: Deletes an Tmedequipment.<br>
     */
    public void delete(Tmedequipment entity);

    public ResultObject getAllLikeText(String text, int start, int pageSize);
}
