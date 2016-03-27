/*
 * $Id: AlbumDAOImpl.java,v 1.2 2005/01/24 10:14:34 asv Exp $
 */

package com.asv.jfotki.business.dao.impl;

import com.asv.jfotki.business.dao.AlbumDAO;
import com.asv.jfotki.model.Album;
import com.asv.jfotki.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * An implementation of {@link AlbumDAO} with help of Spring.   
 *
 * @author Sergey Aleksandrov
 */
public class AlbumDAOImpl extends JdbcDaoSupport implements AlbumDAO {

    public static final String INSERT = "insert into album (a_name, a_user_id) values (?,?)";
    public static final String SELECT = "select a_id, a_name  from album order by a_id";
    public static final String SELECT_BY_USERNAME = "select a.a_id a_id, a.a_name a_name  " +
                                                    "from album a, security_user s " +
                                                    "where s.A_ID = a.A_USER_ID and s.A_USERNAME = ? " +
                                                    "order by a_id";
//    public static final String SELECT_BY_ID = "select a.a_id a_id, a.a_name a_name, a_username, a_fullname  " +
//                                              "from album a, security_user s " +
//                                              "where s.A_ID = a.A_USER_ID and a.A_ID = ? ";
    public static final String SELECT_BY_ID = "select a.a_id a_id, a.a_name a_name " +
                                              "from album a " +
                                              "where a.A_ID = ? ";


    public List getAllAlbums() {
        return getJdbcTemplate().query(SELECT, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                Album album = new Album();
                album.setId(resultSet.getInt(1));
                album.setName(resultSet.getString(2));
                return album;
            }
        });
    }

    public List getAlbumsByUserName(String username) {
        return getJdbcTemplate().query(SELECT_BY_USERNAME, new Object[] { username }, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                Album album = new Album();
                album.setId(resultSet.getInt(1));
                album.setName(resultSet.getString(2));
                return album;
            }
        });

    }

    public Album getAlbum(int id) {
        List albums = getJdbcTemplate().query(SELECT_BY_ID, new Object[] { new Integer(id) }, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                Album album = new Album();
                album.setId(resultSet.getInt(1));
                album.setName(resultSet.getString(2));
                // TODY may be will be useful in future
                //User user = new User();
                //user.setUsername(resultSet.getString(3));
                //user.setFullName(resultSet.getString(4));
                //album.setUser(user);
                return album;
            }
        });
        if (albums != null && albums.size() == 1)
            return (Album) albums.get(0);
        else
            return null;

    }

    /**
     * Only for JDBC 3.0 Drivers
     *
     * @param album
     * @return
     */
    public int create2(final Album album) {
        SqlUpdate su = new SqlUpdate(getDataSource(), INSERT);
        su.declareParameter(new SqlParameter("a_creator", Types.VARCHAR));
        su.declareParameter(new SqlParameter("a_name", Types.VARCHAR));

        su.setReturnGeneratedKeys(true);
        su.compile();
        Object[] val = {album.getName()};

        KeyHolder kh = new GeneratedKeyHolder();
        su.update(val, kh);
        return kh.getKey().intValue();
    }

    public void create(final Album album) {
        getJdbcTemplate().update(INSERT, new Object[] {album.getName(),
                                                       new Integer(album.getUser().getId())});
    }

}
