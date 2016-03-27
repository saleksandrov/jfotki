/*
 * $Id: Picture.java,v 1.2 2005/01/17 15:16:32 asv Exp $
 */

package com.asv.jfotki.model;

/**
 * Model class which contains Picture information.
 *
 * @author Sergey Aleksandrov
 */
public class Picture extends PictureInfo {

    private byte[] bigPicture;
    private byte[] smallPicture;

    public byte[] getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(byte[] bigPicture) {
        this.bigPicture = bigPicture;
    }

    public byte[] getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(byte[] smallPicture) {
        this.smallPicture = smallPicture;
    }

}
