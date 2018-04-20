package com.example.es.temp;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by shilei on 2016/10/5.
 */
public class DocumentTcpDaoImpl implements IDocumentDao {

    //TCP连接客户端：
    private TransportClient client = null;

    public DocumentTcpDaoImpl(TransportClient client) {
        this.client = client;
    }

    @Override
    public boolean insert(Document doc) {
        String json = GsonHolder.gson.toJson(doc);
        IndexResponse response = client.prepareIndex(INDICES, TYPE, String.valueOf(doc.getId())).setSource(json).get();
        return response.isCreated();
    }

    @Override
    public boolean replace(Document doc) {
        return update(doc);
    }

    @Override
    public boolean update(Document doc) {
        String json = GsonHolder.gson.toJson(doc);

        UpdateResponse response = client.prepareUpdate(INDICES, TYPE, String.valueOf(doc.getId()))
                .setDoc(json)
                .get();

        return !response.isCreated();
    }


    @Override
    public boolean delete(long id) {
        DeleteResponse response = client.prepareDelete(INDICES, TYPE, String.valueOf(id)).get();
        return response.isFound();
    }

    @Override
    public Document searchById(long id) {
        GetResponse response = client.prepareGet(INDICES, TYPE, String.valueOf(id)).get();
        if (response.isExists()) {
            String json = response.getSourceAsString();
            return GsonHolder.gson.fromJson(json, Document.class);
        }
        return null;
    }
}
