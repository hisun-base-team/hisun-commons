package com.hisun.util;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by zhouying on 2017/9/20.
 */
public class CompressUtil {

    private static final Logger log = Logger.getLogger(CompressUtil.class);


    /**
     * @param zipFile  zip结果文件的名称（含路径）
     * @param filePath 需要压缩的文件所在的目录
     * @param zipPathName zip压缩文件中的目录名称
     * @throws Exception
     */
    public static void zip(String zipFile, String filePath, String zipPathName) throws Exception {

        File f = new File(zipFile);
        OutputStream out = new FileOutputStream(f);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(bos);
        zaos.setEncoding("UTF-8");

        if (zipPathName!=null && zipPathName.equals("")==false) {
            zipPathName = zipPathName + File.separator;
        } else {
            zipPathName = f.getName().substring(0,f.getName().lastIndexOf(".")) + File.separator;
        }
        zip(zaos, filePath, zipPathName);

        zaos.flush();
        zaos.close();
        bos.flush();
        bos.close();
        out.flush();
        out.close();
    }

    /**
     * @param zaos     流
     * @param filePath 需要打包的目录
     * @param pathName 打包到pathName的目录下
     * @throws FileNotFoundException
     * @throws IOException
     */

    public static void zip(ZipArchiveOutputStream zaos, String filePath, String pathName) throws FileNotFoundException, IOException {

        File file2zip = new File(filePath);
        if (file2zip.isFile()) {
            zaos.putArchiveEntry(new ZipArchiveEntry(pathName + file2zip.getName()));
            FileInputStream fis = new FileInputStream(file2zip.getAbsolutePath());
            IOUtils.copy(fis, zaos);
            fis.close();
            zaos.closeArchiveEntry();
        } else {
            File[] files = file2zip.listFiles();
            if (files == null || files.length < 1) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    zip(zaos, files[i].getAbsolutePath(), pathName + files[i].getName() + File.separator);
                } else {
                    zaos.putArchiveEntry(new ZipArchiveEntry(pathName + files[i].getName()));
                    FileInputStream fis = new FileInputStream(files[i].getAbsolutePath());
                    IOUtils.copy(fis, zaos);
                    fis.close();
                    zaos.closeArchiveEntry();
                }
            }
        }
    }

    /**
     * @param zipFileName  压缩文件名
     * @param zip2FileName 解压路径
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void unzip(String zipFileName, String zip2FileName) throws IOException {

        File zipfile = new File(zipFileName);
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        ZipFile zf = null;
        try {
            zip2FileName = zip2FileName + File.separator;
            FileUtils.forceMkdir(new File(zip2FileName));
            zf = new ZipFile(zipfile, "UTF-8");
            Enumeration zipArchiveEntrys = zf.getEntries();
            while (zipArchiveEntrys.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(zip2FileName + zipArchiveEntry.getName() + File.separator));
                } else {
                    fileOutputStream = FileUtils.openOutputStream(new File(zip2FileName + zipArchiveEntry.getName()));
                    inputStream = zf.getInputStream(zipArchiveEntry);
                    IOUtils.copy(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            throw new IOException("找不到文件：" + zipFileName);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
                fileOutputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (zf != null) {
                ZipFile.closeQuietly(zf);
            }
        }
    }

    /**
     * @param zipFileName  压缩文件名
     * @param zip2FileName 解压路径
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void unzipImage(String zipFileName, String zip2FileName) throws IOException {

        File zipfile = new File(zipFileName);
        FileOutputStream fileOutputStream = null;
        ZipFile zf = null;
        InputStream inputStream = null;
        try {
            zip2FileName = zip2FileName + File.separator;
            FileUtils.forceMkdir(new File(zip2FileName));
            zf = new ZipFile(zipfile, "GBK");
            Enumeration zipArchiveEntrys = zf.getEntries();
            while (zipArchiveEntrys.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                log.info("item image file name is {" + zipArchiveEntry.getName() + "}");
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(zip2FileName + zipArchiveEntry.getName() + File.separator));
                } else {
                    fileOutputStream = FileUtils.openOutputStream(new File(zip2FileName + zipArchiveEntry.getName()));
                    inputStream = zf.getInputStream(zipArchiveEntry);
                    IOUtils.copy(inputStream, fileOutputStream);
                    fileOutputStream.close();
                }
            }
        } catch (Exception e) {
            throw new IOException("找不到文件：" + zipFileName);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (zf != null) {
                ZipFile.closeQuietly(zf);
            }
        }
    }

    /**
     * @param path      要压缩的目录或文件
     * @param baseindex 去掉压缩根目录以上的路径串，一面ZIP文件中含有压缩根目录父目录的层次结构
     * @param out       输出到指定的路径
     * @throws IOException
     */
    public static void zip(String path, int baseindex, ZipOutputStream out)
            throws IOException {
        // 要压缩的目录或文件
        File file = new File(path);
        File[] files;
        if (file.isDirectory()) {// 若是目录，则列出所有的子目录和文件
            files = file.listFiles();
        } else {// 若为文件，则files数组只含有一个文件
            files = new File[1];
            files[0] = file;
        }

        for (File f : files) {
            if (f.isDirectory()) {
                // 去掉压缩根目录以上的路径串，一面ZIP文件中含有压缩根目录父目录的层次结构
                String pathname = f.getPath().substring(baseindex + 1);
                // 空目录也要新建哟个项
                out.putNextEntry(new ZipEntry(pathname + "/"));
                // 递归
                zip(f.getPath(), baseindex, out);
            } else {
                // 去掉压缩根目录以上的路径串，一面ZIP文件中含有压缩根目录父目录的层次结构
                String pathname = f.getPath().substring(baseindex + 1);
                // 新建项为一个文件
                out.putNextEntry(new ZipEntry(pathname));
                // 读文件
                BufferedInputStream in = new BufferedInputStream(
                        new FileInputStream(f));
                int c;
                while ((c = in.read()) != -1)
                    out.write(c);
                in.close();
            }
        }
    }

    /**
     * 删除当前文件夹中的所有文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        //File file =new File(filePath);
        File[] files = null;
        if (file.isDirectory()) {
            files = file.listFiles();
        }
        for (File tempFile : files) {
            if (tempFile.isDirectory()) {
                deleteFile(tempFile);
            }
            tempFile.delete();
        }
    }

    /**
     * 这个方法删除文件或者文件夹
     */
    public final static void del(File file) {
        if (!file.exists()) {
            return;
        }
        // 如果是文件就直接删除.
        if (file.isFile()) {
            file.delete();
            return;
        }
        // 如果是文件夹就继续向下执行代码
        File[] list = null;
        File currentDir = file;
        while (currentDir.isDirectory()) {
            // 取得子文件或者子文件夹
            list = currentDir.listFiles();
            // 如果当前文件夹有子文件或者子文件夹
            if (null != list && list.length > 0) {
                // 遍历每一个子节点
                for (File tmp : list) {
                    // 如果子节点是文件，直接删除
                    // 如果子节点是文件夹，把currentDir赋值为子节点
                    if (tmp.isFile()) {
                        tmp.delete();
                    } else {
                        currentDir = tmp;
                        break;
                    }
                }
            }
            // 如果 'currentDir' 引用指向用户输入的'file'变量，并且文件夹
            // 是空的，删除文件夹并且终止循环
            // delete the directory and stop the loop.
            else if (currentDir.equals(file)) {
                // 删除空文件夹
                currentDir.delete();
                // 终止循环
                break;
            }
            // 如果 'currentDir' 引用指向空文件夹并且这个空文件夹不是用户输入的文件夹
            else {
                // 保存父文件夹.
                File tmpDir = currentDir.getParentFile();
                // 删除空文件夹.
                currentDir.delete();
                // 使 'currentDir' 引用指向父文件夹.
                currentDir = tmpDir;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        CompressUtil.zip("/Users/zhouying/Documents/workspace/store/appdata/zzb-app.zip"
                , "/Users/zhouying/Documents/workspace/store/appdata/9a4428a4750641dc943d7dc026d9af3c/"
                , null);
    }


}
