package id.co.kmn.administrasi.service;

import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedproducer;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/1/12
 * Time: 12:06 PM
 */
public interface ProducerService {
    /**
     * EN: Get a new Mmedproducer object.<br>
     *
     * @return Mmedproducer
     */
    public Mmedproducer getNew();

    /**
     * EN: Get the count of all Mmedproducer.<br>
     *
     * @return int
     */
    public int getCountAll();

    /**
     * EN: Get an Mmedproducer by its ID.<br>
     *
     * @param prodId / the persistence identifier
     * @return Mmedproducer
     */
    public Mmedproducer getById(int prodId);

    /**
     * EN: Get a list of all Mmedproducer.<br>
     *
     * @return List of Mmedproducer
     */
    public List<Mmedproducer> getAll();

    /**
     * EN: Gets a list of Mmedproducer where the Mmedproducer name contains the %string% .<br>
     *
     * @param string Name of the Mmedproducer
     * @return List of Mmedproducer
     */
    public List<Mmedproducer> getListLikeName(String string);

    /**
     * EN: Gets a list of Mmedproducer where the Mmedproducer code contains the %string%
     * .<br>
     *
     * @param string Code of the Mmedproducer
     * @return List of Mmedproducer
     */
    public List<Mmedproducer> getListLikeCode(String string);

    /**
     * EN: Saves new or updates an Mmedproducer.<br>
     * @param entity
     */
    public void saveOrUpdate(Mmedproducer entity);

    /**
     * EN: Deletes an Mmedproducer.<br>
     * @param entity
     */
    public void delete(Mmedproducer entity);

    public ResultObject getAllLikeText(String text, int start, int pageSize);
}
