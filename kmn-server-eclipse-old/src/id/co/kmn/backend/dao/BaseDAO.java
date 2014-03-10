package id.co.kmn.backend.dao;

import id.co.kmn.backend.bean.ResultObject;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/2/12
 * Time: 6:01 AM
 */
public abstract interface BaseDAO<T> {
    /**
         * EN: Get a new entity object.<br>
         *
         * @return entity
         */
        public T getNew();

        /**
         * EN: Get an entity by its ID.<br>
         *
         * @param id / the persistence identifier
         * @return entity
         */
        public T getById(int id);

        /**
         * EN: Get an entity object by its Code.<br>
         *
         * @param code / the entity Code
         * @return entity
         */
        public T getByCode(String code);

        /**
         * EN: Get a list of all entity.<br>
         * @param entityClass / the entity class
         * @return List of entity
         */
        public List<T> getAll(Class<T> entityClass);

        /**
         * EN: Get a list of all entity.<br>
         * @return List of entity
         */
        public List<T> getAll();

        /**
         * EN: Get the count of all entity.<br>
         *
         * @return int
         */
        public int getCountAll();

        /**
         * EN: Gets a list of entity where the entity name contains the %string% .<br>
         *
         * @param string Name of the entity
         * @return List of entity
         */
        public List<T> getListLikeName(String string);

        /**
         * EN: Gets a list of entity where the entity code contains the %string%
         * .<br>
         *
         * @param string code of the entity
         * @return List of entity
         */
        public List<T> getListLikeCode(String string);

        /**
         * EN: Deletes an entity by its Id.<br>
         *
         * @param id / the persistence identifier
         */
        public void deleteById(int id);

        /**
         * EN: Saves new or updates an entity.<br>
         */
        public void saveOrUpdate(T entity);

        /**
         * EN: Deletes an entity.<br>
         */
        public void delete(T entity);

        /**
         * EN: Saves an entity.<br>
         */
        public void save(T entity);

        public ResultObject getAllLikeText(String text, int start, int pageSize);
}
