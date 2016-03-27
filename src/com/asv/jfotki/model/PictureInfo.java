/*
 * $Id: PictureInfo.java,v 1.2 2005/01/31 08:51:03 asv Exp $
 */

package com.asv.jfotki.model;

/**
 * @author Sergey Aleksandrov
 */
public class PictureInfo implements java.io.Serializable {
    private int id;
    private String name;
    private String description;
    private String note;
    private Album album;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Helper method for simplifying client code
     *
     * @return
     */
    public int getAlbumId() {
        return getAlbum().getId();
    }

    /**
     * Helper method for simplifying client code
     *
     * @param albumId
     */
    public void setAlbumId(int albumId) {
        checkIsAlbumNull();
        getAlbum().setId(albumId);
    }

    /**
     * Helper method for simplifying client code
     *
     * @return
     */
    public String getAlbumName() {
        checkIsAlbumNull();
        return getAlbum().getName();
    }

    /**
     * Helper method for simplifying client code
     *
     * @param albumName
     */
    public void setAlbumName(String albumName) {
        getAlbum().setName(albumName);
    }

    private void checkIsAlbumNull() {
        if (getAlbum() == null)
            setAlbum(new Album());
    }

}
