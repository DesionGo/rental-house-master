package com.rentalHouseClient.rhc.common.util;

/**
 * TODO
 *
 * @author czf
 * @date 2021/2/14 14:51
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 选择地区工具，包含全国各地省级市级
 * @author LiuJinan
 *
 */
public class LocalUtil {
    //各地区xml文件路径
    private static final String LOCAL_LIST_PATH = "../rental-house-master/rental-house-client/src/main/java/com/rentalHouseClient/rh/common/LocList.xml";
    //所有国家名称List
    private static final List<String> COUNTRY_REGION = new ArrayList<String>();
    private static LocalUtil localutil;
    private SAXReader reader;
    private Document document;
    private Element rootElement;		//根元素

    //初始化
    private LocalUtil(){
        //1.读取
        reader = new SAXReader();
        try {
            document = reader.read(LOCAL_LIST_PATH);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //2.获得根元素
        rootElement =  document.getRootElement();
        //3.初始化所有国家名称列表
        Iterator it =  rootElement.elementIterator();
        Element ele = null;
        while(it.hasNext()){
            ele = (Element)it.next();
            COUNTRY_REGION.add(ele.attributeValue("Name"));
        }
    }

    /**
     *
     * @author		czf
     * @TODO
     * @return		List<String>
     */
    public List<String> getCountry(){
        return COUNTRY_REGION;
    }

    /**
     *
     * @author		czf
     * @TODO		功能：	根据国家名获取该国所有省份

     * @param countryName	国家名，从getCountry()从取出
     * @return		List<Element>
     */
    private List<Element> provinces(String countryName){
        Iterator it =  rootElement.elementIterator();
        List<Element> provinces = new ArrayList<Element>();
        Element ele = null;
        while(it.hasNext()){
            ele = (Element)it.next();
            COUNTRY_REGION.add(ele.attributeValue("Name"));
            if(ele.attributeValue("Name").equals(countryName)){
                provinces = ele.elements();
                break;
            }
        }
        return provinces;
    }

    /**
     *
     * @author		czf
     * @TODO		功能：	根据国家名获取该国所有省份
     * @param countryName	国家名，从getCountry()从取出
     * @return		List<String>
     */
    public List<String> getProvinces(String countryName){
        List<Element> tmp = this.provinces(countryName);
        List<String> list = new ArrayList<String>();
        for(int i=0; i<tmp.size(); i++){
            list.add(tmp.get(i).attributeValue("Name"));
        }
        return list;
    }

    /**
     *
     * @author		czf
     * @TODO		功能：根据国家名和省份名，获取该省城市名列表
     * @param provinceName,countryName
     * @return
     */
    private List<Element> cities(String countryName, String provinceName){
        List<Element> provinces =  this.provinces(countryName);
        List<Element> cities = new ArrayList<Element>();
        if(provinces==null || provinces.size()==0){		//没有这个城市
            return cities;
        }

        for(int i=0; i<provinces.size(); i++){
            if(provinces.get(i).attributeValue("Name").equals(provinceName)){
                cities = provinces.get(i).elements();
                break;
            }
        }
        return cities;
    }

    /**
     *
     * @author		czf
     * @TODO		功能：根据国家名和省份名获取城市列表
     * @param countryName
     * @param provinceName
     * @return		List<String>
     */
    public List<String> getCities(String countryName, String provinceName){
        List<Element> tmp =  this.cities(countryName, provinceName);
        List<String> cities = new ArrayList<String>();
        for(int i=0; i<tmp.size(); i++){
            cities.add(tmp.get(i).attributeValue("Name"));
        }
        return cities;
    }
    /**
     *
     * @author		czf
     * @TODO		功能：根据国家名和省份名以及市，获取该市下的县区列表
     * @param provinceName,countiesName,countryName
     * @return
     */
    private List<Element> counties(String countryName, String provinceName,String countiesName){
        List<Element> provinces =  this.provinces(countryName);
        List<Element> cities = new ArrayList<Element>();
        List<Element> counties = new ArrayList<Element>();
        if(provinces==null || provinces.size()==0){		//没有这个城市
            return cities;
        }

        for(int i=0; i<provinces.size(); i++){
            if(provinces.get(i).attributeValue("Name").equals(provinceName)){
                cities = provinces.get(i).elements();
                break;
            }
        }
        for(int i=0;i<cities.size();i++){
            if(cities.get(i).attributeValue("Name").equals(countiesName)){
                counties=cities.get(i).elements();
                break;
            }
        }
        return counties;
    }

    /**
     *
     * @author		czf
     * @TODO		功能：根据国家名和省份名以及市，获取该市下的县区列表
     * @param provinceName,countiesName,countryName
     * @return
     */
    public List<String> getCounties(String countryName, String provinceName,String countiesName){
        List<Element> tmp =  this.counties(countryName, provinceName,countiesName);
        List<String> counties = new ArrayList<String>();
        for(int i=0; i<tmp.size(); i++){
            counties.add(tmp.get(i).attributeValue("Name"));
        }
        return counties;
    }
    public static LocalUtil getInstance(){
        if(localutil==null){
            localutil = new LocalUtil();
        }
        return localutil;
    }
}