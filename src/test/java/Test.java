import com.google.protobuf.InvalidProtocolBufferException;
import com.mgame.ServerApplication;
import com.mgame.dao.entity.ItemEntity;
import com.mgame.dao.entity.UserInfoEntity;
import com.mgame.dao.mapper.UserMapper;
import com.mgame.service.impl.ItemService;
import com.mgame.service.impl.PlayerService;
import com.mgame.service.impl.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class Test {
    Logger log = LogManager.getLogger(Test.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemService itemService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private Sid sid;
    @Autowired
    private UserService userService;
    @org.junit.Test
    public void test1() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(sid.nextShort());
        userInfoEntity.setNickName("mgame");
        userInfoEntity.setAccount("123456798");
        userInfoEntity.setPassword("123456");

        int num = userMapper.saveOne(userInfoEntity);
        System.out.println(num);

        List<UserInfoEntity> list = userMapper.queryAllUsers();
        for (UserInfoEntity l : list) {
            System.out.println(l.getId());
        }
    }

    @org.junit.Test
    public void testProto() throws InvalidProtocolBufferException {

    }


    @org.junit.Test
    public void testUpdate() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId("100001");
        userInfoEntity.setNickName("mgame002");
        userMapper.updateOne(userInfoEntity);
    }

    @org.junit.Test
    public void testItemAndPlayer() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setPlayerId(100001);
        itemEntity.setAttribute("dsds");
        itemEntity.setItemId(1);
        itemEntity.setNum(1);
        itemService.insert(itemEntity);
//        itemService.deleteItems(100001L, 1);
        itemService.findItemsByItemId(1);
    }

    @org.junit.Test
    public void testUserService() {
        boolean is = userService.verifyAccountAndPassword("mgamezuishuai","123456");
        System.out.println(is);
        log.info("testtestUserService");
    }
}
