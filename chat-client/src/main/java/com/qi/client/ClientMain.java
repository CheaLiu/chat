package com.qi.client;

import com.qi.chat.common.message.Message;
import com.qi.chat.common.message.TextMessage;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * Creator  liuqi
 * Data     2018/4/18
 * Class    com.qi.client.ClientMain
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        ClientManager manager = new ClientManager(8888);
        manager.connect("127.0.0.1", 6666);
        manager.sendMsg("hahha");
        Observable<Message> observable = manager.receive()
                .subscribeOn(Schedulers.io());
        observable.subscribe(message -> {
            System.out.print(new String(((TextMessage) message).getBody()));
        }, throwable -> {
        }, () -> {
        });
        System.out.println(manager.getLocalHostName() + ":" + manager.getLocalPort());
        Thread.sleep(3000 * 1000);
    }
}
