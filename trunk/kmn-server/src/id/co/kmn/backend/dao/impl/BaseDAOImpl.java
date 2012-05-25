package id.co.kmn.backend.dao.impl;

import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.dao.BaseDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/2/12
 * Time: 6:03 AM
 */
public abstract class BaseDAOImpl<T> implements BaseDAO<T> {
    private HibernateOperations hibernateTemplate;
    private Class<T> type;
    /**
     * constructor
     */
    protected BaseDAOImpl() {}

    protected BaseDAOImpl(Class<T> type) {
        this.type = type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Class<T> type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public Class<T> getType() {
        return type;
    }

    protected HibernateOperations getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateOperations hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    protected void initialize(final Object proxy) throws DataAccessException {
        hibernateTemplate.initialize(proxy);
    }

    protected T merge(T entity) throws DataAccessException {
        return (T) hibernateTemplate.merge(entity);
    }

    protected void persist(T entity) throws DataAccessException {
        hibernateTemplate.persist(entity);
    }

    public void refresh(T entity) throws DataAccessException {
        hibernateTemplate.refresh(entity);
    }

    public void save(T entity) throws DataAccessException {
        hibernateTemplate.save(entity);
    }

    public void saveOrUpdate(T entity) throws DataAccessException {
        hibernateTemplate.saveOrUpdate(entity);
    }

    // public void saveOrUpdateAll(Collection<T> entities) throws
    // DataAccessException {
    // for (T entity : entities) {
    // saveOrUpdate(entity);
    // }
    // }

    public void update(T entity) throws DataAccessException {
        hibernateTemplate.update(entity);
    }

    public void delete(T entity) throws DataAccessException {
        hibernateTemplate.delete(entity);
    }

    protected void deleteAll(Collection<T> entities) throws DataAccessException {
        hibernateTemplate.deleteAll(entities);
    }

    protected T get(Class<T> entityClass, Serializable id) throws DataAccessException {
        return (T) hibernateTemplate.get(entityClass, id);
    }

    protected T get(Serializable id) throws DataAccessException {
        return (T) hibernateTemplate.get(getType(), id);
    }

    protected T getById(Serializable id) throws DataAccessException {
        return get(id);
    }

    public T getById(int id) {
        return get(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void throwError(String error) throws DataAccessException {
        throw new RuntimeException(error);
    }

    public List<T> getAll(Class<T> entityClass) throws DataAccessException {
        return hibernateTemplate.loadAll(entityClass);
    }

    public List<T> getAll() throws DataAccessException {
        return hibernateTemplate.loadAll(getType());
    }

    public int getCountAll() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from " +getType().getSimpleName()));
    }

    public void deleteById(int id) {
        T obj = getById(id);
        if (obj != null) {
            delete(obj);
        }
    }

    public ResultObject getAllLikeText(String propertyName, String text, int start, int pageSize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(getType());
        if (!StringUtils.isEmpty(text)) {
            criteria.add(Restrictions.ilike(propertyName, text, MatchMode.ANYWHERE));
        }
        criteria.addOrder(Order.asc(propertyName));
        int totalCount = getHibernateTemplate().findByCriteria(criteria).size();
        List<T> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
        return new ResultObject(list, totalCount);
    }

    protected T getByCode(String propertyName, String prodCode) {
        DetachedCriteria criteria = DetachedCriteria.forClass(getType());
        criteria.add(Restrictions.eq(propertyName, prodCode));
        return (T) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    protected List<T> getListLikeName(String propertyName, String string) {
        DetachedCriteria criteria = DetachedCriteria.forClass(getType());
        criteria.add(Restrictions.ilike(propertyName, string, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    protected List<T> getListLikeCode(String propertyName, String string) {
        DetachedCriteria criteria = DetachedCriteria.forClass(getType());
        criteria.add(Restrictions.ilike(propertyName, string, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }
}
