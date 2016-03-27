/**
 * Id$
 */
package com.asv.jfotki.web.servlet;

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.web.util.RedirectHelper;
import com.asv.jfotki.web.util.ServiceLocator;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Stores a picture.
 *
 * @author Sergey Aleksandrov
 */
public class FileUploadServlet extends HttpServlet {
    public String getRequired(HttpServletRequest request) {
        return ResourceBundle.getBundle("com.asv.Messages", request.getLocale()).getString("javax.faces.component.UIInput.REQUIRED");
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileUpload upload = new DiskFileUpload();
        try {
            List items = upload.parseRequest(request);

            Iterator iter = items.iterator();
            Picture picture = new Picture();
            String username = null;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fildValue = item.getString();
                    LogFactory.web.debug("Upload picture " + fieldName + " = " + fildValue);
                    if ("albumId".equals(fieldName)) {
                        picture.setAlbumId(Integer.parseInt(fildValue));
                    } else if ("user".equals(fieldName)) {
                        username = fildValue;
                    }  else if ("pictureName".equals(fieldName)) {
                        if (!checkOnNullAndLength(request, response, fildValue))
                            return;
                        picture.setName(fildValue);
                    }  else if ("pictureDescription".equals(fieldName)) {
                        picture.setDescription(fildValue);
                    }
                } else {
                    if (!checkOnNullAndLength(request, response, item.get()))
                        return;
                    picture.setBigPicture(item.get());
                }
            }
            ServiceLocator.getInstance(getServletContext()).getPictureService().createPicture(picture);
            response.sendRedirect(new RedirectHelper(picture.getAlbumId(), username).getRedirectUrl());
        } catch (Exception e) {
            LogFactory.web.error("Cannot upload picture! ", e);
            throw new ServletException("Cannot upload picture! ", e);
        }
	}

    private boolean checkOnNullAndLength(HttpServletRequest request, HttpServletResponse response, byte[] bytes) throws Exception  {
        if (bytes != null && bytes.length > 0)  {
            return true;
        }
        back(request, response, getRequired(request));
        return false;
    }

    private boolean checkOnNullAndLength(HttpServletRequest request, HttpServletResponse response, String str) throws Exception  {
        if (StringUtils.isNotEmpty(str))  {
            return true;
        }
        back(request, response, getRequired(request));
        return false;
    }


    private void back(HttpServletRequest request, HttpServletResponse response, String message) throws Exception {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/editPicture.jsf").forward(request, response);
    }
}
