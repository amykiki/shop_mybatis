package shop.web.filter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.util.ShopException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Amysue on 2016/4/10.
 */
public class MultiPartWrapper extends HttpServletRequestWrapper {
    private              Map<String, String[]> params      = null;
    private              Logger                logger      = null;
    private              HttpServletRequest    request     = null;
    //upload file will be save to temp 500KB
    private static final int                   maxMemSize  = 500 * 1024;
    //max file will be drop 20M
    private static final long                  maxFileSize = 1024 * 1024 * 20;

    public MultiPartWrapper(HttpServletRequest request) throws FileUploadException {
        super(request);
        logger = LogManager.getLogger(this);
        params = new HashMap<>();
        this.request = request;
        setParameter();
    }

    private void setParameter() throws FileUploadException {
        // get servlet parameter
        ServletContext context = request.getServletContext();
        String         tempdir = context.getInitParameter("tempdir");
        logger.debug("Tempdir = " + tempdir);
        FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(context);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);

        String tempPath   = getServletContext().getRealPath("") + File.separator + tempdir;
        File   repository = new File(tempPath);
        if (!repository.exists()) {
            repository.mkdir();
        }
        factory.setRepository(repository);
        factory.setFileCleaningTracker(fileCleaningTracker);

        ServletFileUpload upload = new ServletFileUpload(factory);

        //解决上传文件名的中文乱码
        upload.setHeaderEncoding("UTF-8");
        // TODO: 2016/4/11   有解决不了的问题，当设置了最大上传值后，一旦上传的超过最大上传值，上传会失败，request也得不到
        // TODO: 2016/4/11  解析，先不设置最大上传值，在后续代码解决
//        upload.setFileSizeMax(maxFileSize);

        List<FileItem>     items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            throw e;
        }
        Iterator<FileItem> iter  = items.iterator();
        while (iter.hasNext()) {
            FileItem item = iter.next();
            if (item.isFormField()) {
                processFormFiled(item);
            } else {
                processUploadFile(item);
            }
        }

    }

    private void processUploadFile(FileItem item) {
        String fieldName = item.getFieldName();
        String fileName  = item.getName();
        /*
        注意不同浏览器提交的文件名是不同的，有些浏览器提交的文件名是带路径的，比如d:\b\c\a.txt
        有些则不带文件路径，如a.txt
        */
        Map<String, String> errMap = (Map<String, String>) request.getAttribute("errMap");
        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        logger.debug("fileName=*" + fileName + "*");
        try {
            InputStream is       = item.getInputStream();
            int         fileSize = is.available();
            logger.debug(fileSize + "=" + fileSize);
            if (fileSize == 0) {
                logger.debug("没有上传文件");
                errMap.put(fieldName, "没有上传的文件");
            }
            if (fileSize > maxFileSize) {
                logger.debug("上传文件大小过大，不能上传");
                errMap.put(fieldName, "上传文件大小过大，不能上传");
                item.delete();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileName == null || fileName.trim().equals("")) {
            errMap.put(fieldName, "没有上传的文件");
        }
        request.setAttribute("fileItem-" + fieldName, item);
        fileName = makeFileName(fileName);
        params.put(fieldName, new String[]{"/img/" + fileName});
    }


    /**
     * 生成上传文件的文件名，uuid为随机产生的id，防止文件名相同覆盖的问题
     *
     * @param fileName 文件原始名称
     * @return uuid + "_" + 文件原始名称;
     */
    private String makeFileName(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    private void processFormFiled(FileItem item) {
        String fieldName  = item.getFieldName();
        String fieldValue = null;
        try {
            //解决普通输入项的数据的中文乱码问题
            fieldValue = item.getString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (params.containsKey(fieldName)) {
            String[] vs = params.get(fieldName);
            vs = Arrays.copyOf(vs, vs.length + 1);
            vs[vs.length - 1] = fieldValue;
        } else {
            String[] values = new String[]{fieldValue};
            params.put(fieldName, values);
        }
    }

    @Override
    public String getParameter(String name) {
        String[] vs = params.get(name);
        if (vs == null) {
            return null;
        }
        return vs[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> e = Collections.enumeration(params.keySet());
        return e;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] vs = params.get(name);
        if (vs != null) {
            return vs;
        } else {
            return null;
        }
    }
}
