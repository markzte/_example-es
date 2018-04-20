package com.example.es.temp;

/**
 * Created by shilei on 2016/10/5.
 */
public interface IDocumentDao {
    //indices 名必须小写
    public static final String INDICES = "indexdb";
    // type 名必须小写
    public static final String TYPE = "docs";


    boolean insert(Document doc);

    boolean replace(Document doc);

    boolean update(Document doc);


    boolean delete(long id);

    Document searchById(long id);
}
