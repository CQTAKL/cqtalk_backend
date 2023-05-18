package fdfs;

import com.cqtalk.util.file.FileDfsUtil;
import com.github.tobato.fastdfs.service.TrackerClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;

@SpringBootTest
public class fdfsTest {
    @Autowired
    private FileDfsUtil fileDfsUtil;

}
