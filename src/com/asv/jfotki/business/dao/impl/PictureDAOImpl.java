/*
 * $Id: PictureDAOImpl.java,v 1.3 2005/01/24 10:14:34 asv Exp $
 */

package com.asv.jfotki.business.dao.impl;

import com.asv.jfotki.business.dao.PictureDAO;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.lob.LobHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * An implementation of {@link PictureDAO} with help of Spring.
 *
 * @author Sergey Aleksandrov
 */
public class PictureDAOImpl extends JdbcDaoSupport implements PictureDAO {

    private LobHandler lobHandler;

    /**
     * Used for Oracle specific work with BLOB.
     *
     * @param lobHandler
     */
    public void setLobHandler(final LobHandler lobHandler) {
        this.lobHandler = lobHandler;
    }

    public static final String INSERT =
            "INSERT INTO PICTURE (A_NAME, " +
                                 "A_DESCRIPTION, " +
                                 "A_NOTE, " +
                                 "A_BIG_PICTURE, " +
                                 "A_SMALL_PICTURE, " +
                                 "A_ALBUM_ID) VALUES (?,?,?,?,?,?) ";

    public static final String SELECT_BIG_IMAGE = "SELECT A_BIG_PICTURE FROM PICTURE WHERE A_ID = ?";

    public static final String SELECT_SMALL_IMAGE = "SELECT A_SMALL_PICTURE FROM PICTURE WHERE A_ID = ?";

    public static final String SELECT_PICTURE_BY_ALBUM_ID =
            "SELECT P.*, A.A_NAME  ALBUM_NAME FROM PICTURE P, ALBUM A WHERE P.a_album_id = A.a_id AND A.A_ID = ? ORDER BY P.A_ID";

    public static final String SELECT_PICTURE_BY_ID =
                "SELECT P.*, A.A_NAME  ALBUM_NAME FROM PICTURE P, ALBUM A WHERE P.a_album_id = A.a_id AND P.A_ID = ?";

    public static final String SELECT_MAX_ID = "select max(a_id) from picture where a_album_id = ?";
    public static final String SELECT_MIN_ID = "select min(a_id) from picture where a_album_id = ?";
    public static final String SELECT_NEXT_ID = "select a_id from (select * from picture order by a_id) p where ROWNUM = 1 and a_album_id = ? and p.a_id > ? ";

    //public static final String SELECT_ALL_PICTURES = "SELECT A_ID, A_NAME, A_DESCRIPTION, A_NOTE, A_BIG_PICTURE, A_ALBUM_ID FROM PICTURE ";

    public static final String SELECT_ALL_PICTURES = "SELECT * FROM PICTURE ";

    public static final String DELETE_PICTURE = "delete from picture where a_id = ?";

    public void create(final Picture picture) {
        SqlUpdate su = new SqlUpdate(getDataSource(), INSERT);
        su.declareParameter(new SqlParameter("A_NAME", Types.VARCHAR));
        su.declareParameter(new SqlParameter("A_DESCRIPTION", Types.VARCHAR));
        su.declareParameter(new SqlParameter("A_NOTE", Types.VARCHAR));
        su.declareParameter(new SqlParameter("A_BIG_PICTURE", Types.BLOB));
        su.declareParameter(new SqlParameter("A_SMALL_PICTURE", Types.BLOB));
        su.declareParameter(new SqlParameter("A_ALBUM_ID",Types.INTEGER));
        su.compile();
        Object paramValues[] = new Object[] {
                                   picture.getName(),
                                   picture.getDescription(),
                                   picture.getNote(),
                                   new SqlLobValue(picture.getBigPicture(), lobHandler),
                                   new SqlLobValue(picture.getSmallPicture(), lobHandler),
                                   new Integer(picture.getAlbumId())
        };
        su.update(paramValues);
    }

    public List getPictures(int albumId) {
       return getJdbcTemplate().query(
               SELECT_PICTURE_BY_ALBUM_ID,
               new Object[] {new Integer(albumId)},
               new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                        PictureInfo picture = new Picture();
                        picture.setId(resultSet.getInt("A_ID"));
                        picture.setName(resultSet.getString("A_NAME"));
                        picture.setNote(resultSet.getString("A_NOTE"));
                        picture.setAlbumId(resultSet.getInt("A_ALBUM_ID"));
                        picture.setAlbumName(resultSet.getString("ALBUM_NAME"));
                        return picture;
                    }
        });
    }

    /**
     * Used for export utility.
     *
     * @return
     */
    public List getAllPictures() {
       return getJdbcTemplate().query(
               SELECT_ALL_PICTURES,
               new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                        Picture picture = new Picture();
                        picture.setId(resultSet.getInt("A_ID"));
                        picture.setName(resultSet.getString("A_NAME"));
                        picture.setNote(resultSet.getString("A_NOTE"));
                        picture.setDescription(resultSet.getString("A_DESCRIPTION"));
                        picture.setAlbumId(resultSet.getInt("A_ALBUM_ID"));
                        picture.setBigPicture(lobHandler.getBlobAsBytes(resultSet,5));
                        return picture;
                    }
        });
    }

    public PictureInfo getPicture(int pictureId) {
        List pictures = getJdbcTemplate().query(
                SELECT_PICTURE_BY_ID,
                new Object[] {new Integer(pictureId)},
                new RowMapper() {
                     public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                         PictureInfo picture = new Picture();
                         picture.setId(resultSet.getInt("A_ID"));
                         picture.setName(resultSet.getString("A_NAME"));
                         picture.setNote(resultSet.getString("A_NOTE"));
                         picture.setDescription(resultSet.getString("A_DESCRIPTION"));
                         picture.setAlbumId(resultSet.getInt("A_ALBUM_ID"));
                         picture.setAlbumName(resultSet.getString("ALBUM_NAME"));
                         return picture;
                     }
        });
        if (pictures != null && pictures.size() == 1)
            return (PictureInfo) pictures.get(0);
        else
            return null;
    }

    public PictureInfo nextPicture(int pictureId,  int albumId) {
        int maxId = getJdbcTemplate().queryForInt(SELECT_MAX_ID, new Object[] {new Integer(albumId)});
        int pictureIdForQuery;

        if (pictureId == maxId) {
            pictureIdForQuery = getJdbcTemplate().queryForInt(SELECT_MIN_ID, new Object[] {new Integer(albumId)});
        }  else {
            pictureIdForQuery = getJdbcTemplate().queryForInt(SELECT_NEXT_ID,
                    new Object[] {new Integer(albumId), new Integer(pictureId)});
        }

        return getPicture(pictureIdForQuery);
    }

    public byte[] getBigImage(int pictureId)  {
        return getImage(pictureId, SELECT_BIG_IMAGE);
    }

    public byte[] getSmallImage(int pictureId) {
        return getImage(pictureId, SELECT_SMALL_IMAGE);
    }

    private byte[] getImage(int pictureId, String sqlQuery) {
       List pictures = getJdbcTemplate().query(
                sqlQuery,
                new Object[] {new Integer(pictureId)},
                new RowMapper() {
                     public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                         return lobHandler.getBlobAsBytes(resultSet,1);
                     }
        });
        if (pictures != null && pictures.size() == 1)
            return (byte[]) pictures.get(0);
        else
            return null;
    }

    public void delete(int pictureId) {
        getJdbcTemplate().update(DELETE_PICTURE, new Object[] { new Integer(pictureId) });
    }

}
