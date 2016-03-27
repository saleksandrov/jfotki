package com.asv.jfotki.web.servlet;

import com.asv.jfotki.web.util.ServiceLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.NumberUtils;

/**
 * Outputs a picture in UI.
 *
 * @author Sergey Aleksandrov
 */
public class ImageServlet extends HttpServlet {

    public static final String TYPE_SMALL = "small";
    public static final String TYPE_BIG   = "big";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("Not supported method!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String pictureIdString = request.getParameter("pictureId");
        if (NumberUtils.isNumber(pictureIdString)) {
            int pictureId = Integer.parseInt(pictureIdString);

            response.setContentType("image/jpeg");
            OutputStream outputStream = response.getOutputStream();
            byte[] pictureBytes;
            if (TYPE_BIG.equalsIgnoreCase(type)) {
                pictureBytes = ServiceLocator.getInstance(getServletContext()).getPictureService().getBigImage(pictureId);
            } else {
                pictureBytes = ServiceLocator.getInstance(getServletContext()).getPictureService().getSmallImage(pictureId);
            }
            writePictureBytes(outputStream, pictureBytes);
            outputStream.close();
        }
    }

    private void writePictureBytes(OutputStream stream, byte[] pictureBytes) throws IOException {
        if (pictureBytes != null) {
            stream.write(pictureBytes);
        }
    }
}
