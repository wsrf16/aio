package com.aio.portable.park;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 可以使用@SpringBootTest的webEnvironment属性来进一步优化测试的运行方式：
 * ·MOCK：加载一个WebApplicationContext并提供一个模拟servlet环境。嵌入式servlet容器在使用此注释时不会启动。如果servlet API不在你的类路径上，这个模式将透明地回退到创建一个常规的非web应用程序上下文。可以与@AutoConfigureMockMvc结合使用，用于基于MockMvc的应用程序测试。
 * ·RANDOM_PORT：加载一个EmbeddedWebApplicationContext并提供一个真正的servlet环境。嵌入式servlet容器启动并在随机端口上侦听。
 * ·DEFINED_PORT：加载一个EmbeddedWebApplicationContext并提供一个真正的servlet环境。嵌入式servlet容器启动并监听定义的端口（即从application.properties或默认端口8080）。
 * ·NONE：使用SpringApplication加载ApplicationContext，但不提供任何servlet环境（模拟或其他）。
 * 注意：
 * 1、如果你的测试是@Transactional，默认情况下它会在每个测试方法结束时回滚事务。 但是，由于使用RANDOM_PORT或DEFINED_PORT这种安排隐式地提供了一个真正的servlet环境，所以HTTP客户端和服务器将在不同的线程中运行，从而分离事务。 在这种情况下，在服务器上启动的任何事务都不会回滚。
 * 2、除了@SpringBootTest之外，还提供了许多其他注释来测试应用程序的更具体的切片。
 * 3、不要忘记还要在测试中添加@RunWith（SpringRunner.class），否则注释将被忽略。
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {ParkApplication.class})
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@ComponentScan("com.aio.portable")
public class ParkApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() throws Exception {
    }
}
