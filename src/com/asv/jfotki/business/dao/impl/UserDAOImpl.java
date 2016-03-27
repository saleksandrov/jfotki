/*
 * $Id: UserDAOImpl.java,v 1.2 2005/03/09 07:49:34 asv Exp $
 */

package com.asv.jfotki.business.dao.impl;

import com.asv.jfotki.business.dao.UserDAO;
import com.asv.jfotki.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * @author Sergey Aleksandrov
 */
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {

    public static final String SELECT_USER_BY_USERNAME =  "SELECT a_id, a_username, a_password, a_enabled, a_fullname, a_email " +
                                               "FROM security_user WHERE a_username = ?";
    public static final String INSERT =  "INSERT INTO security_user (a_username, a_password, a_enabled, a_fullname, a_email) " +
                                         "values(?,?,?,?,?)";


    public User getUser(String username) {
        List users = getJdbcTemplate().query(
                SELECT_USER_BY_USERNAME,
                new Object[] {username},
                new RowMapper() {
                     public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                         User user = new User();
                         user.setId(resultSet.getInt("A_ID"));
                         user.setUsername(resultSet.getString("A_USERNAME"));
                         user.setPassword(resultSet.getString("A_PASSWORD"));
                         user.setEnabled(resultSet.getBoolean("A_ENABLED"));
                         user.setFullName(resultSet.getString("A_FULLNAME"));
                         user.setEmail(resultSet.getString("A_EMAIL"));
                         return user;
                     }
        });
        if (users != null && users.size() == 1)
            return (User) users.get(0);
        else
            return null;
    }

     public void create(final User user) {
         SqlUpdate su = new SqlUpdate(getDataSource(), INSERT);
         su.declareParameter(new SqlParameter("A_USERNAME", Types.VARCHAR));
         su.declareParameter(new SqlParameter("A_PASSWORD", Types.VARCHAR));
         su.declareParameter(new SqlParameter("A_ENABLED", Types.INTEGER));
         su.declareParameter(new SqlParameter("A_FULLNAME", Types.VARCHAR));
         su.declareParameter(new SqlParameter("A_EMAIL", Types.VARCHAR));

         su.compile();
         Object paramValues[] = new Object[] {
                                   user.getUsername(),
                                   user.getPassword(),
                                   new Integer(1),
                                   user.getFullName(),
                                   user.getEmail()
         };
         su.update(paramValues);

         User createdUser = getUser(user.getUsername());
         
    }
}
