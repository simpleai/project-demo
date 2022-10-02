package com.xiaoruiit.project.demo.learn.file;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.xiaoruiit.project.demo.utils.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sm.ms图片迁移到oss
 */
@Slf4j
public class SmmsToOss {

    // public static String OLD_MD_FILE_PATH = "d:\\Users\\86182\\Desktop\\oldMd";
    public static String OLD_MD_FILE_PATH = "D:\\knowledge";
    public static String NEW_MD_FILE_PATH = "";
    public static String TEMP_PICTURE_FILE_PATH = "d:\\Users\\86182\\Desktop\\tempPicture";
    public static String NOT_SMMS_PICTURE_PATH = "d:\\Users\\86182\\Desktop\\notSmmsPicture.txt";


    public static void main(String[] args) {
        int level = 1;
        List<String> mdPaths = new ArrayList<>();
        List<String> mdPathList = getMdPathFromMd(new File(OLD_MD_FILE_PATH), level, mdPaths);
        log.info("mdPathList:" + JSON.toJSONString(mdPathList));

        Map<String, Set<String>> mdPicturePathMap = getPicturePathFromMdList(mdPathList);
        log.info("mdPicturePathMap:" + JSON.toJSONString(mdPicturePathMap));

        downloadPicture(mdPicturePathMap);
    }

    /**
     * 从文件夹获取所有的md文件路径
     * @return
     */
    @SneakyThrows
    public static List<String> getMdPathFromMd(File f, int level, List<String> mdPaths){
        File[] childs = f.listFiles();// 返回一个抽象路径名数组，这些路径名表示此抽象路径名所表示目录中地文件
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].isDirectory()) { //
                getMdPathFromMd(childs[i], level + 1, mdPaths);
            } else {
                String filename = childs[i].getName();// 文件名
                String fileSuffix = filename.substring(filename.lastIndexOf(".") + 1);
                if ("md".equals(fileSuffix)) {
                    String path = childs[i].getPath();
                    mdPaths.add(path);
                }
            }

        }
        return mdPaths;
    }

    /**
     * 从md文件中获取sm.ms图片地址
     * @return
     */
    @SneakyThrows
    public static Map<String,Set<String>> getPicturePathFromMdList(List<String> mdPaths){
        Map<String, Set<String>> mdPictures = new HashMap<>();
        for (String mdPath : mdPaths) {
            Set<String> pictures = new HashSet<>();

            File file = new File(mdPath);
            FileReader fr = new FileReader(file);
            char[] chs = new char[1024];
            int len;
            String str = "";
            while ((len = fr.read(chs)) != -1) {
                str += new String(chs, 0, len);
            }

            String regex = "!\\[(.*?)\\]\\((.*?)\\)";
            Pattern patten = Pattern.compile(regex);//编译正则表达式
            Matcher matcher = patten.matcher(str);// 指定要匹配的字符串
            List<String> matchStrs = new ArrayList();

            while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
                matchStrs.add(matcher.group());//获取当前匹配的值
            }
            // 获取当前md文件中的所有图片链接
            matchStrs.stream().map(v -> v.replaceAll(regex, "$2")).forEach(v -> pictures.add(v));
            mdPictures.put(mdPath,pictures);
        }
        return mdPictures;
    }
    /**
     * 下载sm.ms图片到本地临时文件夹
     * @return
     */
    public static void downloadPicture(Map<String, Set<String>> mdPicturePathMap){
        // 每次下载休眠1秒
        // 校验
        downloadPictureCheck(mdPicturePathMap);
        log.info("校验后：mdPicturePathMap:" + JSON.toJSONString(mdPicturePathMap));
        // 下载 todo

    }

    /**
     * 下载校验，非sm.ms图片不下载（C盘图片删除不下载，github图片不下载，网络图片不下载）
     * @return
     */
    public static void downloadPictureCheck(Map<String, Set<String>> mdPicturePathMap){
        Map<String, Set<String>> notSmmsMdPicturePathMap = new HashMap<>();
        // 每次下载休眠1秒
        // 校验
        for (String mdPath : mdPicturePathMap.keySet()) {
            Set<String> picturePaths = mdPicturePathMap.get(mdPath);

            if (picturePaths == null || picturePaths.size() == 0) {
                continue;
            }

            Set<String> notSmmsPicturePaths = new HashSet<>();

            for (int i = 0; i < picturePaths.size(); i++) {
                Iterator<String> iterator = picturePaths.iterator();
                while (iterator.hasNext()) {
                    String picturePath = iterator.next();
                    if (!picturePath.startsWith("https://i.loli.net")){
                        log.warn("非sm.ms图片：mdPath:{},picturePath:{}",JSON.toJSONString(mdPath),JSON.toJSONString(picturePath));
                        // 收集非smms图片
                        notSmmsPicturePaths.add(picturePath);
                        // 下载图片集合中删除此图片路径
                        iterator.remove();
                    }
                }
            }

            if (notSmmsPicturePaths.size() > 0){
                notSmmsMdPicturePathMap.put(mdPath, notSmmsPicturePaths);
            }
        }
        writeTxt(notSmmsMdPicturePathMap);
    }

    /**
     * 非smms图片写到txt文件中，其中C盘图片单独处理
     * @param notSmmsMdPicturePathMap
     */
    @SneakyThrows
    public static void writeTxt(Map<String, Set<String>> notSmmsMdPicturePathMap){
        log.info("非smms图片,notSmmsMdPicturePathMap:{}",JSON.toJSONString(notSmmsMdPicturePathMap));
        String word = JSON.toJSONString(notSmmsMdPicturePathMap);

        FileOutputStream fileOutputStream = null;
        File file = new File(SmmsToOss.NOT_SMMS_PICTURE_PATH);
        if(!file.exists()){
            file.createNewFile();
        }
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(word.getBytes("gbk"));
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 上传图片到OSS
     * @return
     */

    public static void upLoadOss(String pictureName){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "";
        String accessKeySecret = "";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "xiaoruiit";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "img/" + pictureName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 1.获取网络连接。2.存到本地。3.本地读取为InputStream.
        try {
            // 本地图片路径。
            String picturePath = SmmsToOss.TEMP_PICTURE_FILE_PATH + "\\" + pictureName;

            File file = new File(picturePath);
            try {
                FileInputStream inputStream = new FileInputStream(file);
                // 创建PutObjectRequest对象。
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);

                // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
                // ObjectMetadata metadata = new ObjectMetadata();
                // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
                // metadata.setObjectAcl(CannedAccessControlList.Private);
                // putObjectRequest.setMetadata(metadata);

                // 上传。
                ossClient.putObject(putObjectRequest);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 新写md文件，将sm图片地址替换为oss图片地址
     * @return
     */


}
