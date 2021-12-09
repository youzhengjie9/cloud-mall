package com.boot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.UUID;

/**
 * @author 游政杰
 */
@Component
public final class FileUtil {

    private static RedisTemplate redisTemplate;

    @Autowired
    public  void setRedisTemplate(RedisTemplate redisTemplate) {
        FileUtil.redisTemplate = redisTemplate;
    }

    /**
     * 文件工具类
     *
     * @return
     */

    //获取文件后缀名并自动转换成‘’小写‘’
    public static final String getFileSuffix(String originalFilename){

        //获取头像后缀名 ,采用倒序遍历，遇到“.”就break ，保存后缀名
        StringBuilder stringBuilder=new StringBuilder();
        for (int x=originalFilename.length()-1;x>=0;x--){
            if(originalFilename.charAt(x)=='.'){
                break;
            }else {
                stringBuilder.append(originalFilename.charAt(x));
            }
        }
        //因为上面获取的后缀名是倒序的，所以我们要反转字符串
        String post="";
        String s = stringBuilder.toString();
        for (int i = s.length()-1; i >=0 ; i--) {
            post+=s.charAt(i);
        }
        //后缀名变成小写
        String post_lower = post.toLowerCase();


        return post_lower;

    }

    //生成随机名
    public static final String getRandomName(){
        String s = UUID.randomUUID().toString();
        String s1 = s.replaceAll("-", "");
        return s1;
    }

    //获取静态路径
    public static final String getStaticPath() throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:static").getPath();
        String substring = path.substring(1, path.length());
        return substring;
    }

    //图片存储在gateway模块的target中
    //获取gateway的静态路径
    public static final String getStaticPathByRedis(){

        String staticPath = (String) redisTemplate.opsForValue().get("staticPath");

        return staticPath;
    }

    //商品图片存储*****

    //获取图片文件夹路径
    public static final String getBigImgPath() throws FileNotFoundException {
        String staticPath = getStaticPathByRedis();
        staticPath+="/static/img/nav/";
        return staticPath;
    }

    public static final void write(String path,byte[] fileByteArray) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        bufferedOutputStream.write(fileByteArray);


        bufferedOutputStream.flush(); //刷新缓冲区
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    public static final String writeImage(String originalFilename, byte[] fileByteArray) throws IOException {

        String staticPath = FileUtil.getStaticPathByRedis();

        String usericonPath_datebase="/static/img/nav/"; //存入数据库的地址
        String randomName = FileUtil.getRandomName();
        String fileSuffix = FileUtil.getFileSuffix(originalFilename); //获取文件后缀名
        String fileName=randomName+"."+fileSuffix;
        usericonPath_datebase+=fileName;


        staticPath+=usericonPath_datebase;

        //写文件
        write(staticPath,fileByteArray);

        return usericonPath_datebase; //返回需要存储到数据库的图片地址
    }

    //用户头像存储**************

    //获取头像文件夹路径
    public static final String getIconPath() throws FileNotFoundException {
        String staticPath = getStaticPathByRedis();
        staticPath+="/static/img/user-icon/";
        return staticPath;
    }


    public static final String writeIcon(String originalFilename, byte[] fileByteArray) throws IOException {
        FileOutputStream fileOutputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        String usericonPath_datebase="/static/img/user-icon/"; //存入数据库的地址
        try{
            String staticPath = FileUtil.getStaticPathByRedis();


            String randomName = FileUtil.getRandomName();
            String fileSuffix = FileUtil.getFileSuffix(originalFilename); //获取文件后缀名
            String fileName=randomName+"."+fileSuffix;
            usericonPath_datebase+=fileName;

            staticPath+=usericonPath_datebase;

            //写头像
            fileOutputStream = new FileOutputStream(new File(staticPath));
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            bufferedOutputStream.write(fileByteArray);


            bufferedOutputStream.flush(); //刷新缓冲区

        }catch (Exception e)
        {
            e.printStackTrace();

        }finally{
            if(bufferedOutputStream!=null){
                bufferedOutputStream.close();
            }
            if(fileOutputStream!=null)
            {
                fileOutputStream.close();
            }
        }

        return usericonPath_datebase;
    }


    //写轮播图
    public static final String writeSlideShow(String originalFilename, byte[] fileByteArray) throws IOException {
        FileOutputStream fileOutputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        String usericonPath_datebase="/static/img/"; //存入数据库的地址
        try{
            String staticPath = FileUtil.getStaticPathByRedis();


            String randomName = FileUtil.getRandomName();
            String fileSuffix = FileUtil.getFileSuffix(originalFilename); //获取文件后缀名
            String fileName=randomName+"."+fileSuffix;
            usericonPath_datebase+=fileName;

            staticPath+=usericonPath_datebase;

            //写轮播图
            fileOutputStream = new FileOutputStream(new File(staticPath));
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            bufferedOutputStream.write(fileByteArray);


            bufferedOutputStream.flush(); //刷新缓冲区

        }catch (Exception e)
        {
            e.printStackTrace();

        }finally{
            if(bufferedOutputStream!=null){
                bufferedOutputStream.close();
            }
            if(fileOutputStream!=null)
            {
                fileOutputStream.close();
            }
        }

        return usericonPath_datebase;
    }






}
