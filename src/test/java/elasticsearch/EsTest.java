package elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.cqtalk.CqTalkApplication;
import com.cqtalk.dao.PostMapper;
//import com.cqtalk.dao.elasticsearch.PostRepository;
import com.cqtalk.dao.elasticsearch.PostRepository;
import com.cqtalk.entity.post.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CqTalkApplication.class)
public class EsTest {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ElasticsearchClient esClient;

    @Test
    public void addPost() throws IOException {

    }

    @Test
    public void findById() throws IOException {
        /*Post post = new Post();
        post.setId(1002L);
        post.setTitle("redis实战文章张创琦");
        post.setBriefIntro("zcqzcq张创琦张创琦张创琦张创琦张创琦zcq");
        post.setContent("这篇文章是张创琦写的");*/
        // postRepository.save(post);
        /*IndexResponse indexResponse = esClient.index(i -> i
                .index("post")
                .id("10000002")
                .document(post)
        );*/
        /*System.out.println(esClient.create(
                req -> req.index("post")
                        .id("1002")
                        .document(post)
        ).result());*/
        System.out.println(esClient.search(req -> {
                    req.query(
                            q -> q.match(
                                    m -> m.field("title").query("张创琦")
                            )
                    );
                    return req;
                }, Post.class
        ).hits());


        /*SearchResponse<Post> search = esClient.search(s -> s
                .index("post")
                //查询name字段包含hello的document(不使用分词器精确查找)
                .query(q -> q
                        .term(t -> t
                                .field("title")
                                .value(v -> v.stringValue("张创琦"))
                        ))
                //分页查询，从第0页开始查询3个document
                .from(0)
                .size(3)
                //按age降序排序
                *//*.sort(f->f.field(o->o.field("age").order(SortOrder.Desc)))*//*, Post.class
        );
        for (Hit<Post> hit : search.hits().hits()) {
            System.out.println("result: " + hit.source());
        }*/
    }

}

