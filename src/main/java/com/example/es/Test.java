package com.example.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class Test {
	public static void main(String[] args) {
		// 创建对象，设置集群名称和IP地址
//		ElasticsearchUtils es = new ElasticsearchUtils("elasticsearch", "10.200.10.207",9200);
		ElasticsearchUtils es = new ElasticsearchUtils();
		String indexName = "school";// 索引名称
		String typeName = "student";// 类型名称
		String id = "1";
		String jsonData = "{" + "\"name\":\"中华人民共和国\"," + "\"birth\":\"1995-01-30\"," + "\"email\":\"kimchy@163.com\"" + "}";// json数据
		// 1.创建索引(ID可自定义也可以自动创建，此处使用自定义ID)
		es.createIndex(indexName, typeName, id, jsonData);

		// 2.执行查询
		// (1)创建查询条件
		QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "中华人民共和国");// 搜索name为kimchy的数据
		// (2)执行查询
		SearchResponse searchResponse = es.searcher(indexName, typeName,
				queryBuilder);
		// (3)解析结果
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit searchHit : searchHits) {
			String name = (String) searchHit.getSource().get("name");
			String birth = (String) searchHit.getSource().get("birth");
			String email = (String) searchHit.getSource().get("email");
			System.out.println(name);
			System.out.println(birth);
			System.out.println(email);
		}

//		// 3.更新数据
//		jsonData = "{" + "\"name\":\"jack\"," + "\"birth\":\"1996-01-30\"," + "\"email\":\"jack@163.com\"" + "}";// json数据
//		es.updateIndex(indexName, typeName, id, jsonData);
//
//		// 4.删除数据
//		es.deleteIndex(indexName, typeName, id);
	}
}
