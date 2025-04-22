package com.scihei.buyweb.until;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class SciHei {
    private static final Date date;
    private static final SimpleDateFormat formatter;
    private static final UUID randomUUID;
    private static final ObjectMapper mapper;
    private static final Random random;
    static {
        date = new Date();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        randomUUID = UUID.randomUUID();
        mapper = new ObjectMapper();
        random = new Random();
    }

    private SciHei(){}
    //获取当前系统时间，格式yyyy-MM-dd
    public static String getTime(){
        return formatter.format(date);
    }

    //随机取名
    public static String getRandomName(){
        return randomUUID.toString();
    }

    //BCrypt加密
    public static String encodePassword(String password){
        return "{bcrypt}" + User.withUsername("123").password(password).passwordEncoder(password1 -> BCrypt.hashpw(password1, BCrypt.gensalt())).build().getPassword();
    }

    //获得sql模糊查询字符串
    public static String getFuzzy(String name){
        StringBuilder fuzzyName = new StringBuilder();
        for(int i = 0; i < name.length(); i ++ ){
            fuzzyName.append('%');
            fuzzyName.append(name.charAt(i));
        }
        fuzzyName.append('%');
        return fuzzyName.toString();
    }

    //如果输入data为空则返回null
    //如果在反射实例化以及copy过程中出现了异常则返回null
    public static <T> Object getByCopy(T data, Class<?> returnClass) {
        if(data == null)
            return null;
        try{
            Object returnEntity = returnClass.getDeclaredConstructor().newInstance();
            //属性映射
            BeanUtils.copyProperties(data, returnEntity);
            return returnEntity;
        }catch (BeansException e){
            log.warn(returnClass.getName() + "在copy" + data.getClass().getName() + "时出现了异常:" + e.toString());
            return null;
        } catch (InvocationTargetException e) {
            log.warn("调用" + returnClass.getName() + "时出现了异常:" + e.toString());
            return null;
        } catch (InstantiationException e) {
            log.warn("实例化" + returnClass.getName() + "时出现了异常:" + e.toString());
            return null;
        } catch (IllegalAccessException e) {
            log.warn(returnClass.getName() + "反射非法访问:" + e.toString());
            return null;
        } catch (NoSuchMethodException e) {
            log.warn(returnClass.getName() + "没有空构造函数");
            return null;
        }
    }

    //data为空则返回null
    public static <T> List<Object> getListByCopy(List<T> data, Class<?> returnClass){
        if(data == null)
            return null;
        List<Object> objectList = new ArrayList<>();
        for(T i : data)
            objectList.add(getByCopy(i, returnClass));
        return objectList;
    }

    public static long getRandom(int l, int r){
        return random.nextInt(r - l + 1) + l;
    }
}
