package io.github.qifan777.knowledge.ai.wechat;

import com.alibaba.fastjson2.JSONObject;
import io.github.qifan777.knowledge.ai.wechat.dto.MsgDto;
import io.github.qifan777.knowledge.ai.wechat.msg.MsgConvertService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ChatListenerService {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private MsgConvertService msgConvertService;
    public void listen(JSONObject jsonObject) {
        ChatClient chatClient = ChatClient.create(chatModel);
        MsgDto<String> convert = msgConvertService.convert(jsonObject);
        if(convert!=null){
//            String content = chatClient.prompt()
//                    // 输入单条提示词
//                    .user(convert.getContent())
//                    // call代表非流式问答，返回的结果可以是ChatResponse，也可以是Entity（转成java类型），也可以是字符串直接提取回答结果。
//                    .call()
//                    .content();
//            // 发送消息
//            msgConvertService.sendMsg(content, convert);
            new Thread(() -> {
                ChatClient.StreamResponseSpec stream = chatClient.prompt()
                        // 输入单条提示词
                        .user(convert.getContent())
                        // call代表非流式问答，返回的结果可以是ChatResponse，也可以是Entity（转成java类型），也可以是字符串直接提取回答结果。
                        .stream();
                Flux<String> content = stream.content();
                StringBuilder sb = new StringBuilder();
                Flux<List<String>> buffer = content.buffer();
                buffer.toStream().forEach(list -> {
                    for (String s : list) {
                        sb.append(s);
                    }
                });

                // 发送消息
                msgConvertService.sendMsg(sb.toString(), convert);
            }).start();

        }




    }
}
