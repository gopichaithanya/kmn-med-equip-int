/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package id.co.kmn.backend.dao.impl;

import id.co.kmn.backend.dao.GuestBookDAO;
import id.co.kmn.backend.model.GuestBook;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * EN: DAO methods implementation for the <b>Guestbook</b> model class.<br>
 * DE: DAO Methoden Implementierung fuer die <b>Guestbook</b> Model Klasse.<br>
 *
 * @author bbruhns
 * @author Stephan Gerth
 */
@Repository
public class GuestBookDAOImpl extends BasisDAO<GuestBook> implements GuestBookDAO {

    @Override
    public GuestBook getNewGuestBook() {
        return new GuestBook();
    }

    @Override
    public int getCountAllGuestBooks() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from GuestBook"));
    }
}
